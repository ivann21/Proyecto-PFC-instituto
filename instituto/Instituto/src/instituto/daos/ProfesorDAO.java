/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instituto.daos;

import instituto.clases.Profesor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ProfesorDAO {
    
    private Connection connection;

    public ProfesorDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Profesor> obtenerTodosLosProfesores() throws SQLException {
        List<Profesor> profesores = new ArrayList<>();
        String sql = "SELECT * FROM INSTITUTO.PROFESORES";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idProfesor = resultSet.getInt("ID profesor");
                String nombre = resultSet.getString("NOMBRE");
                String apellido = resultSet.getString("APELLIDO");
                String correoElectronico = resultSet.getString("CORREOELECTRONICO");
                String contrasenia = resultSet.getString("CONTRASENIA");

                Profesor profesor = new Profesor(idProfesor, nombre, apellido, correoElectronico, contrasenia);
                profesores.add(profesor);
            }
        }

        return profesores;
    }
       public void eliminarProfesor(int idProfesor) throws SQLException {
    String sql = "DELETE FROM PUBLIC.INSTITUTO.PROFESORES WHERE \"ID profesor\" = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, idProfesor);
        statement.executeUpdate();
    }
   }
}