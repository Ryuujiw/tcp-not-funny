import java.io.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.*;

public class Client implements ActionListener {

  private static Chat chat = null;
  private static Socket socket = null;
  private static PrintWriter out = null;
  private static BufferedReader in = null;

  public void actionPerformed(ActionEvent e){
    //get text from ui
    //write to text area in ui
    //write to stream for server to pickup
    String clientMessage = chat.getClientMessage();
    if (clientMessage != null) {
      chat.writeToChat(clientMessage);
      out.println(clientMessage);
    }
  }

  public Client(){
    JButton sendButton = chat.getSendButton();
    sendButton.addActionListener(this);
  }

  public static void main(String[] args) throws IOException {
        
    try {
      socket = new Socket("localhost", 8888);
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      chat = new Chat();
      new Client();

    } catch (UnknownHostException e) {
      System.err.println("Don't know about host: localhost.");
      System.exit(1);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to: localhost.");
      System.exit(1);
    }

    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

    String fromServer = in.readLine();

    if(fromServer.equals("Sayonara") == false){
      chat.writeToChat(fromServer);

    } else {
      out.close();
      in.close();
      stdIn.close();
      socket.close();
    }
  }
}