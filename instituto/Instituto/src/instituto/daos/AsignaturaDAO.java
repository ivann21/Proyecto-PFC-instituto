/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instituto.daos;
import instituto.clases.Asignatura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsignaturaDAO {
    private Connection connection;

    public AsignaturaDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Asignatura> obtenerTodasLasAsignaturas() throws SQLException {
        List<Asignatura> asignaturas = new ArrayList<>();
        String sql = "SELECT * FROM INSTITUTO.ASIGNATURAS";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idAsignatura = resultSet.getInt("ID Asignatura");
                String nombre = resultSet.getString("NOMBRE");
                String descripcion = resultSet.getString("DESCRIPCION");
                int idCiclos = resultSet.getInt("ID_CICLOS");

                Asignatura asignatura = new Asignatura(idAsignatura, nombre, descripcion, idCiclos);
                asignaturas.add(asignatura);
            }
        }

        return asignaturas;
    }
    public void eliminarAsignatura(int idAsignatura) throws SQLException {
    String sql = "DELETE FROM PUBLIC.INSTITUTO.ASIGNATURAS WHERE \"ID Asignatura\" = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, idAsignatura);
        statement.executeUpdate();
    }
}
}