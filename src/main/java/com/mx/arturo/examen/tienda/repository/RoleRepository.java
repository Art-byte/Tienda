package com.mx.arturo.examen.tienda.repository;

import com.mx.arturo.examen.tienda.model.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, ObjectId> {

    public Role findByName(String name);
}
