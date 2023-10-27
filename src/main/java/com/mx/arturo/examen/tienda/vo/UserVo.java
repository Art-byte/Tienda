package com.mx.arturo.examen.tienda.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    private ObjectId id;
    private String name;
    private String lastName;
    private List<String> roles;
}
