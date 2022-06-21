package com.root.meter.api;

import com.root.meter.DTO.UserDTO;
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
    public ResponseEntity<Users> save(@Valid @RequestBody UserDTO userDTO){
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
        //save user
        Users savedUser = userService.save(userDTO);      //user not saved -> server error
        if(savedUser == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            return new ResponseEntity<Users>(savedUser,HttpStatus.CREATED);
        }
    }
    @GetMapping("/find/ById")
    public ResponseEntity<Users> get(@RequestParam Long id){
        Users savedUser = userService.findById(id);
        return new ResponseEntity<Users>(savedUser,HttpStatus.OK);
    }
    @GetMapping("/find/ByName")
    public ResponseEntity<Users> get(@RequestParam String name){
        Users savedUser = userService.findByName(name);
        return new ResponseEntity<Users>(savedUser,HttpStatus.OK);
    }
}
