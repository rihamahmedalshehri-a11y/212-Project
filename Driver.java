package ridesharingsystem;

/**
 * A driver in the ride-sharing system. Extends Person with a vehicle plate
 * and a vehicle type. The driver ID is fixed at creation time (see Person).
 */
public class Driver extends Person implements IDriver {

    private String vehiclePlate;
    private VehicleType vehicleType;

    /**
     * Creates a driver.
     *
     * @throws IllegalArgumentException if the phone number, plate format
     *         or vehicle type is invalid
     */
    public Driver(int id, String name, String phoneNumber,
            String vehiclePlate, VehicleType vehicleType) {
        super(id, name, phoneNumber);
        setVehiclePlate(vehiclePlate);
        setVehicleType(vehicleType);
    }

    @Override
    public String getVehiclePlate() {
        return vehiclePlate;
    }

    /**
     * Sets the plate. It must be exactly 3 uppercase letters followed by
     * 4 digits (e.g. ABC1234).
     *
     * @throws IllegalArgumentException if the format does not match
     */
    @Override
    public void setVehiclePlate(String vehiclePlate) {
        if (vehiclePlate == null || !vehiclePlate.matches("[A-Z]{3}[0-9]{4}")) {
            throw new IllegalArgumentException(
                    "Vehicle plate must be 3 uppercase letters followed by 4 digits.");
        }

        this.vehiclePlate = vehiclePlate;
    }

    @Override
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    /**
     * @throws IllegalArgumentException if the vehicle type is null
     */
    @Override
    public void setVehicleType(VehicleType vehicleType) {
        if (vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type must not be null.");
        }

        this.vehicleType = vehicleType;
    }

    /** Orders drivers strictly by driver ID. */
    @Override
    public int compareTo(IDriver other) {
        return Integer.compare(this.id, other.getId());
    }

    @Override
    public String toString() {
        return "Driver:[" + super.toString()
                + ", Plate: " + vehiclePlate
                + ", VehicleType: " + vehicleType + "]";
    }
}