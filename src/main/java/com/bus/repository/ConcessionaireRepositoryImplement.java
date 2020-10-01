package com.bus.repository;

import com.bus.controller.comandos.ConcessionaireComando;
import com.bus.entity.Concessionaire;
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
 * Clase que implementa la Interface de concessionaire y detalla el funcionamiento de cada uno de
 * sus metodos al ser Singleton solo puede ser instanciada una vez y esta se encarga de gestionar
 * el llamado a cada uno de sus metodos
 */
@Singleton
@Repository
public class ConcessionaireRepositoryImplement implements ConcessionaireRepository {

    //Objeto utilizado para gestionar la presistencia con la BD
    private EntityManager entityManager;

    /**
     * Constructor del objeto
     * @param entityManager Usado para dar gestión a la persistencia en Bd
     */
    public ConcessionaireRepositoryImplement(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Metodo crear usado para registrar en BD el objeto enviado
     * @param concessionaire Objeto que sera registrado en BD por el EntityManager
     * @return Se retorna el objeto registrado
     */
    @Override
    @Transactional
    @NonBlocking
    public Concessionaire crear(Concessionaire concessionaire) {
        if (concessionaire != null) {
            entityManager.persist(concessionaire);
        }
        return concessionaire;
    }

    /**
     * Metodo usado para buscar en DB un concessionaire por su Id
     * @param idConcessionaire Id del concessionaire a buscar
     * @return Objeto tipo Concessionaire encontrado en BD
     */
    @Override
    @Transactional(readOnly = true)
    @NonBlocking
    public Concessionaire buscarPorId(Long idConcessionaire) {
        return entityManager.find(Concessionaire.class, idConcessionaire);
    }

    /**
     * Metodo usado para listar todos los concessionaires registrados en BD
     * @return Lista de concessionaires registrados en DB
     */
    @Override
    @Transactional(readOnly = true)
    @NonBlocking
    public List<ConcessionaireComando> listar() {
        String qlString = "SELECT c FROM Concessionaire as c";
        TypedQuery<Concessionaire> query = entityManager.createQuery(qlString, Concessionaire.class);
        List<Concessionaire> list = query.getResultList();
        List<ConcessionaireComando>  response= new ArrayList<ConcessionaireComando>();
        for(Concessionaire concessionaire:list){
            response.add(concessionaire.convertToConcessionaireComando());
        }
        System.out.println(list);
        return response;
    }

    /**
     * Metodo usado para actualizar un concessionaire en Bd
     * @param concessionaire Objeto concessionaire con datos modificados para reflejar
     *                       sus cambios en Bd
     */
    @Override
    @Transactional
    @NonBlocking
    public void actualizar(Concessionaire concessionaire) {
        String stringQuery = "UPDATE Concessionaire " +
                "SET name =: name" +
                " WHERE id =: id";
        entityManager.createQuery(stringQuery)
                .setParameter("name", concessionaire.getName())
                .setParameter("id", concessionaire.getId())
                .executeUpdate();
    }

    /**
     * Metodo usado para borrar un objeto de BD
     * @param idConcessionaire Id del concessionaire a borrar
     */
    @Override
    @Transactional
    @NonBlocking
    public void borrar(Long idConcessionaire) {
        entityManager.remove(buscarPorId(idConcessionaire));
    }
}
