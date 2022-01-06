package dinve.mesa.converter;

import dinve.mesa.model.descripcion_agregada_proyecto_programa.*;
import lombok.Getter;
import lombok.Setter;
import dinve.mesa.model.Formulario;
import dinve.mesa.model.alineamiento_brecha_prioritaria.AlineamientoBrechaServiciosPublicosBrechaIdentificada;
import dinve.mesa.model.alineamiento_brecha_prioritaria.IndicadorBrecha;
import dinve.mesa.model.formulario_tipos.Adjunto;
import dinve.mesa.model.formulario_tipos.Formulario5A;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Formulario5ADatos {
    private Long id_user;
    //Datos para Formulario
    private Long id_form;
    private byte estado;
    private byte estado2;

    public Formulario getFormulario(){
        Formulario formulario = new Formulario();
        formulario.setId(id_form);
        formulario.setEstado(estado);
        formulario.setEstado2(estado2);
        return formulario;
    }
    //Datos para Formulario5A
    private Long id_form5a;
    private String nombre_idea;
    private String funcional_division;
    private String funcional_funcion;
    private String funcional_sector;
    private String funcional_grupo;
    private String tipologia_proyecto;
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
    private float costo_aproximado_estudio_preinversion;
    private int ejecucion_tentativa;
    private int fuente_financiamiento;

    public Formulario5A getFormulario5A(){
        Formulario5A formulario5A = new Formulario5A();
        formulario5A.setId(id_form5a);
        formulario5A.setNombre_idea(nombre_idea);
        formulario5A.setFuncional_division(funcional_division);
        formulario5A.setFuncional_funcion(funcional_funcion);
        formulario5A.setFuncional_sector(funcional_sector);
        formulario5A.setFuncional_grupo(funcional_grupo);
        formulario5A.setUf_sector(uf_sector);
        formulario5A.setUf_entidad(uf_entidad);
        formulario5A.setUf_nombre(uf_nombre);
        formulario5A.setUf_responsable(uf_responsable);
        formulario5A.setUfi_sector(ufi_sector);
        formulario5A.setUfi_entidad(ufi_entidad);
        formulario5A.setUfi_nombre(ufi_nombre);
        formulario5A.setUfi_responsable(ufi_responsable);
        formulario5A.setUfp_sector(ufp_sector);
        formulario5A.setUfp_entidad(ufp_entidad);
        formulario5A.setUfp_nombre(ufp_nombre);
        formulario5A.setNaturaleza_intervencion(naturaleza_intervencion);
        formulario5A.setLocalizacion_geografica(localizacion_geografica);
        formulario5A.setCosto_aproximado_estudio_preinversion(costo_aproximado_estudio_preinversion);
        formulario5A.setEjecucion_tentativa(ejecucion_tentativa);
        formulario5A.setFuente_financiamiento(fuente_financiamiento);
        return formulario5A;
    }

    //Datos para ResponsabilidadFuncionalDescripcionAgregada
    private List<Long> id_resp;

    public List<ResponsabilidadFuncionalDescripcionAgregada> getListaResponsabilidadFuncionalDescripcionAgregada(){
        List<ResponsabilidadFuncionalDescripcionAgregada> lista = new ArrayList<>();
        for(int i=0;i<id_resp.size();i++){
            ResponsabilidadFuncionalDescripcionAgregada resp = new ResponsabilidadFuncionalDescripcionAgregada();
            resp.setId(id_resp.get(i));
            lista.add(resp);
        }
        return lista;
    }

    //Datos para ProgramaDeInversion
    private List<Long> id_programa;
    private List<Float> total_proyectos;
    private List<Float> total_ioarr;
    private List<Float> gestion_programa;
    private List<Float> estudios_base_ioarr;
    private List<Float> total;

    public List<ProgramaDeInversion> getListaProgramaDeInversion(){
        List<ProgramaDeInversion> lista = new ArrayList<>();
        for(int i=0;i<id_programa.size();i++){
            ProgramaDeInversion pro = new ProgramaDeInversion();
            pro.setId(id_programa.get(i));
            pro.setTotal_proyectos(total_proyectos.get(i));
            pro.setTotal_ioarr(total_ioarr.get(i));
            pro.setGestion_programa(gestion_programa.get(i));
            pro.setEstudios_base_ioarr(estudios_base_ioarr.get(i));
            pro.setTotal(total.get(i));
            lista.add(pro);
        }
        return lista;
    }

    //Datos para ProyectoDeInversion
    private List<Long> id_proyecto;
    private List<Float> costo;

    public List<ProyectoDeInversion> getListaProyectoDeInversion(){
        List<ProyectoDeInversion> lista = new ArrayList<>();
        for(int i=0;i<id_proyecto.size();i++){
            ProyectoDeInversion pro = new ProyectoDeInversion();
            pro.setId(id_proyecto.get(i));
            pro.setCosto(costo.get(i));
            lista.add(pro);
        }
        return lista;
    }

    //Datos para TipoItem
    private List<List<Long>> id_tipo;
    private List<List<String>> item;
    private List<List<Float>> tipo_costo;

    public List<List<TipoItem>> getListaDeListaDeTipoItem(){
        List<List<TipoItem>> lista = new ArrayList<>();
        for(int i=0;i<id_tipo.size();i++){
            List<TipoItem> listaTipoItem = new ArrayList<>();
            for(int j=0;j<id_tipo.get(i).size();j++){
                TipoItem tipoItem = new TipoItem();
                tipoItem.setId(id_tipo.get(i).get(j));
                tipoItem.setItem(item.get(i).get(j));
                tipoItem.setCosto(tipo_costo.get(i).get(j));
                listaTipoItem.add(tipoItem);
            }
            lista.add(listaTipoItem);
        }
        return lista;
    }

    //Datos para Capacidad
    private List<List<Long>> id_capacidad;
    private List<List<String>> servicio;
    private List<List<String>> um;
    private List<List<String>> capacidad_produccion;

    public List<List<Capacidad>> getListaDeListaDeCapacidad(){
        List<List<Capacidad>> lista = new ArrayList<>();
        for(int i=0;i<id_capacidad.size();i++){
            List<Capacidad> listaCapacidad = new ArrayList<>();
            for(int j=0;j<id_capacidad.get(i).size();j++){
                Capacidad cap = new Capacidad();
                cap.setId(id_capacidad.get(i).get(j));
                cap.setServicio(servicio.get(i).get(j));
                cap.setUm(um.get(i).get(j));
                cap.setCapacidad_produccion(capacidad_produccion.get(i).get(j));
                listaCapacidad.add(cap);
            }
            lista.add(listaCapacidad);
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
