import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Server {

    public static void main(String[] args) throws Exception {
        // Create server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Create /add endpoint
        server.createContext("/add", new AddHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("✅ Web Service running at: http://localhost:8000/add?x=<num1>&y=<num2>");
        System.out.println("Waiting for client requests...");
    }

    static class AddHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery(); // Example: x=10&y=20
            if (query == null) {
                String msg = "Please provide query parameters, e.g., ?x=10&y=20";
                exchange.sendResponseHeaders(400, msg.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(msg.getBytes());
                }
                return;
            }

            String[] parts = query.split("&");
            int x = Integer.parseInt(parts[0].split("=")[1]);
            int y = Integer.parseInt(parts[1].split("=")[1]);
            int result = x + y;

            String response = "<html><body style='font-family:Arial;'>" +
                              "<h2>Simple Java Web Service</h2>" +
                              "<p><b>First Number (x):</b> " + x + "</p>" +
                              "<p><b>Second Number (y):</b> " + y + "</p>" +
                              "<h3>Sum = " + result + "</h3>" +
                              "</body></html>";

            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}