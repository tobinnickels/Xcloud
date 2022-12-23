import javax.swing.*;

public class Wall extends JLabel {

    public Wall(int x, int y){
        ImageIcon i = new ImageIcon("src/rainbow.png");
        setIcon(i);
        setBounds(x,y,40,40);
    }
}
