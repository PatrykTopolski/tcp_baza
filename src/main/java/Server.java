import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static int PORT = 8089;
    public static void main(String[] args) throws IOException {
        log("Startig");
        log("Server socket opening");
        ServerSocket welcomeSocket = new ServerSocket(PORT);
        log("Server socket opened");
        log("Server socket listening");
        Socket clientSocket = welcomeSocket.accept();
        log("Client connected from: " + clientSocket.getInetAddress().toString() +
                ":" + clientSocket.getPort());
        log("Streams collecting");
        OutputStream os = clientSocket.getOutputStream();
        InputStream is = clientSocket.getInputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedWriter bw = new BufferedWriter(osw);
        BufferedReader br = new BufferedReader(isr);
        log("Streams collected");
        log("Reading data from socket");
        String inputLine = br.readLine();
        log("Data from socket: " + inputLine);
        // if flag is corre
        if (inputLine.equals("3243423423423")){
            String outputLine = String.valueOf(16);
            log("Writting data to socket: " + outputLine);
            bw.write(outputLine);
            bw.newLine();
            bw.flush();
            String outputCalculatored = br.readLine();
            log("New data from socket: " + outputCalculatored);
        }else {
            String outputLine = "kurwa student debil";
            log("Writting data to socket");
            bw.write(outputLine);
            bw.newLine();
            bw.flush();
        }
        log("Client socket closing");
        clientSocket.close();
        log("Client socket closed");
        log("Server socket closing");
        welcomeSocket.close();
        log("Server socket closed");
        log("Ending");
    }
    public static void log(String message) {
        System.out.println("[S]: " + message);
        System.out.flush();
    }
}