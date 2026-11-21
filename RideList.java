package ridesharingsystem;

public class RideList implements IRideList {
	
	private LinkedList<IRide> rides;
	 
    public RideList() {
        rides = new LinkedList<IRide>();
    }
 
    @Override
    public boolean addRide(IRide ride) {
        if (ride == null) {
            return false;
        }
 
        if (rides.isEmpty()) {
            rides.insert(ride);
            return true;
        }
 
        rides.findFirst();
 
        while (true) {
            if (ride.compareTo(rides.retrieve()) < 0) {
                rides.insert_before_current(ride);
                return true;
            }
 
            if (rides.last()) {
                break;
            }
 
            rides.findNext();
        }
 
        rides.insert(ride);
        return true;
    }
 
    @Override
    public boolean removeRideById(int rideId) {
        if (rides.isEmpty()) {
            return false;
        }
 
        rides.findFirst();
 
        while (true) {
            if (rides.retrieve().getRideId() == rideId) {
                rides.remove();
                return true;
            }
 
            if (rides.last()) {
                break;
            }
 
            rides.findNext();
        }
 
        return false;
    }
 
    @Override
    public LinkedList<IRide> getAllAlphabetically() {
        return rides;
    }
 
    @Override
    public LinkedList<IRide> findByPickupLocation(String pickupLocation) {
        LinkedList<IRide> result = new LinkedList<IRide>();
 
        if (pickupLocation == null || rides.isEmpty()) {
            return result;
        }
 
        rides.findFirst();
 
        while (true) {
            IRide ride = rides.retrieve();
 
            if (ride.getPickupLocation().equalsIgnoreCase(pickupLocation)) {
                result.add(ride);
            }
 
            if (rides.last()) {
                break;
            }
 
            rides.findNext();
        }
 
        return result;
    }
 
    @Override
    public LinkedList<IRide> findByRiderName(String riderFullName) {
        LinkedList<IRide> result = new LinkedList<IRide>();
 
        if (riderFullName == null || rides.isEmpty()) {
            return result;
        }
 
        rides.findFirst();
 
        while (true) {
            IRide ride = rides.retrieve();
 
            if (hasRiderNamed(ride, riderFullName)) {
                result.add(ride);
            }
 
            if (rides.last()) {
                break;
            }
 
            rides.findNext();
        }
 
        return result;
    }
 
    private boolean hasRiderNamed(IRide ride, String name) {
        if (ride instanceof IPrivateRide) {
            IRider rider = ((IPrivateRide) ride).getRider();
            return rider != null && name.equals(rider.getName());
        }
 
        if (ride instanceof ISharedRide) {
            LinkedList<IRider> participants = ((ISharedRide) ride).getParticipants();
 
            if (participants.isEmpty()) {
                return false;
            }
 
            participants.findFirst();
 
            while (true) {
                if (name.equals(participants.retrieve().getName())) {
                    return true;
                }
 
                if (participants.last()) {
                    break;
                }
 
                participants.findNext();
            }
        }
 
        return false;
    }
 
    @Override
    public int size() {
        return rides.size();
    }

}
