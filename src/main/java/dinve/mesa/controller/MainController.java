package dinve.mesa.controller;

import dinve.mesa.converter.*;
import dinve.mesa.model.Formulario;
import dinve.mesa.model.UnidadProductora;
import dinve.mesa.model.Usuario;
import dinve.mesa.service.FormService;
import dinve.mesa.service.UserService;
import dinve.mesa.service.UpService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class MainController {
    @Qualifier("UserService")
    private final UserService userService;
    @Qualifier("FormService")
    private final FormService formService;
    @Qualifier("UpService")
    private final UpService upService;
    @Autowired
    public MainController(UserService userService, FormService formService, UpService upService){
        this.userService = userService;
        this.formService = formService;
        this.upService = upService;
    }
    /*UNIDADES PRODUCTORAS*/
    //Probado
    @PostMapping(value = "up/create")
    public String create(@RequestHeader(value = "Authorization") String token,@RequestBody UnidadProductoraDatos unidadProductora){
        return upService.save(token, unidadProductora);
    }
    //Probado
    @PutMapping(value = "up/update")
    public String updateUp(@RequestHeader(value = "Authorization") String token,@RequestBody UnidadProductoraDatos unidadProductora){
        return upService.save(token,unidadProductora);
    }
    //Probado
    @GetMapping(value = "up/findAll")
    public Map<String, Object> getAllUp(@RequestHeader(value = "Authorization") String token, Pageable pageable){
        return  upService.getAllUp(token, pageable);
    }
    //Probado
    @DeleteMapping(value = "up/delete")
    public String deleteUp(@RequestHeader(value = "Authorization") String token,Long id){
        return upService.deleteUp(token,id);
        //return null;
    }
    //Probado
    @GetMapping(value = "up/all")
    public Map<String, Object> getAllUps(@RequestHeader(value = "Authorization") String token){
        return upService.getAllUps(token);
    }

    /*USUARIOS*/
    //Probado
    @PostMapping(value="user/create")
    public String create(@RequestHeader(value = "Authorization") String token, @RequestBody UsuarioDatos usuarioDatos){
        return userService.save(token,usuarioDatos);
    }
    //Probado
    @PutMapping(value = "user/login")
    public Map<String, Object> login(@RequestBody Usuario usuario){
        return userService.login(usuario);
    }
    //Probado
    @PutMapping(value = "user/logout")
    public String logout(@RequestBody Usuario usuario){
        return userService.logout(usuario);
    }
    //Probado
    @GetMapping(value="user/get/all")
    public Map<String, Object> getAllUsers(@RequestHeader(value = "Authorization") String token,Pageable pageable) {
        return userService.findAll(token, pageable);
    }

    //Probado
    @GetMapping(value = "user/get")
    public Map<String, Object> getUser(@RequestHeader(value = "Authorization") String token){
        return userService.getUser(token);
    }
    //Probado
    @PutMapping(value = "user/update")
    public String updateUser(@RequestHeader(value = "Authorization") String token,
                             @RequestParam(value = "id_update") Long id_update,
                             @RequestBody UsuarioDatos usuarioDatos){
        return userService.updateUser(token, usuarioDatos, id_update);
    }
    //Probado
    @PutMapping(value = "user/unable")
    public String unableUser(@RequestHeader(value = "Authorization") String token, @RequestParam(value = "id") Long id_user){
        return userService.unableUser(token, id_user);
    }
    //Probado
    @PutMapping(value = "user/enable")
    public String enableUser(@RequestHeader(value = "Authorization") String token,
                             @RequestParam(value = "id") Long id_user,
                             @RequestParam(value = "rol") String rol){
        return userService.enableUser(token, id_user, rol);
    }
    //En pruebas
    @PutMapping(value = "user/updatePassword")
    public String updatePassword(@RequestHeader(value = "Authorization") String token,
                                 @RequestBody PasswordDatos datos_pass){
        return userService.updatePassword(token, datos_pass);
    }

    /*FORMULARIOS*/
    //Probado
    @GetMapping(value="form/get/all")
    public Map<String, Object> getAllForms(@RequestHeader(value = "Authorization") String token,Pageable pageable){
        return formService.getAllForms(token,pageable);
    }
    //Probado
    @GetMapping(value="form/get/me")
    public Map<String, Object> getMyForms(@RequestHeader(value = "Authorization") String token,Pageable pageable){
        return formService.getMyForms(token,pageable);
    }
    //No implementado
    @GetMapping(value="form/get/up")
    public Map<String, Object> getMyUPForms(@RequestHeader(value = "Authorization") String token,Pageable pageable){
        return formService.getMyUPForms(token,pageable);
    }
    //Probado
    @PostMapping(value = "form5b/save")
    public String saveForm5B(@RequestHeader(value = "Authorization") String token, @RequestBody Formulario5BDatos formulario5BDatos){
        return formService.saveForm5B(token,formulario5BDatos);
    }
    //Probado
    @PostMapping(value = "form5a/save")
    public String saveForm5A(@RequestHeader(value = "Authorization") String token, @RequestBody Formulario5ADatos formulario5ADatos){
        return formService.saveForm5A(token,formulario5ADatos);
    }
    //Probado
    @PutMapping(value = "form5a/update")
    public String updateForm5A(@RequestHeader(value = "Authorization") String token,@RequestBody Formulario5ADatos formulario5ADatos){
        return formService.saveForm5A(token,formulario5ADatos);
    }
    //Probado
    @PutMapping(value = "form5b/update")
    public String updateForm5B(@RequestHeader(value = "Authorization") String token,@RequestBody Formulario5BDatos formulario5BDatos){
        return formService.saveForm5B(token,formulario5BDatos);
    }
    //Probado
    @DeleteMapping(value = "form/delete")
    public String deleteForm(@RequestHeader(value = "Authorization") String token,Long id){
        return formService.deleteForm(token,id);
        //return null;
    }
}
