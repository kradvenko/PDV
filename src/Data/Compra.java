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
    
    public void guardarCompra(int proveedor, ObservableList<Articulo> listaCompra, float total, String notas, String numeroNota, int idUsuarioCompra, String usarCaja, float cantidadCajaVenta) {
        try {
            Conexion con = new Conexion();
            String query = "INSERT INTO compras(id_proveedor, id_usuario_compra, fecha_compra, total, estado, notas, numero_nota, usar_caja, cantidad_caja_venta) "
                    + "VALUES (" + proveedor + ", " + idUsuarioCompra + ", datetime(strftime('%s','now'), 'unixepoch', 'localtime'), " + total + ", 'ACTIVO', '" + notas + "', '" + numeroNota + "', '" + usarCaja + "', " + cantidadCajaVenta + ")";
            
            int idCompra = con.executeQueryLastID(query);
            
            if (idCompra > 0) {
                for (int i =0; i < listaCompra.size(); i++) {
                    query = "INSERT INTO compra_detalle(id_compra, id_articulo, cantidad) "
                            + "VALUES (" + idCompra + ", " + listaCompra.get(i).getIdArticulo() + ", " + listaCompra.get(i).getCantidadVenta() + ")";
                    con.executeQueryString(query);
                    Articulo.actualizarExistenciaArticulo(listaCompra.get(i).getIdArticulo(), listaCompra.get(i).getCantidadVenta() * -1);
                }
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
            Conexion con = new Conexion();
            String query = "SELECT compras.*, proveedores.nombre AS proveedor, usuarios.usuario AS usuario_compra "
                    + "FROM compras "
                    + "INNER JOIN proveedores "
                    + "ON proveedores.id_proveedor = compras.id_proveedor "
                    + "INNER JOIN usuarios "
                    + "ON usuarios.id_usuario = compras.id_usuario_compra "
                    + "WHERE fecha >= '" + fecha + " " + turno.getHoraInicio() + "' AND "
                    + "fecha <= '" + fecha + " " + turno.getHoraFin() + "' AND "
                    + "id_proveedor LIKE '" + idProveedor + "'";
                    
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
                    compras.add(c);
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            
        }
        return compras;
    }
    
}
