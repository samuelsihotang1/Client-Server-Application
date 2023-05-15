import java.io.*;
import java.net.*;

public class cl {
  public static void main(String[] args) throws IOException {
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    if (args.length != 0 && args[0].equals("sender")) {
      System.out.print("Masukkan nomor port: ");
      int port = Integer.parseInt(stdin.readLine());
      System.out.print("ID Anda: ");
      String userID = stdin.readLine();
      if (userID.equals("---")) {
        System.exit(0);
      }
      Socket socket = new Socket("localhost", port);
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

      out.println(userID);

      while (true) {
        System.out.print("> ");
        String message = stdin.readLine();
        if (message.equals("---")) {
          break;
        }
        out.println(message);
      }
      socket.close();
    } else if (args.length != 0 && args[0].equals("receiver")) {
      System.out.print("Masukkan nomor port: ");
      int port = Integer.parseInt(stdin.readLine());
      Socket socket = new Socket("localhost", port);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      while (true) {
        String response;
        while ((response = in.readLine()) != null) {
          System.out.println(response);
          break;
        }
      }
    }
  }
}
