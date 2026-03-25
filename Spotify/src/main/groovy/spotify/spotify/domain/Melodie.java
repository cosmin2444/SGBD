package spotify.spotify.domain;

public class Melodie extends Entity<Long>{
    private String denumire;
    private int an_aparitie;
    private Long id_album;

    public Melodie(String denumire, int an_aparitie, Long id_album) {
        this.denumire = denumire;
        this.an_aparitie = an_aparitie;
        this.id_album = id_album;
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

    public Long getId_album() {
        return id_album;
    }

    public void setId_album(Long id_album) {
        this.id_album = id_album;
    }
}
