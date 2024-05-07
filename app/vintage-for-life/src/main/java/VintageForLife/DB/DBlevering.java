package VintageForLife.DB;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBlevering {
    private int id;
    private String status;
    LocalDateTime bezorgdatum;
    private List<DBbestelling> bestellingen;


    public DBlevering() {

    }

    void voegBestellingToe(int id, int klant_id, String status, boolean installatieservice, String straat, String huisnummer, String plaats, String postcode, String land,
                      String voornaam, String tussenvoegsel, String achternaam, String telefoonnummer)
    {
        bestellingen.add(new DBbestelling(id, klant_id, status, installatieservice, straat, huisnummer, plaats, postcode, land,
                                          voornaam, tussenvoegsel, achternaam, telefoonnummer ));
    }

}
