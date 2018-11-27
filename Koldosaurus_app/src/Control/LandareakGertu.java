/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Landareak;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author DM3-2-15
 */
public class LandareakGertu {

    public static Connection datuBaseIzenak() {

        Connection con = null;
        try {
            String url = "jdbc:sqlite:DBs\\datuBaseIzenak4.db";
            con = DriverManager.getConnection(url);
        } catch (SQLException sqex) {
            System.out.println(sqex.getMessage());
        }
        return con;
    }

    public static void createTableSQLiteDatabases() {

        try (Connection con = datuBaseIzenak(); Statement stmt = con.createStatement();) {
            String tablasortu = "CREATE TABLE IF NOT EXISTS SQLiteDatubaseak(\n"
                    + "izena text,"
                    + "mota text);";
            stmt.execute(tablasortu);
        } catch (SQLException sqlex) {
            System.out.println("error");
        }

    }

    public static ObservableList<String> datuBaseIzenZerrenda(String mota) {
        ObservableList<String> listia = FXCollections.observableArrayList();
        try (Connection con = datuBaseIzenak();
                Statement stmt = con.createStatement()) {
            String sententzia = "";
            if (mota.equals("SQLite")) {
                sententzia = "Select * from SQLiteDatubaseak WHERE mota = 'SQLite'";
            } else {
                sententzia = "Select * from SQLiteDatubaseak WHERE mota = 'MYSQL'";
            }

            ResultSet rs = stmt.executeQuery(sententzia);
            while (rs.next()) {
                listia.add(rs.getString("izena"));
            }

        } catch (SQLException sqle) {
            System.out.println("error");
        }

        return listia;
    }

    public static Connection connectSQLite(String izena) {
        Connection con = null;
        try {
            String url = "jdbc:sqlite:DBs\\" + izena;
            con = DriverManager.getConnection(url);
            //ez dau tabla sortzen
            String tablasortu = "CREATE TABLE IF NOT EXISTS landareak"
                    + "(izena text,"
                    + "Description text,"
                    + "color text,"
                    + "size integer,"
                    + "flowers text,"
                    + "CName text primary key);";
            Statement stmt = con.createStatement();
            stmt.execute(tablasortu);
        } catch (SQLException sqex) {
            System.out.println(sqex.getMessage());
        }
        return con;
    }

    //Debug missing
    public static ObservableList<Landareak> SQLiteDatuak(String izena) {
        ObservableList<Landareak> listia = FXCollections.observableArrayList();
        try (Connection con = connectSQLite(izena);
                Statement stmt = con.createStatement()) {
            izena = izena.replace(izena.substring(izena.length() - 3), "");
            if (datuBaseIzenZerrenda("SQLite").contains(izena)) {
                String sententzia = "SELECT * from landareak";
                ResultSet rs = stmt.executeQuery(sententzia);
                while (rs.next()) {
                    Landareak land = new Landareak(rs.getString("izena"), rs.getString("Description"), rs.getString("color"), rs.getInt("size") + "", Boolean.parseBoolean(rs.getString("flowers")), rs.getString("CName"));
                    listia.add(land);
                }
            } else {
                try (Connection con2 = datuBaseIzenak();
                        Statement stmt2 = con2.createStatement()) {
                    String gehituTaula = "insert into SQLiteDatubaseak values('" + izena + "','SQLite');";
                    stmt2.executeUpdate(gehituTaula);
                } catch (SQLException sqlex) {
                    System.out.println(sqlex.getMessage());
                }
            }

        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
        }
        return listia;
    }

    public static void addToDatabase(Landareak landarea, String datubasea) {
        try (Connection con = connectSQLite(datubasea + ".db");
                Statement stmt = con.createStatement();) {
            String inserta = "insert into landareak values('" + landarea.getName() + "','" + landarea.getDescription() + "','" + landarea.getColor() + "'," + Float.parseFloat(landarea.getSize().replace(landarea.getSize().substring(landarea.getSize().length() - 1, landarea.getSize().length()), "")) + ",'" + landarea.getFlowers() + "','" + landarea.getCName() + "');";
            stmt.executeUpdate(inserta);
        } catch (SQLException exSQL) {
            System.out.println("ERROR");
        }
    }

