package Test2.Exam1;

public class Order {
    private int customerNumber;
    private int orderNumber;
    private String coffee;
    private String dessert;
    private String payment;
    private String date;
    private String time;
    private double price;

    public Order(int customerNumber, int orderNumber, String coffee, String dessert, String payment, String date, String time, double price) {
        this.customerNumber = customerNumber;
        this.orderNumber = orderNumber;
        this.coffee = coffee;
        this.dessert = dessert;
        this.payment = payment;
        this.date = date;
        this.time = time;
        this.price = price;
    }

    // Getters
    public int getCustomerNumber() {
        return customerNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
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

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public double getPrice() {
        return price;
    }
}
