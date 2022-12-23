/**
 * Collision.java by Tobin Nickels
 *
 * This class has two static methods which both return a boolean depending on if a projectile
 * hit something.
 */
import java.awt.*;

public class Collision {

    /**
     * Return true if given bounds intersects with any wall.
     *
     * @param bounds
     *      Bounds of an object (probably a cloud).
     * @return
     *      Boolean representing if given bounds intersects with a wall.
     */
    public static boolean hitWall(Rectangle bounds){
        boolean hit = false;
        for (Wall w :MazeManager.getCurrentMaze().getWalls()) {
            hit |= bounds.intersects(w.getBounds());
        }
        return hit;
    }

    /**
     * @param p
     *      A projectile.
     *
     * @return
     *      Boolean representing if the projectile hit you.
     */
    public static boolean hitYou(Projectile p){
        if (p.isEnemy()){
            return p.getBounds().intersects(GameState.getInstance().getYou().getBounds());
        }
        return false;
    }
}
