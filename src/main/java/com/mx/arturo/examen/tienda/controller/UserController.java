package com.mx.arturo.examen.tienda.controller;

import com.mx.arturo.examen.tienda.model.Role;
import com.mx.arturo.examen.tienda.model.User;
import com.mx.arturo.examen.tienda.repository.RoleRepository;
import com.mx.arturo.examen.tienda.repository.UserRepository;
import com.mx.arturo.examen.tienda.vo.UserVo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepositoy;

    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList = null;
        try{
            userList = userRepository.findAll();
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> findById(@PathVariable String id){
        User user = null;
        try{
            user = userRepository.findById(new ObjectId(id)).orElse(null);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/user/name/{name}")
    public ResponseEntity<User> findByName(@PathVariable String name){
        User user = null;
        try{
            user = userRepository.findByName(name);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody  User userOld){
        try{
            User userNew = userRepository.findById(new ObjectId(id)).orElse(null);
            userNew.setName(userOld.getName());
            userNew.setLastName(userOld.getLastName());
            userRepository.save(userNew);
            return new ResponseEntity<>("Usuario actualizado correctamente", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        try{
            userRepository.deleteById(new ObjectId(id));
            return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Metodos para controlar los roles del usuario

    @PostMapping("/user/role/{id}")
    public ResponseEntity<?> addRoleToUser(@PathVariable String id, @RequestParam("roleName") String roleName){
        try {
            ObjectId userId = new ObjectId(id);
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return new ResponseEntity<>("El usuario no se encontró en la base de datos", HttpStatus.NOT_FOUND);
            }

            Role role = roleRepositoy.findByName(roleName);
            if (role == null) {
                return new ResponseEntity<>("El rol no se encontró en la base de datos", HttpStatus.NOT_FOUND);
            }
            List<ObjectId> roles = user.getRoles();
            if (!roles.contains(role.getId())) {
                roles.add(role.getId());
                user.setRoles(roles);
                userRepository.save(user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("El usuario ya cuenta con este rol", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/user/role/{id}")
    public ResponseEntity<?> deleteRoleToUser(@PathVariable String id, @RequestParam("roleName") String roleName){
        User user = null;
        List<ObjectId> actualRole = new ArrayList<>();
        List<ObjectId> finalRole = new ArrayList<>();
        try{
            user = userRepository.findById(new ObjectId(id)).orElse(null);
            Role role = roleRepositoy.findByName(roleName);

            actualRole = user.getRoles();
            actualRole.forEach(data -> {
                if(!role.getId().equals(data)){
                    finalRole.add(data);
                }
            });

            user.setRoles(finalRole);
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/formatUser/{id}")
    public ResponseEntity<UserVo> getUserFormated(@PathVariable String id){

         try{
             List<String> roleNames = new ArrayList<>();
             User user = userRepository.findById(new ObjectId(id)).orElse(null);

             for(ObjectId idRole : user.getRoles()){
                 Role role = roleRepositoy.findById(idRole).orElse(null);
                 roleNames.add(role.getName());
             }

                UserVo userVos = new UserVo();
                userVos.setId(user.getId());
                userVos.setName(user.getName());
                userVos.setLastName(user.getLastName());
                userVos.setRoles(roleNames);
                return new ResponseEntity<>(userVos, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
