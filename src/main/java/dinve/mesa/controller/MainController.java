package dinve.mesa.controller;

import dinve.mesa.converter.Formulario5ADatos;
import dinve.mesa.converter.Formulario5BDatos;
import dinve.mesa.converter.UsuarioDatos;
import dinve.mesa.model.Formulario;
import dinve.mesa.model.UnidadProductora;
import dinve.mesa.model.Usuario;
import dinve.mesa.service.FormService;
import dinve.mesa.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MainController {
    @Qualifier("UserService")
    private final UserService userService;
    @Qualifier("FormService")
    private final FormService formService;
    @Autowired
    public MainController(UserService userService, FormService formService){
        this.userService = userService;
        this.formService = formService;
    }
    //No implementado
    @PostMapping(value = "up/create")
    public String create(@RequestHeader(value = "Authorization") String token,@RequestBody UnidadProductora unidadProductora){
        //return upService.save(unidadProductora);
        return null;
    }
    @PostMapping(value="user/create")
    public String create(@RequestHeader(value = "Authorization") String token, @RequestBody UsuarioDatos usuarioDatos){
        return userService.save(token,usuarioDatos);
    }
    @PutMapping(value = "user/login")
    public Map<String, Object> login(@RequestBody Usuario usuario){
        return userService.login(usuario);
    }
    @PutMapping(value = "user/logout")
    public String logout(@RequestBody Usuario usuario){
        return userService.logout(usuario);
    }
    @GetMapping(value="user/get/all")
    public Map<String, Object> getAllUsers(@RequestHeader(value = "Authorization") String token,Pageable pageable){
        return userService.findAll(token,pageable);
    }
    @GetMapping(value="form/get/all")
    public Map<String, Object> getAllForms(@RequestHeader(value = "Authorization") String token,Pageable pageable){
        return formService.getAllForms(token,pageable);
    }
    @GetMapping(value="form/get/me")
    public Map<String, Object> getMyForms(@RequestHeader(value = "Authorization") String token,Pageable pageable){
        return formService.getMyForms(token,pageable);
    }
    @GetMapping(value="form/get/up")
    public Map<String, Object> getMyUPForms(@RequestHeader(value = "Authorization") String token,Pageable pageable){
        return formService.getMyUPForms(token,pageable);
    }
    @PostMapping(value = "form5b/save")
    public String saveForm5B(@RequestHeader(value = "Authorization") String token, @RequestBody Formulario5BDatos formulario5BDatos){
        return formService.saveForm5B(token,formulario5BDatos);
    }
    @PostMapping(value = "form5a/save")
    public String saveForm5A(@RequestHeader(value = "Authorization") String token,@RequestBody Formulario5ADatos formulario5ADatos){
        return formService.saveForm5A(token,formulario5ADatos);
    }
    @PutMapping(value = "form5b/update")
    public String updateForm5A(@RequestHeader(value = "Authorization") String token,@RequestBody Formulario5ADatos formulario5ADatos){
        return formService.updateForm5A(token,formulario5ADatos);
    }
    @PutMapping(value = "form5a/update")
    public String updateForm5B(@RequestHeader(value = "Authorization") String token,@RequestBody Formulario5BDatos formulario5BDatos){
        return formService.updateForm5B(token,formulario5BDatos);
    }
    @DeleteMapping(value = "form/delete")
    public String deleteForm(@RequestHeader(value = "Authorization") String token,Long id){
        return formService.deleteForm(token,id);
        //return null;
    }
}
