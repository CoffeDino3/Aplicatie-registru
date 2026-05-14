package BazaDeDate.CRUD;

import BazaDeDate.Conectie;
import Functionalitate.Enumerari.Specialitati;
import Functionalitate.Simple.Grupe;
import java.sql.*;
import java.util.ArrayList;

public class GrupeCRUD {
    public static void adauga(Grupe g) throws SQLException {
        String sql="INSERT INTO grupe (an, clasa, nr_grupa, id_specialitate, grupa) VALUES (?, ?, ?, ?, ?)";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, g.getAn());
            ps.setInt(2, g.getClasa());
            ps.setInt(3, g.getNrGrupa());
            ps.setInt(4, g.getSpecialitate().ordinal()+1);
            ps.setString(5, g.getGrupa());
            ps.executeUpdate();
        }
    }

    public static ArrayList<Grupe> getAll() throws SQLException {
        ArrayList<Grupe> lista=new ArrayList<>();
        String sql="SELECT id, an, clasa, nr_grupa, id_specialitate, grupa FROM grupe ORDER BY id ASC";
        try (Connection con=Conectie.getConnection();
             Statement st=con.createStatement();
             ResultSet rs=st.executeQuery(sql)) {
            while (rs.next()) {
                Specialitati spec=Specialitati.values()[rs.getInt("id_specialitate")-1];
                Grupe g=new Grupe(
                        rs.getInt("id"),
                        rs.getInt("an"),
                        rs.getInt("clasa"),
                        rs.getInt("nr_grupa"),
                        spec
                );
                g.setGrupa(rs.getString("grupa"));
                lista.add(g);
            }
        }
        return lista;
    }

    public static Grupe getById(int id) throws SQLException {
        String sql="SELECT id, an, clasa, nr_grupa, id_specialitate, grupa FROM grupe WHERE id=?";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                Specialitati spec=Specialitati.values()[rs.getInt("id_specialitate")-1];
                Grupe g=new Grupe(
                        rs.getInt("id"),
                        rs.getInt("an"),
                        rs.getInt("clasa"),
                        rs.getInt("nr_grupa"),
                        spec
                );
                g.setGrupa(rs.getString("grupa"));
                return g;
            }
        }
        return null;
    }

    public static Grupe getByNume(String nume) throws SQLException {
        String sql="SELECT id, an, clasa, nr_grupa, id_specialitate, grupa FROM grupe WHERE grupa=?";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setString(1, nume);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                Specialitati spec=Specialitati.values()[rs.getInt("id_specialitate")-1];
                Grupe g=new Grupe(
                        rs.getInt("id"),
                        rs.getInt("an"),
                        rs.getInt("clasa"),
                        rs.getInt("nr_grupa"),
                        spec
                );
                g.setGrupa(rs.getString("grupa"));
                return g;
            }
        }
        return null;
    }

    public static void actualizeaza(Grupe g) throws SQLException {
        String sql="UPDATE grupe SET an=?, clasa=?, nr_grupa=?, id_specialitate=?, grupa=? WHERE id=?";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, g.getAn());
            ps.setInt(2, g.getClasa());
            ps.setInt(3, g.getNrGrupa());
            ps.setInt(4, g.getSpecialitate().ordinal()+1);
            ps.setString(5, g.getGrupa());
            ps.setInt(6, g.getId());
            ps.executeUpdate();
        }
    }

    public static void sterge(int id) throws SQLException {
        String sql="DELETE FROM grupe WHERE id=?";
        try (Connection con=Conectie.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}