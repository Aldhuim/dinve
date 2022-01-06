package dinve.mesa.controller;


import dinve.mesa.converter.UnidadProductoraDatos;
import dinve.mesa.converter.UsuarioDatos;
import dinve.mesa.model.UnidadProductora;
import dinve.mesa.model.Usuario;
import dinve.mesa.service.UpService;
import dinve.mesa.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class UpController {

    //Métodos ya implementados en el main Controller
    @Autowired
    private  UpService upService;

    @PostMapping(value = "up/createUp")
    public String create(@RequestHeader(value = "Authorization") String token,@RequestBody UnidadProductoraDatos unidadProductora){
        return upService.save(token, unidadProductora);
        //return null;
    }
    @GetMapping(value = "up/findAll")
    public Map<String, Object> getAllUp(@RequestHeader(value = "Authorization") String token, Pageable pageable){
        return  upService.getAllUp(token, pageable);
    }

}
