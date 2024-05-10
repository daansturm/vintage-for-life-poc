package VintageForLife.DB;

public class GraphhopperLocatie {
    private DBadres adres;
    private String lon;
    private String lat;
    private String id;
    private String naam;


    public GraphhopperLocatie(DBadres adres, String lon, String lat, String id, String naam) {
        this.adres = adres;
        this.lon = lon;
        this.lat = lat;
        this.id = id;
        this.naam = naam;
    }

    public GraphhopperLocatie(DBadres adres, String id, String naam) {
        this.adres = adres;
        this.lon = "";
        this.lat = "";
        this.id = id;
        this.naam = naam;
    }

    public GraphhopperLocatie(DBlevering levering, String id, String naam) {
        this(levering.getFirstAdres(), id, naam);
    }
    public GraphhopperLocatie(DBretour retour, String id, String naam)
    {
        this(retour.getAdres(), id, naam);
    }


    public void setLonLan (String lon, String lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public DBadres getAdres() {
        return adres;
    }


    public String getId()
    {
        return id;
    }
    }


