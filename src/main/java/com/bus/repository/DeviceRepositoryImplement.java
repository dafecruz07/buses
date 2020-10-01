package com.bus.repository;

import com.bus.controller.comandos.DeviceComando;
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
 * Clase que implementa la Interface de Device y detalla el funcionamiento de cada uno de
 * sus metodos al ser Singleton solo puede ser instanciada una vez y esta se encarga de gestionar
 * el llamado a cada uno de sus metodos
 */
@Singleton
@Repository
public class DeviceRepositoryImplement implements DeviceRepository{

    //Objeto utilizado para gestionar la presistencia con la BD
    private EntityManager entityManager;

    /**
     * Constructor del objeto
     * @param entityManager Usado para dar gestión a la persistencia en Bd
     */
    public DeviceRepositoryImplement(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Metodo crear usado para registrar en BD el objeto enviado
     * @param device Objeto que sera registrado en BD por el EntityManager
     * @return Se retorna el objeto registrado
     */
    @Override
    @Transactional
    @NonBlocking
    public Device crear(Device device) {
        if(device != null){
            entityManager.persist(device);
        }
        return device;
    }

    /**
     * Metodo usado para buscar en DB un device por su Id
     * @param idDevice Id del device a buscar
     * @return Objeto tipo Device encontrado en BD
     */
    @Override
    @Transactional(readOnly = true)
    @NonBlocking
    public Device buscarPorId(Long idDevice) {
        return entityManager.find(Device.class, idDevice);
    }

    /**
     * Metodo usado para listar todos los Devices registrados en BD
     * @return Lista de devices registrados en DB
     */
    @Override
    @Transactional(readOnly = true)
    @NonBlocking
    public List<DeviceComando> listar() {
        String qlString = "SELECT d FROM Device as d";
        TypedQuery<Device> query = entityManager.createQuery(qlString, Device.class);
        List<Device> list = query.getResultList();
        List<DeviceComando>  response= new ArrayList<DeviceComando>();
        for(Device device:list){
            //System.out.println(device.getBus().toString());
            response.add(device.convertToDeviceComando());
        }
        System.out.println(response);
        return response;
    }

    /**
     * Metodo usado para borrar un objeto de BD
     * @param idDevice Id del device a borrar
     */
    @Override
    @Transactional
    @NonBlocking
    public void borrar(Long idDevice) {
        entityManager.remove(buscarPorId(idDevice));
    }

    /**
     * Metodo usado para actualizar un device en Bd
     * @param device Objeto device con datos modificados para reflejar sus cambios en Bd
     */
    @Override
    @Transactional
    @NonBlocking
    public void actualizar(Device device) {
        String stringQuery = "UPDATE Device " +
                "SET ip =:ip," +
                "status =:status" +
                " WHERE id=:id";
        System.out.println(device.getId() + "ID DEL QUERY A EJECUTAR");
        entityManager.createQuery(stringQuery)
                .setParameter("ip", device.getIp())
                .setParameter("status", device.getStatus())
                .setParameter("id", device.getId())
                .executeUpdate();
    }
}
