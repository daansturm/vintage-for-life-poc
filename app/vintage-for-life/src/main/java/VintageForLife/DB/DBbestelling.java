package VintageForLife.DB;

import java.util.List;

public class DBbestelling {
    private int id;
    private int klantId;
    private String status;
    private boolean installatieservice;
    private String straat;
    private String huisnummer;
    private String plaats;
    private String postcode;
    private String land;
    private String voornaam;
    private String tussenvoegsel;
    private String achternaam;
    private String telefoonnummer;
    private List<DBproduct> producten;


    public DBbestelling(int id, int klant_id, String status, boolean installatieservice, String straat, String huisnummer, String Plaats, String postcode, String land,
                        String voornaam, String tussenvoegsel, String achternaam, String telefoonnummer)
    {
        this.id = id;
        this.klantId = klant_id;
        this.status = status;
        this.installatieservice = installatieservice;
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.plaats = Plaats;
        this.postcode = postcode;
        this.land = land;
        this.voornaam = voornaam;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.telefoonnummer = telefoonnummer;

    }

    public void voegProductToe(int id, String naam, String beschrijving, String afmeting, String gewicht, int aantal) {
        producten.add(new DBproduct(id, naam, beschrijving, afmeting, gewicht, aantal));
    }


}
