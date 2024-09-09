package com.airbnb.service.implementationClass;

import com.airbnb.entity.AppUser;
import com.airbnb.payload.AppUserDto;
import com.airbnb.payload.LoginDto;
import com.airbnb.repository.AppUserRepository;
import com.airbnb.service.interfaceClass.AuthService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private AppUserRepository appUserRepository;
    private JwtService jwtService;

    public AuthServiceImpl(AppUserRepository appUserRepository, JwtService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    @Override
    public AppUserDto createBnbUser(AppUserDto appUserDto) {
        AppUser appUser = mapToEntity(appUserDto);
        String hashpw = BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt(5));
        appUser.setPassword(hashpw);
        AppUser save = appUserRepository.save(appUser);
        AppUserDto dto = mapToDto(save);
        return dto;
    }
    @Override
    public Optional<AppUserDto> findByEmail(String email) {
        return appUserRepository.findByEmail(email)
                .map(this::mapToDto);
    }

    @Override
    public Optional<AppUserDto> findByUsername(String username) {
        return appUserRepository.findByUsername(username)
                .map(this::mapToDto);
    }

    @Override
    public String userLogin(LoginDto dto) {
        Optional<AppUser> byUsername = appUserRepository.findByUsernameOrEmail(dto.getUsername(), dto.getUsername());

        if(byUsername.isPresent()){
            AppUser appUser = byUsername.get();
            if(BCrypt.checkpw(dto.getPassword(),appUser.getPassword())) {
                return jwtService.generateToken(appUser);

            }
        }
        return null;
    }

    @Override
    public AppUserDto updateUser(long id, AppUserDto appUserDto) {
        Optional<AppUser> byId = appUserRepository.findById(id);
        if(byId.isPresent()){
            AppUser appUser = byId.get();
            appUser.setEmail(appUserDto.getEmail());
            AppUser save = appUserRepository.save(appUser);
            AppUserDto dto = mapToDto(save);
            return dto;
        }
        return null;
    }

    @Override
    public List<AppUserDto> getAllUser() {
        List<AppUser> all = appUserRepository.findAll();
        List<AppUserDto> collect = all.stream().map(this::mapToDto).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void deleteUser(long id) {

        appUserRepository.deleteById(id);
    }

    AppUserDto mapToDto(AppUser save) {
        AppUserDto dto=new AppUserDto();
        dto.setId(save.getId());
        dto.setName(save.getName());
        dto.setEmail(save.getEmail());
        dto.setRole(save.getRole());
        dto.setUsername(save.getUsername());
        dto.setPassword(save.getPassword());
        return dto;
    }

    AppUser mapToEntity(AppUserDto appUserDto) {
        AppUser appUser=new AppUser();

        appUser.setName(appUserDto.getName());
        appUser.setEmail(appUserDto.getEmail());
        appUser.setRole(appUserDto.getRole());
        appUser.setUsername(appUserDto.getUsername());
        appUser.setPassword(appUserDto.getPassword());
        return appUser;
    }
}
