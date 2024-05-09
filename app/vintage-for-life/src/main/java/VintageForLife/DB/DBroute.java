package VintageForLife.DB;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DBroute {
    private int id;
    private String status;
    private LocalDateTime datum;
    private String priotisering;
    private List<DBlevering> leveringen;
    private List<DBretour> retouren;
    private DBadres beginadres;
    private DBadres eindadres;


    public DBroute(int id, String status, String datum, String priotisering)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.id = id;
        this.status = status;
        this.datum = LocalDateTime.parse(datum, format);
        this.priotisering = priotisering;
        this.leveringen = new ArrayList<DBlevering>();
    }

    public DBroute(int id, String status, LocalDateTime datum, String priotisering)
    {

        this.id = id;
        this.status = status;
        this.datum = datum;
        this.priotisering = priotisering;
        this.leveringen = new ArrayList<DBlevering>();
    }

    public void setRoute(DBroute route)
    {
        this.id = route.id;
        this.status = route.status;
        this.datum = route.datum;
        this.priotisering = route.priotisering;
        this.leveringen = route.leveringen;
        this.beginadres = route.beginadres;
        this.eindadres = route.eindadres;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setBeginadres(DBadres beginadres) {
        this.beginadres = beginadres;
    }

    public void setEindadres(DBadres eindadres) {
        this.eindadres = eindadres;
    }

    public void voegLeveringToe(DBlevering levering) {
        this.leveringen.add(levering);
    }

    public void voegRetourToe(DBretour retour) {
        this.retouren.add(retour);
    }

    public DBadres getBeginadres() {
        return beginadres;
    }

    public DBadres getEindadres() {
        return eindadres;
    }

    public String getStatus() {
        return status;
    }

    public String getPriotisering()
    {
        return priotisering;
    }



    public String getDatum()
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this.datum.format(format);

    }

    public List<DBlevering> getLeveringen()
    {
        return leveringen;
    }

    public String getId()
    {
        return String.valueOf(id);
    }




}

