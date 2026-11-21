package ridesharingsystem;

public class RiderList implements IRiderList {

    private LinkedList<IRider> all_riders;

    public RiderList() {
        all_riders = new LinkedList<>();
    }

// GET ALL
    @Override
    public LinkedList<IRider> getAll() {
        return all_riders;
    }

// ADD RIDER
    @Override
    public boolean add(IRider rider) {
        if (rider == null || rider.getEmail() == null) {
            return false;
        }

        if (findByEmail(rider.getEmail()) != null) {
            return false;
        }

        if (all_riders.empty()) {
            all_riders.insert(rider);
            return true;
        }

        all_riders.findFirst();

        while (true) {
            IRider current = all_riders.retrieve();

            if (rider.compareTo(current) == 0) {
                return false;
            }

            if (rider.compareTo(current) < 0) {
                all_riders.insert_before_current(rider);
                return true;
            }

            if (all_riders.last()) {
                break;
            }

            all_riders.findNext();
        }

        all_riders.insert(rider);
        return true;
    }

// FIND BY ID
    @Override
    public IRider findById(int riderId) {
        if (all_riders.empty()) {
            return null;
        }

        all_riders.findFirst();

        while (true) {
            IRider rider = all_riders.retrieve();

            if (rider.getId() == riderId) {
                return rider;
            }

            if (rider.getId() > riderId
                    || all_riders.last()) {
                break;
            }

            all_riders.findNext();
        }

        return null;
    }

// FIND BY NAME
    @Override
    public LinkedList<IRider> findByName(String fullName) {
        LinkedList<IRider> result = new LinkedList<>();

        if (fullName == null || all_riders.empty()) {
            return result;
        }

        all_riders.findFirst();

        while (true) {
            IRider rider = all_riders.retrieve();

            if (fullName.equals(rider.getName())) {
                result.add(rider);
            }

            if (all_riders.last()) {
                break;
            }

            all_riders.findNext();
        }

        return result;
    }

// FIND BY EMAIL
    @Override
    public IRider findByEmail(String email) {
        if (email == null || all_riders.empty()) {
            return null;
        }

        all_riders.findFirst();

        while (true) {
            IRider rider = all_riders.retrieve();

            if (rider.getEmail() != null
                    && rider.getEmail().equalsIgnoreCase(email)) {
                return rider;
            }

            if (all_riders.last()) {
                break;
            }

            all_riders.findNext();
        }

        return null;
    }

// FIND BY HOME CITY
    @Override
    public LinkedList<IRider> findByHomeCity(String homeCity) {
        LinkedList<IRider> result = new LinkedList<>();

        if (homeCity == null || all_riders.empty()) {
            return result;
        }

        all_riders.findFirst();

        while (true) {
            IRider rider = all_riders.retrieve();

            if (rider.getHomeCity() != null
                    && rider.getHomeCity().equalsIgnoreCase(homeCity)) {
                result.add(rider);
            }

            if (all_riders.last()) {
                break;
            }

            all_riders.findNext();
        }

        return result;
    }

// REMOVE BY ID
    @Override
    public boolean removeById(int riderId) {
        if (all_riders.empty()) {
            return false;
        }

        all_riders.findFirst();

        while (true) {
            IRider current = all_riders.retrieve();

            if (current.getId() == riderId) {
                all_riders.remove();
                return true;
            }

            if (all_riders.last()) {
                break;
            }

            all_riders.findNext();
        }

        return false;
    }

// REMOVE BY EMAIL
    @Override
    public boolean removeByEmail(String email) {
        if (email == null || all_riders.empty()) {
            return false;
        }

        all_riders.findFirst();

        while (true) {
            IRider current = all_riders.retrieve();

            if (current.getEmail() != null
                    && current.getEmail().equalsIgnoreCase(email)) {
                all_riders.remove();
                return true;
            }

            if (all_riders.last()) {
                break;
            }

            all_riders.findNext();
        }

        return false;
    }

// REMOVE BY NAME
    @Override
    public int removeByName(String fullName) {
        if (fullName == null || all_riders.empty()) {
            return 0;
        }

        int count = 0;
        all_riders.findFirst();

        while (all_riders.retrieve() != null) {
            IRider rider = all_riders.retrieve();

            if (fullName.equals(rider.getName())) {
                all_riders.remove();
                count++;
            } else {
                all_riders.findNext();
            }
        }

        return count;
    }

// REMOVE BY HOME CITY
    @Override
    public int removeByHomeCity(String homeCity) {
        if (homeCity == null || all_riders.empty()) {
            return 0;
        }

        int count = 0;
        all_riders.findFirst();

        while (all_riders.retrieve() != null) {
            IRider rider = all_riders.retrieve();

            if (rider.getHomeCity() != null
                    && rider.getHomeCity().equalsIgnoreCase(homeCity)) {
                all_riders.remove();
                count++;
            } else {
                all_riders.findNext();
            }
        }

        return count;
    }

// SIZE
    @Override
    public int size() {
        return all_riders.size();
    }
}
