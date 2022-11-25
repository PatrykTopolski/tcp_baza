import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static int PORT = 8089;
    private static final String HANDSHAKE = "3243423423423";
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
        if (inputLine.equals(HANDSHAKE)){
            while(inputLine.equals(HANDSHAKE)) {
                String outputLine = HANDSHAKE + "," + (int) (Math.random() * 10);
                log("Writting data to socket: " + outputLine);
                bw.write(outputLine);
                bw.newLine();
                bw.flush();
                inputLine = br.readLine();
                log("received data : " + inputLine);
                if(inputLine == null || inputLine.isEmpty()) break;
                String[] split = inputLine.split(",");
                if (HANDSHAKE.equals(split[0])) {
                    if(split.length == 1){
                        log("Valid connection but no data received: ");
                    }else {
                        inputLine = HANDSHAKE;
                    }
                }
            }
        }
        String outputLine = "Koniec komunikacji";
        log("Writting data to socket");
        bw.write(outputLine);
        bw.newLine();
        bw.flush();
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