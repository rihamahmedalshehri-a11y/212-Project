package ridesharingsystem;

public abstract class Ride implements IRide {
	
	private static int nextRideId = 1;
	
	private final int rideId;
    private String pickupLocation;
    private final IDateTime pickupTime;
    private final IDateTime dropoffTime;
    private String dropoffLocation;
    private IDriver driver;
 
    public Ride(String pickupLocation, IDateTime pickupTime,
            IDateTime dropoffTime, String dropoffLocation, IDriver driver) {
 
        if (pickupLocation == null || pickupLocation.trim().isEmpty()
                || dropoffLocation == null || dropoffLocation.trim().isEmpty()) {
            throw new IllegalArgumentException("Locations must not be empty.");
        }
 
        if (pickupTime == null || dropoffTime == null
                || pickupTime.compareTo(dropoffTime) >= 0) {
            throw new IllegalArgumentException("Drop-off time must be after pickup time.");
        }
 
        if (driver == null) {
            throw new IllegalArgumentException("A ride must have a driver.");
        }
 
        this.rideId = nextRideId++;
        this.pickupLocation = pickupLocation;
        this.pickupTime = pickupTime;
        this.dropoffTime = dropoffTime;
        this.dropoffLocation = dropoffLocation;
        this.driver = driver;
    }
 
    @Override
    public int getRideId() {
        return rideId;
    }
 
    @Override
    public String getPickupLocation() {
        return pickupLocation;
    }
 
    @Override
    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
 
    @Override
    public IDateTime getPickupTime() {
        return pickupTime;
    }
 
    @Override
    public IDateTime getDropoffTime() {
        return dropoffTime;
    }
 
    @Override
    public String getDropoffLocation() {
        return dropoffLocation;
    }
 
    @Override
    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }
 
    @Override
    public IDriver getDriver() {
        return driver;
    }
 
    @Override
    public void setDriver(IDriver driver) {
        this.driver = driver;
    }
 
    @Override
    public abstract boolean hasRider(int riderId);
 
    @Override
    public int compareTo(IRide other) {
        return this.pickupLocation.compareToIgnoreCase(other.getPickupLocation());
    }
 
    @Override
    public String toString() {
        return "RideID: " + rideId
                + ", Pickup: " + pickupLocation + " (" + pickupTime.format() + ")"
                + ", Dropoff: " + dropoffLocation + " (" + dropoffTime.format() + ")"
                + ", Driver: " + driver.getName();
    }

}
