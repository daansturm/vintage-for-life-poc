package VintageForLife.DB;

<<<<<<< Updated upstream:app/vintage-for-life/src/main/java/VintageForLife/DB/GrapphopperLocatie.java
public class GrapphopperLocatie {
=======
public class GraphhopperLocatie implements DBobject{
>>>>>>> Stashed changes:app/vintage-for-life/src/main/java/VintageForLife/DB/GraphhopperLocatie.java
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

<<<<<<< Updated upstream:app/vintage-for-life/src/main/java/VintageForLife/DB/GrapphopperLocatie.java
    public GrapphopperLocatie(DBlevering levering, String id, String naam) {
=======

    public GraphhopperLocatie(DBlevering levering, String id, String naam) {
>>>>>>> Stashed changes:app/vintage-for-life/src/main/java/VintageForLife/DB/GraphhopperLocatie.java
        this(levering.getFirstAdres(), id, naam);
    }
    public GrapphopperLocatie(DBretour retour, String id, String naam)
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


    public void setLonLan (float lon, float lat) {
        this.lon = lon;
        this.lat = lat;
    }


    public DBadres getAdres() {
        return adres;
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


<<<<<<< Updated upstream:app/vintage-for-life/src/main/java/VintageForLife/DB/GrapphopperLocatie.java
    }
=======
    public void Print() {
        System.out.println("Adres: " );
        adres.Print();
        System.out.println("Lon: " + lon);
        System.out.println("Lat: " + lat);
        System.out.println("Id: " + id);
        System.out.println("Naam: " + naam);

    }
}


>>>>>>> Stashed changes:app/vintage-for-life/src/main/java/VintageForLife/DB/GraphhopperLocatie.java
