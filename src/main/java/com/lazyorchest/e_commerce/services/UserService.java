package com.lazyorchest.e_commerce.services;

import com.lazyorchest.e_commerce.models.User;
import com.lazyorchest.e_commerce.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow();
    }
}
