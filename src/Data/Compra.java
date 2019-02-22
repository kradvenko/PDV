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
 * @author Carlos Contreras
 */
public class Compra {
    private int idCompra;
    private int idProveedor;
    private String proveedor;
    private int idUsuarioCompra;
    private String usuarioCompra;
    private String fechaCompra;
    private float total;
    private String estado;
    private String notas;
    private String numeroNota;
    private String usarCaja;
    private float cantidadCajaVenta;
    ObservableList<Articulo> detalle; 
    
    public Compra() {
        
    }
    
    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }
    
    public int getIdCompra() {
        return this.idCompra;
    }
    
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
    
    public int getIdProveedor() {
        return this.idProveedor;
    }
    
    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
    
    public String getProveedor() {
        return this.proveedor;
    }
    
    public void setIdUsuarioCompra(int idUsuarioCompra) {
        this.idUsuarioCompra = idUsuarioCompra;
    }
    
    public int getIdUsuarioCompra() {
        return this.idUsuarioCompra;
    }
    
    public void setUsuarioCompra(String usuarioCompra) {
        this.usuarioCompra = usuarioCompra;
    }
    
    public String getUsuarioCompra() {
        return this.usuarioCompra;
    }
    
    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
    
    public String getFechaCompra() {
        return this.fechaCompra;
    }
    
    public void setTotal(float total) {
        this.total = total;
    }
    
    public float getTotal() {
        return this.total;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getEstado() {
        return this.estado;
    }
    
    public void setNotas(String notas) {
        this.notas = notas;
    }
    
    public String getNotas() {
        return this.notas;
    }
    
    public void setNumeroNota(String numeroNota) {
        this.numeroNota = numeroNota;
    }
    
    public String getNumeronota() {
        return this.numeroNota;
    }
    
    public void setUsarCaja(String usarCaja) {
        this.usarCaja = usarCaja;
    }
    
    public String getUsarCaja() {
        return this.usarCaja;
    }
    
    public void setCantidadCajaVenta(float cantidadCajaVenta) {
        this.cantidadCajaVenta = cantidadCajaVenta;
    }
    
    public float getCantidadCajaVenta() {
        return this.cantidadCajaVenta;
    }
    
    public void setDetalle(ObservableList<Articulo> detalle) {
        this.detalle = detalle;
    }
    
    public ObservableList<Articulo> getDetalle() {
        return this.detalle;
    }
    
    public void guardarCompra(int proveedor, ObservableList<Articulo> listaCompra, float total, String notas, String numeroNota, int idUsuarioCompra, String usarCaja, float cantidadCajaVenta) {
        try {
            Conexion con = new Conexion();
            String query = "INSERT INTO compras(id_proveedor, id_usuario_compra, fecha_compra, total, estado, notas, numero_nota, usar_caja, cantidad_caja_venta) "
                    + "VALUES (" + proveedor + ", " + idUsuarioCompra + ", datetime(strftime('%s','now'), 'unixepoch', 'localtime'), " + total + ", 'ACTIVO', '" + notas + "', '" + numeroNota + "', '" + usarCaja + "', " + cantidadCajaVenta + ")";
            
            int idCompra = con.executeQueryLastID(query);
            
            if (idCompra > 0) {
                for (int i =0; i < listaCompra.size(); i++) {
                    query = "INSERT INTO compra_detalle(id_compra, id_articulo, cantidad, costo) "
                            + "VALUES (" + idCompra + ", " + listaCompra.get(i).getIdArticulo() + ", " + listaCompra.get(i).getCantidadVenta() + ", " + listaCompra.get(i).getCosto() + ")";
                    con.executeQueryString(query);
                    Articulo.actualizarExistenciaArticulo(listaCompra.get(i).getIdArticulo(), listaCompra.get(i).getCantidadVenta() * -1);
                }
            }
            
            if (usarCaja == "SI") {
                restarCantidadCajaCompras(total);
            }
            
            con.closeCon();
        } catch (Exception exc) {
            
        }
    }
    
    public static ObservableList<Compra> obtenerCompras() {
        ObservableList<Compra> compras = FXCollections.observableArrayList();
        
        try {
            Conexion con = new Conexion();
            String query = "SELECT compras.*, proveedores.nombre AS proveedor, usuarios.usuario AS usuario_compra "
                    + "FROM compras "
                    + "INNER JOIN proveedores "
                    + "ON proveedores.id_proveedor = compras.id_proveedor "
                    + "INNER JOIN usuarios "
                    + "ON usuarios.id_usuario = compras.id_usuario_compra ";
                    
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Compra c = new Compra();
                    c.setIdCompra(res.getInt("id_compra"));
                    c.setIdProveedor(res.getInt("id_proveedor"));
                    c.setProveedor(res.getString("proveedor"));
                    c.setIdUsuarioCompra(res.getInt("id_usuario_compra"));
                    c.setUsuarioCompra(res.getString("usuario_compra"));
                    c.setFechaCompra(res.getString("fecha_compra"));
                    c.setTotal(res.getFloat("total"));
                    c.setEstado(res.getString("estado"));
                    c.setNotas(res.getString("notas"));
                    c.setNumeroNota(res.getString("numero_nota"));
                    c.setUsarCaja(res.getString("usar_caja"));
                    c.setCantidadCajaVenta(res.getFloat("cantidad_caja_venta"));
                    query = "SELECT compra_detalle.*, articulos.nombre, articulos.precio, articulos.codigo "
                            + "FROM compra_detalle "
                            + "INNER JOIN articulos "
                            + "ON articulos.id_articulo = compra_detalle.id_articulo "
                            + "WHERE compra_detalle.id_compra = " + c.getIdCompra();
                    
                    ResultSet detalle = con.executeQueryResultSet(query);
                    ObservableList<Articulo> detalleCompra = FXCollections.observableArrayList();
                    if (detalle != null) {                        
                        detalleCompra.clear();
                        while (detalle.next()) {
                            Articulo a = new Articulo();
                            a.setCodigo(detalle.getString("codigo"));
                            a.setIdArticulo(detalle.getInt("id_articulo"));
                            a.setNombre(detalle.getString("nombre"));
                            a.setPrecio(detalle.getFloat("precio"));
                            a.setCostoCompra(detalle.getFloat("costo"));
                            a.setCantidadCompra(detalle.getFloat("cantidad"));
                            detalleCompra.add(a);
                        }
                    }
                    c.setDetalle(detalleCompra);
                    compras.add(c);
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            
        }
        return compras;
    }
    
    public static ObservableList<Compra> obtenerCompras(String fecha, Turno turno, String idProveedor) {
        ObservableList<Compra> compras = FXCollections.observableArrayList();
        try {
            fecha = fecha.substring(6, 10) + "-" + fecha.substring(3, 5) + "-" + fecha.substring(0, 2);
            Conexion con = new Conexion();
            String query = "SELECT compras.*, proveedores.nombre AS proveedor, usuarios.usuario AS usuario_compra "
                    + "FROM compras "
                    + "INNER JOIN proveedores "
                    + "ON proveedores.id_proveedor = compras.id_proveedor "
                    + "INNER JOIN usuarios "
                    + "ON usuarios.id_usuario = compras.id_usuario_compra "
                    + "WHERE fecha_compra >= '" + fecha + " " + turno.getHoraInicio() + "' AND "
                    + "fecha_compra <= '" + fecha + " " + turno.getHoraFin() + "' AND "
                    + "compras.id_proveedor LIKE '" + idProveedor + "'";
                    
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Compra c = new Compra();
                    c.setIdCompra(res.getInt("id_compra"));
                    c.setIdProveedor(res.getInt("id_proveedor"));
                    c.setProveedor(res.getString("proveedor"));
                    c.setIdUsuarioCompra(res.getInt("id_usuario_compra"));
                    c.setUsuarioCompra(res.getString("usuario_compra"));
                    c.setFechaCompra(res.getString("fecha_compra"));
                    c.setTotal(res.getFloat("total"));
                    c.setEstado(res.getString("estado"));
                    c.setNotas(res.getString("notas"));
                    c.setNumeroNota(res.getString("numero_nota"));
                    c.setUsarCaja(res.getString("usar_caja"));
                    c.setCantidadCajaVenta(res.getFloat("cantidad_caja_venta"));
                    query = "SELECT compra_detalle.*, articulos.nombre, articulos.precio, articulos.codigo "
                            + "FROM compra_detalle "
                            + "INNER JOIN articulos "
                            + "ON articulos.id_articulo = compra_detalle.id_articulo "
                            + "WHERE compra_detalle.id_compra = " + c.getIdCompra();
                    
                    ResultSet detalle = con.executeQueryResultSet(query);
                    ObservableList<Articulo> detalleCompra = FXCollections.observableArrayList();
                    if (detalle != null) {                        
                        detalleCompra.clear();
                        while (detalle.next()) {
                            Articulo a = new Articulo();
                            a.setCodigo(detalle.getString("codigo"));
                            a.setIdArticulo(detalle.getInt("id_articulo"));
                            a.setNombre(detalle.getString("nombre"));
                            a.setPrecio(detalle.getFloat("precio"));
                            a.setCostoCompra(detalle.getFloat("costo"));
                            a.setCantidadCompra(detalle.getFloat("cantidad"));
                            detalleCompra.add(a);
                        }
                    }
                    c.setDetalle(detalleCompra);
                    compras.add(c);
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            String excep = exc.getMessage();
        }
        return compras;
    }
    
    public static ObservableList<Compra> obtenerComprasProveedor(String idProveedor) {
        ObservableList<Compra> compras = FXCollections.observableArrayList();
        try {
            Conexion con = new Conexion();
            String query = "SELECT compras.*, proveedores.nombre AS proveedor, usuarios.usuario AS usuario_compra "
                    + "FROM compras "
                    + "INNER JOIN proveedores "
                    + "ON proveedores.id_proveedor = compras.id_proveedor "
                    + "INNER JOIN usuarios "
                    + "ON usuarios.id_usuario = compras.id_usuario_compra "
                    + "WHERE compras.id_proveedor LIKE '" + idProveedor + "'";
                    
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Compra c = new Compra();
                    c.setIdCompra(res.getInt("id_compra"));
                    c.setIdProveedor(res.getInt("id_proveedor"));
                    c.setProveedor(res.getString("proveedor"));
                    c.setIdUsuarioCompra(res.getInt("id_usuario_compra"));
                    c.setUsuarioCompra(res.getString("usuario_compra"));
                    c.setFechaCompra(res.getString("fecha_compra"));
                    c.setTotal(res.getFloat("total"));
                    c.setEstado(res.getString("estado"));
                    c.setNotas(res.getString("notas"));
                    c.setNumeroNota(res.getString("numero_nota"));
                    c.setUsarCaja(res.getString("usar_caja"));
                    c.setCantidadCajaVenta(res.getFloat("cantidad_caja_venta"));
                    query = "SELECT compra_detalle.*, articulos.nombre, articulos.precio, articulos.codigo "
                            + "FROM compra_detalle "
                            + "INNER JOIN articulos "
                            + "ON articulos.id_articulo = compra_detalle.id_articulo "
                            + "WHERE compra_detalle.id_compra = " + c.getIdCompra();
                    
                    ResultSet detalle = con.executeQueryResultSet(query);
                    ObservableList<Articulo> detalleCompra = FXCollections.observableArrayList();
                    if (detalle != null) {                        
                        detalleCompra.clear();
                        while (detalle.next()) {
                            Articulo a = new Articulo();
                            a.setCodigo(detalle.getString("codigo"));
                            a.setIdArticulo(detalle.getInt("id_articulo"));
                            a.setNombre(detalle.getString("nombre"));
                            a.setPrecio(detalle.getFloat("precio"));
                            a.setCostoCompra(detalle.getFloat("costo"));
                            a.setCantidadCompra(detalle.getFloat("cantidad"));
                            detalleCompra.add(a);
                        }
                    }
                    c.setDetalle(detalleCompra);
                    compras.add(c);
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            String excep = exc.getMessage();
        }
        return compras;
    }
    
    public static Float obtenerTotalCompras(String tipo, String fecha, Turno turno) {
        Float total = 0f;
        try {
            fecha = fecha.substring(6, 10) + "-" + fecha.substring(3, 5) + "-" + fecha.substring(0, 2);
            Conexion con = new Conexion();
            String query = "SELECT compras.*, proveedores.nombre AS proveedor, usuarios.usuario AS usuario_compra "
                    + "FROM compras "
                    + "INNER JOIN proveedores "
                    + "ON proveedores.id_proveedor = compras.id_proveedor "
                    + "INNER JOIN usuarios "
                    + "ON usuarios.id_usuario = compras.id_usuario_compra "
                    + "WHERE fecha_compra >= '" + fecha + " " + turno.getHoraInicio() + "' AND "
                    + "fecha_compra <= '" + fecha + " " + turno.getHoraFin() + "' ";
                    
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    if ("CAJA COMPRAS".equals(tipo)) {
                        if ("SI".equals(res.getString("usar_caja"))) {
                            total = total + res.getFloat("total");
                        }
                    } else if ("CAJA VENTAS".equals(tipo)) {
                        if (res.getFloat("cantidad_caja_venta") > 0) {
                            total = total + res.getFloat("cantidad_caja_venta");
                        }
                    }
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            String excep = exc.getMessage();
        }
        return total;
    }
    
    public static Float obtenerCantidadCajaCompras() {
        Float cantidad = 0f;
        
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM caja_compra";
                    
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    cantidad = res.getFloat("cantidad");
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            String excep = exc.getMessage();
        }
        
        return cantidad;
    }
    
    public void cambiarCantidadCajaCompras(Float cantidad) {
        try {
            Conexion con = new Conexion();
            String query = "UPDATE caja_compra SET cantidad = " + cantidad + ", ultima_actualizacion = datetime(strftime('%s','now'), 'unixepoch', 'localtime')";
            con.executeQueryString(query);
            con.closeCon();
        } catch (Exception exc) {
            
        }
    }
    
    public void restarCantidadCajaCompras(Float cantidad) {
        try {
            Conexion con = new Conexion();
            String query = "UPDATE caja_compra SET cantidad = cantidad - " + cantidad + ", ultima_actualizacion = datetime(strftime('%s','now'), 'unixepoch', 'localtime')";
            con.executeQueryString(query);
            con.closeCon();
        } catch (Exception exc) {
            
        }
    }
    
}
