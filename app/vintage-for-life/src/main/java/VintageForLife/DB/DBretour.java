package VintageForLife.DB;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DBretour implements DBobject {
    private int id;
    private int bestelling_id;
    private String status;
    private String reden;
    private String opmerking;
    private LocalDateTime retourdatum;
    private DBadres adres;
    private DBbestelling bestelling;

    public DBretour(int id, int bestelling_id, String status, String reden, String opmerking, LocalDateTime retourdatum, String straat, String huisnummer, String plaats, String postcode, String land)
    {
        this.id = id;
        this.bestelling_id = bestelling_id;
        this.status = status;
        this.reden = reden;
        this.opmerking = opmerking;
        this.retourdatum = retourdatum;
        this.adres = new DBadres(straat, huisnummer, plaats, postcode, land);

    }

    public DBretour(String id, String bestelling_id, String status, String reden, String opmerking, String retourdatum, String straat, String huisnummer, String plaats, String postcode, String land)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.id = Integer.parseInt(id);
        this.bestelling_id = Integer.parseInt(bestelling_id);
        this.status = status;
        this.reden = reden;
        this.opmerking = opmerking;
        this.retourdatum = LocalDateTime.parse(retourdatum, format);
        this.adres = new DBadres(straat, huisnummer, plaats, postcode, land);

    }

    public void VoegBestellingToe(String id, String klant_id, String status, String installatieservice, String straat, String huisnummer, String plaats, String postcode, String land)
    {
        this.bestelling = new DBbestelling(Integer.parseInt(id),Integer.parseInt(klant_id),status,Boolean.getBoolean(installatieservice),straat,huisnummer,plaats,postcode,land);
    }

    public DBadres getAdres()
    {
        if (adres != null)
            return adres;
        else
            return adres = new DBadres("NULL", "NULL", "NULL", "NULL", "NULL");
    }


    public LocalDateTime getRetourdatum()
    {
        return retourdatum;
    }

    public String getId()
    {
        return String.valueOf(id);
    }

    public boolean equalsDate(LocalDateTime datum)
    {
        LocalDate _datum = LocalDate.from(retourdatum);
        LocalDate __datum = LocalDate.from(datum);
        return _datum.equals(__datum);
    }


    public void Print() {
       System.out.println("Retour ID: " + id + " Bestelling: " + bestelling_id);
       System.out.println("Status: " + status);
       System.out.println("Reden: " + reden);
       System.out.println("Opmerking: " + opmerking);
       System.out.println("Retourdatum: " + retourdatum.toString());
       System.out.println("Adres: ");
       adres.Print();

    }
}
