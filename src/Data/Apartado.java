/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Kradvenko
 */
public class Apartado {
    private int idApartado;
    private String fecha;
    private Float subTotalVenta;
    private Float totalVenta;
    private Float descuentoPorcentaje;
    private Float descuentoEfectivo;
    private String estado;
    private String tipo;
    private Float cambio;
    private String vendedor;
    private String cliente;
    ObservableList<Articulo> detalle = FXCollections.observableArrayList();
    
    public Apartado() {
        
    }
    
    public void setIdApartado(int idVenta) {
        this.idApartado = idVenta;
    }
    
    public int getIdApartado() {
        return this.idApartado;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getFecha() {
        return this.fecha;
    }
    
    public void setSubTotalVenta(Float subTotalVenta) {
        this.subTotalVenta = subTotalVenta;
    }
    
    public Float getSubTotalVenta() {
        return this.subTotalVenta;
    }
    
    public void setTotalVenta(Float totalVenta) {
        this.totalVenta = totalVenta;
    }
    
    public Float getTotalVenta() {
        return this.totalVenta;
    }
    
    public void setDescuentoPorcentaje(Float descuentoPorcentaje) {
        this.descuentoPorcentaje = descuentoPorcentaje;
    }
    
    public Float getDescuentoPorcentaje() {
        return this.descuentoPorcentaje;
    }
    
    public void setDescuentoEfectivo(Float descuentoEfectivo) {
        this.descuentoEfectivo = descuentoEfectivo;
    }
    
    public Float getDescuentoEfectivo() {
        return this.descuentoEfectivo;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getEstado() {
        return this.estado;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getTipo() {
        return this.tipo;
    }
    
    public void setCambio(Float cambio) {
        this.cambio = cambio;
    }
    
    public Float getCambio() {
        return this.cambio;
    }
    
    public void setDetalle(ObservableList<Articulo> detalle) {
        this.detalle = detalle;
    }
    
    public ObservableList<Articulo> getDetalle() {
        return this.detalle;
    }
    
    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }
    
    public String getVendedor() {
        return this.vendedor;
    }
    
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
    public String getCliente() {
        return this.cliente;
    }
    
    public static Apartado guardarApartado(String fecha, Float subTotal, Float total, Float descuentoPorcentaje, Float descuentoEfectivo, String estado, String tipo, Float cambio, ObservableList<Articulo> detalle, String vendedor, String cliente, Float abono) {
        Apartado v = new Apartado();
        try {
            Conexion con = new Conexion();
            String query = "INSERT INTO apartados (fecha, subtotal, total, descuento_porcentaje, descuento_efectivo, estado, tipo, cambio, vendedor, cliente) VALUES ('" + fecha + "', " + subTotal + ", " + total + ", " + descuentoPorcentaje + ", " + descuentoEfectivo + ", '" + estado + "', '" + tipo + "', " + cambio + ", '" + vendedor + "', '" + cliente + "')";
            int id_apartado = con.executeQueryLastID(query);
            v.setDescuentoEfectivo(descuentoEfectivo);
            v.setDescuentoPorcentaje(descuentoPorcentaje);
            v.setDetalle(detalle);
            v.setEstado(estado);
            v.setFecha(fecha);
            v.setIdApartado(id_apartado);
            v.setSubTotalVenta(subTotal);
            v.setTipo(tipo);
            v.setTotalVenta(total);
            v.setCambio(cambio);
            v.setVendedor(vendedor);
            v.setCliente(cliente);
            if (id_apartado > 0) {
                for (int i = 0; i < detalle.size(); i++) {
                    query = "INSERT INTO apartados_detalle (id_apartado, id_articulo, cantidad, total) VALUES (" + id_apartado + ", " + detalle.get(i).getIdArticulo() + ", " + detalle.get(i).getCantidadVenta() + ", " + detalle.get(i).getTotalVenta() + ")";
                    con.executeQueryString(query);
                    Articulo.actualizarExistenciaArticulo(detalle.get(i).getIdArticulo(), detalle.get(i).getCantidadVenta());
                }
                abonarApartado(id_apartado, fecha, abono, tipo);
            }            
            con.closeCon();
        } catch(Exception exc) {
            v = null;
        }
        return v;
    }
    
    public static void abonarApartado(Integer idApartado, String fecha, Float abono, String tipo) {
        try {
            Conexion con = new Conexion();
            String query = "INSERT INTO abonos (id_apartado, abono, fecha, tipo) VALUES (" + idApartado + ", " + abono + ", '" + fecha + "', '" + tipo + "')";
            con.executeQueryString(query);
            con.closeCon();
        } catch(Exception exc) {
            
        }
    }
    
    public static ObservableList<Apartado> obtenerApartadosFecha(String fecha) {
        ObservableList<Apartado> ventas = FXCollections.observableArrayList();
        
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM apartados WHERE fecha LIKE '" + fecha + "%'";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Apartado v = new Apartado();
                    v.setDescuentoEfectivo(res.getFloat("descuento_efectivo"));
                    v.setDescuentoPorcentaje(res.getFloat("descuento_porcentaje"));
                    v.setEstado(res.getString("estado"));
                    v.setFecha(res.getString("fecha"));
                    v.setIdApartado(res.getInt("id_apartado"));
                    v.setSubTotalVenta(res.getFloat("subtotal"));
                    v.setTipo(res.getString("tipo"));
                    v.setTotalVenta(res.getFloat("total"));
                    v.setCambio(res.getFloat("cambio"));
                    v.setVendedor(res.getString("vendedor"));
                    v.setCliente((res.getString("cliente")));
                    query = "SELECT apartados_detalle.*, articulos.nombre, articulos.precio, articulos.codigo FROM apartados_detalle INNER JOIN articulos ON articulos.id_articulo = apartados_detalle.id_articulo WHERE id_apartado = " + v.getIdApartado();
                    ResultSet detalle = con.executeQueryResultSet(query);
                    ObservableList<Articulo> detalleVenta = FXCollections.observableArrayList();
                    if (detalle != null) {                        
                        detalleVenta.clear();
                        while (detalle.next()) {
                            Articulo a = new Articulo();
                            a.setCantidadVenta(detalle.getFloat("cantidad"));
                            a.setCodigo(detalle.getString("codigo"));
                            a.setIdArticulo(detalle.getInt("id_articulo"));
                            a.setNombre(detalle.getString("nombre"));
                            a.setPrecio(detalle.getFloat("precio"));
                            a.setTotalVenta(detalle.getFloat("total"));
                            detalleVenta.add(a);
                        }
                    }
                    v.setDetalle(detalleVenta);
                    ventas.add(v);
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            
        }
        return ventas;
    }
    
    public static void actualizarApartadoResta(int id_apartado, Float resta) {
        try {
            Conexion con = new Conexion();
            String query = "UPDATE apartados SET cambio = " + resta + " WHERE id_apartado = " + id_apartado;
            con.executeQueryString(query);
            con.closeCon();
        } catch (Exception exc) {
            
        }
    }
    
    public static void cancelarApartado(Apartado v, String estado) {
        try {
            Conexion con = new Conexion();
            String query = "UPDATE apartados SET estado = '" + estado + "' WHERE id_apartado = " + v.getIdApartado();
            con.executeQueryString(query);
            for (int i = 0; i < v.getDetalle().size(); i++) {
                if (estado.contains("CANCELADA")) {
                    Articulo.actualizarExistenciaArticulo(v.getDetalle().get(i).getIdArticulo(), v.getDetalle().get(i).getCantidadVenta());
                } else if (estado.contains("ACTIVA")) {
                    Articulo.actualizarExistenciaArticulo(v.getDetalle().get(i).getIdArticulo(), -1 * v.getDetalle().get(i).getCantidadVenta());
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            
        }
    }
    
    public static void cancelarAbono(Abono a) {
        try {
            Conexion con = new Conexion();
            String query = "DELETE FROM abonos WHERE id_abono = " + a.getIdAbono();
            con.executeQueryString(query);            
            con.closeCon();
        } catch (Exception exc) {
            
        }
    }
    
    public static Apartado obtenerApartadoId(int id_apartado) {
        ObservableList<Apartado> ventas = FXCollections.observableArrayList();
        
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM apartados WHERE id_apartado = " + id_apartado;
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Apartado v = new Apartado();
                    v.setDescuentoEfectivo(res.getFloat("descuento_efectivo"));
                    v.setDescuentoPorcentaje(res.getFloat("descuento_porcentaje"));
                    v.setEstado(res.getString("estado"));
                    v.setFecha(res.getString("fecha"));
                    v.setIdApartado(res.getInt("id_apartado"));
                    v.setSubTotalVenta(res.getFloat("subtotal"));
                    v.setTipo(res.getString("tipo"));
                    v.setTotalVenta(res.getFloat("total"));
                    v.setCambio(res.getFloat("cambio"));
                    v.setVendedor(res.getString("vendedor"));
                    v.setCliente((res.getString("cliente")));
                    query = "SELECT apartados_detalle.*, articulos.nombre, articulos.precio, articulos.codigo FROM apartados_detalle INNER JOIN articulos ON articulos.id_articulo = apartados_detalle.id_articulo WHERE id_apartado = " + v.getIdApartado();
                    ResultSet detalle = con.executeQueryResultSet(query);
                    ObservableList<Articulo> detalleVenta = FXCollections.observableArrayList();
                    if (detalle != null) {                        
                        detalleVenta.clear();
                        while (detalle.next()) {
                            Articulo a = new Articulo();
                            a.setCantidadVenta(detalle.getFloat("cantidad"));
                            a.setCodigo(detalle.getString("codigo"));
                            a.setIdArticulo(detalle.getInt("id_articulo"));
                            a.setNombre(detalle.getString("nombre"));
                            a.setPrecio(detalle.getFloat("precio"));
                            a.setTotalVenta(detalle.getFloat("total"));
                            detalleVenta.add(a);
                        }
                    }
                    v.setDetalle(detalleVenta);
                    ventas.add(v);
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            
        }
        return ventas.get(0);
    }
}
