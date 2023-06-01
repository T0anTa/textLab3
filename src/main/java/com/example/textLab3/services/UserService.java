package com.example.textLab3.services;


import com.example.textLab3.entity.User;
import com.example.textLab3.repostitory.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class UserService {
    @Autowired
    private IUserRepository userRepository;
    public void save(User user){
        userRepository.save(user);
    }
}
