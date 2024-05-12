package VintageForLife.DB;

public class DBproduct implements DBobject{
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


    public String getId() {
        return String.valueOf(id);
    }


    public void Print() {
        System.out.println("Product ID: " + id);
        System.out.println("Naam: " + naam);
        System.out.println("Beschrijving: " + beschrijving);
        System.out.println("Afmeting: " + afmeting);
        System.out.println("Gewicht: " + gewicht);
        System.out.println("Aantal: " + aantal);
    }
}
