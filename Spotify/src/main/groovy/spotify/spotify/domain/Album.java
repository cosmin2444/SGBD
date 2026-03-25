package spotify.spotify.domain;

public class Album extends  Entity<Long> {
    private String denumire;
    private int an_aparitie;

    public Album(String denumire, int an_aparitie) {
        this.denumire = denumire;
        this.an_aparitie = an_aparitie;
    }


    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getAn_aparitie() {
        return an_aparitie;
    }

    public void setAn_aparitie(int an_aparitie) {
        this.an_aparitie = an_aparitie;
    }
}
