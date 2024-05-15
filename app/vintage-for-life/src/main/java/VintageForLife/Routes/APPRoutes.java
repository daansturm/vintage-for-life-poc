package VintageForLife.Routes;

import VintageForLife.DB.*;


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class APPRoutes {


    private static List<DBroute> routeList = new ArrayList<>();
    private static List<DBlevering> leveringList = new ArrayList<>();
    private static List<DBretour> retourList = new ArrayList<>();


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

            route.setBeginadres(DBConnection.getSQLBeginEindAdress(1));
            route.setEindadres(DBConnection.getSQLBeginEindAdress(1));
            route.MaakGraphhopperList();
            route.Print();
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

            levering.Print();
        }
    }

    public static void SQLUnAssignedRetour() throws SQLException {
        retourList = DBConnection.getSQLDBRetour();

    }

    public static void MakeRoute() throws SQLException {

        DBroute route;
        LocalDate datum = LocalDate.now();

        if (leveringList.isEmpty() && retourList.isEmpty())
        {
            return;
        }

        if(!leveringList.isEmpty())
            datum = LocalDate.from(leveringList.getFirst().getBezorgdatum());
        else if(!retourList.isEmpty())
            datum = LocalDate.from(retourList.getFirst().getRetourdatum());

        route = new DBroute(datum);

        for(DBlevering levering : leveringList)
        {
            if(levering.equalsDate(route.getDatumTijd()))
                route.voegLeveringToe(levering);

        }

        for(DBretour retour : retourList)
        {
            if(retour.equalsDate(route.getDatumTijd()))
                route.voegRetourToe(retour);

        }

        route.setBeginadres(DBConnection.getSQLBeginEindAdress(1));
        route.setEindadres(DBConnection.getSQLBeginEindAdress(1));

        route.MaakGraphhopperList();
        route.Print();

        route.setRoute(DBConnection.setSQLRoute(route));


        routeList.add(route);



    }

    public static List<DBlevering> getUnAssignedLevering() {
        return leveringList;
    }

    public static List<DBretour> getUnAssignedRetour() {
        return retourList;
    }

    public static List<DBroute> getRoutes() {
        return routeList;
    }


}
