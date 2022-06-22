package com.root.meter.service;

import com.root.meter.DTO.UserDTO;
import com.root.meter.Exception.ObjectNotFoundException;
import com.root.meter.model.Meter;
import com.root.meter.model.Users;
import com.root.meter.repo.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Transactional
@Service
public class UserService {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private MeterService meterService;

    /**
     * create user and meter and assign them to each other
     * @param user
     * @return the saved user
     */
    public Users save(Users user){
        return userRepository.save(user);
    }

    /**
     * find user by meter id
     * @param meterId
     * @return the user of that meter id or throw Object Not Found
     */
    public Users findUserByMeterId(Long meterId){
        Optional<Users> optionalUser = userRepository.findByMeterId(meterId);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        else {
            throw new ObjectNotFoundException("no user linked to this meter with id: "+meterId);
        }
    }
    //convert user to userDto
    public UserDTO userToDTO(Users user){
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        if(user.getMeter()!=null)   dto.setMeterId(user.getMeter().getId());
        return dto;
    }
    public Users dtoToUser(UserDTO dto){
        //TODO: dto may be null (check)
        Users user = new Users();
        BeanUtils.copyProperties(dto, user);
        if(dto.getMeterId()!=null)  user.setMeter(meterService.findById(dto.getMeterId()));
        return user;
    }

    public Users findById(Long id) {
        return userRepository.findById(id).get();
    }
    /**
     * password must contains letters, numbers, special character and no space, at least 8 characters
     * @param password
     * @return
     */
    public boolean checkPassword(String password) {
        String regex = "^(?=\\S*\\d)(?=\\S*[a-zA-Z])(?=\\S*[.!@#$%^&*()_+?]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return  matcher.find();
    }
    /*
    public double getUserConsumption(Long userId){
        double amount = 0;
        Optional<Users> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Meter meter = meterService.findById(user.get().getMeter().getId());
            amount = readingService.getLastReadingByMeterId(userId);
            if(amount == 0) return generate_random_amount();
        }
        return amount;
    }*/

    /**
     * get the user consumption till this moment
     * @param userId
     * @return
     */
    /*
    public double getUserConsumption(Long userId){
        double amount = 0;
        Optional<Users> user = userRepository.findById(userId);
        if (user.isPresent()) {
            amount = meterService.getDebtOfMeterByMeterId(user.get().getMeter().getId());
            if(amount == 0) return generate_random_amount();
        }
        return amount;
    }
*/
    /**
     * just for testin
     * as long as we don't plug load, 0 energy consumption -> 0 $
     * so we generate random amount to billing
     */
    public double generate_random_amount(){
        Random r = new Random();
        int low = 50;
        int high = 100;
        return r.nextInt(high-low) + low;
    }

    public double getUserConsumption(Long userId) {
        Optional<Users> user = userRepository.findById(userId);
        if(user.isPresent()){
            Meter meter = user.get().getMeter();
            if(meter!=null){
                return meter.getDebt();
            }
        }
        //TODO: replace this with suitable
        return 0.0;
    }

    public UserDTO findByName(String name) {
       return userToDTO(userRepository.findByName(name));
    }
}
