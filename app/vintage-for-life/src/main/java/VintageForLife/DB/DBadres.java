package VintageForLife.DB;

public class DBadres {
    private String straat;
    private String huisnummer;
    private String plaats;
    private String postcode;
    private String land;

    public DBadres(String straat, String huisnummer, String plaats, String postcode, String land) {
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.plaats = plaats;
        this.postcode = postcode;
        this.land = land;

    }

    public String getStraat() {
        return straat;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getPlaats() {
        return plaats;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getLand() {
        return land;
    }

}
