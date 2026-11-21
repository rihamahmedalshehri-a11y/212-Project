package ridesharingsystem;

/**
 * A private ride: exactly one rider travelling with one driver.
 */
public class PrivateRide extends Ride implements IPrivateRide {

    private IRider rider;

    /**
     * Creates a private ride.
     *
     * @throws IllegalArgumentException if the rider is null or the
     *         inherited ride data (locations, times, driver) is invalid
     */
    public PrivateRide(String pickupLocation, IDateTime pickupTime,
            IDateTime dropoffTime, String dropoffLocation,
            IDriver driver, IRider rider) {
        super(pickupLocation, pickupTime, dropoffTime, dropoffLocation, driver);

        if (rider == null) {
            throw new IllegalArgumentException("A private ride must have a rider.");
        }

        this.rider = rider;
    }

    @Override
    public IRider getRider() {
        return rider;
    }

    /**
     * @throws IllegalArgumentException if the rider is null
     */
    @Override
    public void setRider(IRider rider) {
        if (rider == null) {
            throw new IllegalArgumentException("A private ride must have a rider.");
        }

        this.rider = rider;
    }

    /** A private ride has exactly one rider, so a single ID comparison is enough. */
    @Override
    public boolean hasRider(int riderId) {
        return rider.getId() == riderId;
    }

    @Override
    public String toString() {
        return super.toString() + ", Rider: " + rider.getName();
    }
}