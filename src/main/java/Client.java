import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class Client {
    public static int SERVER_PORT = 8089;
    //    public static String SERVER_IP = "127.0.0.1";
    public static String SERVER_IP = "localhost";
    public static void main(String[] args) throws IOException {
        log("Startig");
        log("Receiving ip address for server");
        InetAddress serverIp = InetAddress.getByName(SERVER_IP); // DNS
        log("IP address for server: " + serverIp.toString());
        log("Client socket opening - connecting to the server");
        Socket clientSocket = new Socket(serverIp, SERVER_PORT);
        log("Client connected to the server");
        log("Streams collecting");
        OutputStream os = clientSocket.getOutputStream();
        InputStream is = clientSocket.getInputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedWriter bw = new BufferedWriter(osw);
        BufferedReader br = new BufferedReader(isr);
        log("Streams collected");
        String outputLine = "3243423423423";
        log("Writting data to socket");
        bw.write(outputLine);
        bw.newLine();
        bw.flush();
        log("Reading data from socket");
        String inputLine = br.readLine();
        log("Data from socket: " + inputLine);
        String outputNext = prepareOutputLog(inputLine);
        bw.write(outputNext);
        bw.newLine();
        bw.flush();
        log("Client socket closing");
        clientSocket.close();
        log("Client socket closed");
        log("Ending");
    }

    private static String prepareOutputLog(String inputLine) {
        int input = Integer.parseInt(inputLine);
        System.out.println("input: " + input);
        int result =(int) Math.floor((Math.log(input) / Math.log(2)));
        System.out.println("double : " + result);
        return String.valueOf(result);
    }

    private static String prepareOutputReplace(String inputLine){
        char[] characters = inputLine.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            characters[i] = characters[i] == '3' ? '4' : characters[i];
        }
       return Arrays.toString(characters);
    }

    public static void log(String message) {
        System.out.println("[C]: " + message);
        System.out.flush();
    }
}