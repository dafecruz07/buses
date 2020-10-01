package com.bus.repository;

import com.bus.controller.comandos.DeviceTypeComando;
import com.bus.entity.DeviceType;
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
 * Clase que implementa la Interface de DeviceType y detalla el funcionamiento de cada uno de
 * sus metodos al ser Singleton solo puede ser instanciada una vez y esta se encarga de gestionar
 * el llamado a cada uno de sus metodos
 */
@Singleton
@Repository
public class DeviceTypeRepositoryImplement implements DeviceTypeRepository{

    //Objeto utilizado para gestionar la presistencia con la BD
    private EntityManager entityManager;

    /**
     * Constructor del objeto
     * @param entityManager Usado para dar gestión a la persistencia en Bd
     */
    public DeviceTypeRepositoryImplement(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Metodo crear usado para registrar en BD el objeto enviado
     * @param deviceType Objeto que sera registrado en BD por el EntityManager
     * @return Se retorna el objeto registrado
     */
    @Override
    @Transactional
    @NonBlocking
    public DeviceType crear(DeviceType deviceType) {
        if (deviceType != null) {
            entityManager.persist(deviceType);
        }
        return deviceType;
    }

    /**
     * Metodo usado para buscar en DB un deviceType por su Id
     * @param idDeviceType Id del DeviceType a buscar
     * @return Objeto tipo DeviceType encontrado en BD
     */
    @Override
    @Transactional(readOnly = true)
    @NonBlocking
    public DeviceType buscarPorId(Long idDeviceType) {
        return entityManager.find(DeviceType.class, idDeviceType);
    }

    /**
     * Metodo usado para listar todos los DeviceType registrados en BD
     * @return Lista de DeviceType registrados en DB
     */
    @Override
    @Transactional(readOnly = true)
    @NonBlocking
    public List<DeviceTypeComando> listar() {
        String qlString = "SELECT d FROM DeviceType as d";
        TypedQuery<DeviceType> query = entityManager.createQuery(qlString, DeviceType.class);
        List<DeviceType> list = query.getResultList();
        List<DeviceTypeComando>  response= new ArrayList<DeviceTypeComando>();
        for(DeviceType deviceType:list){
            response.add(deviceType.convertToDeviceTypeComando());
        }
        System.out.println(list);
        return response;
    }

    /**
     * Metodo usado para actualizar un DeviceType en Bd
     * @param deviceType Objeto con datos modificados para reflejar sus cambios en Bd
     */
    @Override
    @Transactional
    @NonBlocking
    public void actualizar(DeviceType deviceType) {
        String stringQuery = "UPDATE DeviceType " +
                "SET name =: name" +
                " WHERE id =: id";
        entityManager.createQuery(stringQuery)
                .setParameter("name", deviceType.getName())
                .setParameter("id", deviceType.getId())
                .executeUpdate();
    }

    /**
     * Metodo usado para borrar un objeto de BD
     * @param idDeviceType Id del DeviceType a borrar
     */
    @Override
    @Transactional
    @NonBlocking
    public void borrar(Long idDeviceType) {
        entityManager.remove(buscarPorId(idDeviceType));
    }
}
