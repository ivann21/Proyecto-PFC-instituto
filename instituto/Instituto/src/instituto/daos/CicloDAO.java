/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instituto.daos;

import instituto.clases.Ciclo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CicloDAO {
    private Connection connection;

    public CicloDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Ciclo> obtenerTodosLosCiclos() throws SQLException {
        List<Ciclo> ciclos = new ArrayList<>();
        String sql = "SELECT * FROM PUBLIC.INSTITUTO.CICLOS";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idCiclo = resultSet.getInt("ID Ciclos");
                String nombre = resultSet.getString("NOMBRE");
                int anio = resultSet.getInt("ANIO");
                String descripcion = resultSet.getString("DESCRIPCION");

                Ciclo ciclo = new Ciclo(idCiclo, nombre, anio, descripcion);
                ciclos.add(ciclo);
            }
        }

        return ciclos;
    }
        public void eliminarCiclo(int idCiclo) throws SQLException {
    String sql = "DELETE FROM PUBLIC.INSTITUTO.CICLOS WHERE \"ID Ciclos\" = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, idCiclo);
        statement.executeUpdate();
    }
   }
}