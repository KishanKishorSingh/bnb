package com.airbnb.controller;


import com.airbnb.exception.UserExists;
import com.airbnb.payload.AppUserDto;
import com.airbnb.payload.JWTToken;
import com.airbnb.payload.LoginDto;
import com.airbnb.service.interfaceClass.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/app/v1/airbnb")
public class AuthController {
    private AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;

    }



    @PostMapping("/createUser")
    public ResponseEntity<AppUserDto> createUser(@RequestBody AppUserDto appUserDto){
        Optional<AppUserDto> byEmail = authService.findByEmail(appUserDto.getEmail());
        Optional<AppUserDto> byUsername = authService.findByUsername(appUserDto.getUsername());
        if(byEmail.isPresent()){
            throw new UserExists("User Already Exists");
        }
        if(byUsername.isPresent()){
            throw new UserExists("Username Already Taken Try Different Username");
        }

            appUserDto.setRole("ROLE_USER");
            AppUserDto bnbUser = authService.createBnbUser(appUserDto);

            return new ResponseEntity<>(bnbUser, HttpStatus.CREATED);

    }
    @PostMapping("/ownerUser")
    public ResponseEntity<AppUserDto> ownerUser(@RequestBody AppUserDto appUserDto){
        Optional<AppUserDto> byEmail = authService.findByEmail(appUserDto.getEmail());
        Optional<AppUserDto> byUsername = authService.findByUsername(appUserDto.getUsername());
        if(byEmail.isPresent()){
            throw new UserExists("User Already Exists");
        }
        if(byUsername.isPresent()){
            throw new UserExists("Username Already Taken Try Different Username");
        }

        appUserDto.setRole("ROLE_OWNER");
        AppUserDto bnbUser =authService.createBnbUser(appUserDto);
        return new ResponseEntity<>(bnbUser, HttpStatus.CREATED);

    }
    @PostMapping("/propertyManager")
    public ResponseEntity<AppUserDto> propertyUser(@RequestBody AppUserDto appUserDto){
        Optional<AppUserDto> byEmail = authService.findByEmail(appUserDto.getEmail());
        Optional<AppUserDto> byUsername = authService.findByUsername(appUserDto.getUsername());
        if(byEmail.isPresent()){
            throw new UserExists("User Already Exists");
        }
        if(byUsername.isPresent()){
            throw new UserExists("Username Already Taken Try Different Username");
        }


        appUserDto.setRole("ROLE_MANAGER");
        AppUserDto bnbUser = authService.createBnbUser(appUserDto);
        return new ResponseEntity<>(bnbUser, HttpStatus.CREATED);

    }
    @PostMapping("/Login")
    public ResponseEntity<?> loginByUser(@RequestBody LoginDto dto){
        String token = authService.userLogin(dto);
        JWTToken jwtToken=new JWTToken();
        if(token!=null){
            jwtToken.setTokenType("JWT");
            jwtToken.setToken(token);
            return new ResponseEntity<>(jwtToken,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalidate Username/Password",HttpStatus.UNAUTHORIZED);
        }
    }
    @PatchMapping("/Update")
    public ResponseEntity<AppUserDto> updateUser(@RequestParam long id,@RequestBody AppUserDto appUserDto){
        AppUserDto dto = authService.updateUser(id, appUserDto);
        return new ResponseEntity<>(dto,HttpStatus.ACCEPTED);
    }
    @GetMapping("/List")
    public ResponseEntity<List<AppUserDto>> getAllUser(){
        List<AppUserDto> allUser = authService.getAllUser();
        return new ResponseEntity<>(allUser,HttpStatus.OK);
    }
    @DeleteMapping("/Delete")
    @Transactional
    public ResponseEntity<String> deleteUser(@RequestParam long id){
        authService.deleteUser(id);
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }
}
