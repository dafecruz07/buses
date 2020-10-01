package com.bus.repository;

import com.bus.controller.comandos.ConcessionaireComando;
import com.bus.entity.Concessionaire;
import java.util.List;

/**
 * Interface usada para dar gestión a los objetos tipo Concessionaire
 */
public interface ConcessionaireRepository {

    Concessionaire crear (Concessionaire concessionaire);

    Concessionaire buscarPorId(Long idConcessionaire);

    List<ConcessionaireComando> listar();

    void actualizar (Concessionaire concessionaire);

    void borrar (Long idConcessionaire);
}
