package VintageForLife.DB;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DBretour {
    private int id;
    private int bestelling_id;
    private String status;
    private String reden;
    private String opmerking;
    private LocalDateTime retourdatum;
    private DBadres adres;

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


}
