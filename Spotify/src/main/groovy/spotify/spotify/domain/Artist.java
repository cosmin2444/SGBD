package spotify.spotify.domain;

public class Artist extends Entity<Long>{
    private String nume_scena;

    public Artist(String nume_scena) {
        this.nume_scena = nume_scena;
    }

    public String getNume_scena() {
        return nume_scena;
    }

    public void setNume_scena(String nume_scena) {
        this.nume_scena = nume_scena;
    }
}
