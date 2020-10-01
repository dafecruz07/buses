package com.bus.repository;

import com.bus.controller.comandos.BusComando;
import com.bus.controller.comandos.DeviceComando;
import com.bus.entity.Bus;
import com.bus.entity.Device;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.core.annotation.NonBlocking;
import io.micronaut.spring.tx.annotation.Transactional;
import org.springframework.stereotype.Repository;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa la Interface de bus y detalla el funcionamiento de cada uno de sus metodos
 * Al ser Singleton solo puede ser instanciada una vez y esta se encarga de gestionar el llamado a
 * cada uno de susmetodos
 */
@Singleton
@Repository
public class BusRepositoryImplement implements BusRepository{

    //Objeto utilizado para gestionar la presistencia con la BD
    private EntityManager entityManager;

    /**
     * Constructor del objeto
     * @param entityManager Usado para dar gestión a la persistencia en Bd
     */
    public BusRepositoryImplement(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Metodo crear usado para registrar en BD el objeto enviado
     * @param bus Objeto que sera registrado en BD por el EntityManager
     * @return Se retorna el objeto registrado
     */
    @Override
    @Transactional
    @NonBlocking
    public Bus crear(Bus bus) {
        if(bus != null){
            entityManager.persist(bus);
        }
        return bus;
    }

    /**
     * Metodo usado para buscar en DB un bus por su Id
     * @param idBus Id del bus a buscar
     * @return Objeto tipo Bus encontrado en BD
     */
    @Override
    @Transactional(readOnly = true)
    @NonBlocking
    public Bus buscarPorId(Long idBus) {
        return entityManager.find(Bus.class, idBus);
    }

    /**
     * Metodo usado para listar todos los buses registrado en BD
     * @return Lista de buses registrados en DB
     */
    @Override
    @Transactional(readOnly = true)
    @NonBlocking
    public List<BusComando> listar() {
        String qlString = "SELECT b FROM Bus as b";
        TypedQuery<Bus> query = entityManager.createQuery(qlString, Bus.class);
        List<Bus> list = query.getResultList();
        List<BusComando>  response= new ArrayList<BusComando>();
        for(Bus bus:list){
            response.add(bus.convertToBusComando());
        }
        System.out.println(list);
        return response;
    }

    /**
     * Metodo usado para borrar un objeto de BD
     * @param idBus Id del bus a borrar
     */
    @Override
    @Transactional
    @NonBlocking
    public void borrar(Long idBus) {
        entityManager.remove(buscarPorId(idBus));
    }

    /**
     * Metodo usado para actualizar un bus en Bd
     * @param bus Objeto Bus con datos modificados para reflejar sus cambios en Bd
     */
    @Override
    @Transactional
    @NonBlocking
    public void actualizar(Bus bus) {
        String stringQuery = "UPDATE Bus " +
                "SET type =:type," +
                "motor =:motor," +
                "brakes =:brakes" +
                " WHERE id=:id";
        entityManager.createQuery(stringQuery)
                .setParameter("type", bus.getType())
                .setParameter("motor", bus.getMotor())
                .setParameter("brakes", bus.getBrakes())
                .setParameter("id", bus.getId())
                .executeUpdate();
    }

    /**
     * Meotodo usado para encontrar un bus en Bd usando como criterio de busqueda su Id
     * @param idBus Id del bus a buscar
     * @return Objeto tipo bus con datos del Bus encontrado en Bd
     */
    @Override
    @Transactional
    @NonBlocking
    public List<DeviceComando> findDevices(Long idBus) {

        String stringQuery = "SELECT d FROM Device AS d " +
                             "WHERE busid =:id";

        TypedQuery<Device> query = entityManager.createQuery(stringQuery, Device.class);
        query.setParameter("id", idBus);
        List<Device> list = query.getResultList();
        List<DeviceComando>  response= new ArrayList<DeviceComando>();
        for(Device device:list){
            response.add(device.convertToDeviceComando());
        }
        System.out.println(list);
        return response;
    }
}