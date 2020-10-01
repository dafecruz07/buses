package com.bus.controller.comandos;

import com.bus.entity.DeviceType;
import io.micronaut.core.annotation.Introspected;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase espejo de DeviceType usada por el controlador para gestionar la información con el servidor
 */
@Introspected
public class DeviceTypeComando {
    @Nullable
    private Long id;

    private String name;

    @Nullable
    private List<DeviceComando> devicesComando = new ArrayList<>();

    public DeviceTypeComando(@Nullable Long id, String name, @Nullable List<DeviceComando> deviceComando) {
        this.id = id;
        this.name = name;
        this.devicesComando = deviceComando;
    }

    public DeviceTypeComando(@Nullable Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public DeviceTypeComando(String name) {
        this.name = name;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public List<DeviceComando> getDevicesComando() {
        return devicesComando;
    }

    public void setDevicesComando(@Nullable List<DeviceComando> devicesComando) {
        this.devicesComando = devicesComando;
    }

    @Override
    public String toString() {
        return "DeviceTypeComando{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", devicesComando=" + devicesComando +
                '}';
    }

    /**
     * Metodo usado para convertir el DeviceTypeComando a DeviceType
     * @return Objeto DeviceType con los datos del DeviceTypeComando
     */
    public DeviceType convertToDeviceType () {
        return new DeviceType(id, name);
    }

}
