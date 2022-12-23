/**
 * Server.java by Tobin Nickels
 * 
 * Create a new ServerSocket that listeners for other players.
 * Enumerates each player with a number, so the local gamestates know which
 * cloud to update when reading output.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Server implements Runnable{
    protected static List<Player> players = new ArrayList<>();
    @Override
    public void run() {
        try (var listener = new ServerSocket(58901))
        {
            var pool = Executors.newFixedThreadPool(200);
            while (true)
            {
                pool.execute(new Play(listener.accept()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class Player
{
    Scanner input;
    PrintWriter output;
    protected final int number;

    public Player(int number){this.number = number;}

    public void setInput(Scanner input) { this.input = input;}
    public void setOutput(PrintWriter output) {this.output = output;}
    public Scanner getInput() {return input;}
    public PrintWriter getOutput() {return output;}
}

/**
 * A Player is identified by a character mark which is either 'X' or 'O'. For
 * communication with the client the player has a socket and associated Scanner
 * and PrintWriter.
 */
class Play implements Runnable
{
    Player you;
    Socket socket;

    public Play(Socket socket)
    {
        this.socket = socket;
        this.you = new Player(Server.players.size());
    }

    @Override
    public void run()
    {
        try
        {
            you.setInput(new Scanner(socket.getInputStream()));
            you.setOutput(new PrintWriter(socket.getOutputStream(), true));
            you.getOutput().println("YOU'RE "+you.number);
            Server.players.add(you);
            for(int i = 0; i < Server.players.size(); i++){
                you.getOutput().println("ADD "+Server.players.get(i).number);
            }
            for(int i = 0; i < Server.players.size(); i++){
                if(!isYou(Server.players.get(i))){
                    Server.players.get(i).getOutput().println("ADD "+you.number);
                }
            }
            processCommands();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void processCommands()
    {
        while (you.getInput().hasNextLine())
        {
            for(int i = 0; i < Server.players.size(); i++){
                if(!isYou(Server.players.get(i))){
                    Server.players.get(i).getOutput().println(you.getInput().nextLine());
                }
            }
        }
    }

    public boolean isYou(Player p){
        return you.number == p.number;
    }
}
