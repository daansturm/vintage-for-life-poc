package VintageForLife.DB;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBroute {
    private int id;
    private String status;
    private LocalDateTime datetime;
    private String priotisering;
    private List<DBlevering> leveringen;
    private DBadres beginadres;
    private DBadres eindadres;


    public DBroute(String status, LocalDateTime datetime, String priotisering)
    {
        this.status = status;
        this.datetime = datetime;
        this.priotisering = priotisering;
        this.leveringen = new ArrayList<DBlevering>();
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

    public DBadres getBeginadres() {
        return beginadres;
    }

    public DBadres getEindadres() {
        return eindadres;
    }

    public String getStatus() {
        return status;
    }


}

