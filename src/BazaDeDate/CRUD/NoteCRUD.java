package BazaDeDate.CRUD;

import BazaDeDate.Conectie;
import Functionalitate.Enumerari.Discipline;
import Functionalitate.Simple.Note;
import java.sql.*;
import java.util.ArrayList;

public class NoteCRUD {
    public static void adauga(Note n, int idStudent) throws SQLException {
        String sql="INSERT INTO note (id_student, nota, disciplina) VALUES (?, ?, ?)";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, idStudent);
            ps.setInt(2, n.getNota());
            ps.setString(3, n.getDisciplina().getDenumire());
            ps.executeUpdate();
        }
    }

    public static ArrayList<Object[]> getAllFull() throws SQLException {
        ArrayList<Object[]> lista = new ArrayList<>();
        String sql = "SELECT n.id, n.nota, n.disciplina, n.id_student, s.nume, s.prenume, g.grupa " +
                "FROM note n " +
                "JOIN studenti s ON n.id_student = s.id " +
                "JOIN grupe g ON s.id_grupa = g.id " +
                "ORDER BY n.id ASC";
        try (Connection con = Conectie.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("nota"),
                        rs.getString("disciplina"),
                        rs.getInt("id_student"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("grupa")
                });
            }
        }
        return lista;
    }

    public static Note getById(int id) throws SQLException {
        String sql="SELECT nota, disciplina FROM note WHERE id=?";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                String numeDisciplina=rs.getString("disciplina");
                Discipline d=null;
                for (Discipline disc : Discipline.values()) {
                    if (disc.getDenumire().equals(numeDisciplina)) {
                        d=disc;
                        break;
                    }
                }
                if (d!=null) {
                    return new Note(rs.getInt("nota"), d);
                }
            }
        }
        return null;
    }

    public static ArrayList<Note> getByStudent(int idStudent) throws SQLException {
        ArrayList<Note> lista=new ArrayList<>();
        String sql="SELECT nota, disciplina FROM note WHERE id_student=?";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, idStudent);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                String numeDisciplina=rs.getString("disciplina");
                Discipline d=null;
                for (Discipline disc : Discipline.values()) {
                    if (disc.getDenumire().equals(numeDisciplina)) {
                        d=disc;
                        break;
                    }
                }
                if (d!=null) {
                    Note nota=new Note(rs.getInt("nota"), d);
                    lista.add(nota);
                }
            }
        }
        return lista;
    }

    public static void actualizeaza(int id, Note n) throws SQLException {
        String sql="UPDATE note SET nota=?, disciplina=? WHERE id=?";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, n.getNota());
            ps.setString(2, n.getDisciplina().getDenumire());
            ps.setInt(3, id);
            ps.executeUpdate();
        }
    }

    public static void sterge(int id) throws SQLException {
        String sql="DELETE FROM note WHERE id=?";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    public static int countByStudent(int idStudent) throws SQLException {
        String sql = "SELECT COUNT(*) FROM note WHERE id_student = ?";
        try (Connection con = Conectie.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idStudent);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }
}