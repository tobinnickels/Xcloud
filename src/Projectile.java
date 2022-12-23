/**
 * Projectile by Tobin Nickels
 * 
 * Projectiles can update their location and remove themselves from a JPanel.
 * Subclasses of projectile have unique damage and graphics.
 */
import javax.swing.*;
import java.awt.*;

public abstract class Projectile extends JLabel {
    protected int rise;
    protected int run;
    protected final boolean enemy_projectile;
    protected int damage;

 public Projectile(boolean enemy_projectile, int x, int y, int rise, int run){
            this.enemy_projectile = enemy_projectile;
            this.rise = rise;
            this.run = run;
            setBounds(x+40,y+40,20,20);
        }


    /**
     * Change this projectiles location based on slope and current location.
     * Repaint ui after location is changed.
     */
    public void update() {
        int old_x = getX();
        int old_y = getY();

        setLocation(old_x+run, old_y+rise);
        try {
            getParent().getParent().repaint();
        }
        catch (NullPointerException e){}
    }

    /**
     * Remove this projectile from the container it is in.
     * Repaint the ui after this projectile has been removed.
     */
    public void remove(){
        Container game_panel = getParent();
        game_panel.remove(this);
        try {
            game_panel.getParent().repaint();
        }
        catch (NullPointerException e){

        }
    }
    public boolean isEnemy(){
        return enemy_projectile;
    }

    public int dealDamage(){
        return damage;
    }
}

class RainDrop extends Projectile{
    public RainDrop(boolean enemy_projectile, int x, int y, int rise, int run){
        super(enemy_projectile,x,y,rise,run);
        damage = 2;

        ImageIcon i = new ImageIcon("src/water.png");
        setIcon(i);
    }
}
class SnowFlake extends Projectile{
    public SnowFlake(boolean enemy_projectile, int x, int y, int rise, int run){
        super(enemy_projectile,x,y,rise,run);
        damage = 1;

        ImageIcon i = new ImageIcon("src/snowflake.png");
        setIcon(i);
    }
}
class LightningBolt extends Projectile{
    public LightningBolt(boolean enemy_projectile, int x, int y, int rise, int run){
        super(enemy_projectile,x,y,rise,run);
        damage = 4;

        ImageIcon i = new ImageIcon("src/lightning.png");
        setIcon(i);
    }
}