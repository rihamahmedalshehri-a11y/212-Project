package ridesharingsystem;

/**
 * A driver in the ride-sharing system.
 * Extends Person with a vehicle plate and vehicle type.
 */
public class Driver extends Person implements IDriver {

    private String vehiclePlate;
    private VehicleType vehicleType;

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
     * Sets the vehicle plate.
     * Format: 3 uppercase letters followed by 4 digits.
     */
    public void setVehiclePlate(String vehiclePlate) {

        if (vehiclePlate == null ||
                !vehiclePlate.matches("[A-Z]{3}[0-9]{4}")) {

            throw new IllegalArgumentException(
                    "Vehicle plate must be 3 uppercase letters followed by 4 digits.");
        }

        this.vehiclePlate = vehiclePlate;
    }

    @Override
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {

        if (vehicleType == null) {
            throw new IllegalArgumentException(
                    "Vehicle type must not be null.");
        }

        this.vehicleType = vehicleType;
    }

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