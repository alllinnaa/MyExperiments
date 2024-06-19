package Test2.Exam1;

class Customer {
      private final int uniqueNumber;
      private int countOrders;
      private int discount;

      public Customer(int uniqueNumber) {
            this.uniqueNumber = uniqueNumber;
            this.countOrders = 0;
            this.discount = 0;
      }

      public int getUniqueNumber() {
            return uniqueNumber;
      }

      public int getCountOrders() {
            return countOrders;
      }

      public void incrementCountOrders() {
            this.countOrders++;
      }

      public int getDiscount() {
            return discount;
      }

      public void setDiscount(int discount) {
            this.discount = discount;
      }
}
