/**
 * GameState.java by Tobin Nickels
 *
 * A singleton based class that holds most information about your XCLOUD session.
 * Other classes can use getInstance() to call methods without having to have a reference
 * to the GameState.
 */
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameState {
    private int you;
    private Map<Integer,Cloud> players = new HashMap<>();
    private Map<Integer,CloudType> select_clouds = new HashMap<>();
    private static int players_left;

    private Client client;
    private XCloudUI ui;
    private static GameState instance;
    private ExecutorService projectile_thread;

    /**
     * Singleton constructor and getInstance() method.
     */
    private GameState(){
        instance = this;
        client = null;
    }
    public static GameState getInstance(){
        if (instance == null)
            instance = new GameState();
        return instance;
    }

    /**
     *
     * @return
     *      Cloud belonging to you.
     */
    public Cloud getYou(){return players.get(you);}

    /**
     *
     * @return
     *      Collection of all Clouds in the game
     */
    public Collection<Cloud> getClouds() {
        return players.values();
    }

    /**
     *
     * @param c
     *      The client object that you will use to communicate to the server.
     */
    public void setClient(Client c){client = c;}

    /**
     * Save your player number to easily access the cloud you will control.
     *
     * @param i
     *      Player number.
     */
    public void setYou(Integer i){
        this.you = i;
    }
    /**
     * Move your cloud on you UI and write its new location to the server.
     *
     * @param direction
     *      Enum used in a switch statement to determine which direction your cloud will move.
     */
    public void youMove(Direction direction){
        var your_bounds = players.get(you).getBounds();
        int x = your_bounds.x;
        int y = your_bounds.y;
        switch (direction){
            case LEFT -> {
                your_bounds.setLocation(x-10,y);
                if(!Collision.hitWall(your_bounds)){
                    players.get(you).left();
                    ui.repaint();
                }
            }
            case UP -> {
                your_bounds.setLocation(x,y+10);
                if(!Collision.hitWall(your_bounds)){
                    players.get(you).up();
                    ui.repaint();
                }
            }
            case RIGHT -> {
                your_bounds.setLocation(x+10,y);
                if(!Collision.hitWall(your_bounds)){
                    players.get(you).right();
                    ui.repaint();
                }
            }
            case DOWN -> {
                your_bounds.setLocation(x,y+10);
                if(!Collision.hitWall(your_bounds)) {
                    players.get(you).down();
                    ui.repaint();
                }
            }
        }
        client.getOut().println("MOVE "+you+" "+players.get(you).getX()+" "+players.get(you).getY());
    }

    /**
     * Create a new projectile and write its spawn location to the server.
     *
     * @param mouse_x
     *      X cordinate from mouse click.
     *
     * @param mouse_y
     *      Y cordinate from mouse click.
     */
    public void youShoot(int mouse_x, int mouse_y){
        players.get(you).shoot(false,mouse_x, mouse_y);
        client.getOut().println("SHOOT "+you+" "+ mouse_x +" "+mouse_y);
    }

    /**
     * This method closes the game frame and write that you won to the dialogue frame.
     */
    public void youWin(){
        projectile_thread.shutdown();
        ui.dispose();
        DialoguePanel.write("YOU WON!");
    }

    /**
     * This method closes the game frame and write that you lost to the dialogue frame.
     */
    public void youLose(){
        projectile_thread.shutdown();
        ui.dispose();
        DialoguePanel.write("YOU LOST :(");
        client.getOut().println("DEAD "+you);
    }

    /**
     * After reading that another player has moved from the server set that cloud's X and Y based
     * on the information read.
     *
     * @param player
     *      A player number
     * @param x
     *      New x coordinate
     * @param y
     *      New y coordinate
     */
    public void enemyMove(int player, int x, int y){
        players.get(player).setLocation(x,y);
    }
    public void enemyShoot(int player, int mouse_x, int mouse_y){
        if(player != you){
            players.get(player).shoot(true,mouse_x,mouse_y);
        }
    }

    /**
     * Decrement players left, remove a cloud from the JPanel, and check if you have won the game.
     *
     * @param player
     *      Player number
     */
    public void playerDied(Integer player){
        players_left--;
        if (players_left ==1){
            GameState.getInstance().youWin();
        }
        MazeManager.getCurrentMaze().remove(players.get(player));
        ui.revalidate();
        ui.pack();
        ui.repaint();
    }

    /**
     * Add a new player to the game state. Set their selected cloud as the default rain cloud.
     *
     * @param i
     *      Player number.
     */
    public void addPlayer(Integer i){
        select_clouds.put(i,CloudType.RainCloud);
        DialoguePanel.write("PLAYER "+i.toString()+" JOINED");
    }

    /**
     * Create a new maze based on selected maze and all the player's selected clouds. Create
     * new instance of the game frame. Write to the server that you have started the game.
     */
    public void outStart(){
        newClouds();
        if (MazeManager.getCurrentMaze() == null){
            MazeManager.setCurrentMaze("MAZE1");
            client.getOut().println("MAP "+MazeManager.getCurrentMaze().getName());
        }
        client.getOut().println("START");
        MazeManager.restart();
        players_left = players.size();
        projectile_thread = Executors.newSingleThreadExecutor();
        projectile_thread.execute(new ProjectileTracker());
        ui = new XCloudUI();
    }
    /**
     * Create a new maze based on selected maze and all the player's selected clouds. Create
     * new instance of the game frame after your client has read that someone started the game.
     */
    public void inStart(){
        newClouds();
        MazeManager.restart();
        players_left = players.size();
        projectile_thread = Executors.newSingleThreadExecutor();
        projectile_thread.execute(new ProjectileTracker());
        ui = new XCloudUI();
    }

    /**
     * Stop the current game, and write to the server that you have stopped the game.
     */
    public void outStop(){
        client.getOut().println("STOP");
        ui.dispose();
    }

    /**
     * Stop the game.
     */
    public void inStop(){
        ui.dispose();
    }

    /**
     * Change which cloud you have selected. Write to the server that you have changed your cloud.
     *
     * @param s
     *      Name of a cloud.
     */
    public void selectCloud(String s){
        switch (s){
            case "RAIN" ->{
                select_clouds.put(you,CloudType.RainCloud);
            }
            case "SNOW" ->{
                select_clouds.put(you,CloudType.SnowCloud);
            }
            case "THUNDER" -> {
                select_clouds.put(you,CloudType.ThunderCloud);
            }
        }
        client.getOut().println("PICK "+you+" "+s);
    }

    /**
     * Change which cloud given player has selected after reading it from the server.
     *
     * @param i
     *      Player Number
     *
     * @param s
     *      Name of a cloud
     */

    public void selectCloud(Integer i,String s){
        switch (s){
            case "RAIN" ->{
                select_clouds.put(i,CloudType.RainCloud);
            }
            case "SNOW" ->{
                select_clouds.put(i,CloudType.SnowCloud);
            }
            case "THUNDER" -> {
                select_clouds.put(i,CloudType.ThunderCloud);
            }
        }
    }

    /**
     * Create a new Cloud for each player based on which cloud they have selected.
     */
    public void newClouds(){
        for (Integer i: select_clouds.keySet()) {
            switch (select_clouds.get(i)){
                case RainCloud -> {
                    players.put(i,new RainCloud(i,400,400));
                }
                case SnowCloud -> {
                    players.put(i,new SnowCloud(i,400,400));
                }
                case ThunderCloud -> {
                    players.put(i,new ThunderCloud(i,400,400));
                }
            }
        }
    }

}

