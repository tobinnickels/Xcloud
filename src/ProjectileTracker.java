import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * ProjectileTracker.java by Tobin Nickels
 *
 * New projectiles are added to a queue while the old projectiles are being updated.
 * Once every old projectile has been updated the new projectiles are added to the list of all projectiles.
 * This list is iterated over and methods are called to detect if a projectile has hit a player or wall.
 * This class is meant to be run in a separate thread so this loop does not interrupt other processes.
 */
public class ProjectileTracker implements Runnable{
    static Queue<Projectile> new_projectiles;
    @Override
    public void run() {
        new_projectiles = new LinkedList<>();
        List <Projectile> projectiles = new ArrayList<>();
        while (true) {
            while (!new_projectiles.isEmpty()){
                projectiles.add(new_projectiles.poll());
            }
            int i = 0;
            while (i < projectiles.size()) {
                Projectile p = projectiles.get(i);
                p.update();
                if (Collision.hitYou(p)){
                    GameState.getInstance().getYou().takeDamage(p);
                    p.remove();
                    projectiles.remove(i);
                }
                else if(Collision.hitWall(p.getBounds())){
                    p.remove();
                    projectiles.remove(i);
                } else {
                    i++;
                }
            }
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}