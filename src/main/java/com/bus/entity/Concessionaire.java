package com.bus.entity;

import com.bus.controller.comandos.BusComando;
import com.bus.controller.comandos.ConcessionaireComando;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Concessionaire usada para instanciar los objetos de la tabla concessionaire en BD
 */
@Entity
@Table(name = "concessionaire")
public class Concessionaire {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonInclude
    private Long id;

    @Column(name = "name", nullable = true)
    @JsonInclude
    private String name;

    //Lista de buses asociados al concessionaire, cada uno de ellos apunta en DB hacia el cocessionaire
    //Con el atributo concessionaireId
    @OneToMany(mappedBy = "concessionaire", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("concessionaire")
    private List<Bus> buses= new ArrayList<>();

    public Concessionaire(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Concessionaire(String name) {
        this.name = name;
    }

    public Concessionaire() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metodo toString usado para verificar la información contenida en cada Concessionaire
     * @return cadena de texto con la información del Concessionaire
     */
    @Override
    public String toString() {
        return "Concessionaire{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * Metodo que convierte el Concessionaire a ConcessionaireComando el cual es usado por el Controlador
     * para enviar y recibir información del servidor
     * @return ConcessionaireComando con los datos del Concessionaire
     */
    public ConcessionaireComando convertToConcessionaireComando() {
        if (buses == null) {
            return new ConcessionaireComando(id, name);
        } else {
            List<BusComando> busesComandos= new ArrayList<>();
            for (Bus b:buses){
                busesComandos.add(new BusComando(b.getId(), b.getMotor(), b.getBrakes(), null, b.getType()));
            }
            return new ConcessionaireComando(id, name, busesComandos);
        }
    }

    /**
     * Metodo que convierte el Concessionaire a ConcessionaireComando el cual es usado por el Controlador
     * para enviar y recibir información del servidor, en este caso no envia información de los
     * Buses para evitar un ciclo
     * @return ConcessionaireComando con los datos del Concessionaire
     */
    public ConcessionaireComando convertToConcessionaireComandoDesdeBus() {
        return new ConcessionaireComando(id, name);
    }
}