package com.mx.arturo.examen.tienda.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String name;
    private String lastName;
    List<ObjectId> roles;

    public User(String name, String lastName){
        this.name = name;
        this.lastName = lastName;
    }

}
