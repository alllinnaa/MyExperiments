import java.util.HashMap;
import java.util.Map;

public class ClientDiscount {
    private Map<String, Integer> clientOrderCount = new HashMap<>();

    public void addOrder(String clientId) {
        clientOrderCount.put(clientId, clientOrderCount.getOrDefault(clientId, 0) + 1);
    }

    public double applyDiscount(String clientId, double price) {
        int orderCount = clientOrderCount.getOrDefault(clientId, 0);
        double discount = 0.0;

        if (orderCount >= 15) {
            discount = 0.50;
        } else if (orderCount >= 10) {
            discount = 0.30;
        } else if (orderCount >= 5) {
            discount = 0.15;
        }

        return price * (1 - discount);
    }
}

