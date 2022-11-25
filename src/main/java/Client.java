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

        // sending first line
        String handshake = "3243423423423";
        log("Writting data to socket");
        bw.write(handshake);
        bw.newLine();
        bw.flush();
        log("Reading data from socket");
        String inputLine = br.readLine();
        log("Data from socket: " + inputLine);

        // sending second line
        String outputNext = prepareOutputLog(inputLine.split(",")[1]);
        bw.write(handshake+","+outputNext);
        bw.newLine();
        bw.flush();
        log("Reading data from socket");

        String inputLine3 = br.readLine();
        log("Data from socket: " + inputLine3);
        outputNext = prepareOutputReplace(inputLine3.split(",")[1]);
        System.out.println(outputNext);
        bw.write(handshake+","+outputNext);
        bw.newLine();
        bw.flush();
        int[] ints = new int[6];
        for (int i = 0; i < 6; i++) {
            bw.write(handshake + ","+ i);
            bw.newLine();
            bw.flush();
            ints[i] = Integer.parseInt(br.readLine().split(",")[1]);
            System.out.println(Arrays.toString(ints));
        }
        bw.write(String.valueOf(
                        Arrays.stream(ints)
                                .reduce(Integer::sum)
                                .getAsInt())
                );
        bw.newLine();
        bw.flush();
        System.out.println(br.readLine());
        bw.write("");
        bw.newLine();
        bw.flush();
        log("received: " + br.readLine());
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
        StringBuilder builder = new StringBuilder();
        for (char character : characters) {
            builder.append(character == '3' ? '4' : character);
        }
       return builder.toString();
    }

    public static void log(String message) {
        System.out.println("[C]: " + message);
        System.out.flush();
    }
}