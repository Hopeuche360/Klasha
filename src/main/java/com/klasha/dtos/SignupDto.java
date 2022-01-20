package com.klasha.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupDto {
    private String name;
    private String email;
    private String password;
}
