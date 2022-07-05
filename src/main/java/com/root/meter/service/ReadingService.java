package com.root.meter.service;

import com.root.meter.DTO.EnergyView;
import com.root.meter.DTO.MeterDTO;
import com.root.meter.DTO.ReadingDTO;
import com.root.meter.enums.Months;
import com.root.meter.model.Meter;
import com.root.meter.model.Reading;
import com.root.meter.model.Users;
import com.root.meter.repo.ReadingRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Transactional
@Service
public class ReadingService {
    @Autowired
    private ReadingRepo readingRepo;
    @Autowired
    private MeterService meterService;
    @Autowired
    private UserService userService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MonthlyConsumptionService monthlyConsumptionService;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private EntityManager entityManager;

    /**
     * calculate the amount(money) from the amount of energy based on the KWH of the state of the user
     * @param readingDTO
     * @return reading id
     */
    public Reading save(ReadingDTO readingDTO) {
        //get meter
        Meter meter = meterService.findById(readingDTO.getMeterId());
        //get the state price of the user of that meter
        Users userByMeterId = userService.findUserByMeterId(readingDTO.getMeterId());
        //TODO:should i check of null for useByMeterID although if not exist the user?
        // service should throw not found exception??
        String userState = userByMeterId.getState();
        //get the state KWH price in cents
        Double statePricePerKWH = jdbcTemplate.queryForObject(
                "select price from states_prices where state = ?",
                Double.class,
                userState
        );
        //calculate the total price in (cents) of this reading
        double energyDebt = readingDTO.getEnergy() + meter.getEnergyDebt();
        double amount = readingDTO.getEnergy() * statePricePerKWH;  //in cents
        //get month
        String oldDate = readingDTO.getDate();
        LocalDateTime localDateTime = convertTimeStamp(oldDate);
                //STPMTimeStampToLocalDateTime(oldDate);
        //update meter debt
        amount += meter.getDebt();  //accumulate the old debt
        meter.setDebt(amount);      //set the updated debt
        meter.setEnergyDebt(energyDebt);
        //create new reading object to be saved
        Reading newReading = new Reading(
                meter,localDateTime, readingDTO.getVolt(),readingDTO.getElectric_current(),energyDebt,amount
        );
        //save monthly
        //monthlyConsumptionService.save(newReading);
        return readingRepo.save(newReading);
    }

    /**
     * convert form 2022-02-26 3:40:32 to 2022-02-26T03:40:32
     * @param oldDate
     * @return
     */
    public LocalDateTime convertTimeStamp(String oldDate) {
        String[] tkns = oldDate.split(" ");
        return LocalDateTime.parse(tkns[0]+"T"+tkns[1]);
    }

    /**
     * get list of readings by meter ud
     * @param meterId
     * @return
     */
    public List<ReadingDTO> getReadingsByMeterId(Long meterId){
        Optional<List<Reading>> optReadingList = readingRepo.findAllByMeterId(meterId);
        if(optReadingList.isPresent()){
            List<ReadingDTO> dailyReadingDTOS = new ArrayList<>();
            List<Reading> dailyReadings = optReadingList.get();
            //TODO: search more on lambda expression
            dailyReadings.forEach(dailyReading -> dailyReadingDTOS.add(readingToDto(dailyReading)));
            return dailyReadingDTOS;
        }
        else return new ArrayList<>();
    }

    /**
     * convert ReadingDTO to Reading
     * @param dto
     * @return
     */
    public Reading dtoToReading(ReadingDTO dto){
        Reading reading = new Reading();
        BeanUtils.copyProperties(dto, reading);
        reading.setMeter(meterService.findById(dto.getMeterId()));
        reading.setDate(STPMTimeStampToLocalDateTime(dto.getDate()));
        return reading;
    }

    /**
     * convert reading to readingDTO
     * @param reading
     * @return
     */
    public ReadingDTO readingToDto (Reading reading){
        ReadingDTO dto = new ReadingDTO();
        BeanUtils.copyProperties(reading, dto);
        //set meterId in readingDto
        dto.setMeterId(reading.getMeter().getId());
        dto.setDate(String.valueOf(reading.getDate()));
        return dto;
    }

    /**
     * get list of reading between 2 time stamps of format 2022-02-26T03:40:32
     * @param start
     * @param end
     * @return
     */
    public List<ReadingDTO> getReadingsBetween2TimeStamps(LocalDateTime start, LocalDateTime end) {
        Optional<List<Reading>> optReadingList = readingRepo.findAllByDateBetween(start, end);
        if(optReadingList.isPresent()){
            List<ReadingDTO> dailyReadingDTOS = new ArrayList<>();
            List<Reading> dailyReadings = optReadingList.get();
            dailyReadings.forEach(dailyReading -> dailyReadingDTOS.add(readingToDto(dailyReading)));
            return dailyReadingDTOS;
        }
        else return new ArrayList<>();
    }

    /**
     * convert date and time from format Thu Feb 26 03:40:32 2022 to 2022-02-26T03:40:32
     * @param oldDate
     * @return
     */
    public LocalDateTime STPMTimeStampToLocalDateTime(String oldDate){
        Months month = Months.valueOf(oldDate.substring(4, 7).toUpperCase());
        //convert mon to int
        Integer mon = month.value();
        //TODO:replace this hardcoded with more flexible/abstract approach
        String date = oldDate.substring(8);
        int dd = Integer.parseInt(date.substring(0,2));
        int hh = Integer.parseInt(date.substring(3,5));
        int mm = Integer.parseInt(date.substring(6,8));
        int ss = Integer.parseInt(date.substring(9,11));
        int yyyy = Integer.parseInt(date.substring(12,16));

        return LocalDateTime.of(yyyy,mon,dd,hh,mm,ss);
    }
    public double getLastReadingByMeterId(Long meterId){
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", meterId);
        return namedParameterJdbcTemplate.queryForObject("SELECT TOP 1 amount FROM reading where meter_id = :id", namedParameters, Double.class);
    }

    public List<EnergyView> getEnergyAndAmountBetween2DatesByMeterId(Long meterId, LocalDateTime start, LocalDateTime end){
        final String GET_LAST_WEEK_CONSUMPTION =
                "select new com.root.meter.DTO.EnergyView(r.date, sum(r.energy), sum(r.amount)) " +
                        "from Reading r " +
                        "where meter_id = :meter_id AND (date between  :start AND :end) group by date";

        TypedQuery<EnergyView> query = entityManager.createQuery(GET_LAST_WEEK_CONSUMPTION, EnergyView.class);
        query.setParameter("meter_id", meterId);
        query.setParameter("start", start);
        query.setParameter("end", end);

        return query.getResultList();
        }

}
