public class Ship extends Thread {
    private final String name;
    private final int capacity;
    private int containers;
    private final Port port;
    private Berth berth;
    private int totalTimeInPort;
    private final long totalTimeInPortMillis;
    private long startTime;

    public Ship(String name, int capacity, Port port, int totalTimeInPort, int initialContainers) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (port == null) {
            throw new IllegalArgumentException("Port cannot be null.");
        }
        if (capacity <= 0 || totalTimeInPort <= 0 || initialContainers < 0) {
            throw new IllegalArgumentException("Capacity, total time in port, and initial containers must be positive values.");
        }

        this.name = name;
        this.capacity = capacity;
        this.port = port;
        this.containers = initialContainers;
        this.totalTimeInPort = totalTimeInPort;
        totalTimeInPortMillis =(long)totalTimeInPort* 1000;
    }

    public String getShipName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public Berth getBerth() {
        return berth;
    }
    public int getContainers() {
        return containers;
    }
    public void setContainers(int containers) {
        this.containers = containers;
    }
    public int getTotalTimeInPort() {
        return totalTimeInPort;
    }
    public long getTotalTimeInPortMillis() {
        return totalTimeInPortMillis;
    }
    public long getStartTime() {
        return startTime;
    }

    @Override
    public void run() {
        berth = port.getFreeBerth();
        berth.dockShip(this);
        startTime = System.currentTimeMillis();
        while (totalTimeInPort > 0) {
            int action = (int) (Math.random() * 2);
            int amount = (int) (Math.random() * 5) + 1;
            synchronized (port) {
                if (action == 0) {
                    if (containers + amount <= capacity) {
                        port.unloadContainersFromPortToShip(amount, this);
                    } else {
                        System.out.println(name + " cannot load " + amount + " containers. Ship's capacity is exceeded.");
                    }
                } else {
                    if (containers >= amount) {
                        port.loadContainersToPortFromShip(amount, this);
                    } else {
                        System.out.println(name + " cannot unload " + amount + " containers. Ship does not have enough containers.");
                    }
                }
            }
            try {
                Thread.sleep(2000);
                totalTimeInPort -= 2;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Ship waiting error when something do in berth");
            }
            if (System.currentTimeMillis() - startTime > totalTimeInPortMillis) {
                System.out.println(name + " has exceeded its total time in port and is leaving.");
                break;
            }
        }
        if (berth != null) {
            berth.undockShip();
        }
        System.out.println(name + " has left the port.");
    }
}