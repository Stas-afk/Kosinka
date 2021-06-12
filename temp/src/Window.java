
import java.awt.Container;

import javax.swing.JFrame;

class Window extends JFrame {
    public Window(){
        Container cont = getContentPane();
        cont.add(new Panel());
        setBounds(0, 0, 1000, 700);
        
        setTitle("Пасьянс-Косынка");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        setVisible(true);
    }
    
}