package BDA.grupo1.repository;

import BDA.grupo1.model.Producto;

import java.util.List;

public interface ProductoRepository {
    public Producto crear(Producto producto);
    public List<Producto> getAll();
    public String update(Producto producto, Integer id);
    public void delete(Integer id);
    public List<Producto> getProductoPage(int pagina, int tamanoPagina);
    public Producto getproductoById(Integer id);
    public void updateProductoStock(Integer id, Integer stock);
    public Integer getTotalPages(Integer pageSize);
}
