package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**RestartDto is used for creating new password by clicking on link sent to user*/
public class RestartDto {

    private String activatedCode;
    private String password;
}
