package VintageForLife.DB;

public class DBadres implements DBobject{
    private int id;
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

    public DBadres(String id, String straat, String huisnummer, String plaats, String postcode, String land) {
        this.id = Integer.parseInt(id);
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


    public String getId() {
        return String.valueOf(id);
    }

    public void Print()
    {
        System.out.println("Adres:");
        System.out.println("straat: " + straat);
        System.out.println("huisnummer: " + huisnummer);
        System.out.println("plaats: " + plaats);
        System.out.println("postcode: " + postcode);
        System.out.println("land: " + land);

    }

}
