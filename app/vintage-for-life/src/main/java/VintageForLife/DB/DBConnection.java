package VintageForLife.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/vintage_for_life_poc";
                String username = "root";
                String password = "password";
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static DBbestelling getSQLDBproduct(DBbestelling bestelling) throws SQLException {

        String id = bestelling.getId();
        String sql = "Select * From product inner join bestelling_regels as br on product.id = br.id where bestelling_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, id);

        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            id = resultSet.getString("id");
            String naam = resultSet.getString("naam");
            String beschrijving = resultSet.getString("beschrijving");
            String afmeting = resultSet.getString("afmeting");
            String gewicht = resultSet.getString("gewicht");
            String aantal = resultSet.getString("aantal");

            bestelling.voegProductToe(id, naam, beschrijving, afmeting, gewicht, aantal);


        }

        return bestelling;
    }

    public static DBlevering getSQLDBbestelling(DBlevering levering) throws SQLException
    {

        String id = levering.getId();
        String sql = "Select * From bestelling inner join bestelling_levering bl on bestelling.id = bl.id where levering_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, id);

        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            id = resultSet.getString("id");
            String klant_id = resultSet.getString("klant_id");
            String status = resultSet.getString("status");
            String installatieservice = resultSet.getString("installatieservice");
            String straat = resultSet.getString("straat");
            String huisnummer = resultSet.getString("huisnummer");
            String plaats = resultSet.getString("plaats");
            String postcode = resultSet.getString("postcode");
            String land = resultSet.getString("land");


            levering.voegBestellingToe(id,klant_id,status,installatieservice,straat,huisnummer,plaats,postcode,land);
        }


        return levering;
    }


    public static List<DBlevering> getSQLDBlevering() throws SQLException {

        String sql = "SELECT * FROM levering where status = 'nieuw'";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet resultSet = pstmt.executeQuery();

        List<DBlevering> leveringen = new ArrayList<>();
        while (resultSet.next()) {
            leveringen.add(new DBlevering(resultSet.getInt("id"), resultSet.getString("status"), resultSet.getString("bezorgdatum")));

        }


        return leveringen;

    }

    public static DBroute getSQLDBlevering(DBroute route) throws SQLException {

        String id = route.getId();
        String sql = "SELECT * FROM levering inner join levering_route lr on levering.id = lr.id where route_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, id);
        ResultSet resultSet = pstmt.executeQuery();


        while (resultSet.next()) {
            route.voegLeveringToe( new DBlevering(resultSet.getInt("id"), resultSet.getString("status"), resultSet.getString("bezorgdatum")));
        }


        return route;

    }

    public static List<DBroute> getSQLDBroute() throws SQLException {

        String sql = "SELECT * FROM route ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet resultSet = pstmt.executeQuery();

        List<DBroute> routes = new ArrayList<>();
        while (resultSet.next()) {
            routes.add(new DBroute(resultSet.getInt("id"), resultSet.getString("status"), resultSet.getString("datum"), resultSet.getString("priotisering") ));

        }



        return routes;
    }

    public static void setSQLDBlevering(DBlevering levering)
    {

    }

    public static void setSQLRoute(DBroute route)
    {

    }

}
