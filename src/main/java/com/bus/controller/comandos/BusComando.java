package com.bus.controller.comandos;


import com.bus.entity.Bus;
import io.micronaut.core.annotation.Introspected;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase espejo de Bus usada por el controlador para gestionar la información con el servidor
 */
@Introspected
public class BusComando {

    @Nullable
    private Long id;
    private String motor ;
    private String brakes ;
    private ConcessionaireComando concessionaireComando ;
    private String Type ;
    @Nullable
    private List<DeviceComando> deviceComandos= new ArrayList<>();

    public BusComando(Long id, String motor, String brakes, ConcessionaireComando concessionaireComando, String type, List<DeviceComando> deviceComandos) {
        this.id = id;
        this.motor = motor;
        this.brakes = brakes;
        this.concessionaireComando = concessionaireComando;
        this.Type = type;
        this.deviceComandos = deviceComandos;
    }

    public BusComando(@Nullable Long id, String motor, String brakes, ConcessionaireComando concessionaireComando, String type) {
        this.id = id;
        this.motor = motor;
        this.brakes = brakes;
        this.concessionaireComando = concessionaireComando;
        Type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusComando() {
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getBrakes() {
        return brakes;
    }

    public void setBrakes(String brakes) {
        this.brakes = brakes;
    }

    public ConcessionaireComando getConcessionaireid() {
        return concessionaireComando;
    }

    public void setConcessionaireid(ConcessionaireComando concessionaireComando) {
        this.concessionaireComando = concessionaireComando;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public String toString() {
        return "BusComando{" +
                "motor='" + motor + '\'' +
                ", brakes='" + brakes + '\'' +
                ", concessionaireComando=" + concessionaireComando +
                ", Type='" + Type + '\'' +
                '}';
    }

    /**
     * Metodo usado para convertir el BusComando a Bus
     * @return Objeto Bus con los datos del Bus Comando
     */
    public Bus ConvertToBus(){
        Bus b;
        if (concessionaireComando == null) {
            b = new Bus(Type,  motor,  brakes, null, null);
        } else {
            b = new Bus(Type,  motor,  brakes,concessionaireComando.ConvertToConcessionaire(), null);
        }
        b.setId(id);
        return b;
    }
}
