import java.util.ArrayList;
import java.util.List;

public class Port {
    private final int capacity;
    private int containers;
    private final List<Berth> berths;

    private static final int MAX_WAIT_TIME = 10000;

    public Port(int capacity, int numberOfBerths, int initialContainers) {
        if (capacity <= 0 || numberOfBerths <= 0 || initialContainers < 0) {
            System.out.println("Capacity, number of berths, and initial containers must be non-negative values.");
        }
        this.capacity = capacity;
        this.containers = initialContainers;
        this.berths = new ArrayList<>(numberOfBerths);
        for (int i = 0; i < numberOfBerths; i++) {
            berths.add(new Berth());
        }
    }
    public synchronized Berth getFreeBerth() {
        for (Berth berth : berths) {
            if (!berth.isOccupied()) {
                return berth;
            }
        }
        return null;
    }

    public synchronized void loadContainersToPortFromShip(int amount, Ship ship) {
        while (containers + amount > capacity) {
            try {
                if(System.currentTimeMillis()-ship.getStartTime()> ship.getTotalTimeInPortMillis()) return;
                System.out.printf("%s has WAIT loadContainersToPortFromShip. Ship:%d. Port:%d. Want:%d \n",ship.getShipName(), ship.getContainers(),containers, amount);
                wait(MAX_WAIT_TIME);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Error waiting for a ship " + ship.getShipName() + " to load containers at the port");
            }
        }
        System.out.println(ship.getShipName()+"doing loadContainersToPortFromShip");
        containers += amount;
        ship.setContainers(ship.getContainers() - amount);
        System.out.println(amount + " containers loaded from " + ship.getShipName() + ". Total containers in port: " + containers + ". Number containers in ship:" + ship.getContainers());
        notifyAll();
    }

    public synchronized void unloadContainersFromPortToShip(int amount, Ship ship) {
        while (containers - amount < 0) {
            try {
                if(System.currentTimeMillis()-ship.getStartTime()> ship.getTotalTimeInPortMillis()) return;
                System.out.printf("%s has WAIT unloadContainersFromPortToShip. Ship:%d. Port:%d. Want:%d \n",ship.getShipName(), ship.getContainers(),containers, amount);
                wait(MAX_WAIT_TIME);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Error waiting for a ship " + ship.getShipName() + " to load containers on it");
            }
        }
        System.out.println(ship.getShipName()+"doing unloadContainersFromPortToShip");
        containers -= amount;
        ship.setContainers(ship.getContainers() + amount);
        System.out.println(amount + " containers unloaded to " + ship.getShipName() + ". Total containers in port: " + containers + ". Number containers in ship:" + ship.getContainers());
        notifyAll();
    }

    public int getContainers() {
        return containers;
    }
    public int getNumberOfBerths() {
        return berths.size();
    }
}