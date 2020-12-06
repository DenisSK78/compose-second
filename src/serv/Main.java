package serv;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Main {

    public static void main(String[] args) {
        createServer();
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = timeForNewYear();
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private static void createServer(){
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(9000), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert server != null;
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
    }

    private static String timeForNewYear() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newYear = LocalDateTime.of(2021, 1, 1, 0, 0);
        LocalDateTime tempDateTime = LocalDateTime.from(now);
        long days = tempDateTime.until(newYear, ChronoUnit.DAYS );
        tempDateTime = tempDateTime.plusDays( days );
        long hours = tempDateTime.until(newYear, ChronoUnit.HOURS );
        tempDateTime = tempDateTime.plusHours( hours );
        long minutes = tempDateTime.until(newYear, ChronoUnit.MINUTES );
        return String.format("Time for new 2021 year: %s days %s hours %s minutes!\n", days, hours, minutes);
    }
}
