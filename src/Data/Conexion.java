/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Kradvenko
 */
public class Conexion {
    public Conexion(){

    }
    
    private Connection connection;
    
    public void closeCon() throws SQLException {
        connection.close();
    }

    public ResultSet executeQueryResultSet(String q) {
        String driver = "org.sqlite.JDBC";
        String url = "jdbc:sqlite:C:\\PDV\\PDV.db";
	try {
		Class.forName(driver);
		connection = DriverManager.getConnection(url);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(q);
                return rs;
        }catch(ClassNotFoundException cnfe) {
            return null;
	}catch(SQLException sqle) {
            //JOptionPane.showMessageDialog(null, sqle.getMessage());
            return null;
	}
    }
    
    public String executeQueryString(String q) {
        String driver = "org.sqlite.JDBC";
        String url = "jdbc:sqlite:C:\\PDV\\PDV.db";
	try {
		Class.forName(driver);
		connection = DriverManager.getConnection(url);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(q);
                return "OK";
        }catch(  ClassNotFoundException | SQLException cnfe) {            
            return cnfe.getMessage();
	}
    }
    
    public int executeQueryLastID(String q) {
        String driver = "org.sqlite.JDBC";
        String url = "jdbc:sqlite:C:\\PDV\\PDV.db";
	try {
		Class.forName(driver);
		connection = DriverManager.getConnection(url);
		Statement statement = connection.createStatement();
		statement.executeUpdate(q);
                ResultSet r = statement.getGeneratedKeys();
                r.next();
                int last_id = r.getInt(1);                
                return last_id;
        }catch(ClassNotFoundException cnfe) {
            return -1;
	}catch(SQLException sqle) {
            return -100;
	}
    }
}
