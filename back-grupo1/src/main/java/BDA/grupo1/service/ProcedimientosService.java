package BDA.grupo1.service;

import BDA.grupo1.model.DetalleOrden;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProcedimientosService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Sql2o sql2o;

    // generar el reporte de los usuarios con más querys mediante el llamado al procedimiento almacenado
    public Map<String, List<String>> obtenerReporteAgrupado() {
        // Llamar al procedimiento almacenado para crear la tabla temporal
        jdbcTemplate.execute("CALL reporte_top_usuarios_querys()");

        // Consultar la tabla temporal generada por el procedimiento
        List<Map<String, Object>> filas = jdbcTemplate.queryForList("SELECT * FROM tmp_reporte_top_usuarios");

        // Agrupar los resultados por cliente
        Map<String, List<String>> reporte = new LinkedHashMap<>();
        for (Map<String, Object> fila : filas) {
            String nombreCliente = (String) fila.get("nombre_cliente");
            String consultaRealizada = (String) fila.get("consulta_realizada");
            String fechaConsulta = fila.get("fecha_consulta").toString();

            reporte.putIfAbsent(nombreCliente, new ArrayList<>());
            reporte.get(nombreCliente).add("[" + fechaConsulta + "] " + consultaRealizada);
        }

        return reporte;
    }

    // servicio que llama a obtenerReporteAgrupado para darle un formato al top de clientes
    public String generarReporteAgrupado() {
        Map<String, List<String>> reporteAgrupado = obtenerReporteAgrupado();

        StringBuilder reporteString = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : reporteAgrupado.entrySet()) {
            String nombreCliente = entry.getKey();
            List<String> consultas = entry.getValue();

            // Encabezado del usuario
            reporteString.append("Cliente: ").append(nombreCliente).append("\n");

            // Consultas realizadas
            for (String consulta : consultas) {
                reporteString.append("    ").append(consulta).append("\n");
            }
            reporteString.append("\n");
        }
        return reporteString.toString();
    }

    // aplicar descuento a los articulos de una categoría si no hay ventas en los últimos 30 días
    public void aplicarDescuentoACategoria(int idCategoria, float descuento) {
        String sql = "CALL aplicar_descuento_a_categoria(:idCategoria, :descuento)"; // llamado al procedimiento almacenado
        BigDecimal nuevoDescuento = new BigDecimal(descuento);
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
            .addParameter("idCategoria", idCategoria)
                    .addParameter("descuento", nuevoDescuento.setScale(4, BigDecimal.ROUND_HALF_UP))
                    .executeUpdate(); // ejecución del procedimiento
        }
    }

    // crear una orden de compra mediante un procedimiento almacenado
    public String crearOrdenCompra(int idCliente, List<DetalleOrden> detalles, Double latitud, Double longitud) throws JSONException {
        JSONArray detallesJson = new JSONArray(); // arreglo con los detalles de orden
        for (DetalleOrden detalle : detalles) {
            JSONObject detalleJson = new JSONObject();
            detalleJson.put("id_producto", detalle.getId_producto());
            detalleJson.put("cantidad", detalle.getCantidad());
            detalleJson.put("precio_unitario", detalle.getPrecio_unitario());
            detallesJson.put(detalleJson);
        }
        String detallesJsonString = detallesJson.toString();
        String sql = "CALL registrar_orden(:p_id_cliente, :lista_detalleOrden::json, ST_SetSRID(ST_Point(:longitud, :latitud), 4326))"; // añadir parámetros a la query
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("p_id_cliente", idCliente)
                    .addParameter("lista_detalleOrden", detallesJsonString)
                    .addParameter("latitud", latitud)
                    .addParameter("longitud", longitud)
                    .executeUpdate();
        }
        return "Orden creada con éxito";
    }
}