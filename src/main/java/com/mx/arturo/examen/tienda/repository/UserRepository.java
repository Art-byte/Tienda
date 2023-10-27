package com.mx.arturo.examen.tienda.repository;

import com.mx.arturo.examen.tienda.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    public User findByName(String name);
}
