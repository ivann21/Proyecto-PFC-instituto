/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instituto.daos;

import instituto.clases.Aula;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AulaDAO {
    private Connection connection;

    public AulaDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Aula> obtenerTodasLasAulas() throws SQLException {
        List<Aula> aulas = new ArrayList<>();
        String sql = "SELECT * FROM INSTITUTO.AULAS";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idAula = resultSet.getInt("ID aula");
                String nombre = resultSet.getString("NOMBRE");
                String ubicacion = resultSet.getString("UBICACION");
                int capacidadAula = resultSet.getInt("CAPACIDAD_AULA");
                boolean pizarra = resultSet.getBoolean("PIZARRA");
                boolean ordenadores = resultSet.getBoolean("ORDENADORES");

                Aula aula = new Aula(idAula, nombre, ubicacion, capacidadAula, pizarra, ordenadores);
                aulas.add(aula);
            }
        }

        return aulas;
    }
       public void eliminarAula(int idAula) throws SQLException {
    String sql = "DELETE FROM PUBLIC.INSTITUTO.AULA WHERE \"ID aula\" = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, idAula);
        statement.executeUpdate();
    }
   }
}