package com.bus.repository;

import com.bus.controller.comandos.DeviceTypeComando;
import com.bus.entity.DeviceType;
import java.util.List;

/**
 * Interface usada para dar gestión a los objetos tipo DeviceType
 */
public interface DeviceTypeRepository {
    DeviceType crear (DeviceType deviceType);

    DeviceType buscarPorId(Long idDeviceType);

    List<DeviceTypeComando> listar();

    void actualizar (DeviceType deviceType);

    void borrar (Long idDeviceType);
}
