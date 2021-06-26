package com.example.demo.model.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**Option class is used for defining if answer in quiz is correct*/
public class Option {

    String answer;
    boolean proper;


}
