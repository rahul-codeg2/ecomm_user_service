package com.ecomm_app.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse
{
    private String jwtToken;
    private  String username;


}
