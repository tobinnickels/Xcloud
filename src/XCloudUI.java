/**
 * XcloudUI.java by Tobin Nickels
 * 
 * Creates a JPanel with the current map, and get's user inputs.
 */
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

class XCloudUI extends JFrame implements KeyListener, MouseListener {
    public XCloudUI(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800,720));
        setMaximumSize(new Dimension(800,720));
        setResizable(false);
        setVisible(true);

        add(MazeManager.getCurrentMaze());

        addKeyListener(this);
        addMouseListener(this);
        revalidate();
        pack();
    }



    @Override
    public void keyPressed(KeyEvent e) {
        //Left
        if(e.getKeyCode() == 37){
            GameState.getInstance().youMove(Direction.LEFT);
        } //Up
        else if(e.getKeyCode() == 38){
            GameState.getInstance().youMove(Direction.UP);
        } //Right
        else if(e.getKeyCode() == 39){
            GameState.getInstance().youMove(Direction.RIGHT);
        } //Down
        else if(e.getKeyCode() == 40){
            GameState.getInstance().youMove(Direction.DOWN);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        int mouse_x = e.getX();
        int mouse_y = e.getY();
        GameState.getInstance().youShoot(mouse_x,mouse_y);
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}




