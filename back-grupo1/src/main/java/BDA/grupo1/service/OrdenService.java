package BDA.grupo1.service;

import BDA.grupo1.model.Orden;
import BDA.grupo1.model.Zona;
import BDA.grupo1.repository.OrdenRepository;
import BDA.grupo1.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private ZonaService zonaService;

    // servicio para crear una orden
    public Orden crear(Orden orden) {
        return ordenRepository.crear(orden);
    }

    // servicio para encontrar todas las ordenes de la tabla
    public List<Orden> findAll(){
        return ordenRepository.getAll();
    }

    // servicio para actualizar los datos de una orden
    public String update(Orden orden, Integer id) {
        return ordenRepository.update(orden, id);
    }

    // servicio para eliminar una orden según su identificador
    public void delete(Integer id){
        ordenRepository.delete(id);
    }

    // servicio para obtener las ordenes según el tamaño de la página
    public List<Orden> getOrdersPage(Integer page, Integer pageSize){
        return ordenRepository.getOrdenPages(page,pageSize);
    }

    // servicio para obtener las ordenes de un usuario según el tamaño de la página
    public List<Orden> getOrdersPageUser(Integer User, Integer page, Integer pageSize){
        return ordenRepository.getOrdersPageUser(User,page,pageSize);
    }

    // servicio para obtener las ordenes de un usuario
    public List<Orden> getOrdenByUserId(Integer id){
        return ordenRepository.getOrdenByUserId(id);
    }

    // servicio para obtener una orden según su identificador
    public Orden getOrdenById(Integer id){
        return ordenRepository.getOrdenById(id);
    }

    // servicio para obtener el identificador de una orden según su Timestamp
    public Integer getOrdenIDByTimestamp(){
        return ordenRepository.getOrdenIDByTimestamp();
    }

    public Boolean getIfPointIsInRestrictedZone(Double x, Double y){
        Boolean R1 =  ordenRepository.getIfPointIsInRestrictedZone(x,y);
        Boolean R2 =  getIfPointIsInDeliveryZone(x,y);
        System.out.println(R1);
        System.out.println(R2);
        return !R1 && R2;
    }

    public Boolean getIfPointIsInDeliveryZone(Double x, Double y){
        return ordenRepository.getIfPointIsInDeliveryZone(x,y);
    }

    public Integer getOrdersTotalPages(Integer pageSize){ return ordenRepository.getOrdersTotalPages(pageSize); }

    public Integer getOrdersTotalPagesUser(Integer User, Integer pageSize){ return ordenRepository.getOrdersTotalPagesUser(User,pageSize); }

}
