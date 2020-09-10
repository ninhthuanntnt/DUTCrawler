package com.ntnt.dut.crawlerapi.services;

import com.ntnt.dut.crawlerapi.models.entities.UserEntity;
import com.ntnt.dut.crawlerapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    public UserEntity saveUser(UserEntity user){
        return userRepo.saveAndFlush(user);
    }

    public boolean isExistedUser(UserEntity user){
        return userRepo.getIdByUsername(user.getUsername()) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<UserEntity> userOpt = userRepo.findByUsername(s);
        return userOpt.map(userEntity -> (UserDetails) new User(userEntity.getUsername(), userEntity.getPassword(), null))
                        .orElse(null);
    }
}
