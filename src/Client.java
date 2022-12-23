/**
 * Client.java by Tobin Nickels
 * 
 * Connects to the server socket.
 * Reads output from server, and writes output as strings.
 */

import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{
    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    public Client(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, 58901);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public Scanner getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }
    @Override
    public void run() {
        try {
            while (in.hasNextLine()) {
                String[] response = in.nextLine().split(" ");
                if(response[0].equals("YOU'RE")){
                    GameState.getInstance().setYou(Integer.parseInt(response[1]));
                }
                else if(response[0].equals("ADD")){
                    GameState.getInstance().addPlayer(Integer.parseInt(response[1]));
                } else if (response[0].equals("SHOOT")) {
                    GameState.getInstance().enemyShoot(Integer.parseInt(response[1]),
                            Integer.parseInt(response[2]),Integer.parseInt(response[3]));
                } else if (response[0].equals("MAP")) {
                    MazeManager.setCurrentMaze(response[1]);
                } else if (response[0].equals("MOVE")) {
                    GameState.getInstance().enemyMove(Integer.parseInt(response[1]),Integer.parseInt(response[2]),Integer.parseInt(response[3]));
                } else if (response[0].equals("DEAD")) {
                    GameState.getInstance().playerDied(Integer.parseInt(response[1]));
                } else if (response[0].equals("START")){
                    GameState.getInstance().inStart();
                } else if (response[0].equals("STOP")) {
                    GameState.getInstance().inStop();
                } else if (response[0].equals("PICK")) {
                    GameState.getInstance().selectCloud(Integer.parseInt(response[1]),response[2]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}