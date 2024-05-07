package VintageForLife.DB;

public class DBproduct {
    private int id;
    private String naam;
    private String beschrijving;
    private String afmeting;
    private String gewicht;
    private int aantal;

    DBproduct(int id, String naam, String beschrijving, String afmeting, String gewicht, int aantal)
    {
        this.id = id;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.afmeting = afmeting;
        this.gewicht = gewicht;
        this.aantal = aantal;
    }
}
