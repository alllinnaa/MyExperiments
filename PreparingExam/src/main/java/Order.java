public class Order {
    private String coffee;
    private String dessert;
    private String payment;
    private String time;
    private double price;

    public Order(String coffee, String dessert, String payment, String time, double price) {
        this.coffee = coffee;
        this.dessert = dessert;
        this.payment = payment;
        this.time = time;
        this.price = price;
    }

    public String getCoffee() {
        return coffee;
    }

    public String getDessert() {
        return dessert;
    }

    public String getPayment() {
        return payment;
    }

    public String getTime() {
        return time;
    }

    public double getPrice() {
        return price;
    }
}

