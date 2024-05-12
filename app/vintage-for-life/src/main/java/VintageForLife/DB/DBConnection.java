package VintageForLife.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

        String sql = "SELECT * FROM levering where id not in (select levering_id From levering_route )";
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
        String sql = "SELECT * FROM levering inner join levering_route lr on levering.id = lr.levering_id where route_id = ?"; //verkeerde id nummer terug
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, id);
        ResultSet resultSet = pstmt.executeQuery();


        while (resultSet.next()) {
            route.voegLeveringToe( new DBlevering(resultSet.getInt("id"), resultSet.getString("status"), resultSet.getString("bezorgdatum")));
        }


        return route;

    }

    public static List<DBretour> getSQLDBRetour() throws SQLException {
        String sql = "SELECT * FROM retour where id not in (select retour_id From retour_route )";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet resultSet = pstmt.executeQuery();

        List<DBretour> retouren = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String bestelling_id = resultSet.getString("klant_id");
            String status = resultSet.getString("status");
            String reden = resultSet.getString("reden");
            String opmerking = resultSet.getString("opmerking");
            String retourdatum = resultSet.getString("retourdatum");
            String straat = resultSet.getString("straat");
            String huisnummer = resultSet.getString("huisnummer");
            String plaats = resultSet.getString("plaats");
            String postcode = resultSet.getString("postcode");
            String land = resultSet.getString("land");
            retouren.add(new DBretour(id,bestelling_id,status,reden,opmerking,retourdatum,straat,huisnummer,plaats,postcode,land));


        }
        return retouren;


    }

    public static DBroute getSQLDBRetour(DBroute route) throws SQLException {

        String id = route.getId();
        String sql = "SELECT * FROM retour inner join retour_route lr on retour.id = lr.id where route_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, id);
        ResultSet resultSet = pstmt.executeQuery();


        while (resultSet.next()) {
            id = resultSet.getString("id");
            String bestelling_id = resultSet.getString("klant_id");
            String status = resultSet.getString("status");
            String reden = resultSet.getString("reden");
            String opmerking = resultSet.getString("opmerking");
            String retourdatum = resultSet.getString("retourdatum");
            String straat = resultSet.getString("straat");
            String huisnummer = resultSet.getString("huisnummer");
            String plaats = resultSet.getString("plaats");
            String postcode = resultSet.getString("postcode");
            String land = resultSet.getString("land");

            route.voegRetourToe( new DBretour(id,bestelling_id,status,reden,opmerking,retourdatum,straat,huisnummer,plaats,postcode,land));
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

    public static DBadres getSQLBeginEindAdress(int id) throws SQLException
    {
        String sql = "SELECT * FROM begin_eindadres where id = ? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, String.valueOf(id));
        ResultSet resultSet = pstmt.executeQuery();

        DBadres adres = null;
        while (resultSet.next()) {
            String straat = resultSet.getString("straat");
            String huisnummer = resultSet.getString("huisnummer");
            String plaats = resultSet.getString("plaats");
            String postcode = resultSet.getString("postcode");
            String land = resultSet.getString("land");
            adres = new DBadres(straat,huisnummer,plaats,postcode,land);

        }

        return adres;

    }

    public static void setSQLDBlevering(DBlevering levering)
    {
         //TODO Julian

    }

    public static DBroute setSQLRoute(DBroute route) throws SQLException {
<<<<<<< Updated upstream


=======
>>>>>>> Stashed changes
        String id = route.getId();

        //update
        if(id != "" || id == null) {
            String sql = "UPDATE route SET  status = ?, datum = ?, priotisering = ?, beginadres = ?, eindadreswhere = ? where id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, route.getStatus());
            pstmt.setString(2, route.getDatum());
            pstmt.setString(3, route.getPriotisering());
            pstmt.setString(4,"1");
            pstmt.setString(5,"1");
            pstmt.setString(6, id);
            int row = pstmt.executeUpdate();
        }
        else
        {
            String sql = "INSERT into route (status, datum, priotisering, beginadres, eindadres) values (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, route.getStatus());
            pstmt.setString(2, route.getDatum());
            pstmt.setString(3, route.getPriotisering());
            pstmt.setString(4,"1");
            pstmt.setString(5,"1");
            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    route.setId(generatedKeys.getInt(1));

                }
            }



        }

<<<<<<< Updated upstream
        String sql = "DELETE retour_route where route_id = ?" ;
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, route.getId());
        pstmt.executeUpdate();

        sql = "DELETE levering_route where route_id = ?" ;
        pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, route.getId());
        pstmt.executeUpdate();

        for(DBlevering levering : route.getLeveringen())
        {
            sql = "INSERT INTO levering_route (route_id, levering_id ) values (?,?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, route.getId());
            pstmt.setString(2, levering.getId());
            pstmt.executeUpdate();
        }

        for(DBretour retour : route.getRetouren())
        {
            sql = "INSERT INTO retour_route (route_id, retour_id ) values (?,?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, route.getId());
            pstmt.setString(2, retour.getId());
            pstmt.executeUpdate();
        }


      return route;
    }

}
=======
        id = route.getId();

        // Update levering routes
        String insertLeveringSql = "INSERT INTO levering_route (route_id, levering_id) VALUES (?, ?) ON DUPLICATE KEY UPDATE route_id = VALUES(route_id), levering_id = VALUES(levering_id)";
        try (PreparedStatement insertLeveringStmt = connection.prepareStatement(insertLeveringSql)) {
            for (DBlevering levering : route.getLeveringen()) {
                insertLeveringStmt.setString(1, id);
                insertLeveringStmt.setString(2, levering.getId());
                insertLeveringStmt.executeUpdate();
            }
        }

        // Update retour routes
        String insertRetourSql = "INSERT INTO retour_route (route_id, retour_id) VALUES (?, ?) ON DUPLICATE KEY UPDATE route_id = VALUES(route_id), retour_id = VALUES(retour_id)";
        try (PreparedStatement insertRetourStmt = connection.prepareStatement(insertRetourSql)) {
            for (DBretour retour : route.getRetouren()) {
                insertRetourStmt.setString(1, id);
                insertRetourStmt.setString(2, retour.getId());
                insertRetourStmt.executeUpdate();
            }
        }

        // Verwijder levering routes die niet meer in de lijst staan
        String deleteLeveringSql = "DELETE FROM levering_route WHERE route_id = ? AND levering_id NOT IN (" + String.join(",", Collections.nCopies(route.getLeveringen().size(), "?")) + ")";
        try (PreparedStatement deleteLeveringStmt = connection.prepareStatement(deleteLeveringSql)) {
            deleteLeveringStmt.setString(1, id);
            int parameterIndex = 2;
            for (DBlevering levering : route.getLeveringen()) {
                deleteLeveringStmt.setString(parameterIndex++, levering.getId());
            }
            deleteLeveringStmt.executeUpdate();
        }
     /*
        // Verwijder retour routes die niet meer in de lijst staan
        String deleteRetourSql = "DELETE FROM retour_route WHERE route_id = ? AND retour_id NOT IN (" + String.join(",", Collections.nCopies(route.getRetouren().size(), "?")) + ")";
        try (PreparedStatement deleteRetourStmt = connection.prepareStatement(deleteRetourSql)) {
            deleteRetourStmt.setString(1, id);
            int parameterIndex = 2;
            for (DBretour retour : route.getRetouren()) {
                deleteRetourStmt.setString(parameterIndex++, retour.getId());
            }
            deleteRetourStmt.executeUpdate();
        }
        */


        return route;
    }

}
>>>>>>> Stashed changes
