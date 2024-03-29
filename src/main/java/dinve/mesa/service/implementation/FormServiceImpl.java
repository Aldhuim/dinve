package dinve.mesa.service.implementation;

import dinve.mesa.converter.Formulario5ADatos;
import dinve.mesa.converter.Formulario5BDatos;
import dinve.mesa.model.Formulario;
import dinve.mesa.model.Usuario;
import dinve.mesa.model.alineamiento_brecha_prioritaria.AlineamientoBrechaServiciosPublicosBrechaIdentificada;
import dinve.mesa.model.alineamiento_brecha_prioritaria.IndicadorBrecha;
import dinve.mesa.model.descripcion_agregada_ioarr.ResponsabilidadFuncionalDescripcionIOARR;
import dinve.mesa.model.descripcion_agregada_proyecto_programa.*;
import dinve.mesa.model.formulario_tipos.Adjunto;
import dinve.mesa.model.formulario_tipos.Formulario5A;
import dinve.mesa.model.formulario_tipos.Formulario5B;
import dinve.mesa.repository.*;
import dinve.mesa.service.FormService;
import dinve.mesa.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("FormService")
public class FormServiceImpl implements FormService {
    private final UsuarioRepository usuarioRepository;
    private final FormularioRepository formularioRepository;
    private final Formulario5BRepository formulario5BRepository;
    private final Formulario5ARepository formulario5ARepository;
    private final AdjuntoRepository adjuntoRepository;
    private final ResponFunIOARRRepository responFunIOARRRepository;
    private final ResponFunAgregadaRepository responFunAgregadaRepository;
    private final AlineaBrechaServPubBrechaIdRepository alineaBrechaServPubBrechaIdRepository;
    private final IndicadorBrechaRepository indicadorBrechaRepository;
    private final ProyectoInversionRepository proyectoInversionRepository;
    private final ProgramaInversionRepository programaInversionRepository;
    private final TipoItemRepository tipoItemRepository;
    private final CapacidadRepository capacidadRepository;
    private final JWTUtil jwtUtil;

