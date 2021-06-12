import javax.swing.JPanel;
import javax.imageio.*;
import javax.swing.*;



import java.awt.*;
import java.awt.event.*;
import java.io.*; 


public class Panel extends JPanel {
    private Timer tmDraw;
    private JButton button1, button2;
    private Image superfon;
    private Logic myGame;

public class myMouse1 implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (myGame.endG==false) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            if ((e.getButton()==1) && (e.getClickCount()==1)) {
                myGame.mousePressed(mouseX, mouseY);
            }
            else if ((e.getButton()==1) && (e.getClickCount()==2)) {
                myGame.mouseDoublePressed(mouseX,mouseY);
            }
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (myGame.endG==false) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            if (e.getButton()==1) {
                myGame.mouseReleased(mouseX, mouseY);
            }
        }
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}

public class myMouse2 implements MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent e) {
        if (myGame.endG==false) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            myGame.mouseDragged(mouseX, mouseY);
        }
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}

public Panel() {
    addMouseListener(new myMouse1());
    addMouseMotionListener(new myMouse2());
    myGame = new Logic();
    try {
        superfon = ImageIO.read(new File("C:/Users/User/Desktop/Kosinka/temp/dist/table-background.png"));
    } catch (Exception ex) {System.out.println("Not Upload");}
    setLayout(null);
    button1 = new JButton();
    button1.setText("New Game");
    button1.setForeground(Color.GREEN);
    button1.setFont(new Font("serif", 0, 20));
    button1.setBounds(820,50,150,50);
    button1.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            myGame.start();
            
        }
        
    });
    add(button1);

    button2 = new JButton();
    button2.setText("Выход");
    button2.setForeground(Color.RED);
    button2.setFont(new Font("serif", 0, 20));
    button2.setBounds(820,110,150,50);
    button2.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
            
        }
        
    });
    add(button2);

    tmDraw = new Timer(20,new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    });
    tmDraw.start();
}

public void paintComponent(Graphics gr) {
    super.paintComponent(gr);
    gr.drawImage(superfon,0,0,1000,700,null);
    gr.setColor(Color.WHITE);
    for (int i = 0; i < 7; i++) {
        if (i!=2) gr.drawRect(30+i*110, 130, 72, 97);
    }
    for (int i = 0; i < 7; i++) {
        gr.drawRect(30+i*110,130,72, 97);
    }
    myGame.drawKoloda(gr);
}
}