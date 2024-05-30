/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instituto.daos;

import instituto.clases.Horario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HorarioDAO {
    private Connection connection;

    public HorarioDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Horario> obtenerTodosLosHorarios() throws SQLException {
        List<Horario> horarios = new ArrayList<>();
        String sql = "SELECT * FROM INSTITUTO.HORARIOS";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idHorario = resultSet.getInt("ID horario");
                int idAsignatura = resultSet.getInt("ID asignatura");
                int idProfesor = resultSet.getInt("ID profesor");
                int idAula = resultSet.getInt("ID aula");
                String diaSemana = resultSet.getString("dia de la semana");
                Time horaInicio = resultSet.getTime("hora de inicio");
                Time horaFin = resultSet.getTime("hora de fin");

                Horario horario = new Horario(idHorario, idAsignatura, idProfesor, idAula, diaSemana, horaInicio, horaFin);
                horarios.add(horario);
            }
        }

        return horarios;
    }
      public void eliminaHorario(int idCiclo) throws SQLException {
    String sql = "DELETE FROM PUBLIC.INSTITUTO.HORARIOS WHERE \"ID horario\" = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, idCiclo);
        statement.executeUpdate();
    }
   }
}