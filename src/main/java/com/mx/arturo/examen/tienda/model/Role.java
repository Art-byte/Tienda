package com.mx.arturo.examen.tienda.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "role")
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String name;

    public Role(String name){
        this.name = name;
    }

}
