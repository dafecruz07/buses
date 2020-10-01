package com.bus.controller.comandos;

import com.bus.entity.Bus;
import com.bus.entity.Concessionaire;
import io.micronaut.core.annotation.Introspected;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase espejo de Concessionaire usada por el controlador para gestionar la información con el servidor
 */
@Introspected
public class ConcessionaireComando {
    @Nullable
    private Long id;

    private String name;

    @Nullable
    private List<BusComando> busesComando= new ArrayList<>();

    public ConcessionaireComando(Long id, String name, List<BusComando> busesComando) {
        this.id = id;
        this.name = name;
        this.busesComando = busesComando;
    }

    public ConcessionaireComando(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ConcessionaireComando(String name) {
        this.name = name;
    }

    public ConcessionaireComando(String name, List<BusComando> busesComando) {
        this.name = name;
        this.busesComando = busesComando;
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
    public List<BusComando> getBusesComando() {
        return busesComando;
    }

    @Override
    public String toString() {
        return "ConcessionaireComando{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", busesComando=" + busesComando +
                '}';
    }

    public void setBusesComando(@Nullable List<BusComando> busesComando) {
        this.busesComando = busesComando;
    }

    /**
     * Metodo usado para convertir el ConcessionaireComando a Concessionaire
     * @return Objeto Concessionaire con los datos del ConcessionaireComando
     */
    public Concessionaire ConvertToConcessionaire () {
        return new Concessionaire(id, name);
    }

}
