package BazaDeDate.CRUD;

import BazaDeDate.Conectie;
import Functionalitate.Enumerari.Specialitati;
import Functionalitate.Simple.Grupe;
import java.sql.*;
import java.util.ArrayList;

public class GrupeCRUD {
    public static void adauga(Grupe g) throws SQLException {
        String sql="INSERT INTO grupe (id_grupa, id_specialitate, grupa) VALUES (?, ?, ?)";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1,g.getIdGrupa());
            ps.setInt(2, g.getSpecialitate().ordinal() + 1);
            ps.setString(3,g.getGrupa());
            ps.executeUpdate();
        }
    }
    public static ArrayList<Grupe> getAll() throws SQLException {
        ArrayList<Grupe> lista=new ArrayList<>();
        String sql = "SELECT * FROM grupe";
        try (Connection con =Conectie.getConnection();
             Statement st= con.createStatement();
             ResultSet rs=st.executeQuery(sql)) {
            while (rs.next()) {
                Specialitati spec = Specialitati.values()[rs.getInt("id_specialitate") - 1];
                Grupe g =new Grupe(rs.getInt("id_grupa"), spec);
                g.makeGrupa();
                lista.add(g);
            }
        }
        return lista;
    }
    public static Grupe getById(int id) throws SQLException {
        String sql = "SELECT * FROM grupe WHERE id_grupa = ?";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps= con.prepareStatement(sql)) {
            ps.setInt(1,  id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Specialitati spec=Specialitati.values()[rs.getInt("id_specialitate") - 1];
                Grupe g =new Grupe(rs.getInt("id_grupa"), spec);
                g.makeGrupa();
                return g;
            }
        }
        return null;
    }
    public static void actualizeaza(Grupe g) throws SQLException {
        String sql = "UPDATE grupe SET id_specialitate=?, grupa=? WHERE id_grupa=?";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1,  g.getSpecialitate().ordinal() + 1);
            ps.setString(2,g.getGrupa());
            ps.setInt(3,g.getIdGrupa());
            ps.executeUpdate();
        }
    }

    // DELETE
    public static void sterge(int id) throws SQLException {
        String sql = "DELETE FROM grupe WHERE id_grupa=?";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1,  id);
            ps.executeUpdate();
        }
    }
}