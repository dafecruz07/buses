package com.bus.controller;

import com.bus.controller.comandos.DeviceComando;
import com.bus.entity.Device;
import com.bus.repository.DeviceRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.reactivex.Maybe;
import javax.inject.Inject;
import java.util.List;

/**
 * Clase controlador usada para crear los endpoint asociados al CRUD
 * ruta (localhost:8080/device/)
 */
@Controller("/device")
public class DeviceController {

    @Inject
    private DeviceRepository deviceRepository;

    /**
     * Endpoint getAll encargado de listar todos los objetos Device registrados
     * @return Lista de objetos registrados
     * Ruta (localhost:8080/device/all)
     */
    @Get("/all")
    public Maybe<?> getAll() {
        Maybe<?> response = Maybe.create(emitter -> {
            try {
                List<DeviceComando> list = deviceRepository.listar();
                //System.out.println("List"+list);
                //System.out.println("Value" + list.get(0));
                emitter.onSuccess(list);
            }
            catch(Exception exception) {
                emitter.onError(exception);
            }
        });
        return response;
    }

    /**
     * Endpoint crear encargado de registrar un objeto Device en Bd
     * @param deviceComando El objeto a registrar
     * @return El objeto registrado
     * Ruta (localhost:8080/device/crear)
     */
    @Put("/crear")
    public DeviceComando crear (DeviceComando deviceComando){
        System.out.println(deviceComando.toString());
        Device device = deviceRepository.crear(deviceComando.convertToDevice());
        return device.convertToDeviceComando();
    }

    /**
     * Endpoint borrar encargado de borrar un objeto Device de Bd
     * @param idDevice Indentificador del device a borrar
     * @return Objeto con la respuesta obtenida del servidor
     * Ruta (localhost:8080/device/borrar/{idDevice})
     */
    @Delete("/borrar/{idDevice}")
    public Maybe<?> borrar (Long idDevice) {
        System.out.println(idDevice);
        Maybe<?> response = Maybe.create(emitter -> {
            try {
                deviceRepository.borrar(idDevice);
                emitter.onSuccess(HttpResponse.ok());
            }
            catch(Exception exception) {
                emitter.onError(exception);
            }
        });
        return response;
    }

    /**
     * endpoint buscarPorId encargado de buscar un objeto Device en Bd
     * @param id Id del device a buscar
     * @return Objeto conteniendo la información del device en Db
     * Ruta (localhost:8080/device/{id})
     */
    @Get("/{id}")
    public Maybe<?> buscarPorId (Long id){
        Maybe<?> response = Maybe.create(emitter -> {
            try {
                Device device = deviceRepository.buscarPorId(id);
                System.out.println(device.toString());
                emitter.onSuccess(device);
            }
            catch(Exception exception) {
                emitter.onError(exception);
            }
        });
        return response;
    }

    /**
     * Endpint actualizar encargado de registrar los cambios realizados a un objeto Device
     * @param deviceComando Objeto con valores modificados
     * @return Objeto con la respuesta obtenida del servidor
     * Ruta (localhost:8080/device/actualizar)
     */
    @Post("/actualizar")
    public Maybe<?> actualizar (DeviceComando deviceComando) {
        System.out.println(deviceComando);
        Maybe<?> response = Maybe.create(emitter -> {
            try {
                deviceRepository.actualizar(deviceComando.convertToDevice());
                emitter.onSuccess(HttpResponse.ok());
            }
            catch(Exception exception) {
                emitter.onError(exception);
            }
        });
        return response;
    }
}
