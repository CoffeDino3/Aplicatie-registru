package BazaDeDate.CRUD;

import BazaDeDate.Conectie;
import Functionalitate.Enumerari.Specialitati;
import Functionalitate.Simple.Grupe;
import Functionalitate.Simple.Note;
import Functionalitate.Simple.Studenti;
import java.sql.*;
import java.util.ArrayList;

public class StudentiCRUD {
    public static void adauga(Studenti s) throws SQLException {
        String sql = "INSERT INTO studenti (nume, prenume, id_grupa, poza) VALUES (?, ?, ?, ?)";
        try (Connection con = Conectie.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getNume());
            ps.setString(2, s.getPrenume());
            ps.setInt(3, s.getGrupa().getId());
            ps.setString(4, s.getPoza());
            ps.executeUpdate();
        }
    }

    public static ArrayList<Studenti> getAll() throws SQLException {
        ArrayList<Studenti> lista = new ArrayList<>();
        String sql = "SELECT s.id, s.nume, s.prenume, s.poza, " +
                "g.id AS id_grupa, g.an, g.clasa, g.nr_grupa, g.id_specialitate, g.grupa " +
                "FROM studenti s JOIN grupe g ON s.id_grupa = g.id ORDER BY s.id ASC";
        try (Connection con = Conectie.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Specialitati spec = Specialitati.values()[rs.getInt("id_specialitate") - 1];
                Grupe g = new Grupe(rs.getInt("id_grupa"), rs.getInt("an"), rs.getInt("clasa"), rs.getInt("nr_grupa"), spec);
                g.setGrupa(rs.getString("grupa"));
                Studenti s = new Studenti(rs.getInt("id"), rs.getString("nume"), rs.getString("prenume"), g);
                s.setPoza(rs.getString("poza"));
                lista.add(s);
            }
        }
        return lista;
    }

    public static Studenti getById(int id) throws SQLException {
        String sql = "SELECT s.id, s.nume, s.prenume, s.poza, " +
                "g.id AS id_grupa, g.an, g.clasa, g.nr_grupa, g.id_specialitate, g.grupa " +
                "FROM studenti s JOIN grupe g ON s.id_grupa = g.id WHERE s.id=?";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                Specialitati spec = Specialitati.values()[rs.getInt("id_specialitate") - 1];
                Grupe g = new Grupe(rs.getInt("id_grupa"), rs.getInt("an"), rs.getInt("clasa"), rs.getInt("nr_grupa"), spec);
                g.setGrupa(rs.getString("grupa"));
                Studenti s=new Studenti(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        g
                );
                s.setPoza(rs.getString("poza"));
                return s;
            }
        }
        return null;
    }

    // Aici am pus logica de "Update" pe care o aveai in UI
    public static void actualizeaza(Studenti s) throws SQLException {
        String sql = "UPDATE studenti SET nume=?, prenume=?, id_grupa=?, poza=? WHERE id=?";
        try (Connection con = Conectie.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getNume());
            ps.setString(2, s.getPrenume());
            ps.setInt(3, s.getGrupa().getIdGrupa());
            ps.setString(4, s.getPoza());
            ps.setInt(5, s.getId());
            ps.executeUpdate();
        }
    }

    public static void sterge(int id) throws SQLException {
        String sql="DELETE FROM studenti WHERE id=?";
        try (Connection con= Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}