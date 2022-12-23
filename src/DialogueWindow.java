/**
 * DialogueWindow.java by Tobin Nickels
 *
 * This class dialogue frame used to get commands.
 * 
 * Has an action listener for the enter key and a JPanel.
 */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.concurrent.Executors;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DialogueWindow extends JFrame{
    private DialoguePanel dialogue_panel;
    public DialogueWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(250,250));
        setMaximumSize(new Dimension(250,250));
        setVisible(true);

        dialogue_panel = new DialoguePanel();
        add(dialogue_panel);

        revalidate();
        pack();
    }
}

/**
 * Has a text field so you can write commands, and uneditable text area to write output to.
 */
class DialoguePanel extends JPanel {
    protected JTextField text;
    protected static JTextArea log;
    DialoguePanel(){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setSize(250,250);
        text = new JTextField("",20);
        text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processCommand(text.getText());
                text.setText("");
            }
        });
        log = new JTextArea("Welcome to XCLOUD\nUSE \"/COMMANDS\" for list of commands",40,100);
        log.setEditable(false);
        add(log);
        add(text);
    }

    /**
     * When enter is pressed this method is called to interpret the command,
     * or do nothing if the commands isn't recognized.
     *
     * @param c
     *      What you wrote in the text field.
     */
    protected void processCommand(String c){

        if (c.equals("/HOST")){
            try {
                String IP = InetAddress.getLocalHost().getHostAddress();
                var executor = Executors.newFixedThreadPool(2);
                executor.execute(new Server());
                log.setText(log.getText()+"Your IP is "+IP+"\n");
                Client client = new Client(IP);
                GameState.getInstance().setClient(client);
                executor.execute(client);
            } catch (Exception e) {
                log.setText(log.getText()+"Unable to host.\n");
            }
        }
        else if(c.startsWith("/JOIN")){
            String[] split = c.split(" ");
            Client client = null;
            try {
                client = new Client(split[1]);
                GameState.getInstance().setClient(client);
                var executor = Executors.newSingleThreadExecutor();
                executor.execute(client);
            } catch (Exception e) {
                log.setText(log.getText()+"Unable to connect\n");
            }
        }
        else if(c.startsWith("/MAP")){
            String[] split = c.split(" ");
            MazeManager.setCurrentMaze(split[1]);
        }

        else if(c.equals("/START")){
            try{
                GameState.getInstance().outStart();
                log.setText("");
            } catch(NullPointerException e){
                log.setText("You must host or join game before starting.");
            }
            
        }
        else if(c.equals("/STOP")){
            GameState.getInstance().outStop();
        }
        else if (c.startsWith("/PICK")) {
            String[] split = c.split(" ");
            GameState.getInstance().selectCloud(split[1]);
        } else if (c.startsWith("/COMMANDS")) {
            write("/HOST : start server");
            write("/JOIN <ip> : join a game");
            write("/MAP <MAZE1,MAZE2> : pick a map");
            write("/PICK <RAIN,SNOW,THUNDER> : pick a cloud");
            write("/START : start a game of XCLOUD");
            write("/STOP : stop the game");
        }
    }

    /**
     * Method used so other Classes can write output to the text area.
     *
     * @param message
     *      What to write to the text area.
     */
    public static void write(String message){
        log.setText(log.getText()+message+"\n");
    }
}