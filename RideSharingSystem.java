package ridesharingsystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

  

    public class RideSharingSystem implements IRideSharingSystem {

        private RiderList riders;
        private DriverList drivers;
        private LinkedList<IDriver> driversAlphabetically;
        private RideList rides;
        private LinkedList<IRider> ridersAlphabetically;

        public RideSharingSystem() {
            riders = new RiderList();
            drivers = new DriverList();
            driversAlphabetically = new LinkedList<IDriver>();
            ridersAlphabetically = new LinkedList<IRider>();
            rides = new RideList();
        }

        @Override
        public boolean addRider(IRider rider) {
            if (rider == null || !riders.add(rider)) {
                return false;
            }

            insertRiderAlphabetically(rider);
            return true;
        }

        private void insertRiderAlphabetically(IRider rider) {
            if (ridersAlphabetically.isEmpty()) {
                ridersAlphabetically.insert(rider);
                return;
            }

            ridersAlphabetically.findFirst();

            while (true) {
                IRider currentRider = ridersAlphabetically.retrieve();

                if (rider.getName().compareToIgnoreCase(currentRider.getName()) < 0) {
                    ridersAlphabetically.insert_before_current(rider);
                    return;
                }

                if (ridersAlphabetically.last()) {
                    ridersAlphabetically.insert(rider);
                    return;
                }

                ridersAlphabetically.findNext();
            }
        }

        private void removeFromRiderAlphabeticalList(int riderId) {
            if (ridersAlphabetically.isEmpty()) {
                return;
            }

            ridersAlphabetically.findFirst();

            while (true) {
                if (ridersAlphabetically.retrieve().getId() == riderId) {
                    ridersAlphabetically.remove();
                    return;
                }

                if (ridersAlphabetically.last()) {
                    return;
                }

                ridersAlphabetically.findNext();
            }
        }
        // ======================= DRIVERS PART =======================

        /**
         * Adds a driver. Fails if the driver is null or if its ID or vehicle
         * plate already exists (DriverList.add enforces both).
         */
        @Override
        public boolean addDriver(IDriver driver) {
            if (driver == null || !drivers.add(driver)) {
                return false;
            }

            insertAlphabetically(driver);
            return true;
        }

        @Override
        public IDriver searchDriverById(int driverId) {
            return drivers.findById(driverId);
        }

        @Override
        public IDriver searchDriverByVehiclePlate(String vehiclePlate) {
            return drivers.findByVehiclePlate(vehiclePlate);
        }

        @Override
        public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType) {
            return drivers.findByVehicleType(vehicleType);
        }

        /**
         * Returns all drivers, already kept in alphabetical order by name.
         */
        @Override
        public LinkedList<IDriver> getAllDrivers() {
            return driversAlphabetically;
        }

        /**
         * Removes a driver and every ride (private or shared) assigned to him
         * or her. Each deleted ride is also removed from its riders' ride
         * history.
         */
        @Override
        public boolean removeDriver(int driverId) {
            if (drivers.findById(driverId) == null) {
                return false;
            }

            LinkedList<IRide> allRides = rides.getAllAlphabetically();

            if (!allRides.isEmpty()) {
                allRides.findFirst();

                while (allRides.retrieve() != null) {
                    IRide currentRide = allRides.retrieve();

                    if (currentRide.getDriver().getId() == driverId) {
                        removeRideFromRidersHistory(currentRide);
                        allRides.remove();   // remove() moves the cursor to the next ride
                    } else {
                        allRides.findNext();
                    }
                }
            }

            removeFromAlphabeticalList(driverId);
            return drivers.removeById(driverId);
        }

        /**
         * Loads drivers from a CSV file
         * (driverId,name,phoneNumber,vehiclePlate,vehicleType). All-or-nothing:
         * if any line is invalid or duplicates an existing driver, nothing is
         * added and false is returned.
         */
        @Override
        public boolean loadDriversFromCSV(String driversFilePath) {
            DriverList tempDrivers = new DriverList();

            try (BufferedReader reader = new BufferedReader(new FileReader(driversFilePath))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    String[] data = line.split(",", -1);

                    if (data.length != 5) {
                        return false;
                    }

                    int id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    String phoneNumber = data[2].trim();
                    String vehiclePlate = data[3].trim();
                    // accepts both LUXURY_SEDAN and "LUXURY SEDAN"
                    VehicleType vehicleType = VehicleType.valueOf(
                            data[4].trim().toUpperCase().replace(' ', '_'));

                    Driver driver = new Driver(id, name, phoneNumber, vehiclePlate, vehicleType);

                    // duplicates against the system, and against earlier lines of the file
                    if (drivers.findById(id) != null
                            || drivers.findByVehiclePlate(vehiclePlate) != null
                            || !tempDrivers.add(driver)) {
                        return false;
                    }
                }
            } catch (IOException | IllegalArgumentException e) {
                return false;
            }

            // the whole file is valid: commit to the system
            LinkedList<IDriver> loaded = tempDrivers.getAll();

            if (!loaded.isEmpty()) {
                loaded.findFirst();

                while (loaded.retrieve() != null) {
                    IDriver driver = loaded.retrieve();

                    drivers.add(driver);
                    insertAlphabetically(driver);
                    loaded.findNext();
                }
            }

            return true;
        }

        /**
         * Inserts a driver into the alphabetical list (by name, ignoring case).
         */
        private void insertAlphabetically(IDriver driver) {
            if (driversAlphabetically.isEmpty()) {
                driversAlphabetically.insert(driver);
                return;
            }

            driversAlphabetically.findFirst();

            while (true) {
                IDriver currentDriver = driversAlphabetically.retrieve();

                if (driver.getName().compareToIgnoreCase(currentDriver.getName()) < 0) {
                    driversAlphabetically.insert_before_current(driver);
                    return;
                }

                if (driversAlphabetically.last()) {
                    driversAlphabetically.insert(driver);
                    return;
                }

                driversAlphabetically.findNext();
            }
        }

        /**
         * Removes the driver with the given ID from the alphabetical list.
         */
        private void removeFromAlphabeticalList(int driverId) {
            if (driversAlphabetically.isEmpty()) {
                return;
            }

            driversAlphabetically.findFirst();

            while (driversAlphabetically.retrieve() != null) {
                if (driversAlphabetically.retrieve().getId() == driverId) {
                    driversAlphabetically.remove();
                    return;
                }

                driversAlphabetically.findNext();
            }
        }

        /**
         * Removes a deleted ride from the ride history of every rider on it.
         */
        private void removeRideFromRidersHistory(IRide ride) {
            if (ride instanceof IPrivateRide) {
                removeRideFromHistory(
                        ((IPrivateRide) ride).getRider().getRideHistory(), ride.getRideId());

            } else if (ride instanceof ISharedRide) {
                LinkedList<IRider> participants = ((ISharedRide) ride).getParticipants();

                if (!participants.isEmpty()) {
                    participants.findFirst();

                    while (participants.retrieve() != null) {
                        removeRideFromHistory(
                                participants.retrieve().getRideHistory(), ride.getRideId());
                        participants.findNext();
                    }
                }
            }
        }

        public IRider searchRiderById(int riderId) {
            return riders.findById(riderId);
        }

        public IRider searchRiderByEmail(String email) {
            return riders.findByEmail(email);
        }

        public LinkedList<IRider> searchRidersByName(String fullName) {
            return riders.findByName(fullName);
        }

        public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {
            return riders.findByHomeCity(homeCity);
        }

        @Override
        public LinkedList<IRider> getAllRiders() {
            return ridersAlphabetically;
        }

        public boolean loadRidersFromCSV(String ridersFilePath) {
            RiderList tempRiders = new RiderList();

            try (BufferedReader reader
                    = new BufferedReader(new FileReader(ridersFilePath))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    String[] data = line.split(",", -1);

                    if (data.length != 5) {
                        return false;
                    }

                    int id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    String email = data[2].trim();
                    String phoneNumber = data[3].trim();
                    String homeCity = data[4].trim();

                    Rider rider = new Rider(
                            id, name, phoneNumber, email, homeCity
                    );

                    if (riders.findById(id) != null
                            || riders.findByEmail(email) != null
                            || !tempRiders.add(rider)) {
                        return false;
                    }
                }

                LinkedList<IRider> loaded = tempRiders.getAll();

                if (!loaded.empty()) {
                    loaded.findFirst();

                    while (true) {
                        IRider loadedRider = loaded.retrieve();

                        riders.add(loadedRider);
                        insertRiderAlphabetically(loadedRider);

                        if (loaded.last()) {
                            break;
                        }

                        loaded.findNext();
                    }
                }
            return true;

        }
        catch (IOException | IllegalArgumentException e

        
            ) {
            return false;
        }
    }

    public boolean schedulePrivateRide(String pickupLocation,
            IDateTime pickupTime,
            IDateTime dropoffTime,
            String dropoffLocation,
            int riderId,
            int driverId) {

        IRider rider = riders.findById(riderId);
        IDriver driver = drivers.findById(driverId);

        if (rider == null || driver == null) {
            return false;
        }

        if (pickupTime == null || dropoffTime == null
                || pickupTime.compareTo(dropoffTime) >= 0) {
            return false;
        }

        LinkedList<IRide> allRides = rides.getAllAlphabetically();

        if (!allRides.isEmpty()) {

            allRides.findFirst();

            while (allRides.retrieve() != null) {

                IRide currentRide = allRides.retrieve();

                boolean sameDriver
                        = currentRide.getDriver().getId() == driverId;

                boolean sameRider
                        = currentRide.hasRider(riderId);

                boolean timeConflict
                        = pickupTime.compareTo(currentRide.getDropoffTime()) < 0
                        && dropoffTime.compareTo(currentRide.getPickupTime()) > 0;

                if (timeConflict && (sameDriver || sameRider)) {
                    return false;
                }

                allRides.findNext();
            }
        }

        try {

            PrivateRide newRide = new PrivateRide(
                    pickupLocation,
                    pickupTime,
                    dropoffTime,
                    dropoffLocation,
                    driver,
                    rider
            );

            if (!rides.addRide(newRide)) {
                return false;
            }

            rider.getRideHistory().add(newRide);
            driver.getRideHistory().add(newRide);

            return true;

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean scheduleSharedRide(String pickupLocation,
            IDateTime pickupTime,
            IDateTime dropoffTime,
            String dropoffLocation,
            int[] riderIds,
            int driverId) {

        if (riderIds == null || riderIds.length < 2) {
            return false;
        }

        IDriver driver = drivers.findById(driverId);

        if (driver == null || pickupTime == null || dropoffTime == null
                || pickupTime.compareTo(dropoffTime) >= 0) {
            return false;
        }
        IRider[] selectedRiders = new IRider[riderIds.length];

        for (int i = 0; i < riderIds.length; i++) {

            IRider rider = riders.findById(riderIds[i]);

            if (rider == null) {
                return false;
            }

            for (int j = 0; j < i; j++) {
                if (riderIds[i] == riderIds[j]) {
                    return false;
                }
            }

            selectedRiders[i] = rider;
        }

        LinkedList<IRide> allRides = rides.getAllAlphabetically();

        if (!allRides.isEmpty()) {

            allRides.findFirst();

            while (allRides.retrieve() != null) {

                IRide currentRide = allRides.retrieve();

                boolean timeConflict
                        = pickupTime.compareTo(currentRide.getDropoffTime()) < 0
                        && dropoffTime.compareTo(currentRide.getPickupTime()) > 0;

                if (timeConflict) {

                    if (currentRide.getDriver().getId() == driverId) {
                        return false;
                    }

                    for (int i = 0; i < riderIds.length; i++) {
                        if (currentRide.hasRider(riderIds[i])) {
                            return false;
                        }
                    }
                }

                allRides.findNext();
            }
        }

        try {

            SharedRide newRide = new SharedRide(
                    pickupLocation,
                    pickupTime,
                    dropoffTime,
                    dropoffLocation,
                    driver
            );

            for (int i = 0; i < selectedRiders.length; i++) {
                if (!newRide.addParticipant(selectedRiders[i])) {
                    return false;
                }
            }

            if (!rides.addRide(newRide)) {
                return false;
            }

            driver.getRideHistory().add(newRide);

            for (int i = 0; i < selectedRiders.length; i++) {
                selectedRiders[i].getRideHistory().add(newRide);
            }

            return true;

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {
        return rides.findByPickupLocation(pickupLocation);
    }

    public LinkedList<IRide> searchRidesByRiderName(String riderName) {
        return rides.findByRiderName(riderName);
    }

    public LinkedList<IRide> getAllRidesAlphabetically() {
        return rides.getAllAlphabetically();
    }

    public LinkedList<IRider> getSharedRideParticipants(String pickupLocation) {

        LinkedList<IRider> result = new LinkedList<IRider>();
        LinkedList<IRide> foundRides
                = rides.findByPickupLocation(pickupLocation);

        if (foundRides.isEmpty()) {
            return result;
        }

        foundRides.findFirst();

        while (foundRides.retrieve() != null) {

            IRide ride = foundRides.retrieve();

            if (ride instanceof ISharedRide) {

                LinkedList<IRider> participants
                        = ((ISharedRide) ride).getParticipants();

                if (!participants.isEmpty()) {
                    participants.findFirst();

                    while (participants.retrieve() != null) {
                        result.add(participants.retrieve());
                        participants.findNext();
                    }
                }
            }

            foundRides.findNext();
        }

        return result;
    }

    public boolean removeRider(int riderId) {

        IRider rider = riders.findById(riderId);

        if (rider == null) {
            return false;
        }

        LinkedList<IRide> allRides
                = rides.getAllAlphabetically();

        if (!allRides.isEmpty()) {

            allRides.findFirst();

            while (allRides.retrieve() != null) {

                IRide currentRide = allRides.retrieve();

                if (currentRide instanceof IPrivateRide) {

                    if (currentRide.hasRider(riderId)) {

                        // Remove the ride from the driver's history
                        LinkedList<IRide> driverHistory
                                = currentRide.getDriver().getRideHistory();

                        removeRideFromHistory(
                                driverHistory,
                                currentRide.getRideId());

                        // Remove the ride from the system
                        allRides.remove();

                    } else {
                        allRides.findNext();
                    }

                } else if (currentRide instanceof ISharedRide) {

                    ISharedRide sharedRide
                            = (ISharedRide) currentRide;

                    if (sharedRide.hasRider(riderId)) {

                        sharedRide.removeParticipantById(riderId);

                        if (sharedRide.isEmpty()) {

                            // Remove from driver's history
                            LinkedList<IRide> driverHistory
                                    = currentRide.getDriver().getRideHistory();

                            removeRideFromHistory(
                                    driverHistory,
                                    currentRide.getRideId());

                            // Remove the empty shared ride
                            allRides.remove();

                        } else {
                            allRides.findNext();
                        }

                    } else {
                        allRides.findNext();
                    }

                } else {
                    allRides.findNext();
                }
            }
        }
        removeFromRiderAlphabeticalList(riderId);
        return riders.removeById(riderId);
    }

    public boolean loadRidesFromCSV(String ridesFilePath) {

        RideList tempRides = new RideList();

        try (BufferedReader reader
                = new BufferedReader(new FileReader(ridesFilePath))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",", -1);

                if (data.length != 7) {
                    return false;
                }

                String rideType = data[0].trim();
                String pickupLocation = data[1].trim();
                IDateTime pickupTime = parseDateTime(data[2].trim());
                IDateTime dropoffTime = parseDateTime(data[3].trim());
                String dropoffLocation = data[4].trim();
                int driverId = Integer.parseInt(data[5].trim());
                String ridersData = data[6].trim();

                boolean isPrivate = rideType.equalsIgnoreCase("PRIVATE");
                boolean isShared = rideType.equalsIgnoreCase("SHARED");

                if (!isPrivate && !isShared) {
                    return false;
                }

                IDriver driver = drivers.findById(driverId);

                if (driver == null) {
                    return false;
                }

                int[] riderIds;

                if (isPrivate) {
                    riderIds = new int[]{Integer.parseInt(ridersData)};
                } else {
                    String[] riderParts = ridersData.split(";");
                    riderIds = new int[riderParts.length];

                    for (int i = 0; i < riderParts.length; i++) {
                        riderIds[i] = Integer.parseInt(riderParts[i].trim());
                    }
                }

                IRider[] selectedRiders = new IRider[riderIds.length];

                for (int i = 0; i < riderIds.length; i++) {

                    IRider rider = riders.findById(riderIds[i]);

                    if (rider == null) {
                        return false;
                    }

                    for (int j = 0; j < i; j++) {
                        if (riderIds[i] == riderIds[j]) {
                            return false;
                        }
                    }

                    selectedRiders[i] = rider;
                }

                // conflicts with rides already in the system or earlier in this file
                if (hasConflict(rides.getAllAlphabetically(), pickupTime,
                        dropoffTime, driverId, riderIds)
                        || hasConflict(tempRides.getAllAlphabetically(), pickupTime,
                                dropoffTime, driverId, riderIds)) {
                    return false;
                }

                IRide ride;

                if (isPrivate) {
                    ride = new PrivateRide(pickupLocation, pickupTime,
                            dropoffTime, dropoffLocation, driver,
                            selectedRiders[0]);
                } else {
                    SharedRide shared = new SharedRide(pickupLocation, pickupTime,
                            dropoffTime, dropoffLocation, driver);

                    for (int i = 0; i < selectedRiders.length; i++) {
                        if (!shared.addParticipant(selectedRiders[i])) {
                            return false;
                        }
                    }

                    ride = shared;
                }

                if (!tempRides.addRide(ride)) {
                    return false;
                }
            }

            // whole file is valid: commit to the real system
            LinkedList<IRide> loaded = tempRides.getAllAlphabetically();

            if (!loaded.empty()) {
                loaded.findFirst();

                while (true) {

                    IRide ride = loaded.retrieve();

                    rides.addRide(ride);
                    ride.getDriver().getRideHistory().add(ride);

                    if (ride instanceof IPrivateRide) {
                        ((IPrivateRide) ride).getRider()
                                .getRideHistory().add(ride);

                    } else if (ride instanceof ISharedRide) {
                        LinkedList<IRider> parts
                                = ((ISharedRide) ride).getParticipants();

                        parts.findFirst();

                        while (parts.retrieve() != null) {
                            parts.retrieve().getRideHistory().add(ride);
                            parts.findNext();
                        }
                    }

                    if (loaded.last()) {
                        break;
                    }

                    loaded.findNext();
                }
            }

            return true;

        } catch (IOException | IllegalArgumentException e) {
            return false;
        }
    }

    private boolean hasConflict(LinkedList<IRide> list,
            IDateTime pickupTime,
            IDateTime dropoffTime,
            int driverId,
            int[] riderIds) {

        if (list.isEmpty()) {
            return false;
        }

        list.findFirst();

        while (list.retrieve() != null) {

            IRide ride = list.retrieve();

            boolean timeConflict
                    = pickupTime.compareTo(ride.getDropoffTime()) < 0
                    && dropoffTime.compareTo(ride.getPickupTime()) > 0;

            if (timeConflict) {

                if (ride.getDriver().getId() == driverId) {
                    return true;
                }

                for (int i = 0; i < riderIds.length; i++) {
                    if (ride.hasRider(riderIds[i])) {
                        return true;
                    }
                }
            }

            list.findNext();
        }

        return false;
    }

    private IDateTime parseDateTime(String value) {

        String[] parts = value.split(" ");

        if (parts.length != 2) {
            throw new IllegalArgumentException(
                    "Invalid date and time.");
        }

        String[] dateParts = parts[0].split("/");
        String[] timeParts = parts[1].split(":");

        if (dateParts.length != 3
                || timeParts.length != 2) {

            throw new IllegalArgumentException(
                    "Invalid date and time.");
        }

        int month
                = Integer.parseInt(dateParts[0]);

        int day
                = Integer.parseInt(dateParts[1]);

        int year
                = Integer.parseInt(dateParts[2]);

        int hour
                = Integer.parseInt(timeParts[0]);

        int minute
                = Integer.parseInt(timeParts[1]);

        return new DateTime(
                year, month, day, hour, minute);
    }

    private void removeRideFromHistory(
            LinkedList<IRide> history,
            int rideId) {

        if (history == null || history.isEmpty()) {
            return;
        }

        history.findFirst();

        while (history.retrieve() != null) {

            if (history.retrieve().getRideId() == rideId) {
                history.remove();
            } else {
                history.findNext();
            }
        }
    }
}
