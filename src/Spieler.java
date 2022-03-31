public class Spieler {
    private String name;
    private int konto, wette, anzahlPunkte, nummer;

    public Spieler(String name) {
        this.name = name;
        this.konto = 100;
    }

    public void setWette(int wette) {
        this.wette = wette;
    }

    public int getWette() {
        return this.wette;
    }

    public int getAnzahlPunkte() {
        return anzahlPunkte;
    }

    public void setAnzahlPunkte(int anzahlPunkte) {
        this.anzahlPunkte = anzahlPunkte;
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public int getKonto() {
        return konto;
    }

    public void setKonto(int konto) {
        this.konto = konto;
    }

    public String getName() {
        return name;
    }
}
