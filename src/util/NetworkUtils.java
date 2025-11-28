package util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class NetworkUtils {

    public static boolean isConnected() {
        try (Socket socket = new Socket()) {
            // Connect to Google's public DNS server
            socket.connect(new InetSocketAddress("1.1.1.1", 53), 1500);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
