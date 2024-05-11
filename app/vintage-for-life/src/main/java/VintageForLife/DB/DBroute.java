package VintageForLife.DB;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DBroute {
    private int id;
    private String status;
    private LocalDateTime datum;
    private String priotisering;
    private List<DBlevering> leveringen;
    private List<DBretour> retouren;
    private DBadres beginadres;
    private DBadres eindadres;
    private List<GraphhopperLocatie> locaties;

    private List<String> prio_index;


    public DBroute()
    {
        this(-1, "nieuw", LocalDateTime.now(),"");
    }

    public DBroute(LocalDate datum)
    {
        this(-1, "nieuw", datum.atStartOfDay(),"");
    }

    public DBroute(int id, String status, String datum, String priotisering)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.id = id;
        this.status = status;
        this.datum = LocalDateTime.parse(datum, format);
        this.priotisering = priotisering;
        this.leveringen = new ArrayList<DBlevering>();
        this.retouren = new ArrayList<>();
        this.locaties = new ArrayList<>();

    }

    public DBroute(int id, String status, LocalDateTime datum, String priotisering)
    {

        this.id = id;
        this.status = status;
        this.datum = datum;
        this.priotisering = priotisering;
        this.leveringen = new ArrayList<DBlevering>();
        this.retouren = new ArrayList<>();
        this.locaties = new ArrayList<>();

    }



    public void MaakGraphhopperList()
    {
        prio_index = Arrays.asList(priotisering.split(":"));
        boolean correct = checkGraphhopperList(prio_index);
        String index;

        if(correct) {
            locaties.add(new GraphhopperLocatie(beginadres, "b_1", "Begin Adres"));
            for (String prio : prio_index) {

                if (prio.contains("l_")) {
                    index = prio.replace("l_", "");
                    locaties.add(new GraphhopperLocatie(leveringen.get(Integer.parseInt(index)), prio, "Levering: " + index));

                }
                if (prio.contains("r_")) {
                    index = prio.replace("l_", "");
                    locaties.add(new GraphhopperLocatie(retouren.get(Integer.parseInt(index)), prio, "Retour: " + index));
                }
            }

            locaties.add(new GraphhopperLocatie(eindadres, "e_1", "Eind Adres"));
        }
        else
        {
            locaties.add(new GraphhopperLocatie(beginadres, "b_1", "Begin Adres"));

            for(DBlevering levering : leveringen)
                locaties.add(new GraphhopperLocatie(levering, "l_" + levering.getId(), "Levering: " + levering.getId()));
            for(DBretour retour : retouren)
                locaties.add(new GraphhopperLocatie(retour, "r_" + retour.getId(), "Retour: " + retour.getId()));

            locaties.add(new GraphhopperLocatie(eindadres, "e_1", "Eind Adres"));

            // TODO DAAN Graphhopper call om VRP opnieuw uit te voeren, alleen locaties die goed zijn mee terug geven

            maakPriotisering(locaties);




            for(GraphhopperLocatie locatie : locaties)
            {
                System.out.println(locatie.getAdres().getPlaats());
                System.out.println(locatie.getId());
            }


        }
    }


    private boolean checkGraphhopperList(List<String> prio_index)
    {
        int count = 0;
        int item_count = leveringen.size() + retouren.size();
        String index;
        for(String prio :  prio_index)
        {
            if (prio.contains("l_")) {
                index = prio.replace("l_", "");
                count++;

                boolean gevonden = false;
                for(DBlevering levering : leveringen)
                {
                    if(Objects.equals(levering.getId(), index)) {
                        gevonden = true;
                        break;
                    }
                }
                if(!gevonden)
                    return false;

            }
            if (prio.contains("r_")) {
                index = prio.replace("r_", "");
                count++;

                boolean gevonden = false;
                for(DBlevering levering : leveringen)
                {
                    if(Objects.equals(levering.getId(), index)) {
                        gevonden = true;
                        break;
                    }
                }
                if(!gevonden)
                    return false;
            }
        }

        if(count != item_count)
            return false;
        else
            return true;

    }


    private void maakPriotisering(List<GraphhopperLocatie> locaties)
    {
        priotisering = "";
        for (GraphhopperLocatie loc : locaties) {
            priotisering += loc.getId() + ":";

        }
        priotisering = priotisering.substring(0, priotisering.length() - 1); //laatste character weghalen;

        // Vergelijk locatie-id met levering-id's en verwijder onnodige leveringen
        Iterator<DBlevering> leveringIterator = leveringen.iterator();
        while (leveringIterator.hasNext()) {
            DBlevering levering = leveringIterator.next();
            boolean gevonden = false;
            for (GraphhopperLocatie loc : locaties) {
                if (loc.getId().equals("l_" + levering.getId())) {
                    gevonden = true;
                    break;
                }
            }
            if (!gevonden) {
                leveringIterator.remove();
            }
        }

        // Vergelijk locatie-id met retour-id's en verwijder onnodige retouren
        Iterator<DBretour> retourIterator = retouren.iterator();
        while (retourIterator.hasNext()) {
            DBretour retour = retourIterator.next();
            boolean gevonden = false;
            for (GraphhopperLocatie loc : locaties) {
                if (loc.getId().equals("r_" + retour.getId())) {
                    gevonden = true;
                    break;
                }
            }
            if (!gevonden) {
                retourIterator.remove();
            }
        }
    }



    public void setRoute(DBroute route)
    {
        this.id = route.id;
        this.status = route.status;
        this.datum = route.datum;
        this.priotisering = route.priotisering;
        this.leveringen = route.leveringen;
        this.beginadres = route.beginadres;
        this.eindadres = route.eindadres;
        MaakGraphhopperList();

    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setBeginadres(DBadres beginadres) {
        this.beginadres = beginadres;
    }

    public void setEindadres(DBadres eindadres) {
        this.eindadres = eindadres;
    }

    public void voegLeveringToe(DBlevering levering) {
        this.leveringen.add(levering);
    }

    public void voegRetourToe(DBretour retour) {
        this.retouren.add(retour);
    }



    public DBadres getBeginadres() {
        return beginadres;
    }

    public DBadres getEindadres() {
        return eindadres;
    }

    public String getStatus() {
        return status;
    }

    public String getPriotisering()
    {
        return priotisering;
    }



    public String getDatum()
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this.datum.format(format);

    }

    public LocalDateTime getDatumTijd()
    {
        return datum;
    }

    public List<DBlevering> getLeveringen()
    {
        return leveringen;
    }

    public List<DBretour> getRetouren()
    {
        return retouren;
    }

    public String getId()
    {
        return String.valueOf(id);
    }

    public List<GraphhopperLocatie> getLocaties() {
        return locaties;
    }

    public String getRoute_info()
    {
        return this.locaties.get(1).getAdres().getPlaats() + " <> " + this.locaties.get(locaties.size() - 2).getAdres().getPlaats();

    }
}

