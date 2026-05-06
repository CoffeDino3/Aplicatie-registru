package BazaDeDate.CRUD;

import BazaDeDate.Conectie;
import Functionalitate.Enumerari.Discipline;
import Functionalitate.Simple.Note;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NoteCRUD {
    public static void adauga(Note n, int idStudent) throws SQLException {
        String sql="INSERT INTO note (id_student, nota, disciplina) VALUES (?, ?, ?)";
        try (Connection con= Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, idStudent);
            ps.setInt(2, n.getNota());
            ps.setString(3, n.getDisciplina().getDenumire());
            ps.executeUpdate();
        }
    }
    public static ArrayList<Note> getByStudent(int idStudent) throws SQLException {
        ArrayList<Note> lista=new ArrayList<>();
        String sql="SELECT * FROM note WHERE id_student=?";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, idStudent);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                Discipline d=Discipline.valueOf(
                        rs.getString("disciplina").toUpperCase().replace(" ", "_")
                );
                Note nota=new Note(rs.getInt("nota"), d);
                lista.add(nota);
            }
        }
        return lista;
    }
    public static void actualizeaza(int id, Note n) throws SQLException {
        String sql="UPDATE note SET nota=?, disciplina=? WHERE id=?";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1,n.getNota());
            ps.setString(2,  n.getDisciplina().getDenumire());
            ps.setInt(3,  id);
            ps.executeUpdate();
        }
    }
    public static void sterge(int id) throws SQLException {
        String sql="DELETE FROM note WHERE id=?";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }
}
