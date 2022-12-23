/**
 * I forgot if this class currently works.
 */
import javax.swing.*;
import java.awt.*;

public class EndScreen extends JPanel {

    public EndScreen(String text){
        setLayout(null);
        setBackground(Color.PINK);
        JLabel l = new JLabel(text);
        l.setBounds(400,400,100,100);
        add(l);
    }
}
