/**
 * MazeManager.java by Tobin Nickels
 *
 * MazeManager is used to create new mazes, add current players to the maze, and retrieve information
 * about the current maze. A maze is a JPanel with a predetermined assortment of walls.
 */
import javax.swing.*;
import java.awt.*;

public class MazeManager {
    private static Maze current_maze;

    public static void restart(){
        setCurrentMaze(getCurrentMaze().name);
    }
    public static boolean setCurrentMaze(String name) {
        if(name.equals("MAZE1")){
            current_maze =new Maze1();
            return true;
        }
        if (name.equals("MAZE2")) {
            current_maze = new Maze2();
            return true;
        }
        return false;
    }

    public static Maze getCurrentMaze(){
        return current_maze;
    }
}

abstract class Maze extends JPanel {
    protected Wall[] walls;
    protected String name;

    public Wall[] getWalls(){return walls;}
    public String getName(){return name;}


}

class Maze2 extends Maze {
    public Maze2(){
        name = "MAZE2";
        setLayout(null);
        setBackground(Color.BLUE);

        walls = new Wall[107];

        //Top border
        for(int i = 0; i<19;i++){
            walls[i] = new Wall(i*40,0);
        }

        //Right border
        for(int i = 0; i<18;i++){
            walls[19+i] = new Wall(760,i*40);
        }
        //Bottom border
        for(int i = 0; i<19;i++){
            walls[36+i] = new Wall(i*40,640);
        }
        //Left border
        for(int i = 1; i<18;i++){
            walls[54+i] = new Wall(0,i*40);
        }

        //Top left
        walls[72] = new Wall(40,160);
        walls[73] = new Wall(80,160);

        walls[74] = new Wall(200,40);
        walls[75] = new Wall(200,80);
        walls[76] = new Wall(200,120);
        walls[77] = new Wall(200,160);

        //Small wall
        walls[78] = new Wall(480,40);
        walls[79] = new Wall(480,80);
        walls[80] = new Wall(480,120);
        walls[81] = new Wall(480,160);

        //Top right
        walls[82] = new Wall(640,40);
        walls[83] = new Wall(640,80);

        walls[84] = new Wall(640,240);
        walls[85] = new Wall(640,280);
        walls[86] = new Wall(640,320);
        walls[87] = new Wall(680,240);
        walls[88] = new Wall(720,240);

        //Mid
        walls[89] = new Wall(480,520);
        walls[90] = new Wall(440,520);
        walls[91] = new Wall(400,520);
        walls[92] = new Wall(440,560);
        walls[93] = new Wall(440,600);
        walls[94] = new Wall(440,640);

        //Big wall
        walls[95] = new Wall(280,400);
        walls[96] = new Wall(280,440);
        walls[97] = new Wall(280,480);
        walls[98] = new Wall(280,520);
        walls[99] = new Wall(280,560);
        walls[100] = new Wall(280,600);
        walls[101] = new Wall(280,640);

        //Bottom left
        walls[102] = new Wall(40,440);
        walls[103] = new Wall(80,440);
        walls[104] = new Wall(120,440);
        walls[105] = new Wall(120,480);
        walls[106] = new Wall(120,640);

        for (Wall w: walls) {
            add(w);
        }
        for (Cloud c: GameState.getInstance().getClouds()) {
            add(c);
        }
    }
}

class Maze1 extends Maze {
    public Maze1(){
        name = "MAZE1";
        setLayout(null);
        setBackground(Color.BLUE);

        walls = new Wall[98];

        //Top border
        for(int i = 0; i<19;i++){
            walls[i] = new Wall(i*40,0);
        }

        //Right border
        for(int i = 0; i<18;i++){
            walls[19+i] = new Wall(760,i*40);
        }
        //Bottom border
        for(int i = 0; i<19;i++){
            walls[36+i] = new Wall(i*40,640);
        }
        //Left border
        for(int i = 1; i<18;i++){
            walls[54+i] = new Wall(0,i*40);
        }
        //Top wall
        for(int i = 1; i<4;i++){
            walls[69+i] = new Wall(360,40*i);
        }
        //Left wall
        for(int i = 1; i<4;i++){
            walls[72+i] = new Wall(40*i,320);
        }
        //Bottom wall
        for(int i = 1; i<4;i++){
            walls[75+i] = new Wall(360,480+(40*i));
        }
        //Right wall
        for(int i = 1; i<4;i++){
            walls[78+i] = new Wall(600+(i*40),320);
        }

        //Top left square
        walls[82] = new Wall(200,160);
        walls[83] = new Wall(240,160);
        walls[84] = new Wall(200,200);
        walls[85] = new Wall(240,200);

        //Top right square
        walls[86] = new Wall(520,160);
        walls[87] = new Wall(520,200);
        walls[88] = new Wall(560,160);
        walls[89] = new Wall(560,200);

        //Bottom left square
        walls[90] = new Wall(200,440);
        walls[91] = new Wall(240,440);
        walls[92] = new Wall(200,480);
        walls[93] = new Wall(240,480);

        //Top right square
        walls[94] = new Wall(520,440);
        walls[95] = new Wall(520,480);
        walls[96] = new Wall(560,440);
        walls[97] = new Wall(560,480);

        for (Wall w: walls) {
            add(w);
        }
        for (Cloud c: GameState.getInstance().getClouds()) {
            add(c);
        }
    }
}
