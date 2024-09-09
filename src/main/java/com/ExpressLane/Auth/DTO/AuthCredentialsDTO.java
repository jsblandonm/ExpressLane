package com.ExpressLane.Auth.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthCredentialsDTO {

    private String email;
    private String password;
}
