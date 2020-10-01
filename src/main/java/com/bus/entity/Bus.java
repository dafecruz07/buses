package com.bus.entity;

import com.bus.controller.comandos.BusComando;
import com.bus.controller.comandos.DeviceComando;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Bus usada para instanciar los objetos de la tabla bus en BD
 */
@Entity
@Table(name = "bus")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonInclude
    private Long id;

    @Column(name = "type", nullable = true)
    @JsonInclude
    private String type;

    @Column(name = "motor", nullable = true)
    @JsonInclude
    private String motor;

    @Column(name = "brakes", nullable = true)
    @JsonInclude
    private String brakes;

    //Concesionario usado para hacer referencia en BD con su Id
    //hacia la tabla Concessionaire con el atributo concessionaireId
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="concessionaireid", nullable = false)
    @JsonIgnoreProperties("bus")
    private Concessionaire concessionaire;

    //Lista de dispositivos del bus, cada uno de ellos apunta en DB hacia el bus
    //Con el atributo busId
    @OneToMany(mappedBy = "bus", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("bus")
    private List<Device> devices = new ArrayList<>();

    public Bus(String type, String motor, String brakes, Concessionaire concessionaire, List<Device> devices) {
        this.type = type;
        this.motor = motor;
        this.brakes = brakes;
        this.concessionaire = concessionaire;
        this.devices = devices;
    }

    public Bus() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getBrakes() {
        return brakes;
    }

    public void setBrakes(String brakes) {
        this.brakes = brakes;
    }

    public Concessionaire getConcessionaire() {
        return concessionaire;
    }

    public void setConcessionaire(Concessionaire concessionaire) {
        this.concessionaire = concessionaire;
    }

    /**
     * Metodo toString usado para verificar la información contenida en cada Bus
     * @return cadena de texto con la información del Bus
     */
    @Override
    public String toString() {
        return "Bus{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", motor='" + motor + '\'' +
                ", brakes='" + brakes + '\'' +
                ", concessionaire=" + concessionaire +
                '}';
    }

    /**
     * Metodo que convierte el Bus a BusComando el cual es usado por el Controlador
     * para enviar y recibir información del servidor
     * @return busComando con los datos del Bus
     */
    public BusComando convertToBusComando (){
        //return new BusComando(id,  motor,  brakes, concessionaire == null ? null : concessionaire.convertToConcessionaireComandoDesdeBus(),  type);
        if (devices == null) {
            return new BusComando(id, motor, brakes,
                                  concessionaire.convertToConcessionaireComandoDesdeBus(), type);
        } else {
            List<DeviceComando> deviceComandos= new ArrayList<>();
            for (Device d:devices){
                deviceComandos.add(new DeviceComando(d.getId(), d.getIp(), null,
                                                     d.getBus() , d.getStatus()));
            }
            return new BusComando(id, motor, brakes, concessionaire.convertToConcessionaireComandoDesdeBus(),
                                    type, deviceComandos);
        }
    }

    /**
     * Metodo que convierte el Bus a BusComando el cual es usado por el Controlador
     * para enviar y recibir información del servidor, en este caso no envia información del
     * concessionaire para evitar un ciclo
     * @return busComando con los datos del Bus
     */
    public BusComando convertToBusComandoDesdeDevice () {
        return new BusComando(id,  motor,  brakes, null,type,  null);
    }
}
