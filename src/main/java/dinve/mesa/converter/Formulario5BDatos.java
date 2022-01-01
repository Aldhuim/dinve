package dinve.mesa.converter;

import lombok.Getter;
import lombok.Setter;
import dinve.mesa.model.Formulario;
import dinve.mesa.model.alineamiento_brecha_prioritaria.AlineamientoBrechaServiciosPublicosBrechaIdentificada;
import dinve.mesa.model.alineamiento_brecha_prioritaria.IndicadorBrecha;
import dinve.mesa.model.descripcion_agregada_ioarr.ResponsabilidadFuncionalDescripcionIOARR;
import dinve.mesa.model.formulario.Adjunto;
import dinve.mesa.model.formulario.Formulario5B;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Formulario5BDatos {
    private Long id_user;
    //Datos para Formulario
    private Long id_form;
    private byte estado;
    private byte estado2;

    public Formulario getFormulario(){
        Formulario formulario = new Formulario();
        formulario.setId(id_form);
        formulario.setEstado(estado);
        formulario.setEstado2(estado);
        return formulario;
    }
    //Datos para Formulario5B
    private Long id_form5b;
    private String nombre_idea;
    private String funcional_division;
    private String funcional_funcion;
    private String funcional_sector;
    private String funcional_grupo;
    private String uf_sector;
    private String uf_entidad;
    private String uf_nombre;
    private String uf_responsable;
    private String ufi_sector;
    private String ufi_entidad;
    private String ufi_nombre;
    private String ufi_responsable;
    private String ufp_sector;
    private String ufp_entidad;
    private String ufp_nombre;
    private String naturaleza_intervencion;
    private String localizacion_geografica;
    private int modalidad_ejecucion;
    private int fuente_financiamiento;

    public Formulario5B getFormulario5B(){
        Formulario5B formulario5B = new Formulario5B();
        formulario5B.setId(id_form5b);
        formulario5B.setNombre_idea(nombre_idea);
        formulario5B.setFuncional_division(funcional_division);
        formulario5B.setFuncional_funcion(funcional_funcion);
        formulario5B.setFuncional_sector(funcional_sector);
        formulario5B.setFuncional_grupo(funcional_grupo);
        formulario5B.setUf_sector(uf_sector);
        formulario5B.setUf_entidad(uf_entidad);
        formulario5B.setUf_nombre(uf_nombre);
        formulario5B.setUf_responsable(uf_responsable);
        formulario5B.setUfi_sector(ufi_sector);
        formulario5B.setUfi_entidad(ufi_entidad);
        formulario5B.setUfi_nombre(ufi_nombre);
        formulario5B.setUfi_responsable(ufi_responsable);
        formulario5B.setUfp_sector(ufp_sector);
        formulario5B.setUfp_entidad(ufp_entidad);
        formulario5B.setUfp_nombre(ufp_nombre);
        formulario5B.setNaturaleza_intervencion(naturaleza_intervencion);
        formulario5B.setLocalizacion_geografica(localizacion_geografica);
        formulario5B.setModalidad_ejecucion(modalidad_ejecucion);
        formulario5B.setFuente_financiamiento(fuente_financiamiento);
        return formulario5B;

    }
    //Datos para ResponsabilidadFuncionalDescripcionIOARR
    private List<Long> id_resp;
    private List<String> tipo_resp;
    private List<Float> costo_resp;

    public List<ResponsabilidadFuncionalDescripcionIOARR> getListaResponsabilidadFuncionalDescripcionIOARR(){
        List<ResponsabilidadFuncionalDescripcionIOARR> lista = new ArrayList<>();
        for(int i=0;i<id_resp.size();i++){
            ResponsabilidadFuncionalDescripcionIOARR resp = new ResponsabilidadFuncionalDescripcionIOARR();
            resp.setId(id_resp.get(i));
            resp.setTipo(tipo_resp.get(i));
            resp.setCosto(costo_resp.get(i));
            lista.add(resp);
        }
        return lista;
    }
    //Datos para AlineamientoBrechaServiciosPublicosBrechaIdentificada
    private List<Long> id_ali;
    private List<String> nombre_ali;
    private List<Integer> contribucion_valor;

    public List<AlineamientoBrechaServiciosPublicosBrechaIdentificada> getListaAlineamientoBrechaServiciosPublicosBrechaIdentificada(){
        List<AlineamientoBrechaServiciosPublicosBrechaIdentificada> lista = new ArrayList<>();
        for(int i=0;i<id_ali.size();i++){
            AlineamientoBrechaServiciosPublicosBrechaIdentificada ali = new AlineamientoBrechaServiciosPublicosBrechaIdentificada();
            ali.setId(id_ali.get(i));
            ali.setNombre(nombre_ali.get(i));
            ali.setContribucion_valor(contribucion_valor.get(i));
            lista.add(ali);
        }
        return lista;
    }
    //Datos para IndicadorBrecha
    private List<List<Long>> id_indi;
    private List<List<String>> nombre_indi;
    private List<List<Integer>> unidad_medida;
    private List<List<String>> espacio_geografico;
    private List<List<Integer>> anno;
    private List<List<Float>> valor;

    public List<List<IndicadorBrecha>> getListaDeListaDeIndicadorBrecha(){
        List<List<IndicadorBrecha>> lista = new ArrayList<>();
        for(int i=0;i<id_indi.size();i++){//Me indica cuantos alineamiento hay(2)
            //Estoy en las brechas del primer alineamiento
            List<IndicadorBrecha> listaIndicadorBrecha = new ArrayList<>();
            for(int j=0;j<id_indi.get(i).size();j++){//Me indica cuantas brechas hay en el alineamiento 0
                IndicadorBrecha indicadorBrecha = new IndicadorBrecha();
                indicadorBrecha.setId(id_indi.get(i).get(j));
                indicadorBrecha.setNombre(nombre_indi.get(i).get(j));
                indicadorBrecha.setUnidad_medida(unidad_medida.get(i).get(j));
                indicadorBrecha.setEspacio_geografico(espacio_geografico.get(i).get(j));
                indicadorBrecha.setAnno(anno.get(i).get(j));
                indicadorBrecha.setValor(valor.get(i).get(j));
                listaIndicadorBrecha.add(indicadorBrecha);
            }
            lista.add(listaIndicadorBrecha);
        }
        return lista;
    }

    //Datos para Adjunto
    private List<Long> id_adjunto;
    private List<String> tipo_adjunto;
    private List<String> nombre;
    private List<String> direccion;

    public List<Adjunto> getListaAdjunto(){
        List<Adjunto> lista = new ArrayList<>();
        for(int i=0;i<id_adjunto.size();i++){
            Adjunto adj = new Adjunto();
            adj.setId(id_adjunto.get(i));
            adj.setTipo(tipo_adjunto.get(i));
            adj.setNombre(nombre.get(i));
            adj.setDireccion(direccion.get(i));
            lista.add(adj);
        }
        return lista;
    }
}
