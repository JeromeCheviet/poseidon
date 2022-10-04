package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.User;

public interface UserService {
    User getUserByUsername(String username);
}
