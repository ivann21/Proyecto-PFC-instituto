/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instituto.daos;

import instituto.clases.Alumno;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class AlumnoDAO {
    private Connection connection;

    public AlumnoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Alumno> obtenerTodosLosAlumnos() throws SQLException {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT * FROM PUBLIC.INSTITUTO.ALUMNOS";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idAlumno = resultSet.getInt("ID alumno");
                String nombre = resultSet.getString("NOMBRE");
                String apellido = resultSet.getString("APELLIDO");
                String correoElectronico = resultSet.getString("CORREOELECTRONICO");
                int idCiclos = resultSet.getInt("ID ciclos");

                Alumno alumno = new Alumno(idAlumno, nombre, apellido, correoElectronico, idCiclos);
                alumnos.add(alumno);
            }
        }

        return alumnos;
    }
    public void eliminarAlumno(int idAlumno) throws SQLException {
    String sql = "DELETE FROM PUBLIC.INSTITUTO.ALUMNOS WHERE \"ID alumno\" = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, idAlumno);
        statement.executeUpdate();
    }
}
}