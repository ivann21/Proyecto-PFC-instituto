/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instituto.perfil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProfesorDAO {
    private Connection connect() {
        // Conexión a la base de datos
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public List<String> getAllProfesorNames() {
        List<String> nombres = new ArrayList<>();
        String sql = "SELECT NOMBRE FROM PUBLIC.INSTITUTO.PROFESORES";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                nombres.add(rs.getString("NOMBRE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nombres;
    }
}