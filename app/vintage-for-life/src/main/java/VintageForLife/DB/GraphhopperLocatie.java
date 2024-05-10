package VintageForLife.DB;

public class GraphhopperLocatie {
    private DBadres adres;
    private String lon;
    private String lat;
    private String id;


    public GraphhopperLocatie(DBadres adres, String lon, String lat, String id) {
        this.adres = adres;
        this.lon = lon;
        this.lat = lat;
        this.id = id;
    }

    public GraphhopperLocatie(DBadres adres, String id) {
        this.adres = adres;
        this.lon = "";
        this.lat = "";
        this.id = id;
    }

    public GraphhopperLocatie(DBlevering levering, String id) {
        this(levering.getFirstAdres(), id);
    }
    public GraphhopperLocatie(DBretour retour, String id)
    {
        this(retour.getAdres(), id);
    }


    public void setLonLan (String lon, String lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public DBadres getAdres() {
        return adres;
    }



    }
