package com.myblog.payload;

import lombok.*;
import org.springframework.stereotype.Service;
@Data
public class SignUpDto {

    private String name;

    private String username;

    private String email;

    private String password;
}
