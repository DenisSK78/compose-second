package serv;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) {
        createServer();
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = String.format(LINK_BODY, SECOND_LINK, MAIN_LINK);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private static void createServer(){
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(9002), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert server != null;
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
    }

    private static final String FIRST_LINK = "<p><a href='#' onclick='goTo(1)'>Open first link 9001</a></p>";
    private static final String SECOND_LINK = "<p><a href='#' onclick='goTo(2)'>Open second link 9002</a></p>";
    private static final String MAIN_LINK = "<p><a href='#' onclick='goTo(0)'>Open main link 9000</a></p>";

    private static final String LINK_BODY = "<!DOCTYPE HTML >\n" +
            "<html lang='html'>\n" +
            "<head>\n" +
            "    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>\n" +
            "    <title>Links</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h2>First page links: </h2>\n" +
            "%s\n" +
            "%s\n" +
            "</body>\n" +
            "<script>\n" +
            "    function goTo(page){\n" +
            "        switch (page) {\n" +
            "            case 1 :\n" +
            "                window.location.replace('http://localhost:9001/');\n" +
            "                break;\n" +
            "            case 2 :\n" +
            "                window.location.replace('http://localhost:9002/');\n" +
            "                break;\n" +
            "            case 0 :\n" +
            "                window.location.replace('http://localhost:9000/');\n" +
            "                break;\n" +
            "            default: console.log(\"Exception !!!\")\n" +
            "        }\n" +
            "    }\n" +
            "</script>\n" +
            "</html>";
}
