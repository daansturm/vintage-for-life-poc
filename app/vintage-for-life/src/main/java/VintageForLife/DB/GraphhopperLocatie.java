package VintageForLife.DB;


public class GraphhopperLocatie implements DBobject{
    private DBadres adres;
    private String lon;
    private String lat;
    private String id;
    private String naam;


    public GraphhopperLocatie(DBadres adres, String lon, String lat, String id) {
        this.adres = adres;
        this.lon = lon;
        this.lat = lat;
        this.id = id;
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

    public void setGraphhopperLocatie(GraphhopperLocatie gl)
    {
        this.adres = gl.adres;
        this.lon = gl.lon;
        this.lat = gl.lat;
        this.id = gl.id;
        this.naam = gl.naam;

    }


    public void setLonLan (String lon, String lat) {
        this.lon = lon;
        this.lat = lat;
    }


    public DBadres getAdres() {
        if (adres != null)
            return adres;
        else
            return adres = new DBadres("NULL", "NULL", "NULL", "NULL", "NULL");
    }



    public String getLon()
    {
        return lon;
    }
    public String getLat()
    {
        return lat;
    }

    public String getNaam()
    {
        return naam;
    }

    public String getId()
    {
        return id;
    }


    public void Print() {
        System.out.println("Adres: " );
        adres.Print();
        System.out.println("Lon: " + lon);
        System.out.println("Lat: " + lat);
        System.out.println("Id: " + id);
        System.out.println("Naam: " + naam);

    }
}

