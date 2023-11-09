package com.ecomm_app.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtRequest
{
    private String email;
    private String password;

}
