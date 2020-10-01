package com.bus.entity;

import com.bus.controller.comandos.DeviceComando;
import com.bus.controller.comandos.DeviceTypeComando;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DeviceType usada para instanciar los objetos de la tabla bus en BD
 */
@Entity
@Table(name = "devicetype")
public class DeviceType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonInclude
    private Long id;

    @Column(name = "name", nullable = true)
    @JsonInclude
    private String name;

    //Lista de dispositivos del DeviceType, cada uno de ellos apunta en DB hacia el deviceType
    //Con el atributo deviceTypeIdId
    @OneToMany(mappedBy = "deviceType", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("deviceType")
    private List<Device> devices= new ArrayList<>();

    public DeviceType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public DeviceType(String name) {
        this.name = name;
    }

    public DeviceType() {
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

    @Override
    public String toString() {
        return "DeviceType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", devices=" + devices +
                '}';
    }

    /**
     * Metodo que convierte el DeviceType a DeviceTypeComando el cual es usado por el Controlador
     * para enviar y recibir información del servidor
     * @return DeviceTypeComando con los datos del DeviceType
     */
    public DeviceTypeComando convertToDeviceTypeComando () {
        if (devices == null) {
            return new DeviceTypeComando(id, name);
        } else {
            List<DeviceComando> devicesComandos= new ArrayList<>();
            for (Device d:devices){
                devicesComandos.add(new DeviceComando(d.getId(),d.getIp(), null,
                                    d.getBus() , d.getStatus()));
            }
            return new DeviceTypeComando(id, name, devicesComandos);
        }
    }

    /**
     * Metodo que convierte el DeviceType a DeviceTypeComando el cual es usado por el Controlador
     * para enviar y recibir información del servidor, en este caso no envia información de los
     * Devices para evitar un ciclo
     * @return DeviceTypeComando con los datos del DeviceType
     */
    public DeviceTypeComando convertToDeviceTypeComandoDesdeDevice() {
        return new DeviceTypeComando(id, name);
    }
}
