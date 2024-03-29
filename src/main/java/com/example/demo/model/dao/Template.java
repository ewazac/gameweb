package com.example.demo.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("template")
/**Template class is used for creating HTML e-mail templates*/
public class Template {

    @Id
    private String id;
    private String name;
    private String subject;
    private String body;

}
