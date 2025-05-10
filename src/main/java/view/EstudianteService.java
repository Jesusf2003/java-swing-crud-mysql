package view;

import model.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteService {

    public boolean save(
            String nombre,
            String apellidos,
            String edad,
            String pais,
            String correo,
            String celular
    ) {
        String sql = "INSERT INTO estudiantes (nombre, apellidos, edad, pais, correo, celular) values (?, ?, ?, ?, ?, ?);";
        try (Connection cn = Conexion.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setString(3, edad);
            ps.setString(4, pais);
            ps.setString(5, correo);
            ps.setString(6, celular);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear estudiante: "+e.getMessage());
            return false;
        }
    }

    public List<Estudiante> list() {
        List<Estudiante> lStudent = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes;";
        try (
                Connection cn = Conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Estudiante student = new Estudiante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("edad"),
                        rs.getString("pais"),
                        rs.getString("correo"),
                        rs.getString("celular")
                );
                lStudent.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar estudiantes: "+e.getMessage());
        }
        return lStudent;
    }

    public Estudiante listById(int id) {
        String sql = "SELECT * FROM estudiantes WHERE id = ?;";
        try (
                Connection cn = Conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Estudiante(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("edad"), rs.getString("pais"), rs.getString("correo"), rs.getString("celular"));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar estudiantes: "+e.getMessage());
        }
        return null;
    }

    public boolean update(int id, String nombre, String apellidos, String edad, String pais, String correo, String celular) {
        String sql = "UPDATE estudiantes SET nombre = ?, apellidos = ?, edad = ?, pais = ?, correo = ?, celular = ? WHERE id = ?;";
        try (
                Connection cn = Conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setString(3, edad);
            ps.setString(4, pais);
            ps.setString(5, correo);
            ps.setString(6, celular);
            ps.setInt(7, id);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estudiante: "+e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM estudiantes WHERE id = ?;";
        try (
                Connection cn = Conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar estudiante: "+e.getMessage());
            return false;
        }
    }
}