    public static void deleteFromDatabase(Landareak landarea, String datubasea) {
        String sql = "DELETE FROM landareak WHERE CName = ?";

        try (Connection con = connectSQLite(datubasea + ".db");
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, landarea.getCName());
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //MSQL Datu Baseak hemendik aurrera
    public static ObservableList<Landareak> MySQLDatuak(String izena) {
        ObservableList<Landareak> listia = FXCollections.observableArrayList();
        //Lio guapua hurrengo lineakin :(
        try (Connection con = DriverManager.getConnection(izena.replace(izena.substring(izena.length() - 3), ""),"root","root");
                Statement stmt = con.createStatement()) {
            izena = izena.replace(izena.substring(izena.length() - 3), "");
            if (datuBaseIzenZerrenda("MYSQL").contains(izena)) {
                String sententzia = "SELECT * from landareak";
                ResultSet rs = stmt.executeQuery(sententzia);
                while (rs.next()) {
                    Landareak land = new Landareak(rs.getString("izena"), rs.getString("Description"), rs.getString("color"), rs.getInt("size") + "", Boolean.parseBoolean(rs.getString("flowers")), rs.getString("CName"));
                    listia.add(land);
                }
            } else {
                datubaseaSortu(izena);
                try (Connection con2 = datuBaseIzenak();
                        Statement stmt2 = con2.createStatement()) {
                    String gehituTaula = "insert into SQLiteDatubaseak values('" + izena + "','MYSQL');";
                    stmt2.executeUpdate(gehituTaula);
                } catch (SQLException sqlex) {
                    System.out.println(sqlex.getMessage());
                }
            }

        } catch (SQLException sqlex) {
            datubaseaSortu(izena);
            try (Connection con2 = datuBaseIzenak();
                    Statement stmt2 = con2.createStatement()) {
                String gehituTaula = "insert into SQLiteDatubaseak values('" + izena + "','MYSQL');";
                stmt2.executeUpdate(gehituTaula);
            } catch (SQLException sqlex2) {
                System.out.println(sqlex2.getMessage());
            }
        }
        return listia;
    }

    public static Connection KonexioaMySQL() {
        String database = "jdbc:mysql://localhost";
        Connection conn = null;
        try {
            String user = "root";
            String pass = "root";
            conn = DriverManager.getConnection(database, user, pass);

            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException ahh) {
            System.out.println(ahh.getMessage());
        }
        return conn;
    }

    public static void datubaseaSortu(String datubasea) {
        try (Connection conn = KonexioaMySQL(); Statement state = conn.createStatement()) {
            String sql = "CREATE DATABASE " + datubasea;
            state.execute(sql);
            System.out.println("Datu basea sortu da");
            taulaSortu(datubasea);
        } catch (SQLException ahh) {
            System.out.println("Errorea datu basea sortzerakoan");
        }
    }

    public static void taulaSortu(String database) {
        String sql = "CREATE TABLE IF NOT EXISTS landareak(\n"
                + "id integer PRIMARY KEY,\n"
                + "name text NOT NULL,\n"
                + "capacity text\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(database, "root", "root");
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
/*
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Konexioa
{
    public static Connection Konexioa()
    {
        String database = "jdbc:mysql://localhost";
        Connection conn = null;
        try
        {           
            String user = "root";
            String pass = "root";
            conn = DriverManager.getConnection(database, user, pass);
           
            System.out.println("Connection to SQLite has been established.");
        }
        catch(SQLException ahh)
        {
            System.out.println(ahh.getMessage());
        }
        return conn;
    }
}

package SQL_sententziak;

import connection.Konexioa;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLsententziak
{
    public static void datubaseaSortu()
    {
        try(Connection conn = Konexioa.Konexioa(); Statement state = conn.createStatement())
        {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("The driver name is "+ meta.getDriverName());
            String sql = "CREATE DATABASE izaPaliza";
            state.execute(sql);
            System.out.println("Datu basea sortu da");
        }
        catch(SQLException ahh)
        {
            System.out.println("Errorea datu basea sortzerakoan");
        }
    }
    public static void taulaSortu(String database, String table)
    {
        String sql = "CREATE TABLE IF NOT EXISTS "+ table + "(\n"
                + "id integer PRIMARY KEY,\n"
                + "name text NOT NULL,\n"
                + "capacity text\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(database,"root","root");
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void insert(String database, String table ,int ID, String name, String capacity) throws SQLException
    {
        String sql = "INSERT INTO "+table+" (id, name, capacity) VALUES(?,?,?)";
       
        try (Connection conn = DriverManager.getConnection(database, "root", "root"); PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, ID);
            pstmt.setString(2, name);
            pstmt.setString(3, capacity);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
 */