    @Autowired
    public FormServiceImpl(
            UsuarioRepository usuarioRepository,
            FormularioRepository formularioRepository,
            Formulario5ARepository formulario5ARepository,
            Formulario5BRepository formulario5BRepository,
            AdjuntoRepository adjuntoRepository,
            ResponFunIOARRRepository responFunIOARRRepository,
            ResponFunAgregadaRepository responFunAgregadaRepository,
            AlineaBrechaServPubBrechaIdRepository alineaBrechaServPubBrechaIdRepository,
            IndicadorBrechaRepository indicadorBrechaRepository,
            ProyectoInversionRepository proyectoInversionRepository,
            ProgramaInversionRepository programaInversionRepository,
            TipoItemRepository tipoItemRepository,
            CapacidadRepository capacidadRepository,
            JWTUtil jwtUtil){
        this.usuarioRepository = usuarioRepository;
        this.formularioRepository = formularioRepository;
        this.formulario5ARepository = formulario5ARepository;
        this.formulario5BRepository = formulario5BRepository;
        this.adjuntoRepository = adjuntoRepository;
        this.responFunIOARRRepository = responFunIOARRRepository;
        this.responFunAgregadaRepository = responFunAgregadaRepository;
        this.alineaBrechaServPubBrechaIdRepository = alineaBrechaServPubBrechaIdRepository;
        this.indicadorBrechaRepository = indicadorBrechaRepository;
        this.proyectoInversionRepository = proyectoInversionRepository;
        this.programaInversionRepository = programaInversionRepository;
        this.tipoItemRepository = tipoItemRepository;
        this.capacidadRepository = capacidadRepository;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public Map<String, Object> getAllForms(String token, Pageable pageable){
        Map<String,Object> datos = new HashMap<>();
        try {
            byte rol = jwtUtil.getRol(token);
            Usuario u = usuarioRepository.findById(Long.parseLong(jwtUtil.getId(token))).get();

            if((rol == 1 || rol == 2) && (u.isActivo() == true)) {
                Page<Formulario> pageFormulario = formularioRepository.findAll(pageable);
                datos.put("Size",pageFormulario.getSize());
                datos.put("TotalPaginas",pageFormulario.getTotalPages());
                datos.put("TotalElementos",pageFormulario.getTotalElements());
                datos.put("AllForms",pageFormulario.getContent());
                return datos;
            }

        }catch(ExpiredJwtException e){
            datos.put("Message","Session expired");
        }
        return datos;
    }
    @Override
    public Map<String, Object> getMyForms(String token,Pageable pageable){
        Map<String,Object> datos = new HashMap<>();
        try {
            Usuario u = usuarioRepository.findByUser(jwtUtil.getUser(token));
            if(u.isActivo() == true) {

                Page<Formulario> pageFormulario = formularioRepository.findAllByUsuario(u, pageable);

                datos.put("Size",pageFormulario.getSize());
                datos.put("TotalPaginas",pageFormulario.getTotalPages());
                datos.put("TotalElementos",pageFormulario.getTotalElements());
                datos.put("MyForms",pageFormulario.getContent());

                return datos;
            }
        }catch(ExpiredJwtException e){
            datos.put("Message","Session expired");
        }
        return datos;
    }
    @Override
    public Map<String, Object> getMyUPForms(String token, Pageable pageable){
        Map<String,Object> datos = new HashMap<>();
        try {
            Usuario u = usuarioRepository.findByUser(jwtUtil.getUser(token));
            if(u.isActivo() == true) {

                Page<Formulario> pageFormulario = formularioRepository.findAllByUnidadProductora(u, u.getUnidad_productora(), pageable);

                datos.put("Size",pageFormulario.getSize());
                datos.put("TotalPaginas",pageFormulario.getTotalPages());
                datos.put("TotalElementos",pageFormulario.getTotalElements());
                datos.put("MyUPForms",pageFormulario.getContent());

                return datos;
            }
        }catch(ExpiredJwtException e){
            datos.put("Message","Session expired");
        }
        return datos;
    }
    @Override
    public String saveForm5A(String token, Formulario5ADatos formulario5ADatos){
        try {
            Usuario u = usuarioRepository.findByUser(jwtUtil.getUser(token));
            formulario5ADatos.setId_user(u.getId());
            if(u.isActivo() == true) {
                Formulario f = formulario5ADatos.getFormulario();
                Formulario5A f5a = formulario5ADatos.getFormulario5A();
                List<ResponsabilidadFuncionalDescripcionAgregada> respFuncDescAgr = formulario5ADatos.getListaResponsabilidadFuncionalDescripcionAgregada();
                List<AlineamientoBrechaServiciosPublicosBrechaIdentificada> aliBreServPubBreIden = formulario5ADatos.getListaAlineamientoBrechaServiciosPublicosBrechaIdentificada();
                List<List<IndicadorBrecha>> indBre = formulario5ADatos.getListaDeListaDeIndicadorBrecha();
                List<Adjunto> adjunto = formulario5ADatos.getListaAdjunto();

                if(formulario5ADatos.getCosto()== null){
                    List<ProgramaDeInversion> progInv = formulario5ADatos.getListaProgramaDeInversion();

                    for(int i=0; i<progInv.size(); i++){
                        respFuncDescAgr.get(i).setProgramaDeInversion(progInv.get(i));
                    }
                } else {
                    List<ProyectoDeInversion> proyInv = formulario5ADatos.getListaProyectoDeInversion();
                    List<List<TipoItem>> tipoItem = formulario5ADatos.getListaDeListaDeTipoItem();
                    List<List<Capacidad>> capacidad = formulario5ADatos.getListaDeListaDeCapacidad();

                    for(int i=0; i<proyInv.size(); i++) {
                        respFuncDescAgr.get(i).setProyectoDeInversion(proyInv.get(i));
                    }
                    for (int i=0;i<tipoItem.size();i++){
                        for(int j=0;j<tipoItem.get(i).size();j++) {
                            proyInv.get(i).addTipoItem(tipoItem.get(i).get(j));
                        }
                    }
                    for (int k=0;k<capacidad.size();k++){
                        for(int l=0;l<capacidad.get(k).size();l++){
                            proyInv.get(k).addCapacidad(capacidad.get(k).get(l));
                        }
                    }
                }

                u.addFormulario(f);
                f.setFormulario5A(f5a);
                for (Adjunto adj : adjunto) {
                    f5a.addAdjunto(adj);
                }
                for (ResponsabilidadFuncionalDescripcionAgregada resp : respFuncDescAgr) {
                    f5a.addResponsabilidadFuncionalDescripcionAgregada(resp);
                }

                for (AlineamientoBrechaServiciosPublicosBrechaIdentificada ali : aliBreServPubBreIden) {
                    f5a.addALineamientoBrechaServiciosPublicosBrechaIdentificada(ali);
                }
                for (int i = 0; i < indBre.size(); i++) {
                    for (int j = 0; j < indBre.get(i).size(); j++) {
                        aliBreServPubBreIden.get(i).addIndicadorBrecha(indBre.get(i).get(j));
                    }
                }

                usuarioRepository.save(u);
                return "Success";
            }else {
                return "Inactive user";
            }
        }catch(ExpiredJwtException e){
            return "Session expired";
        }catch (Exception ignored){
        }

        return "Failed";
    }
    @Override
    public String saveForm5B(String token, Formulario5BDatos formulario5BDatos){
        try {
            Usuario usuario = usuarioRepository.findByUser(jwtUtil.getUser(token));
            formulario5BDatos.setId_user(usuario.getId());
            if(usuario.isActivo()) {
                Formulario formulario = formulario5BDatos.getFormulario();
                Formulario5B formulario5B = formulario5BDatos.getFormulario5B();
                List<ResponsabilidadFuncionalDescripcionIOARR> responsabilidadFuncionalDescripcionIOARRList = formulario5BDatos.getListaResponsabilidadFuncionalDescripcionIOARR();
                List<AlineamientoBrechaServiciosPublicosBrechaIdentificada> alineamientoBrechaServiciosPublicosBrechaIdentificadaList = formulario5BDatos.getListaAlineamientoBrechaServiciosPublicosBrechaIdentificada();
                List<List<IndicadorBrecha>> indicadorBrechaListList = formulario5BDatos.getListaDeListaDeIndicadorBrecha();
                List<Adjunto> adjuntoList = formulario5BDatos.getListaAdjunto();
                usuario.addFormulario(formulario);
                formulario.setFormulario5B(formulario5B);
                for (Adjunto adj : adjuntoList) {
                    formulario5B.addAdjunto(adj);
                }
                for (ResponsabilidadFuncionalDescripcionIOARR resp : responsabilidadFuncionalDescripcionIOARRList) {
                    formulario5B.addResponsabilidadFuncionalDescripcionIOARR(resp);
                }
                for (AlineamientoBrechaServiciosPublicosBrechaIdentificada ali : alineamientoBrechaServiciosPublicosBrechaIdentificadaList) {
                    formulario5B.addALineamientoBrechaServiciosPublicosBrechaIdentificada(ali);
                }
                for (int i = 0; i < indicadorBrechaListList.size(); i++) {
                    for (int j = 0; j < indicadorBrechaListList.get(i).size(); j++) {
                        alineamientoBrechaServiciosPublicosBrechaIdentificadaList.get(i).addIndicadorBrecha(indicadorBrechaListList.get(i).get(j));
                    }
                }
                usuarioRepository.save(usuario);
                return "Success";
            }
        }catch(ExpiredJwtException e) {
            return "Session expired";
        }catch(Exception ignored){
        }
        return "Failed";
    }
    @Override
    public String updateForm5A(String token,Formulario5ADatos formulario5ADatos){
        try {
            if(usuarioRepository.existsById(Long.valueOf(jwtUtil.getId(token)))) {
                Usuario u = usuarioRepository.findByUser(jwtUtil.getUser(token));

                if(u.isActivo() && formulario5ADatos.getId_user() == u.getId()) {
                    Formulario formulario = formulario5ADatos.getFormulario();
                    Formulario5A formulario5A = formulario5ADatos.getFormulario5A();
                    List<ResponsabilidadFuncionalDescripcionAgregada> respFuncDescrAgre = formulario5ADatos.getListaResponsabilidadFuncionalDescripcionAgregada();
                    List<ProyectoDeInversion> proyInv = formulario5ADatos.getListaProyectoDeInversion();
                    List<List<TipoItem>> tipoItem = formulario5ADatos.getListaDeListaDeTipoItem();
                    List<List<Capacidad>> capacidad = formulario5ADatos.getListaDeListaDeCapacidad();
                    List<ProgramaDeInversion> progInv = formulario5ADatos.getListaProgramaDeInversion();
                    List<AlineamientoBrechaServiciosPublicosBrechaIdentificada> aliBreSerPubBreIdent = formulario5ADatos.getListaAlineamientoBrechaServiciosPublicosBrechaIdentificada();
                    List<List<IndicadorBrecha>> indicadorBrechaListList = formulario5ADatos.getListaDeListaDeIndicadorBrecha();
                    List<Adjunto> adjuntoList = formulario5ADatos.getListaAdjunto();
                    formularioRepository.save(formulario);
                    formulario5ARepository.save(formulario5A);
                    adjuntoRepository.saveAll(adjuntoList);
                    responFunAgregadaRepository.saveAll(respFuncDescrAgre);
                    proyectoInversionRepository.saveAll(proyInv);
                    for (List<TipoItem> i : tipoItem) {
                        tipoItemRepository.saveAll(i);
                    }
                    for (List<Capacidad> i : capacidad) {
                        capacidadRepository.saveAll(i);
                    }
                    programaInversionRepository.saveAll(progInv);
                    alineaBrechaServPubBrechaIdRepository.saveAll(aliBreSerPubBreIdent);
                    for (List<IndicadorBrecha> i : indicadorBrechaListList) {
                        indicadorBrechaRepository.saveAll(i);
                    }
                    return "Success";
                }
            }
        }catch(ExpiredJwtException e){
            return "Session expired";
        }catch(Exception ignored){}
        return "Failed";
    }
    @Override
    public String updateForm5B(String token,Formulario5BDatos formulario5BDatos) {
        try {
            if(usuarioRepository.existsById(Long.valueOf(jwtUtil.getId(token)))) {
                Usuario u = usuarioRepository.findByUser(jwtUtil.getUser(token));
                formulario5BDatos.setId_user(u.getId());
                System.out.println("Id_form: "+formulario5BDatos.getId_form());
                System.out.println("Id_form5b: "+formulario5BDatos.getId_form5b());
                if(u.isActivo() && formulario5BDatos.getId_user() == u.getId()) {
                    Formulario formulario = formulario5BDatos.getFormulario();
                    Formulario5B formulario5B = formulario5BDatos.getFormulario5B();
                    List<ResponsabilidadFuncionalDescripcionIOARR> responsabilidadFuncionalDescripcionIOARRList = formulario5BDatos.getListaResponsabilidadFuncionalDescripcionIOARR();
                    List<AlineamientoBrechaServiciosPublicosBrechaIdentificada> alineamientoBrechaServiciosPublicosBrechaIdentificadaList = formulario5BDatos.getListaAlineamientoBrechaServiciosPublicosBrechaIdentificada();
                    List<List<IndicadorBrecha>> indicadorBrechaListList = formulario5BDatos.getListaDeListaDeIndicadorBrecha();
                    List<Adjunto> adjuntoList = formulario5BDatos.getListaAdjunto();
                    System.out.println("Id_form: "+formulario5BDatos.getId_form());
                    System.out.println("Id_form5b: "+formulario5BDatos.getId_form5b());
                    formularioRepository.save(formulario);
                    formulario5BRepository.save(formulario5B);
                    adjuntoRepository.saveAll(adjuntoList);
                    responFunIOARRRepository.saveAll(responsabilidadFuncionalDescripcionIOARRList);
                    alineaBrechaServPubBrechaIdRepository.saveAll(alineamientoBrechaServiciosPublicosBrechaIdentificadaList);
                    for (List<IndicadorBrecha> i : indicadorBrechaListList) {
                        indicadorBrechaRepository.saveAll(i);
                    }
                    System.out.println("Id_form: "+formulario5BDatos.getId_form());
                    System.out.println("Id_form5b: "+formulario5BDatos.getId_form5b());
                    return "Success ";
                }
            }
        }catch(ExpiredJwtException e){
            return "Session expired";
        }catch(Exception ignored){}
        return "Failed";
    }
    @Override
    public String deleteForm(String token,Long id) {
        try {
            Usuario u = usuarioRepository.findByUser(jwtUtil.getUser(token));
            if (usuarioRepository.existsById(Long.valueOf(jwtUtil.getId(token))) && u.isActivo() == true) {
                Formulario f = formularioRepository.findById(id).orElse(null);
                if (f != null) {
                    formularioRepository.delete(f);
                    return "Success";
                }
            }
        } catch (ExpiredJwtException e) {
            return "Session expired";
        }
        return "Failed";
    }
    /*
    public Formulario getFormulario5B(Long id) {
        Usuario u = usuarioRepository.findById(1L).orElse(null);
        assert u != null;
        return u.getFormularios().get(id.intValue()-1);
    }
    */
}
