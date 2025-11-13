import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.awt.Desktop;

public class Client {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter first number: ");
            int a = sc.nextInt();

            System.out.print("Enter second number: ");
            int b = sc.nextInt();

            String urlStr = "http://localhost:8000/add?x=" + a + "&y=" + b;
            System.out.println("Sending request to: " + urlStr);

            // This works on Java 8, 11, 17, 20, etc.
            URL url = new URL(urlStr);

            // Open in browser
            Desktop.getDesktop().browse(url.toURI());

            // Connect to server
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            System.out.println("\nResponse from Web Service:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close();
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
