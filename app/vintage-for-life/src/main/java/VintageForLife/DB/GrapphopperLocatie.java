package VintageForLife.DB;

public class GrapphopperLocatie {
    private DBadres adres;
    private float lon;
    private float lat;
    private String id;
    private String naam;


    public GrapphopperLocatie(DBadres adres, float lon, float lat, String id) {
        this.adres = adres;
        this.lon = lon;
        this.lat = lat;
        this.id = id;
    }
    public GrapphopperLocatie(DBadres adres, String id, String naam) {
        this.adres = adres;
        this.lon = 0;
        this.lat = 0;
        this.id = id;
        this.naam = naam;
    }

    public GrapphopperLocatie(DBlevering levering, String id, String naam) {
        this(levering.getFirstAdres(), id, naam);
    }
    public GrapphopperLocatie(DBretour retour, String id, String naam)
    {
        this(retour.getAdres(), id, naam);
    }


    public void setLonLan (float lon, float lat) {
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
