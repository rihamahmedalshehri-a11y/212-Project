package ridesharingsystem;

public class SharedRide extends Ride implements ISharedRide {
	
	private LinkedList<IRider> participants;
	 
    public SharedRide(String pickupLocation, IDateTime pickupTime,
            IDateTime dropoffTime, String dropoffLocation, IDriver driver) {
        super(pickupLocation, pickupTime, dropoffTime, dropoffLocation, driver);
        participants = new LinkedList<IRider>();
    }
 
    @Override
    public LinkedList<IRider> getParticipants() {
        return participants;
    }
 
    @Override
    public boolean addParticipant(IRider rider) {
        if (rider == null || hasRider(rider.getId())) {
            return false;
        }
 
        participants.add(rider);
        return true;
    }
 
    @Override
    public boolean removeParticipantById(int riderId) {
        if (participants.isEmpty()) {
            return false;
        }
 
        participants.findFirst();
 
        while (true) {
            if (participants.retrieve().getId() == riderId) {
                participants.remove();
                return true;
            }
 
            if (participants.last()) {
                break;
            }
 
            participants.findNext();
        }
 
        return false;
    }
 
    @Override
    public boolean isEmpty() {
        return participants.isEmpty();
    }
 
    @Override
    public boolean hasRider(int riderId) {
        if (participants.isEmpty()) {
            return false;
        }
 
        participants.findFirst();
 
        while (true) {
            if (participants.retrieve().getId() == riderId) {
                return true;
            }
 
            if (participants.last()) {
                break;
            }
 
            participants.findNext();
        }
 
        return false;
    }
    
    @Override
    public String toString() {
        String names = "";

        if (!participants.isEmpty()) {
            participants.findFirst();

            while (participants.retrieve() != null) {
                names += participants.retrieve().getName() + "; ";
                participants.findNext();
            }
        }

        return super.toString() + ", Riders: " + names;
    }

}
