package BDA.grupo1.repository;

import BDA.grupo1.model.Orden;


import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

public interface OrdenRepository {
    public Orden crear(Orden orden);
    public List<Orden> getAll();
    public String update(Orden orden, Integer id);
    public void delete(Integer id);
    public List<Orden> getOrdenPages(int page, int pageSize);
    public List<Orden> getOrdersPageUser(Integer User, int page, int pageSize);
    public List<Orden> getOrdenByUserId(int id);
    public Orden getOrdenById(int id);
    public Integer getOrdenIDByTimestamp();
    public Boolean getIfPointIsInRestrictedZone(Double x, Double y);
    public Integer getOrdersTotalPages(Integer pageSize);
    public Integer getOrdersTotalPagesUser(Integer User, Integer pageSize);
    public Boolean getIfPointIsInDeliveryZone(Double x, Double y);
    public Orden getLastOrdenByUser(Integer id);
}
