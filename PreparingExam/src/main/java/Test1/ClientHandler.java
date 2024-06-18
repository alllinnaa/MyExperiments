package Test1;

import Test1.AppForRestaurant;
import Test1.ClientDiscount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private AppForRestaurant app;
    private static ClientDiscount clientDiscount = new ClientDiscount();

    public ClientHandler(Socket socket, AppForRestaurant app) {
        this.socket = socket;
        this.app = app;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String clientId = in.readLine();
            String coffee = in.readLine();
            String dessert = in.readLine();
            String payment = in.readLine();
            String time = in.readLine();

            double price = calculatePrice(coffee, dessert, clientId);
            Order order = new Order(coffee, dessert, payment, time, price);
            app.addOrder(order);

            clientDiscount.addOrder(clientId);
            out.println("Test1.Order received. Your price: " + price);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double calculatePrice(String coffee, String dessert, String clientId) {
        double coffeePrice = switch (coffee) {
            case "Espresso" -> 5.0;
            case "Americano" -> 4.0;
            case "Cappuccino" -> 6.0;
            case "Latte" -> 6.5;
            default -> 0.0;
        };

        double dessertPrice = switch (dessert) {
            case "Tiramisu" -> 7.0;
            case "Cheesecake" -> 6.0;
            case "Eclair" -> 4.0;
            case "Muffin" -> 3.5;
            default -> 0.0;
        };

        double total = coffeePrice + dessertPrice;
        return clientDiscount.applyDiscount(clientId, total);
    }
}
