package BDA.grupo1.service;

import BDA.grupo1.model.Producto;
import BDA.grupo1.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // servicio para crear un producto
    public Producto crear(Producto producto) {
        return productoRepository.crear(producto);
    }

    // servicio para obtener todos los productos
    public List<Producto> findAll() {
        return productoRepository.getAll();
    }

    // servicio para actualizar los datos de un producto
    public String update(Producto producto, Integer id) {
        return productoRepository.update(producto, id);
    }

    // servicio para eliminar un producto según su identificador
    public void delete(Integer id) {
        productoRepository.delete(id);
    }

    // servicio para obtener los productos según el tamaño de la página
    public List<Producto> getProductoPage(Integer page, Integer pageSize){
        return productoRepository.getProductoPage(page,pageSize);
    }

    // servicio para obtener un producto según su identificador
    public Producto getproductoById(Integer id) {
        return productoRepository.getproductoById(id);
    }

    // servicio para actualizar el stock de un producto
    public void updateProductoStock(Integer id, Integer stock) { productoRepository.updateProductoStock(id, stock); }

    public Integer getTotalPages(Integer pageSize){ return productoRepository.getTotalPages(pageSize); }

    public List<Producto> getProductosAleatoreosByCategoria(Integer idCategoria) {
        return productoRepository.getProductosAleatoreosByCategoria(idCategoria);
    }

    // servicio para obtener el id_categoria de un producto según su id
    public Integer getCategoriaId(Integer id_producto) { return productoRepository.getCategoriaId(id_producto); }
}
