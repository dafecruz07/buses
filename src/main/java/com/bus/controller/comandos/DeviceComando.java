package com.bus.controller.comandos;

import com.bus.entity.Bus;
import com.bus.entity.Device;
import io.micronaut.core.annotation.Introspected;

import javax.annotation.Nullable;

/**
 * Clase espejo de Device usada por el controlador para gestionar la información con el servidor
 */
@Introspected
public class DeviceComando {

    @Nullable
    private Long id;
    private String ip;
    private DeviceTypeComando deviceTypeComando;
    private Bus bus;
    private String status;

    public DeviceComando(@Nullable Long id, String ip, DeviceTypeComando deviceTypeComando,
                         Bus bus, String status) {
        this.id = id;
        this.ip = ip;
        this.deviceTypeComando = deviceTypeComando;
        this.bus = bus;
        this.status = status;
    }

    public DeviceComando() {
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public DeviceTypeComando getDeviceTypeComando() {
        return deviceTypeComando;
    }

    public void setDeviceTypeComando(DeviceTypeComando deviceTypeComando) {
        this.deviceTypeComando = deviceTypeComando;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    @Override
    public String toString() {
        return "DeviceComando{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", deviceType=" + deviceTypeComando +
                ", bus=" + bus +
                ", status='" + status + '\'' +
                '}';
    }

    /**
     * Metodo usado para convertir el DeviceComando a Device
     * @return Objeto Device con los datos del DeviceComando
     */
    public Device convertToDevice () {
        Device d = new Device(ip, deviceTypeComando.convertToDeviceType(), bus, status);
        d.setId(id);
        return d;
    }
}
