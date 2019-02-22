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
public class Venta {
    private int idVenta;
    private String fecha;
    private Float subTotalVenta;
    private Float totalVenta;
    private Float descuentoPorcentaje;
    private Float descuentoEfectivo;
    private String estado;
    private String tipo;
    private Float cambio;
    private String vendedor;
    private Float ganancia;
    ObservableList<Articulo> detalle = FXCollections.observableArrayList();
    
    public Venta() {
        
    }
    
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }
    
    public int getIdVenta() {
        return this.idVenta;
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
    
    public void setGanancia(Float ganancia) {
        this.ganancia = ganancia;
    }
    
    public Float getGanancia() {
        return this.ganancia;
    }
    
    public static Venta guardarVenta(String fecha, Float subTotal, Float total, Float descuentoPorcentaje, Float descuentoEfectivo, String estado, String tipo, Float cambio, ObservableList<Articulo> detalle, String vendedor, Float gananciaVenta) {
        Venta v = new Venta();
        try {
            Conexion con = new Conexion();
            String query = "INSERT INTO ventas (fecha, subtotal, total, descuento_porcentaje, descuento_efectivo, estado, tipo, cambio, vendedor, ganancia) VALUES ('" + fecha + "', " + subTotal + ", " + total + ", " + descuentoPorcentaje + ", " + descuentoEfectivo + ", '" + estado + "', '" + tipo + "', " + cambio + ", '" + vendedor + "', " + gananciaVenta + ")";
            int id_venta = con.executeQueryLastID(query);
            v.setDescuentoEfectivo(descuentoEfectivo);
            v.setDescuentoPorcentaje(descuentoPorcentaje);
            v.setDetalle(detalle);
            v.setEstado(estado);
            v.setFecha(fecha);
            v.setIdVenta(id_venta);
            v.setSubTotalVenta(subTotal);
            v.setTipo(tipo);
            v.setTotalVenta(total);
            v.setCambio(cambio);
            v.setVendedor(vendedor);
            if (id_venta > 0) {
                for (int i = 0; i < detalle.size(); i++) {
                    query = "INSERT INTO venta_detalle (id_venta, id_articulo, cantidad, total, ganancia) VALUES (" + id_venta + ", " + detalle.get(i).getIdArticulo() + ", " + detalle.get(i).getCantidadVenta() + ", " + detalle.get(i).getTotalVenta() + ", " + detalle.get(i).getGananciaEnVenta() + ")";
                    con.executeQueryString(query);
                    Articulo.actualizarExistenciaArticulo(detalle.get(i).getIdArticulo(), detalle.get(i).getCantidadVenta());
                }
            }
            con.closeCon();
        } catch(Exception exc) {
            v = null;
        }
        return v;
    }
    
    public static ObservableList<Venta> obtenerVentasFecha(String fecha) {
        ObservableList<Venta> ventas = FXCollections.observableArrayList();
        
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM ventas WHERE fecha LIKE '" + fecha + "%'";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Venta v = new Venta();
                    v.setDescuentoEfectivo(res.getFloat("descuento_efectivo"));
                    v.setDescuentoPorcentaje(res.getFloat("descuento_porcentaje"));
                    v.setEstado(res.getString("estado"));
                    v.setFecha(res.getString("fecha"));
                    v.setIdVenta(res.getInt("id_venta"));
                    v.setSubTotalVenta(res.getFloat("subtotal"));
                    v.setTipo(res.getString("tipo"));
                    v.setTotalVenta(res.getFloat("total"));
                    v.setCambio(res.getFloat("cambio"));
                    v.setVendedor(res.getString("vendedor"));
                    v.setGanancia((res.getFloat("ganancia")));
                    query = "SELECT venta_detalle.*, articulos.nombre, articulos.precio, articulos.codigo FROM venta_detalle INNER JOIN articulos ON articulos.id_articulo = venta_detalle.id_articulo WHERE id_venta = " + v.getIdVenta();
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
                            a.setGananciaEnVenta(detalle.getFloat("ganancia"));
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
    
    public static ObservableList<Venta> obtenerVentasFecha(String fecha, Turno turno) {
        ObservableList<Venta> ventas = FXCollections.observableArrayList();
        
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM ventas WHERE fecha >= '" + fecha + " " + turno.getHoraInicio() + "' AND fecha <= '" + fecha + " " + turno.getHoraFin() + "' ";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Venta v = new Venta();
                    v.setDescuentoEfectivo(res.getFloat("descuento_efectivo"));
                    v.setDescuentoPorcentaje(res.getFloat("descuento_porcentaje"));
                    v.setEstado(res.getString("estado"));
                    v.setFecha(res.getString("fecha"));
                    v.setIdVenta(res.getInt("id_venta"));
                    v.setSubTotalVenta(res.getFloat("subtotal"));
                    v.setTipo(res.getString("tipo"));
                    v.setTotalVenta(res.getFloat("total"));
                    v.setCambio(res.getFloat("cambio"));
                    v.setVendedor(res.getString("vendedor"));
                    v.setGanancia((res.getFloat("ganancia")));
                    query = "SELECT venta_detalle.*, articulos.nombre, articulos.precio, articulos.codigo FROM venta_detalle INNER JOIN articulos ON articulos.id_articulo = venta_detalle.id_articulo WHERE id_venta = " + v.getIdVenta();
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
                            a.setGananciaEnVenta(detalle.getFloat("ganancia"));
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
    
    public static void cancelarVenta(Venta v, String estado) {
        try {
            Conexion con = new Conexion();
            String query = "UPDATE ventas SET estado = '" + estado + "' WHERE id_venta = " + v.getIdVenta();
            con.executeQueryString(query);
            for (int i = 0; i < v.getDetalle().size(); i++) {
                if (estado.contains("CANCELADA")) {
                    Articulo.actualizarExistenciaArticulo(v.getDetalle().get(i).getIdArticulo(), v.getDetalle().get(i).getCantidadVenta());
                } else if (estado.contains("ACTIVA")) {
                    Articulo.actualizarExistenciaArticulo(v.getDetalle().get(i).getIdArticulo(), -1 * v.getDetalle().get(i).getCantidadVenta());
                }
            }
        } catch (Exception exc) {
            
        }
    }
}
