import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Spiel {

    Scanner scanner = new Scanner(System.in);
    boolean gameOver = false;

    Spieler[] spieler;

    public Spiel() {
        //Generiert Array mit angegebener Anzahl von Spielern
        System.out.println("Wie viele Spieler spielen mit?");
        int anzahlSpieler = 0;

        while(true) {
            while (true) {
                try {
                    anzahlSpieler = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Bitte Zahl angeben");
                    scanner.nextLine();
                }
            }

            if(anzahlSpieler > 1 && anzahlSpieler < 6) {
                this.spieler = new Spieler[anzahlSpieler];
                break;
            } else {
                System.out.println("Es können nur 2 bis 5 Spieler geben!");
            }
        }
        scanner.nextLine(); //Clears scanner

        //Fragt nach dem Namen der Spieler
        for(int i = 0; i < spieler.length; i++) {
            System.out.println("Wie heißt du Spieler " + (i + 1) + "?");
            spieler[i] = new Spieler(scanner.nextLine());
        }
    }

    public void spielen() {
        while(gameOver == false) {
            wettenAbfragen();

            for(int i = 0; i < spieler.length; i++) {
                boolean rundeOver = false;
                while(rundeOver == false) {
                    System.out.println("Möchtest du Würfeln " + spieler[i].getName() + "?\t" +
                            "Du hast gerade " + spieler[i].getAnzahlPunkte() + " Punkte");
                    System.out.println("1: Würfel\t 2: Aufhören (Pussy)");

                    int entscheidung;
                    while (true) {
                        try {
                            entscheidung = scanner.nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Bitte Zahl angeben");
                        }
                    }

                    switch (entscheidung) {
                        case 1:
                            int wurf1 = wuerfeln();
                            int wurf2 = wuerfeln();
                            int summe = wurf1 + wurf2;

                            if (summe == 7) {
                                System.out.println("(╯°□°）╯︵ ┻━┻ ┻━┻ ︵ ヽ(°□°ヽ)\n");
                                System.out.println("Du hast eine 7 gewürfelt Bozo. \t" + "Würfel 1: " + wurf1 + " Würfel 2: " + wurf2 + "\n");

                                spieler[i].setKonto(spieler[i].getKonto() - spieler[i].getWette());
                                spieler[i].setAnzahlPunkte(0);
                                System.out.println("Du hast jetzt nur noch " + spieler[i].getKonto() + " Geld.\n\n");
                                rundeOver = true;
                            } else {
                                System.out.println("└(^o^ )Ｘ( ^o^)┘\n");
                                System.out.println("Du hast eine " + summe + " gewürfelt. \t" + "Würfel 1: " + wurf1 + " Würfel 2: " + wurf2 + "\n");

                                spieler[i].setAnzahlPunkte(spieler[i].getAnzahlPunkte() + summe);
                            }
                            break;
                        case 2:
                            System.out.println("Du hast " + spieler[i].getAnzahlPunkte() + " Punkte\n\n");
                            rundeOver = true;
                            break;
                        default:
                            System.out.println("BRUH");
                            break;
                    }
                }
            }

            int gewinner = gewinnerRunde();
            try{
                spieler[gewinner].setKonto(spieler[gewinner].getKonto() + spieler[gewinner].getWette());
                System.out.println(spieler[gewinner].getName() + " hat diese Runde gewonnen!\t ( 。・_・。)人(。・_・。 )" );
                printPunkte();
                System.out.println();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("(ಠ_ಠ) Unentschieden (ಠ_ಠ)\n");
            }

            int spielerMitMeisetenPunkten = checkKonten();
            System.out.println(spieler[spielerMitMeisetenPunkten].getName() + " führt gerade mit " + spieler[spielerMitMeisetenPunkten].getKonto()+ " Geld.\n");

            checkGameOver();
        }
    }

    private void wettenAbfragen() {
        for(int i = 0; i < spieler.length; i++) {
            int wette;
            while(true) {
                System.out.println("Wie viel möchtest du Wetten " + spieler[i].getName() + "\t Dein Konto: " + spieler[i].getKonto());
                while (true) {
                    try {
                        wette = scanner.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Bitte eine Nummer angeben!");
                        scanner.nextLine();
                    }
                }

                if(wette > 0 && wette <= spieler[i].getKonto()) {
                    spieler[i].setWette(wette);
                    break;
                } else {
                    System.out.println("Du hast nicht genug Geld!");
                }
            }
        }
    }

    private void printKonten() {
        for(Spieler i : spieler) {
            System.out.print(i.getName() + ": " + i.getKonto() + " Geld | ");
        }
    }

    private void printPunkte() {
        for(Spieler i : spieler) {
            System.out.print(i.getName() + ": " + i.getAnzahlPunkte() + " | ");
        }
    }

    private int gewinnerRunde() {
        int index = -1;
        int hPunkte = 0;
        for(int i = 0; i < spieler.length; i++) {
            if(hPunkte < spieler[i].getAnzahlPunkte()) {
                hPunkte = spieler[i].getAnzahlPunkte();
                index = i;
            }
        }
        return index;
    }

    private int checkKonten() {
        int index = -1;
        int hPunkte = 0;
        for(int i = 0; i < spieler.length; i++) {
            if(hPunkte < spieler[i].getKonto()) {
                hPunkte = spieler[i].getKonto();
                index = i;
            }
        }
        return index;
    }

    private void checkGameOver() {
        for(Spieler i : spieler) {
            if(i.getKonto() <= 0) {
                System.out.println(i.getName() + " hat kein Geld mehr! Das Spiel ist zu ende.\n");
                int gewinner = checkKonten();
                System.out.println("Der Gewinner ist " + spieler[gewinner] + " mit " + spieler[gewinner].getKonto() + " Geld.\n");
                printKonten();
                gameOver = true;
            } else if(i.getKonto() >= 300) {
                System.out.println(i.getName() + " hat 300 Geld ereicht! Das Spiel ist zu ende.\n");
                int gewinner = checkKonten();
                System.out.println("Der Gewinner ist " + spieler[gewinner].getName() + " mit " + spieler[gewinner].getKonto() + " Geld.\n");
                printKonten();
                gameOver = true;
            }
        }
    }

    private int wuerfeln() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        return randomNum;
    }
}
