package com.root.meter.api;

import com.root.meter.DTO.UserDTO;
import com.root.meter.Exception.ObjectNotFoundException;
import com.root.meter.Requests.UpdateUserRequest;
import com.root.meter.model.Meter;
import com.root.meter.model.Users;
import com.root.meter.service.MeterService;
import com.root.meter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserApi {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private MeterService meterService;
    @PostMapping("/create")
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO userDTO){
        //TODO : add email checker NO DUPs
        //if passwords doesn't match
        if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
          return ResponseEntity.badRequest().build();
        }
        //invalid password structure
        if(userService.checkPassword(userDTO.getPassword())) {
           //return ResponseEntity.badRequest().build();
        }
        //hashing password
        String hashedPass = bCryptPasswordEncoder.encode(userDTO.getPassword());
        //store hashed password in the userDTO
        userDTO.setPassword(hashedPass);
        //convert dto to user
        Users user = userService.dtoToUser(userDTO);
        Users temp = user;
        //CREATE NEW USER
        if(userDTO.getId()==null){
            temp = userService.save(user);
            //assign saved user(got id) to meter
            Meter meter = new Meter(temp);
            meterService.save(meter);
            //assign meter to user
            temp.setMeter(meter);
        }
        Users savedUser = userService.save(temp);
        if(savedUser == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            return new ResponseEntity<UserDTO>(userService.userToDTO(savedUser),HttpStatus.CREATED);
        }
    }
    @PostMapping("/update")
    public ResponseEntity<UserDTO> update(@RequestBody UpdateUserRequest req){
        if(req.getId() == null) throw new ObjectNotFoundException("user id can't be null");
        Users userById = userService.findById(req.getId());
        //update name
        if(req.getName()!=null){
            userById.setName(req.getName());
        }
        //update password
        if(req.getPassword()!=null){
            if(req.getPassword().equals(req.getConfirmPassword())){
                userById.setPassword( bCryptPasswordEncoder.encode(req.getPassword()));
            }
        }
        Users saved = userService.save(userById);
        UserDTO userDTO = userService.userToDTO(saved);
        return new ResponseEntity<UserDTO>(userDTO,HttpStatus.CREATED);
    }
    @GetMapping("/find/ById")
    public ResponseEntity<UserDTO> get(@RequestParam Long id){
        UserDTO savedUser = userService.userToDTO(userService.findById(id));
        return new ResponseEntity<UserDTO>(savedUser,HttpStatus.OK);
    }
    @GetMapping("/find/ByName")
    public ResponseEntity<UserDTO> get(@RequestParam String name){
        UserDTO savedUser = userService.findByName(name);
        return new ResponseEntity<UserDTO>(savedUser,HttpStatus.OK);
    }
}
