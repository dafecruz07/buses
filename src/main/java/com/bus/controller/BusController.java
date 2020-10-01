package com.bus.controller;


import com.bus.controller.comandos.BusComando;
import com.bus.controller.comandos.DeviceComando;
import com.bus.entity.Bus;
import com.bus.repository.BusRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.reactivex.Maybe;
import javax.inject.Inject;
import java.util.List;

/**
 * Clase controlador usada para crear los endpoint asociados al CRUD y en este caso particular
 * listar Devices asociados a un Bus
 * ruta (localhost:8080/bus/)
 */
@Controller("/bus")
public class BusController {

    @Inject
    private BusRepository busRepository;

    /**
     * Endpoint getAll encargado de listar todos los objetos Bus registrado
     * @return Lista de objetos registrados
     * Ruta (localhost:8080/bus/all)
     */
    @Get("/all")
    public Maybe<?> getAll() {

        Maybe<?> response = Maybe.create(emitter -> {
            try {
                List<BusComando> list = busRepository.listar();
                System.out.println("List"+list);
                System.out.println("Value" + list.get(0));
                emitter.onSuccess(list);
            }
            catch(Exception exception) {
                emitter.onError(exception);
            }
        });
        return response;
    }

    /**
     * Endpoint crear encargado de registrar un objeto Bus en Bd
     * @param bus El objeto a registrar
     * @return El objeto registrado
     * Ruta (localhost:8080/bus/crear)
     */
    @Put("/crear")
    public BusComando crear (BusComando bus){
        System.out.println(bus.toString());
        Bus b = busRepository.crear(bus.ConvertToBus());
        return b.convertToBusComando();
    }

    /**
     * Endpoint borrar encargado de borrar un objeto Bus de Bd
     * @param idBus Indentificador del Bus a borrar
     * @return Objeto con la respuesta obtenida del servidor
     * Ruta (localhost:8080/bus/borrar/{idBus})
     */
    @Delete("/borrar/{idBus}")
    public Maybe<?> borrar (Long idBus) {
        System.out.println(idBus);
        Maybe<?> response = Maybe.create(emitter -> {
            try {
                busRepository.borrar(idBus);
                emitter.onSuccess(HttpResponse.ok());
            }
            catch(Exception exception) {
                emitter.onError(exception);
            }
        });
        return response;
    }

    /**
     * Endpint actualizar encargado de registrar los cmabios realizados a un objeto Bus
     * @param busComando Objeto con valores modificados
     * @return Objeto con la respuesta obtenida del servidor
     * Ruta (localhost:8080/bus/actualizar)
     */
    @Post("/actualizar")
    public Maybe<?> actualizar (BusComando busComando) {
        System.out.println(busComando);
        Maybe<?> response = Maybe.create(emitter -> {
            try {
                busRepository.actualizar(busComando.ConvertToBus());
                emitter.onSuccess(HttpResponse.ok());
            }
            catch(Exception exception) {
                emitter.onError(exception);
            }
        });
        return response;
    }

    /**
     * endpoint buscarPorId encargado de buscar un objeto Bus en Bd
     * @param id Id del bus a buscar
     * @return Objeto conteniendo la información del bus en Db
     * Ruta (localhost:8080/bus/{idBus})
     */
    @Get("/{id}")
    public Maybe<?> buscarPorId (Long id){
        Maybe<?> response = Maybe.create(emitter -> {
            try {
                Bus bus = busRepository.buscarPorId(id);
                System.out.println(bus.toString());
                emitter.onSuccess(bus);
            }
            catch(Exception exception) {
                emitter.onError(exception);
            }

        });
        return response;
    }

    /**
     * Endpoint busDevice encargado de buscar los devices asociados a un Bus en Bd
     * @param busId Id del bus del cual se den listar los devices
     * @return Listar de Devices asociados al Bus
     * Ruta (localhost:8080/bus/busDevice/{busId})
     */
    @Get("/busDevice/{busId}")
    public List<DeviceComando> getBusDevice(Long busId) {
        List<DeviceComando> devices = busRepository.findDevices(busId);

        if(devices == null) {
            throw new RuntimeException("Bus id not found -"+busId);
        }
        return devices;
    }
}