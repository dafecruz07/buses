package com.bus.repository;

import com.bus.controller.comandos.DeviceComando;
import com.bus.entity.Device;
import java.util.List;

/**
 * Interface usada para dar gestión a los objetos tipo Device
 */
public interface DeviceRepository {

    Device crear(Device device);

    Device buscarPorId(Long idDevice);

    List<DeviceComando> listar();

    void borrar (Long idDevice);

    void actualizar (Device device);
}
