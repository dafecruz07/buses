package com.bus.controller;

import com.bus.controller.comandos.DeviceTypeComando;
import com.bus.entity.DeviceType;
import com.bus.repository.DeviceTypeRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.reactivex.Maybe;
import javax.inject.Inject;
import java.util.List;

/**
 * Clase controlador usada para crear los endpoint asociados al CRUD
 * ruta (localhost:8080/deviceType/)
 */
@Controller("/deviceType")
public class DeviceTypeController {

    @Inject
    private DeviceTypeRepository deviceTypeRepository;

    /**
     * Endpoint crear encargado de registrar un objeto DeviceType en Bd
     * @param deviceTypeComando El objeto a registrar
     * @return El objeto registrado
     * Ruta (localhost:8080/deviceType/crear)
     */
    @Put("/crear")
    public DeviceTypeComando crear (DeviceTypeComando deviceTypeComando) {
        System.out.println(deviceTypeComando.toString());
        DeviceType d = deviceTypeRepository.crear(deviceTypeComando.convertToDeviceType());
        return d.convertToDeviceTypeComando();
    }

    /**
     * Endpoint getAll encargado de listar todos los objetos DeviceType registrados
     * @return Lista de objetos registrados
     * Ruta (localhost:8080/deviceType/all)
     */
    @Get("/all")
    public Maybe<?> getAll() {
        Maybe<?> response = Maybe.create(emitter -> {
            try {
                List<DeviceTypeComando> list = deviceTypeRepository.listar();
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
     * Endpoint borrar encargado de borrar un objeto DeviceType de Bd
     * @param idDeviceType Indentificador del deviceType a borrar
     * @return Objeto con la respuesta obtenida del servidor
     * Ruta (localhost:8080/deviceType/borrar/{idDeviceType})
     */
    @Delete("/borrar/{idDeviceType}")
    public Maybe<?> borrar (Long idDeviceType) {
        System.out.println(idDeviceType);
        Maybe<?> response = Maybe.create(emitter -> {
            try {
                deviceTypeRepository.borrar(idDeviceType);
                emitter.onSuccess(HttpResponse.ok());
            }
            catch(Exception exception) {
                emitter.onError(exception);
            }
        });
        return response;
    }

    /**
     * endpoint buscarPorId encargado de buscar un objeto DeviceType en Bd
     * @param id Id del deviceType a buscar
     * @return Objeto conteniendo la información del device en Db
     * Ruta (localhost:8080/deviceType/{id})
     */
    @Get("/{id}")
    public Maybe<?> buscarPorId (Long id){
        Maybe<?> response = Maybe.create(emitter -> {
            try {
                DeviceType deviceType = deviceTypeRepository.buscarPorId(id);
                System.out.println(deviceType.toString());
                emitter.onSuccess(deviceType);
            }
            catch(Exception exception) {
                emitter.onError(exception);
            }
        });
        return response;
    }

    /**
     * Endpoint actualizar encargado de registrar los cambios realizados a un objeto DeviceType
     * @param deviceTypeComando Objeto con valores modificados
     * @return Objeto con la respuesta obtenida del servidor
     * Ruta (localhost:8080/deviceType/actualizar)
     */
    @Post("/actualizar")
    public Maybe<?> actualizar (DeviceTypeComando deviceTypeComando) {
        System.out.println(deviceTypeComando);
        Maybe<?> response = Maybe.create(emitter -> {
            try {
                deviceTypeRepository.actualizar(deviceTypeComando.convertToDeviceType());
                emitter.onSuccess(HttpResponse.ok());
            }
            catch(Exception exception) {
                emitter.onError(exception);
            }
        });
        return response;
    }
}
