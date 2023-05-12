import java.io.*;
import java.net.*;

public class Client2 {

  public static void main(String args[]) throws Exception {
    ServerSocket checkServer = null;
    Socket clientSocket = null;
    Socket cl = null;
    BufferedReader is = null;
    DataOutputStream os = null;
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    String userInput = null;
    String output = null;
    String mode = null;
    int TESTPORT = 1;

    // Determine mode: server or client
    if (args.length > 0 && args[0].equals("server")) {
      mode = "server";
    }
    if (args.length > 0 && args[0].equals("client")) {
      mode = "client";
    }

    // Start server or connect to server
    if (mode.equals("server")) {
      try {
        System.out.print("ID Anda: ");
        String str = stdin.readLine();
        TESTPORT = str.codePoints().sum();
        checkServer = new ServerSocket(TESTPORT);
        System.out.println("Host aktif dengan ID: " + str);

        clientSocket = checkServer.accept();
        is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        os = new DataOutputStream(clientSocket.getOutputStream());
      } catch (IOException e) {
        System.out.println(e);
      }
    } else {
      cl = connectToServer(stdin);
      is = new BufferedReader(new InputStreamReader(cl.getInputStream()));
      os = new DataOutputStream(cl.getOutputStream());
    }

    // Chat loop
    try {
      while (true) {
        if (mode.equals("client")) {
          System.out.print("Anda: ");
          userInput = stdin.readLine();
          os.writeBytes(userInput + "\n");
          output = is.readLine();
          System.out.println("Teman: " + output);
          if (userInput.equalsIgnoreCase("exit") || output.equalsIgnoreCase("exit")) {
            break;
          }
        } else if (mode.equals("server")) {
          output = is.readLine();
          System.out.println("Teman: " + output);
          System.out.print("Anda: ");
          userInput = stdin.readLine();
          os.writeBytes(userInput + "\n");
          if (userInput.equalsIgnoreCase("exit") || output.equalsIgnoreCase("exit")) {
            break;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Close connection
    try {
      is.close();
      os.close();
      if (mode.equals("server")) {
        clientSocket.close();
        checkServer.close();
      } else {
        cl.close();
      }
    } catch (IOException x) {
      System.out.println("Gagal Menutup Koneksi");
    }
  }

  // Function to Connect to Server
  private static Socket connectToServer(BufferedReader stdin) throws IOException {
    Socket cl = null;
    boolean again = false;
    int REMOTE_PORT;

    do {
      try {
        if (again == true) {
          System.out.print("Coba Masukkan Kembali Host: ");
        } else {
          System.out.print("Masukkan ID Target: ");
        }
        
        String str = stdin.readLine();
        REMOTE_PORT = str.codePoints().sum();
        cl = new Socket("localhost", REMOTE_PORT);
        again = false;
      } catch (UnknownHostException el) {
        System.out.println("Gagal Mengakses Host");
        again = true;
      } catch (IOException e2) {
        System.out.println("Gagal Mengakses Host");
        again = true;
      }
    } while (again == true);

    System.out.println("Koneksi ke server berhasil!");
    return cl;
  }
}