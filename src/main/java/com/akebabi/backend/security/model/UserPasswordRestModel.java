package com.akebabi.backend.security.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordRestModel {
    private String userName;
    private String passwordToken;
}
