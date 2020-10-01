package com.bus.controller;

import com.bus.controller.comandos.ConcessionaireComando;
import com.bus.entity.Concessionaire;
import com.bus.repository.ConcessionaireRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.reactivex.Maybe;
import javax.inject.Inject;
import java.util.List;

/**
 * Clase controlador usada para crear los endpoint asociados al CRUD
 * ruta (localhost:8080/concessionaire/)
 */
@Controller("/concessionaire")
public class ConcessionaireController {

    @Inject
    private ConcessionaireRepository concessionaireRepository;

    /**
     * Endpoint crear encargado de registrar un objeto Concessionaire en Bd
     * @param concessionaireComando El objeto a registrar
     * @return El objeto registrado
     * Ruta (localhost:8080/concessionaire/crear)
     */
    @Put("/crear")
    public ConcessionaireComando crear (ConcessionaireComando concessionaireComando) {
        System.out.println(concessionaireComando.toString());
        Concessionaire c = concessionaireRepository.crear(concessionaireComando.ConvertToConcessionaire());
        return c.convertToConcessionaireComando();
    }

    /**
     * Endpoint getAll encargado de listar todos los objetos Concessionaire registrado
     * @return Lista de objetos registrados
     * Ruta (localhost:8080/concessionaire/all)
     */
    @Get("/all")
    public Maybe<?> getAll() {

        Maybe<?> response = Maybe.create(emitter -> {
            try {
                List<ConcessionaireComando> list = concessionaireRepository.listar();
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
     * Endpoint borrar encargado de borrar un objeto Concessionaire de Bd
     * @param idConcessionaire Indentificador del concessionaire a borrar
     * @return Objeto con la respuesta obtenida del servidor
     * Ruta (localhost:8080/concessionaire/borrar/{idConcessionaire})
     */
    @Delete("/borrar/{idConcessionaire}")
    public Maybe<?> borrar (Long idConcessionaire) {
        System.out.println(idConcessionaire);
        Maybe<?> response = Maybe.create(emitter -> {
            try {
                concessionaireRepository.borrar(idConcessionaire);
                emitter.onSuccess(HttpResponse.ok());
            }
            catch(Exception exception) {
                emitter.onError(exception);
            }
        });
        return response;
    }

    /**
     * endpoint buscarPorId encargado de buscar un objeto Concessionaire en Bd
     * @param id Id del concessionaire a buscar
     * @return Objeto conteniendo la información del concessionaire en Db
     * Ruta (localhost:8080/concessionaire/{idConcessionaire})
     */
    @Get("/{id}")
    public Maybe<?> buscarPorId (Long id){
        Maybe<?> response = Maybe.create(emitter -> {
            try {
                Concessionaire concessionaire = concessionaireRepository.buscarPorId(id);
                System.out.println(concessionaire.toString());
                emitter.onSuccess(concessionaire);
            }
            catch(Exception exception) {
                emitter.onError(exception);
            }
        });
        return response;
    }

    /**
     * Endpint actualizar encargado de registrar los cmabios realizados a un objeto Concessionaire
     * @param concessionaireComando Objeto con valores modificados
     * @return Objeto con la respuesta obtenida del servidor
     * Ruta (localhost:8080/concessionaire/actualizar)
     */
    @Post("/actualizar")
    public Maybe<?> actualizar (ConcessionaireComando concessionaireComando) {
        System.out.println(concessionaireComando);
        Maybe<?> response = Maybe.create(emitter -> {
            try {
                concessionaireRepository.actualizar(concessionaireComando.ConvertToConcessionaire());
                emitter.onSuccess(HttpResponse.ok());
            }
            catch(Exception exception) {
                emitter.onError(exception);
            }
        });
        return response;
    }
}