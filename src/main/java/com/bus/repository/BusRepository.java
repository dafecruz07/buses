package com.bus.repository;

import com.bus.controller.comandos.BusComando;
import com.bus.controller.comandos.DeviceComando;
import com.bus.entity.Bus;
import java.util.List;

/**
 * Interface usada para dar gestión a los objetos tipo Bus
 */
public interface BusRepository {

    Bus crear(Bus bus);

    Bus buscarPorId(Long idBus);

    List<BusComando> listar();

    void borrar (Long idBus);

    void actualizar (Bus bus);

    List<DeviceComando> findDevices(Long idBus);

}
