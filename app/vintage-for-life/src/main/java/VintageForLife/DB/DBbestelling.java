package VintageForLife.DB;

import java.util.ArrayList;
import java.util.List;

public class DBbestelling implements DBobject{
    private int id;
    private int klantId;
    private String status;
    private boolean installatieservice;
    private DBadres adres;
    private List<DBproduct> producten;


    public DBbestelling(int id, int klant_id, String status, boolean installatieservice, String straat, String huisnummer, String plaats, String postcode, String land)
    {
        this.id = id;
        this.klantId = klant_id;
        this.status = status;
        this.installatieservice = installatieservice;
        this.adres = new DBadres(straat,huisnummer,plaats, postcode, land);
        this.producten = new ArrayList<DBproduct>();
    }

    public void voegProductToe(String id, String naam, String beschrijving, String afmeting, String gewicht, String aantal) {
        producten.add(new DBproduct(Integer.parseInt(id), naam, beschrijving, afmeting, gewicht, Integer.parseInt(aantal)));
    }

    public void setBestelling(DBbestelling bestelling)
    {
        this.id = bestelling.id;
        this.klantId = bestelling.klantId;
        this.status = bestelling.status;
        this.installatieservice = bestelling.installatieservice;
        this.adres = bestelling.adres;
        this.producten = bestelling.producten;
    }

    public DBadres getAdres()
    {
        return adres;
    }

    public DBproduct getProduct(int index)
    {
        if (index < 0 || index >= producten.size())
            return null;

        return producten.get(index);
    }

    public String getId()
    {
        return String.valueOf(this.id);
    }

    public int getProductCount()
    {
        return producten.size();
    }


    public void Print() {
        System.out.println("Bestelling ID: " + this.id + " Klant ID: " + this.klantId);
        System.out.println("Status: " + this.status);
        System.out.println("Installatieservice: " + this.installatieservice);
        System.out.println("Adres: ");
        this.adres.Print();
        System.out.println("Producten: ");
        for(DBproduct p : producten)
            p.Print();

    }
}
