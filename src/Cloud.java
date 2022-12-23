import javax.swing.*;

public abstract class Cloud extends JLabel {
    protected int health;
    /**
     * Move cloud left.
     */
    public void left(){
        int x = getX();
        int y = getY();

        setLocation(x-10,y);
    }

    /**
     * Move cloud up.
     */
    public void up(){
        int x = getX();
        int y = getY();

        setLocation(x,y-10);
    }

    /**
     * Move cloud right.
     */
    public void right(){
        int x = getX();
        int y = getY();

        setLocation(x+10,y);
    }

    /**
     * Move cloud down.
     */
    public void down(){
        int x = getX();
        int y = getY();

        setLocation(x,y+10);
    }

    /**
     * Create a new projectile based on location of mouse on click and current location of this cloud.
     *
     * @param mouse_x
     *      X coordinate of mouse location on click in relation to ui.
     *
     * @param mouse_y
     *      Y coordinate of mouse location on click in relation to ui.
     */
    public abstract void shoot(boolean enemy_projectile,int mouse_x, int mouse_y);

    public void takeDamage(Projectile p){
        health -= p.dealDamage();
        if(health <= 0){
            GameState.getInstance().youLose();
        }
    }
}
class RainCloud extends Cloud {
    public RainCloud(Integer player_number, int x, int y){
        health = 2;
        ImageIcon i = new ImageIcon("src/RainCloud.png");
        setIcon(i);
        setBounds(x,y,80,80);
    }
    @Override
    public void shoot(boolean enemy_projectile,int mouse_x, int mouse_y){
        int curr_x = getX();
        int curr_y = getY();

        int rise;
        int run;

        // This weird block of code is just so rise and run are never 0
        if ((mouse_y - curr_y) > 0){
            rise = (int) -Math.floorDiv(-(mouse_y - curr_y),20);
        } else {
            rise = (int) Math.floorDiv((mouse_y - curr_y),20);
        }
        if ((mouse_x - curr_x) > 0){
            run = (int) -Math.floorDiv((-mouse_x - curr_x),20);
        } else {
            run = (int) Math.floorDiv((mouse_x - curr_x),20);
        }

        Projectile p = new RainDrop(enemy_projectile,curr_x,curr_y,rise,run);
        ProjectileTracker.new_projectiles.add(p);
        getParent().add(p);
    }
}
class SnowCloud extends Cloud{
    public SnowCloud(Integer player_number, int x, int y){
        health = 4;
        ImageIcon i = new ImageIcon("src/SnowCloud.png");
        setIcon(i);
        setBounds(x,y,80,80);
    }
    @Override
    public void shoot(boolean enemy_projectile,int mouse_x, int mouse_y){
        int curr_x = getX();
        int curr_y = getY();

        int rise;
        int run;

        // This weird block of code is just so rise and run are never 0
        if ((mouse_y - curr_y) > 0){
            rise = (int) -Math.floorDiv(-(mouse_y - curr_y),20);
        } else {
            rise = (int) Math.floorDiv((mouse_y - curr_y),20);
        }
        if ((mouse_x - curr_x) > 0){
            run = (int) -Math.floorDiv(-(mouse_x - curr_x),20);
        } else {
            run = (int) Math.floorDiv((mouse_x - curr_x),20);
        }

        Projectile p = new SnowFlake(enemy_projectile,curr_x,curr_y,rise,run);
        ProjectileTracker.new_projectiles.add(p);
        getParent().add(p);
    }
}
class ThunderCloud extends Cloud{
    public ThunderCloud(Integer player_number, int x, int y){
        health = 1;
        ImageIcon i = new ImageIcon("src/ThunderCloud.png");
        setIcon(i);
        setBounds(x,y,80,80);
    }
    @Override
    public void shoot(boolean enemy_projectile,int mouse_x, int mouse_y){
        int curr_x = getX();
        int curr_y = getY();

        int rise;
        int run;

        // This weird block of code is just so rise and run are never 0
        if ((mouse_y - curr_y) > 0){
            rise = (int) -Math.floorDiv(-(mouse_y - curr_y),20);
        } else {
            rise = (int) Math.floorDiv((mouse_y - curr_y),20);
        }
        if ((mouse_x - curr_x) > 0){
            run = (int) -Math.floorDiv(-(mouse_x - curr_x),20);
        } else {
            run = (int) Math.floorDiv((mouse_x - curr_x),20);
        }

        Projectile p = new LightningBolt(enemy_projectile,curr_x,curr_y,rise,run);
        ProjectileTracker.new_projectiles.add(p);
        getParent().add(p);
    }
}