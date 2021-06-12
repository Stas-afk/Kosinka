import javax.imageio.*;
import java.awt.*;
import java.io.*; 

class Karta {
    public int x,y;
    public Image img;
    public boolean tipRubahka;
    public Image rubahka;
    public int maw;
    public int tipkarta;
    public boolean vibrana;
    public boolean red_karta; 

public Karta (String path, Image rubahka, int nan) {
    vibrana = false;
    this.rubahka = rubahka;
    try {
        img = ImageIO.read(new File(path));
    } catch(IOException ex) {}
    x = 30;
    y = 15;
    tipRubahka = true;
    maw = (nan-1)/4;
    red_karta = true;
    if (maw<=1) red_karta = false;
}

public void draw(Graphics gr) {
    if (tipRubahka==false) {
        gr.drawImage(img, x, y, 72, 97, null);
    } else {
        gr.drawImage(rubahka, x, y, 72, 97, null);
    }
    if (vibrana==true) {
        gr.setColor(Color.BLUE);
        gr.drawRect(x, y, 72, 97);
    }
}
}