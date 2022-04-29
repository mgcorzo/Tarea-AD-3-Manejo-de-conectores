/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conectores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xp
 */
public class Conectores {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String reiniciar = "si";
        while (reiniciar.equals("si")) {
            try {
                //cargar driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                //Conectar con la BD
                Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/tienda", "root", "");
                System.out.println("Escribe el nombre del fabricante:");
                String nombreFabricante = sc.nextLine();
                //crea consulta
                String consulta = "select * from fabricante where nombre = ? ";// + cod_fabricante;
                //prepara la consulta
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                //el 1 se refiere al primer parámetro
                sentencia.setString(1, nombreFabricante);
                //ejecuta la consulta
                ResultSet rs = sentencia.executeQuery();
                if (rs.next()) {
                    System.out.println("El fabricante existe");
                } else {
                    consulta = "INSERT INTO fabricante (nombre) VALUES (?)";
                    //prepara la consulta
                    PreparedStatement sentencia1 = conexion.prepareStatement(consulta);
                    //el 1 se refiere al primer parámetro
                    sentencia1.setString(1, nombreFabricante);
                    sentencia1.executeUpdate();
                    System.out.println("Has añadido el nombre del fabricante");
                }
                //Para añadir el producto y su precio 
                System.out.println("Escribe el nombre del producto:");
                String nombreProducto = sc.nextLine();
                System.out.println("Escribe el precio del producto:");
                String precioProducto = sc.nextLine();
                int cod_fabricante = 0;
                //crea consulta
                String consulta1 = "select * from producto where nombre = ? ";
                //prepara la consulta
                PreparedStatement sentencia2 = conexion.prepareStatement(consulta1);
                //el 1 se refiere al primer parámetro
                sentencia2.setString(1, nombreProducto);
                sentencia2.setString(1, precioProducto);
                //ejecuta la consulta
                ResultSet rs1 = sentencia2.executeQuery();
                if (rs1.next()) {
                    System.out.println("El producto existe");
                } else {
                    String consulta8 = "SELECT codigo FROM fabricante WHERE nombre= ?";
                    PreparedStatement sentencia8 = conexion.prepareStatement(consulta8);
                    //el 1 se refiere al primer parámetro
                    sentencia8.setString(1, nombreFabricante);
                    //ejecuta la consulta
                    ResultSet rs8 = sentencia8.executeQuery();
                    if (rs8.next()) {
                        cod_fabricante = cod_fabricante + rs8.getInt("codigo");
                    }
                    consulta1 = "INSERT INTO producto (nombre, precio, codigo_fabricante) VALUES (?, ?, ?)";
                    //prepara la consulta
                    PreparedStatement sentencia4 = conexion.prepareStatement(consulta1);
                    //el 1 se refiere al primer parámetro

                    sentencia4.setString(1, nombreProducto);
                    sentencia4.setString(2, precioProducto);
                    sentencia4.setInt(3, cod_fabricante);

                    sentencia4.execute();
                    System.out.println("Has añadido el nombre y el precio del producto");
                }

                //cod_fabricante = cod_fabricante + rs.getInt("codigo");
                //sentencia1.setString(1, nombreProducto);
                //sentencia1.setString(2, nombrePrecio);
            } catch (Exception ex) {
                Logger.getLogger(Conectores.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }
            System.out.println("¿Quieres volver a reiniciar el programa?");
            reiniciar = sc.nextLine();
        }
    }
}
