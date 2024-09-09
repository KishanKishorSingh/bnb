package com.airbnb.service.interfaceClass;

import com.airbnb.payload.AppUserDto;
import com.airbnb.payload.LoginDto;

import java.util.List;
import java.util.Optional;

public interface AuthService {
    AppUserDto createBnbUser(AppUserDto appUserDto);

    Optional<AppUserDto> findByEmail(String email);

    Optional<AppUserDto> findByUsername(String username);

    String userLogin(LoginDto dto);

    AppUserDto updateUser(long id, AppUserDto appUserDto);

    List<AppUserDto> getAllUser();

    void deleteUser(long id);
}
