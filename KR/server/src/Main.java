import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;
import java.text.ParseException;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            user user = new user();

            StringBuilder sb = new StringBuilder();
            InputStream ios = t.getRequestBody();
            int i;
            while ((i = ios.read()) != -1) {
                sb.append((char) i);
            }


            StringTokenizer st1 = new StringTokenizer(sb.toString(), ";");
            while (st1.hasMoreTokens())
            {
                String token = st1.nextToken();
                String[] element = token.split(":");
                switch (element[0]){
                    case "name":
                        user.setName(element[1]);
                        break;
                    case "surname":
                        user.setSurname(element[1]);
                        break;
                    case "email":
                        user.setEmail(element[1]);
                        break;
                }
            }
            System.out.println(user.getName());
            System.out.println(user.getSurname());
            System.out.println(user.getEmail());
            String response = "Hello, World123!";

            DB db = null;
            try {
                db = new DB();
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
                throw new RuntimeException(e);

            } catch (ClassNotFoundException e) {
                System.out.println(e.getLocalizedMessage());
                throw new RuntimeException(e);

            }
            try {
                db.createPersonal(
                        user.getName(),
                        user.getSurname(),
                        user.getEmail()
                );
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }


}