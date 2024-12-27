package BDA.grupo1.repository;

import BDA.grupo1.model.ClienteSesion;

public interface ClienteSesionRepository {
    public Integer crear(Integer cliente_id);
    public void deleteAll();
}
