/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Kradvenko
 */
public class Articulo {
    private int idArticulo;
    private int idCategoria;
    private String categoria;
    private String nombre;
    private int existencia;
    private Float precio;
    private String codigo;
    //Variables para la venta
    private int cantidadVenta;
    private Float totalVenta;
    private Float descuentoArticuloEfectivo;
    private Float descuentoArticuloPorcentaje;
    private int randomStuff;
    //Para las entregas
    private int cantidadEntrega;
    private String fechaEntrega;
    private String folio;
    
    public Articulo() {
        
    }
    
    public Articulo(Articulo n) {
        this.idArticulo = n.idArticulo;
        this.idCategoria = n.idCategoria;
        this.categoria = n.categoria;
        this.nombre = n.nombre;
        this.existencia = n.existencia;
        this.precio = n.precio;
        this.codigo = n.codigo;        
    }
    
    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }
    
    public int getIdArticulo() {
        return this.idArticulo;
    }
    
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    public int getIdCategoria() {
        return this.idCategoria;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }
    
    public int getExistencia() {
        return this.existencia;
    }
    
    public void setPrecio(Float precio) {
        this.precio = precio;
    }
    
    public Float getPrecio() {
        return this.precio;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getCodigo() {
        return this.codigo;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getCategoria() {
        return this.categoria;
    }
    
    public void setCantidadVenta(int cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
    }
    
    public int getCantidadVenta() {
        return this.cantidadVenta;
    }
    
    public void setTotalVenta(Float totalVenta) {
        this.totalVenta = totalVenta;
    }
    
    public Float getTotalVenta() {
        return this.totalVenta;
    }
    
    public void setDescuentoArticuloEfectivo(Float descuentoArticuloEfectivo) {
        this.descuentoArticuloEfectivo = descuentoArticuloEfectivo;
    }
    
    public Float getDescuentoArticuloEfectivo() {
        return this.descuentoArticuloEfectivo;
    }
    
    public void setDescuentoArticuloPorcentaje(Float descuentoArticuloPorcentaje) {
        this.descuentoArticuloPorcentaje = descuentoArticuloPorcentaje;
    }
    
    public Float getDescuentoArticuloPorcentaje() {
        return this.descuentoArticuloPorcentaje;
    }
    
    public void setRandomStuff(int randomStuff) {
        this.randomStuff = randomStuff;
    }
    
    public int getRandomStuff() {
        return this.randomStuff;
    }
    
    public void setCantidadEntrega(int cantidadEntrega) {
        this.cantidadEntrega = cantidadEntrega;
    }
    
    public int getCantidadEntrega() {
        return this.cantidadEntrega;
    }
    
    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    
    public String getFechaEntrega() {
        return this.fechaEntrega;
    }
    
    public void setFolio(String folio) {
        this.folio = folio;
    }
    
    public String getFolio() {
        return this.folio;
    }
    
    public static ObservableList<Articulo> obtenerArticulosCategoria(int idCategoria) {
        ObservableList<Articulo> articulos = FXCollections.observableArrayList();
        try {
            Conexion con = new Conexion();
            String query = "SELECT articulos.*, categorias.nombre AS categoria FROM articulos INNER JOIN categorias ON categorias.id_categoria = articulos.id_categoria WHERE articulos.id_categoria = " + idCategoria;
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Articulo a = new Articulo();
                    a.setCodigo(res.getString("codigo"));
                    a.setExistencia(res.getInt("existencia"));
                    a.setIdArticulo(res.getInt("id_articulo"));
                    a.setIdCategoria(res.getInt("id_categoria"));
                    a.setNombre(res.getString("nombre"));
                    a.setPrecio(res.getFloat("precio"));
                    a.setCategoria(res.getString("categoria"));
                    articulos.add(a);
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            
        }
        return articulos;
    }
    
    public static void nuevoArticulo(String nombre, String codigo, int existencia, int idCategoria, Float precio, String folio) {
        try {
            Conexion con = new Conexion();
            String query = "INSERT INTO articulos (nombre, codigo, id_categoria, existencia, precio) VALUES ('" + nombre + "', '" + codigo + "', " + idCategoria + ", " + existencia + ", " + precio + ")";
            int id_articulo = con.executeQueryLastID(query);            
            con.closeCon();
            insertarRegistroEntrega(id_articulo, existencia, folio);
        } catch (Exception exc) {
            
        }
    }
    
    public static void actualizarArticulo(int idArticulo, String nombre, String codigo, int existencia, int idCategoria, Float precio, int diferencia, String folio) {
        try {
            Conexion con = new Conexion();
            String query = "UPDATE articulos SET nombre = '" + nombre + "', codigo = '" + codigo + "', existencia = " + existencia + ", id_categoria = " + idCategoria + ", precio = " + precio + " WHERE id_articulo = " + idArticulo;
            con.executeQueryString(query);
            con.closeCon();
            insertarRegistroEntrega(idArticulo, diferencia, folio);
        } catch (Exception exc) {
            
        }
    }
    
    public static void actualizarExistenciaArticulo(int idArticulo, int cantidad) {
        try {
            Conexion con = new Conexion();
            String query = "UPDATE articulos SET existencia = existencia - " + cantidad + " WHERE id_articulo = " + idArticulo;
            con.executeQueryString(query);
            con.closeCon();            
        } catch (Exception exc) {
            
        }
    }
    
     public static void actualizarExistenciaArticuloEntrega(int idArticulo, int cantidad, String folio) {
        try {
            Conexion con = new Conexion();
            String query = "UPDATE articulos SET existencia = existencia + " + cantidad + " WHERE id_articulo = " + idArticulo;
            con.executeQueryString(query);
            con.closeCon();
            insertarRegistroEntrega(idArticulo, cantidad, folio);
        } catch (Exception exc) {
            
        }
    }
    
    public static ObservableList<Articulo> obtenerArticulosCodigo(String codigo) {
        ObservableList<Articulo> articulos = FXCollections.observableArrayList();
        try {
            Conexion con = new Conexion();
            String query = "SELECT articulos.*, categorias.nombre as categoria FROM articulos INNER JOIN categorias ON categorias.id_categoria = articulos.id_categoria WHERE articulos.codigo LIKE '%" + codigo + "'";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Articulo a = new Articulo();
                    a.setCodigo(res.getString("codigo"));
                    a.setExistencia(res.getInt("existencia"));
                    a.setIdArticulo(res.getInt("id_articulo"));
                    a.setIdCategoria(res.getInt("id_categoria"));
                    a.setNombre(res.getString("nombre"));
                    a.setPrecio(res.getFloat("precio"));
                    a.setCategoria(res.getString("categoria"));                    
                    articulos.add(a);
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            
        }
        return articulos;
    }
    
    public static ObservableList<Articulo> obtenerArticulosNombre(String nombre) {
        ObservableList<Articulo> articulos = FXCollections.observableArrayList();
        try {
            Conexion con = new Conexion();
            String query = "SELECT articulos.*, categorias.nombre as categoria FROM articulos INNER JOIN categorias ON categorias.id_categoria = articulos.id_categoria WHERE articulos.nombre LIKE '%" + nombre + "%'";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Articulo a = new Articulo();
                    a.setCodigo(res.getString("codigo"));
                    a.setExistencia(res.getInt("existencia"));
                    a.setIdArticulo(res.getInt("id_articulo"));
                    a.setIdCategoria(res.getInt("id_categoria"));
                    a.setNombre(res.getString("nombre"));
                    a.setPrecio(res.getFloat("precio"));
                    a.setCategoria(res.getString("categoria"));
                    articulos.add(a);
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            
        }
        return articulos;
    }
    
    public static int calcularTotalTienda() {
        int totalTienda = 0;
        try {
            Conexion con = new Conexion();
            String query = "SELECT SUM(existencia * precio) AS suma FROM articulos";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    totalTienda = res.getInt("suma");
                }
            }
        } catch(Exception exc) {
            
        }
        return totalTienda;
    }
    
    public static int calcularArticulosTienda() {
        int totalArticulos = 0;
        try {
            Conexion con = new Conexion();
            String query = "SELECT SUM(existencia) AS suma FROM articulos";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    totalArticulos = res.getInt("suma");
                }
            }
        } catch(Exception exc) {
            
        }
        return totalArticulos;
    }
    
    public static ObservableList<Articulo> obtenerArticulosEntregaFecha(String fecha) {
        ObservableList<Articulo> articulos = FXCollections.observableArrayList();
        try {
            Conexion con = new Conexion();
            String query = "SELECT articulos.*, registros_entregas.cantidad AS cantidadEntrega, registros_entregas.fecha AS fechaEntrega, registros_entregas.folio as folio FROM articulos INNER JOIN registros_entregas ON registros_entregas.id_articulo = articulos.id_articulo WHERE registros_entregas.fecha = '" + fecha + "'";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Articulo a = new Articulo();
                    a.setCodigo(res.getString("codigo"));
                    a.setExistencia(res.getInt("existencia"));
                    a.setIdArticulo(res.getInt("id_articulo"));                    
                    a.setNombre(res.getString("nombre"));
                    a.setPrecio(res.getFloat("precio"));                    
                    a.setCantidadEntrega(res.getInt("cantidadEntrega"));
                    a.setFechaEntrega(res.getString("fechaEntrega"));
                    a.setFolio(res.getString("folio"));
                    articulos.add(a);
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            
        }
        return articulos;
    }
    
    public static ObservableList<Articulo> obtenerArticulosEntregaFolio(String folio) {
        ObservableList<Articulo> articulos = FXCollections.observableArrayList();
        try {
            Conexion con = new Conexion();
            String query = "SELECT articulos.*, registros_entregas.cantidad AS cantidadEntrega, registros_entregas.fecha AS fechaEntrega, registros_entregas.folio as folio FROM articulos INNER JOIN registros_entregas ON registros_entregas.id_articulo = articulos.id_articulo WHERE registros_entregas.folio = '" + folio + "'";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Articulo a = new Articulo();
                    a.setCodigo(res.getString("codigo"));
                    a.setExistencia(res.getInt("existencia"));
                    a.setIdArticulo(res.getInt("id_articulo"));                    
                    a.setNombre(res.getString("nombre"));
                    a.setPrecio(res.getFloat("precio"));                    
                    a.setCantidadEntrega(res.getInt("cantidadEntrega"));
                    a.setFechaEntrega(res.getString("fechaEntrega"));
                    a.setFolio(res.getString("folio"));
                    articulos.add(a);
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            
        }
        return articulos;
    }
    
    public static void insertarRegistroEntrega(int idArticulo, int cantidad, String folio) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();            
        String fecha = dateFormat.format(cal.getTime());
        //();
        try {
            Conexion con = new Conexion();
            String query = "INSERT INTO registros_entregas (id_articulo, cantidad, fecha, folio) VALUES (" + idArticulo + ", " + cantidad + ", '" + fecha.substring(0, 10) + "', '" + folio + "')";
            con.executeQueryString(query);
            con.closeCon();
        } catch (Exception exc) {
            
        }
    }
}