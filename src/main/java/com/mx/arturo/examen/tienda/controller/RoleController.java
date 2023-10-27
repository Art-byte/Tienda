package com.mx.arturo.examen.tienda.controller;

import com.mx.arturo.examen.tienda.model.Role;
import com.mx.arturo.examen.tienda.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private RoleRepository roleRepositoy;

    @GetMapping("/role")
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roles = null;
        try{
            roles = roleRepositoy.findAll();
            return new ResponseEntity<>(roles, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/role/{roleName}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String roleName){
        Role role = null;
        try{
            role = roleRepositoy.findByName(roleName);
            return new ResponseEntity<>(role, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/role")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        try{
             roleRepositoy.save(role);
             return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @PutMapping("/role/{roleName}")
    public ResponseEntity<String> updateRole(@PathVariable String roleName, @RequestBody Role roleOld){
        Role roleNew = null;
        try{
            roleNew = roleRepositoy.findByName(roleName);
            roleNew.setName(roleOld.getName());
            roleRepositoy.save(roleNew);
            return new ResponseEntity<>("Se ha actualizado correctamente el rol", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/role/{roleName}")
    public ResponseEntity<String> deleteRole(@PathVariable String roleName){
        try{
            Role role = roleRepositoy.findByName(roleName);
            roleRepositoy.deleteById(role.getId());
            return new ResponseEntity<>("Rol eliminado correctamente", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
