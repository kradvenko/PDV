/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdv;

import Data.Tienda;
import Data.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kradvenko
 */
public class MainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Usuario usuarioActual;
    
    @FXML private AnchorPane apMain;
    @FXML private MenuItem miVentas;
    @FXML private MenuItem miArticulos;
    
    @FXML private Label lblLogo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        login();
    }
    
    public void login() {
        try {
            Stage stage = new Stage();        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = (Parent)loader.load();
            LoginController controller = loader.<LoginController>getController();
            controller.setParent(this);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            
        }
    }
    
    public void setUsuarioActual(Usuario u) {
        if (u != null) {
            this.usuarioActual = u;
            if (usuarioActual.getTipo().contains("ADMINISTRADOR")) {
                miVentas.disableProperty().setValue(Boolean.FALSE);
                miArticulos.disableProperty().setValue(Boolean.FALSE);
                Tienda t = Tienda.obtenerDatosTienda();
                lblLogo.setText(t.getNombre());
            } else {
                miVentas.disableProperty().setValue(Boolean.FALSE);
                Tienda t = Tienda.obtenerDatosTienda();
                lblLogo.setText(t.getNombre());
            }
        } else {
            
        }
    }
    
    public void mostrarVentanaVenta() {
        try {
            Stage stage = new Stage();        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NuevaVenta.fxml"));
            Parent root = (Parent)loader.load();
            NuevaVentaController controller = loader.<NuevaVentaController>getController();            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            
        }
    }
    
    public void mostrarVentanaCorte() {
        try {
            Stage stage = new Stage();        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Corte.fxml"));
            Parent root = (Parent)loader.load();
            CorteController controller = loader.<CorteController>getController();
            controller.setUsuario(usuarioActual);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            
        }
    }
    
    public void mostrarVentanaApartados() {
        try {
            Stage stage = new Stage();        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Apartados.fxml"));
            Parent root = (Parent)loader.load();
            ApartadosController controller = loader.<ApartadosController>getController();
            controller.setUsuario(usuarioActual);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            
        }
    }
    
    public void mostrarVentanaArticulos() {
        try {
            Stage stage = new Stage();        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Articulos.fxml"));
            Parent root = (Parent)loader.load();
            ArticulosController controller = loader.<ArticulosController>getController();            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            
        }
    }
    
    public void mostrarVentanaIngresar() {
        try {
            Stage stage = new Stage();        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("IngresoMercancia.fxml"));
            Parent root = (Parent)loader.load();
            IngresoMercanciaController controller = loader.<IngresoMercanciaController>getController();            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            
        }
    }
    
    public void mostrarVentanaCategorias() {
        try {
            Stage stage = new Stage();        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Categorias.fxml"));
            Parent root = (Parent)loader.load();
            CategoriasController controller = loader.<CategoriasController>getController();            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            
        }
    }

    public void mostraVentanaBitacora() {
        try {
            Stage stage = new Stage();        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Bitacora.fxml"));
            Parent root = (Parent)loader.load();
            BitacoraController controller = loader.<BitacoraController>getController();            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            
        }
    }
    
    public void mostraVentanaConfiguracion() {
        try {
            Stage stage = new Stage();        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Configuracion.fxml"));
            Parent root = (Parent)loader.load();
            ConfiguracionController controller = loader.<ConfiguracionController>getController();            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            
        }
    }
    
    public void mostrarInventarioVentas()
    {
        try {
            Stage stage = new Stage();        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Articulos.fxml"));
            Parent root = (Parent)loader.load();
            ArticulosController controller = loader.<ArticulosController>getController();            
            controller.OcultarDatosArticulo();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            
        }
    }
    
    public void mostrarConvertidor()
    {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Convertidor.fxml"));
            Parent root = (Parent)loader.load();
            ConvertidorController controller = loader.<ConvertidorController>getController();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            
        }
    }
    
    public void cerrar() {
        Stage stage = (Stage) lblLogo.getScene().getWindow();
        stage.close();
    }
}