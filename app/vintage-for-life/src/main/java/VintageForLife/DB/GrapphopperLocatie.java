package VintageForLife.DB;

public class GrapphopperLocatie {
    private DBadres adres;
    private float lon;
    private float lat;
    private String id;


    public GrapphopperLocatie(DBadres adres, float lon, float lat, String id) {
        this.adres = adres;
        this.lon = lon;
        this.lat = lat;
        this.id = id;
    }

    public void setLonLan (float lon, float lat) {
        this.lon = lon;
        this.lat = lat;
    }



    }
