package com.springbank.user.cmd.api.services;

public interface PasswordEncoder {
    String hashPassword(String password);
}
