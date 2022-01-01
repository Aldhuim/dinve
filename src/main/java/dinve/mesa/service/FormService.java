package dinve.mesa.service;

import dinve.mesa.converter.Formulario5ADatos;
import dinve.mesa.converter.Formulario5BDatos;
import dinve.mesa.model.Formulario;
import dinve.mesa.model.Usuario;
import dinve.mesa.model.alineamiento_brecha_prioritaria.AlineamientoBrechaServiciosPublicosBrechaIdentificada;
import dinve.mesa.model.alineamiento_brecha_prioritaria.IndicadorBrecha;
import dinve.mesa.model.descripcion_agregada_ioarr.ResponsabilidadFuncionalDescripcionIOARR;
import dinve.mesa.model.formulario.Adjunto;
import dinve.mesa.model.formulario.Formulario5B;
import dinve.mesa.repository.*;
import dinve.mesa.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("FormService")
public class FormService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private FormularioRepository formularioRepository;
    @Autowired
    private Formulario5ARepository formulario5ARepository;
    @Autowired
    private Formulario5BRepository formulario5BRepository;
    @Autowired
    private AdjuntoRepository adjuntoRepository;
    @Autowired
    private ResponFunIOARRRepository responFunIOARRRepository;
    @Autowired
    private ResponFunAgregadaRepository responFunAgregadaRepository;
    @Autowired
    private AlineaBrechaServPubBrechaIdRepository alineaBrechaServPubBrechaIdRepository;
    @Autowired
    private IndicadorBrechaRepository indicadorBrechaRepository;

    @Autowired
    private JWTUtil jwtUtil;

    public List<Formulario> getAllForms(String token, Pageable pageable){
        byte rol = jwtUtil.getRol(token);
        System.out.println(rol);
        if(rol == 1 || rol == 2) {
            return formularioRepository.findAll(pageable).getContent();
        }
        return null;
    }

    public List<Formulario> getMyForms(String token,Pageable pageable){
        Usuario u = usuarioRepository.findByUser(jwtUtil.getUser(token));
        return formularioRepository.findAllByUsuario(u,pageable).getContent();
    }

    public List<Formulario> getMyUPForms(String token, Pageable pageable){
        Usuario u = usuarioRepository.findByUser(jwtUtil.getUser(token));
        return formularioRepository.findAllByUnidadProductora(u,u.getUnidad_productora(),pageable).getContent();
    }

    public String saveForm5B(String token, Formulario5BDatos formulario5BDatos){
        try {
            Usuario usuario = usuarioRepository.findByUser(jwtUtil.getUser(token));
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
        }catch (Exception ignored){

        }
        return "Failed";
    }

    public String deleteForm(String token,Long id){
        Formulario f = formularioRepository.findById(id).orElse(null);
        if(f != null){
            formularioRepository.delete(f);
            return "Success";
        }
        return "Failed";
    }

    public void updateFormulario5B(String token,Formulario5BDatos formulario5BDatos) {
        Formulario formulario = formulario5BDatos.getFormulario();
        Formulario5B formulario5B = formulario5BDatos.getFormulario5B();
        List<ResponsabilidadFuncionalDescripcionIOARR> responsabilidadFuncionalDescripcionIOARRList = formulario5BDatos.getListaResponsabilidadFuncionalDescripcionIOARR();
        List<AlineamientoBrechaServiciosPublicosBrechaIdentificada> alineamientoBrechaServiciosPublicosBrechaIdentificadaList = formulario5BDatos.getListaAlineamientoBrechaServiciosPublicosBrechaIdentificada();
        List<List<IndicadorBrecha>> indicadorBrechaListList = formulario5BDatos.getListaDeListaDeIndicadorBrecha();
        List<Adjunto> adjuntoList = formulario5BDatos.getListaAdjunto();
        formularioRepository.save(formulario);
        formulario5BRepository.save(formulario5B);
        adjuntoRepository.saveAll(adjuntoList);
        responFunIOARRRepository.saveAll(responsabilidadFuncionalDescripcionIOARRList);
        alineaBrechaServPubBrechaIdRepository.saveAll(alineamientoBrechaServiciosPublicosBrechaIdentificadaList);
        for (List<IndicadorBrecha> i:indicadorBrechaListList) {
            indicadorBrechaRepository.saveAll(i);
        }
    }

    public Formulario getFormulario5B(Long id) {
        Usuario u = usuarioRepository.findById(1L).orElse(null);
        assert u != null;
        return u.getFormularios().get(id.intValue()-1);
    }

    public String saveForm5A(String token, Formulario5ADatos formulario5ADatos){
        return null;
    }
}
