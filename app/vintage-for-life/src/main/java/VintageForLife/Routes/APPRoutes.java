package VintageForLife.Routes;

import VintageForLife.DB.DBConnection;
import VintageForLife.DB.DBbestelling;
import VintageForLife.DB.DBlevering;
import VintageForLife.DB.DBroute;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class APPRoutes {


    private static List<DBroute> routeList = new ArrayList<>();
    private static List<DBlevering> leveringList = new ArrayList<>();


    public static void SQLRoutes() throws SQLException {

        routeList = DBConnection.getSQLDBroute();
        for (DBroute route : routeList) {
            route.setRoute(DBConnection.getSQLDBlevering(route));
            route.setRoute(DBConnection.getSQLDBRetour(route));
            for(DBlevering levering : route.getLeveringen())
            {
                levering.setLevering(DBConnection.getSQLDBbestelling(levering));
                for(DBbestelling bestelling : levering.getBestellingen())
                {
                    bestelling.setBestelling(DBConnection.getSQLDBproduct(bestelling));

                }
            }
        }
    }

    public static void SQLUnAssignedLeveringen() throws SQLException {

        leveringList = DBConnection.getSQLDBlevering();
        for (DBlevering levering : leveringList) {
            levering.setLevering(DBConnection.getSQLDBbestelling(levering));
            for(DBbestelling bestelling : levering.getBestellingen())
            {
                bestelling.setBestelling(DBConnection.getSQLDBproduct(bestelling));
            }
        }
    }

    public static List<DBlevering> getUnAssignedLevering() {
        return leveringList;
    }

    public static List<DBroute> getRoutes() {
        return routeList;
    }


}
