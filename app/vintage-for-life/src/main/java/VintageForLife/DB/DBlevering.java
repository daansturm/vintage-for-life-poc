package VintageForLife.DB;

import kotlin.text.UStringsKt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBlevering implements DBobject{
    private int id;
    private String status;
    LocalDateTime bezorgdatum;
    private float lon;
    private float lat;
    private List<DBbestelling> bestellingen;


    public DBlevering(int id, String status, String bezorgdatum)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.id = id;
        this.status = status;
        this.bezorgdatum = LocalDateTime.parse(bezorgdatum, format);
        this.bestellingen = new ArrayList<>();

    }


    public void voegBestellingToe(String id, String klant_id, String status, String installatieservice, String straat, String huisnummer, String plaats, String postcode, String land)
    {
        bestellingen.add(new DBbestelling(Integer.parseInt(id) , Integer.parseInt(klant_id), status, Boolean.getBoolean(installatieservice), straat, huisnummer, plaats, postcode, land));
    }

    public void setLonLan( float lon,float lat)
    {
        this.lon = lon;
        this.lat = lat;
    }

    public void setLevering(DBlevering levering)
    {
        this.id = levering.id;
        this.status = levering.status;
        this.bezorgdatum = levering.bezorgdatum;
        this.lon = levering.lon;
        this.lat = levering.lat;
        this.bestellingen = levering.bestellingen;
    }

    public DBadres getFirstAdres()
    {
        if (bestellingen.size() == 0)
            return null;

        return bestellingen.get(0).getAdres();
    }

    public String getId()
    {
           return String.valueOf(this.id);
    }

    public LocalDateTime getBezorgdatum() {
        return bezorgdatum;
    }

    public int getBestellingCount()
    {
        return bestellingen.size();
    }

    public DBbestelling getBestelling(int index)
    {
        if (index < 0 || index >= bestellingen.size())
            return null;

        return bestellingen.get(index);
    }

    public List<DBbestelling> getBestellingen() {
        return bestellingen;
    }

    public boolean equalsDate(LocalDateTime datum)
    {
        LocalDate _datum = LocalDate.from(bezorgdatum);
        LocalDate __datum = LocalDate.from(datum);
        return _datum.equals(__datum);
    }


    public void Print() {
        System.out.println("Levering ID: " + this.id + " status: " + this.status + " datum: " + this.bezorgdatum.toString());
        System.out.println("lon: " + this.lon + " lat: " + this.lat);
        System.out.println("bestellingen: ");
        for(DBbestelling bestelling : bestellingen)
            bestelling.Print();

    }
}
