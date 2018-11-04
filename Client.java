import java.io.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.*;

public class Client implements ActionListener {

  private static Chat chat = null;
  private static Socket socket = null;
  static PrintWriter out = null;
  static BufferedReader in = null;

  public void actionPerformed(ActionEvent e){
    String clientMessage = chat.getClientMessage();
    if (clientMessage != null) {
      System.out.println("You: " + clientMessage);
      chat.writeToChat(clientMessage);
      out.println(clientMessage);
    }
  }

  public Client(){
    try {
      socket = new Socket("localhost", 8888);
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      chat = new Chat();

    } catch (UnknownHostException e) {
      System.err.println("Don't know about host: localhost.");
      System.exit(1);

    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to: localhost.");
      System.exit(1);
    }

    JButton sendButton = chat.getSendButton();
    sendButton.addActionListener(this);
  }

  public static void main(String[] args) throws IOException {
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    String fromServer;
    String fromUser;
    Client client = new Client();    

    while ((fromServer = in.readLine()) != null) {
      System.out.println("Server: " + fromServer);
      chat.writeToChat(fromServer);
      if (fromServer.equals("Sayonara")) //end message from server
          break;  
    }
    out.close();
    in.close();
    socket.close();
  }
}