public class Berth {
    private Ship ship;

    public synchronized void dockShip(Ship ship) {
        this.ship = ship;
        System.out.println(ship.getShipName() + " docked at the berth.");
    }

    public synchronized void undockShip() {
        System.out.println(ship.getShipName() + " undocked from the berth.");
        this.ship = null;
    }

    public boolean isOccupied() {
        return ship != null;
    }

    public Ship getShip() {
        return ship;
    }
}