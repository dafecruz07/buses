package com.bus.entity;

import com.bus.controller.comandos.DeviceComando;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.*;

/**
 * Clase Device usada para instanciar los objetos de la tabla device en BD
 */
@Entity
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonInclude
    private Long id;

    @Column(name = "ip", nullable = true)
    @JsonInclude
    private String ip;

    //DeviceType usado para hacer referencia en BD con su Id
    //hacia la tabla DeviceType con el atributo deviceTypeId
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="devicetypeid", nullable = false)
    @JsonIgnoreProperties("device")
    private DeviceType deviceType;

    //Bus usado para hacer referencia en BD con su Id
    //hacia la tabla Bus con el atributo busId
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="busid", nullable = false)
    @JsonIgnoreProperties("device")
    private Bus bus;

    @Column(name = "status", nullable = true)
    @JsonInclude
    private String status;

    public Device(String ip, DeviceType deviceType, Bus bus, String status) {
        this.ip = ip;
        this.deviceType = deviceType;
        this.bus = bus;
        this.status = status;
    }

    public Device() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Metodo toString usado para verificar la información contenida en cada Device
     * @return cadena de texto con la información del Device
     */
    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", deviceType=" + deviceType +
                ", bus=" + bus +
                ", status='" + status + '\'' +
                '}';
    }

    /**
     * Metodo que convierte el Device a DeviceComando el cual es usado por el Controlador
     * para enviar y recibir información del servidor
     * @return DeviceComando con los datos del Device
     */
    public DeviceComando convertToDeviceComando() {
        return new DeviceComando(id, ip, deviceType.convertToDeviceTypeComandoDesdeDevice(),
                                bus, status);
    }
}