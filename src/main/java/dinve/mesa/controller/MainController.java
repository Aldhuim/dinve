package dinve.mesa.controller;

import dinve.mesa.converter.Formulario5ADatos;
import dinve.mesa.converter.Formulario5BDatos;
import dinve.mesa.converter.UsuarioDatos;
import dinve.mesa.model.Formulario;
import dinve.mesa.model.UnidadProductora;
import dinve.mesa.model.Usuario;
import dinve.mesa.service.FormService;
import dinve.mesa.service.UPService;
import dinve.mesa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MainController {
    @Autowired
    @Qualifier("UserService")
    private UserService userService;
    @Autowired
    @Qualifier("UPService")
    private UPService upService;
    @Autowired
    @Qualifier("FormService")
    private FormService formService;

    //No implementado
    @PostMapping(value = "up/create")
    public String create(@RequestHeader(value = "Authorization") String token,@RequestBody UnidadProductora unidadProductora){
        //return upService.save(unidadProductora);
        return null;
    }

    //Listo
    @PostMapping(value="user/create")
    public String create(@RequestHeader(value = "Authorization") String token,@RequestBody UsuarioDatos usuarioDatos){
        return userService.save(token,usuarioDatos);
    }
    //Listo
    @PutMapping(value = "user/login")
    public Map<String, Object> login(@RequestBody Usuario usuario){
        return userService.login(usuario);
    }
    //Listo
    @PutMapping(value = "user/logout")
    public String logout(@RequestBody Usuario usuario){
        return userService.logout(usuario);
    }
    //Listo
    @GetMapping(value="user/get/all")
    public List<Usuario> getAllUsers(@RequestHeader(value = "Authorization") String token,Pageable pageable){
        return userService.findAll(token,pageable);
    }
    //Listo
    @GetMapping(value="form/get/all")
    public List<Formulario> getAllForms(@RequestHeader(value = "Authorization") String token,Pageable pageable){
        return formService.getAllForms(token,pageable);
    }
    //Listo
    @GetMapping(value="form/get/me")
    public List<Formulario> getMyForms(@RequestHeader(value = "Authorization") String token,Pageable pageable){
        return formService.getMyForms(token,pageable);
    }
    //Listo
    @GetMapping(value="form/get/up")
    public List<Formulario> getMyUPForms(@RequestHeader(value = "Authorization") String token,Pageable pageable){
        return formService.getMyUPForms(token,pageable);
    }
    //Listo
    @PostMapping(value = "form5b/save")
    public String saveForm5B(@RequestHeader(value = "Authorization") String token, @RequestBody Formulario5BDatos formulario5BDatos){
        return formService.saveForm5B(token,formulario5BDatos);
    }
    //No probado
    @PostMapping(value = "form5a/save")
    public String saveForm5A(@RequestHeader(value = "Authorization") String token,@RequestBody Formulario5ADatos formulario5ADatos){
        //return formService.saveForm5B(token,formulario5ADatos);
        return null;
    }
    //No probado
    @DeleteMapping(value = "form/delete")
    public String deleteForm(@RequestHeader(value = "Authorization") String token,Long id){
        return formService.deleteForm(token,id);
        //return null;
    }
}
