package ridesharingsystem;

/**
 * Stores drivers in a linked list kept in sorted order by driver ID.
 * Driver ID and vehicle plate are unique across the list.
 */
public class DriverList implements IDriverList {

    private LinkedList<IDriver> drivers;

    public DriverList() {
        drivers = new LinkedList<IDriver>();
    }

    /**
     * Inserts a driver in sorted order by ID.
     * Fails (returns false) if the driver is null, or if a driver with the
     * same ID or the same vehicle plate already exists.
     */
    @Override
    public boolean add(IDriver driver) {
        if (driver == null) {
            return false;
        }

        if (findByVehiclePlate(driver.getVehiclePlate()) != null) {
            return false;
        }

        if (drivers.isEmpty()) {
            drivers.insert(driver);
            return true;
        }

        drivers.findFirst();

        while (true) {
            IDriver currentDriver = drivers.retrieve();

            if (driver.getId() == currentDriver.getId()) {
                return false;
            }

            if (driver.getId() < currentDriver.getId()) {
                drivers.insert_before_current(driver);
                return true;
            }

            if (drivers.last()) {
                drivers.insert(driver);
                return true;
            }

            drivers.findNext();
        }
    }

    /** Returns the driver with the given ID, or null if none exists. */
    @Override
    public IDriver findById(int driverId) {
        if (drivers.isEmpty()) {
            return null;
        }

        drivers.findFirst();

        while (true) {
            IDriver currentDriver = drivers.retrieve();

            if (currentDriver.getId() == driverId) {
                return currentDriver;
            }

            if (drivers.last()) {
                break;
            }

            drivers.findNext();
        }

        return null;
    }

    /** Returns every driver whose full name exactly matches the given name. */
    @Override
    public LinkedList<IDriver> findByName(String fullName) {
        LinkedList<IDriver> result = new LinkedList<IDriver>();

        if (drivers.isEmpty()) {
            return result;
        }

        drivers.findFirst();

        while (true) {
            IDriver currentDriver = drivers.retrieve();

            if (currentDriver.getName().equals(fullName)) {
                result.add(currentDriver);
            }

            if (drivers.last()) {
                break;
            }

            drivers.findNext();
        }

        return result;
    }

    /** Returns the driver with the given vehicle plate, or null if none exists. */
    @Override
    public IDriver findByVehiclePlate(String vehiclePlate) {
        if (drivers.isEmpty()) {
            return null;
        }

        drivers.findFirst();

        while (true) {
            IDriver currentDriver = drivers.retrieve();

            if (currentDriver.getVehiclePlate().equals(vehiclePlate)) {
                return currentDriver;
            }

            if (drivers.last()) {
                break;
            }

            drivers.findNext();
        }

        return null;
    }

    /** Returns every driver with the given vehicle type. */
    @Override
    public LinkedList<IDriver> findByVehicleType(VehicleType vehicleType) {
        LinkedList<IDriver> result = new LinkedList<IDriver>();

        if (drivers.isEmpty()) {
            return result;
        }

        drivers.findFirst();

        while (true) {
            IDriver currentDriver = drivers.retrieve();

            if (currentDriver.getVehicleType() == vehicleType) {
                result.add(currentDriver);
            }

            if (drivers.last()) {
                break;
            }

            drivers.findNext();
        }

        return result;
    }

    /** Returns all drivers (sorted by ID). */
    @Override
    public LinkedList<IDriver> getAll() {
        return drivers;
    }

    @Override
    public int size() {
        return drivers.size();
    }

    /** Removes the driver with the given ID. Returns true if one was removed. */
    @Override
    public boolean removeById(int driverId) {
        if (drivers.isEmpty()) {
            return false;
        }

        drivers.findFirst();

        while (true) {
            IDriver currentDriver = drivers.retrieve();

            if (currentDriver.getId() == driverId) {
                drivers.remove();
                return true;
            }

            if (drivers.last()) {
                break;
            }

            drivers.findNext();
        }

        return false;
    }

    /** Removes the driver with the given plate. Returns true if one was removed. */
    @Override
    public boolean removeByVehiclePlate(String vehiclePlate) {
        if (drivers.isEmpty()) {
            return false;
        }

        drivers.findFirst();

        while (true) {
            IDriver currentDriver = drivers.retrieve();

            if (currentDriver.getVehiclePlate().equals(vehiclePlate)) {
                drivers.remove();
                return true;
            }

            if (drivers.last()) {
                break;
            }

            drivers.findNext();
        }

        return false;
    }

    /**
     * Removes every driver whose full name exactly matches the given name.
     * Traverses the whole list (names are not unique).
     * Returns the number of drivers removed.
     */
    @Override
    public int removeByName(String fullName) {
        int count = 0;

        if (drivers.isEmpty()) {
            return count;
        }

        drivers.findFirst();

        while (drivers.retrieve() != null) {
            IDriver currentDriver = drivers.retrieve();

            if (currentDriver.getName().equals(fullName)) {
                drivers.remove();   // remove() moves the cursor to the next node
                count++;
            } else {
                drivers.findNext();
            }
        }

        return count;
    }

    /**
     * Removes every driver with the given vehicle type.
     * Traverses the whole list (vehicle types are not unique).
     * Returns the number of drivers removed.
     */
    @Override
    public int removeByVehicleType(VehicleType vehicleType) {
        int count = 0;

        if (drivers.isEmpty()) {
            return count;
        }

        drivers.findFirst();

        while (drivers.retrieve() != null) {
            IDriver currentDriver = drivers.retrieve();

            if (currentDriver.getVehicleType() == vehicleType) {
                drivers.remove();   // remove() moves the cursor to the next node
                count++;
            } else {
                drivers.findNext();
            }
        }

        return count;
    }
}