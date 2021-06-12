import javax.imageio.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Logic {
    public Image rubahka;
    private Stopka[] stopki;
    private boolean pervVidacha;
    public boolean endG;

    private int nomStopki;
    private int nomKarti;
    private int dx, dy;
    public int oldX, oldY;
    private Timer tmEndGame;

    public Logic() {
        try {
            rubahka = ImageIO.read(new File("dist/k0.png"));
        } catch (Exception ex) {
            System.out.println("Not Upload");
        }
        stopki = new Stopka[13];
        for (int i = 0; i < 13; i++) {
            stopki[i] = new Stopka();
        }
        tmEndGame = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                for (int i = 2; i <= 5; i++) {
                    Karta getKarta = stopki[i].get(0);
                    stopki[i].add(getKarta);
                    stopki[i].remove(0);
                }
            }
        });
        start();
    }

    private void testEndGame() {
        if ((stopki[2].size() == 13) && (stopki[3].size() == 13) && (stopki[4].size() == 13)
                && (stopki[5].size() == 13)) {
            endG = true;
            tmEndGame.start();
        }
    }

    private void openKarta() {
        for (int i = 6; i <= 12; i++) {
            if (stopki[i].size() > 0) {
                int nomPosled = stopki[i].size() - 1;
                Karta getKarta = stopki[i].get(nomPosled);
                if (getKarta.tipRubahka == true)
                    getKarta.tipRubahka = false;
            }
        }
    }

    public void mouseDragged(int mouseX, int mouseY) {
        if (nomStopki>=0) {
            Karta getKarta = stopki[nomStopki].get(nomKarti);
            getKarta.x = mouseX-dx;
            getKarta.y = mouseY-dy;
            if (getKarta.x<0) getKarta.x = 0;
            if (getKarta.x>720) getKarta.x = 720;
            if (getKarta.y<0) getKarta.y = 0;
            if (getKarta.y>650) getKarta.y = 650;
            int y = 20;
            for (int i = nomKarti+1; i < stopki[nomStopki].size(); i++) {
                stopki[nomStopki].get(i).x = getKarta.x;
                stopki[nomStopki].get(i).y = getKarta.y+y;
            y+=20;
            }
        }
    }
    private void setVibrana(int nom, int mouseX, int mouseY) {
        if ((nom>=1) && (nom<=5)) {
            if (stopki[nom].size()>0) {
                int nomPosled = stopki[nom].size()-1;
                Karta getKarta = stopki[nom].get(nomPosled);
                getKarta.vibrana = true;
                nomKarti = nomPosled;
                nomStopki = nom;
                dx = mouseX-getKarta.x;
                dy = mouseY-getKarta.y;
                oldX = getKarta.x;
                oldY = getKarta.y;
            }
        }
        else if ((nom>=6) && (nom<=12)) {
            if (stopki[nom].size()>0) {
                int nomPosled = stopki[nom].size()-1;
                Karta getKarta = stopki[nom].get(nomPosled);
                int nomVibrana = -1;
                if ((mouseY>=getKarta.y) && (mouseY<=(getKarta.y+97))) {
                    nomVibrana = nomPosled;
                }
                else if (mouseY < getKarta.y) {
                    nomVibrana = (mouseY-130)/20;
                    if (stopki[nom].get(nomVibrana).tipRubahka==true) {
                        nomVibrana = -1;
                    }
                }
                if (nomVibrana!=-1) {
                    Karta getKartaVibrana = stopki[nom].get(nomVibrana);
                    getKartaVibrana.vibrana = true;
                    nomKarti = nomVibrana;
                    nomStopki = nom;
                    dx = mouseX-getKartaVibrana.x;
                    dy = mouseY-getKartaVibrana.y;
                    oldX = getKartaVibrana.x;
                    oldY = getKartaVibrana.y;
                }
            }
        }
    }
    public void mousePressed(int mouseX, int mouseY) {
        int nom = getNomKolodaPress(mouseX, mouseY);
        setVibrana(nom,mouseX,mouseY);
    }
    private boolean testPerenos (int nom1, int nom2) {
        boolean rez = false;
        Karta getKarta1 = stopki[nom1].get(nomKarti);
        Karta getKarta2 = null;
        if (stopki[nom2].size()>0) {
            getKarta2 = stopki[nom2].get(stopki[nom2].size()-1);
        }
        if ((nom2>=2) && (nom2<=5)) {
            if (nomKarti==(stopki[nom1].size()-1)) {
                if (getKarta2==null) {
                    if (getKarta1.tipkarta==12) rez=true;
                }
                else if ((getKarta2.tipkarta==12) &&
                (getKarta1.maw==getKarta2.maw) &&
                (getKarta1.tipkarta==0)) {
                    rez = true;
                }
                else if ((getKarta2.tipkarta>=0) && 
                (getKarta2.tipkarta<11) && 
                (getKarta1.maw == getKarta2.maw)) {
                    if ((getKarta2.tipkarta+1==getKarta1.tipkarta)) {
                        rez = true;
                    }
                }
                if (rez==true) {
                    getKarta1.x=(110*(nom2+1))+30;
                    getKarta1.y = 15;
                    stopki[nom2].add(getKarta1);
                    stopki[nom1].remove(nomKarti);
                    testEndGame();
                }
            }
        }
        if ((nom2>=6) && (nom2<=12)) {
            int x = 30+(nom2-6)*110;
            int y = 130;
            if (getKarta2==null) {
                if (getKarta1.tipkarta==11) rez=true;
            }
            else {
                if (getKarta2.tipRubahka==false) {
                    if (getKarta2.tipkarta!=12) {
                        if ((getKarta2.tipkarta==getKarta1.tipkarta+1)||
                        ((getKarta2.tipkarta==0) && (getKarta1.tipkarta==12))) {
                            if (getKarta2.red_karta!=getKarta1.red_karta) {
                                y=getKarta2.y+20;
                                rez = true;
                            }
                        }
                    }
                }
            }
            if (rez==true) {
                for (int i = nomKarti; i < stopki[nom1].size(); i++) {
                    Karta getKarta_ = stopki[nom1].get(i);
                    getKarta_.x = x;
                    getKarta_.y = y;
                    stopki[nom2].add(getKarta_);
                    y+=20;
                }
                for (int i = stopki[nom1].size()-1; i <= nomKarti; i--) {
                    stopki[nom1].remove(i);
                }
            }
        }
        return rez;
    }
    public void mouseDoublePressed(int mouseX, int mouseY) {
        int nom = getNomKolodaPress(mouseX,mouseY);
        if ((nom==1) || ((nom>=6) && (nom<=12))) {
            if (stopki[nom].size()>0) {
                int nomPosled = stopki[nom].size()-1;
                Karta getKarta = stopki[nom].get(nomPosled);
                if ((mouseY>=getKarta.y) && (mouseY<=(getKarta.y+97))) {
                    for (int i = 2; i <= 5; i++) {
                        int rez = -1;
                        if (stopki[i].size()==0) {
                            if (getKarta.tipkarta==12) {
                                rez = i;
                            }
                        }
                        else {
                            int nomPosled2 = stopki[i].size()-1;
                            Karta getKarta2 = stopki[i].get(nomPosled2);

                            if ((getKarta2.tipkarta==12) && 
                            (getKarta.maw == getKarta2.maw) &&
                            (getKarta.tipkarta==0)) {
                                rez = i;
                            }
                            else if ((getKarta2.tipkarta>=0) &&
                            (getKarta2.tipkarta<11) && 
                            (getKarta.maw == getKarta2.maw)) {
                                if ((getKarta2.tipkarta+1==getKarta.tipkarta)) {
                                    rez = i;
                                }
                            }
                        }
                        if (rez>=0) {
                            getKarta.x = (110*(rez+1))+30;
                            getKarta.y = 15;
                            stopki[rez].add(getKarta);
                            stopki[nom].remove(nomPosled);
                            testEndGame();
                            break;
                        }
                    }
                }
            }
        }
        openKarta();
    }

    public void mouseReleased(int mouseX, int mouseY) {
        int nan = getNomKolodaPress(mouseX, mouseY);
        if (nan == 0) {
            vidacha();
        }
    }

    public int getNomKolodaPress(int mouseX, int mouseY) {
        int nan = 1;
        if ((mouseY >= 15) && (mouseY <= (15 + 97))) {
            if ((mouseY >= 30) && (mouseX <= (30 + 72)))
                nan = 0;
            if ((mouseY >= 140) && (mouseX <= (140 + 72)))
                nan = 1;
            if ((mouseY >= 360) && (mouseX <= (360 + 72)))
                nan = 2;
            if ((mouseY >= 470) && (mouseX <= (470 + 72)))
                nan = 3;
            if ((mouseY >= 580) && (mouseX <= (580 + 72)))
                nan = 4;
            if ((mouseY >= 690) && (mouseX <= (690 + 72)))
                nan = 5;
        } else if ((mouseY >= 130) & (mouseY <= (700))) {
            if ((mouseX >= 30) && (mouseX <= 110 * 7)) {
                if (((mouseX - 30) % 110) <= 72) {
                    nan = (mouseX - 30) / 110;
                    nan += 6;
                }
            }
        }
        return nan;
    }

    public void vidacha() {
        if (stopki[0].size() > 0) {
            int nan;
            if (pervVidacha == true) {
                nan = (int) (Math.random() * stopki[0].size());
            } else {
                nan = stopki[0].size() - 1;
            }
            Karta getKarta = stopki[0].get(nan);
            getKarta.tipRubahka = false;
            getKarta.x += 110;
            stopki[1].add(getKarta);
            stopki[0].remove(nan);
        } else {
            int nanPosled = stopki[1].size() - 1;
            for (int i = nanPosled; i >= 0; i--) {
                Karta getKarta = stopki[1].get(i);
                getKarta.tipRubahka = true;
                getKarta.x -= 110;
                stopki[0].add(getKarta);
            }
            stopki[1].clear();
            pervVidacha = false;
        }
    }

    public void start() {
        for (int i = 0; i < 13; i++) {
            stopki[i].clear();
        }
        load();
        razdacha();
        endG = false;
        pervVidacha = true;
        nomKarti = -1;
        nomStopki = -1;
    }

    public void drawKoloda(Graphics gr) {
        if (stopki[0].size() > 0) {
            stopki[0].get(stopki[0].size() - 1).draw(gr);
        }
        for (int i = 2; i <= 5; i++) {
            if (stopki[i].size() > 1) {
                stopki[i].get(stopki[i].size() - 2).draw(gr);
                stopki[i].get(stopki[i].size() - 1).draw(gr);
            } else if (stopki[i].size() == 1) {
                stopki[i].get(stopki[i].size() - 1).draw(gr);
            }
        }
        for (int i = 6; i < 13; i++) {
            if (stopki[i].size() > 0) {
                for (int j = 0; j < stopki[i].size(); j++) {
                    if (stopki[i].get(j).vibrana == true)
                        break;
                    stopki[i].get(j).draw(gr);
                }
            }
        }
        if (stopki[1].size() > 1) {
            stopki[1].get(stopki[1].size() - 2).draw(gr);
            stopki[1].get(stopki[1].size() - 1).draw(gr);
        } else if (stopki[1].size() == 1) {
            stopki[1].get(stopki[1].size() - 1).draw(gr);
        }
        if (nomStopki!=-1) {
            for (int i = nomKarti; i < stopki[nomStopki].size(); i++) {
                stopki[nomStopki].get(i).draw(gr);
            }
        }
    }

    private void load() {
        for (int i = 1; i <= 52; i++) {
            stopki[0].add(new Karta("dist/k" + i + ".png", rubahka, i));
        }
    }

    private void razdacha() {
        int x = 30;
        for (int i = 6; i < 13; i++) {
            for (int j = 6; j <= i; j++) {
                int rnd = (int) (Math.random() * stopki[0].size());
                Karta getKarta = stopki[0].get(rnd);
                if (j < i)
                    getKarta.tipRubahka = true;
                else
                    getKarta.tipRubahka = false;
                getKarta.x = x;
                getKarta.y = 130 + stopki[i].size() * 20;
                stopki[i].add(getKarta);
                stopki[0].remove(rnd);
            }
            x += 110;
        }
    }
}
