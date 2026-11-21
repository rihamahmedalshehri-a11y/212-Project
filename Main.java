package ridesharingsystem;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        RideSharingSystem system = new RideSharingSystem();
        Scanner input = new Scanner(System.in);

          if (!system.loadRidersFromCSV("riders_100.csv")) {
            System.out.println("Failed to load riders.");
        }
        if (!system.loadDriversFromCSV("drivers_30.csv")) {
            System.out.println("Failed to load drivers.");
        }
        if (!system.loadRidesFromCSV("rides_40.csv")) {
            System.out.println("Failed to load rides.");
        }
        int choice;

        do {
            System.out.println("1. List all riders.");
            System.out.println("2. Search for riders by home city.");
            System.out.println("3. Search for riders by name.");
            System.out.println("4. Add a rider.");
            System.out.println("5. List all drivers.");
            System.out.println("6. Search for drivers by vehicle type.");
            System.out.println("7. Add a driver.");
            System.out.println("8. List all rides alphabetically by pickup location.");
            System.out.println("9. Search for rides by pickup location.");
            System.out.println("10. Search for rides by rider name.");
            System.out.println("11. List the participants of a shared ride by pickup location.");
            System.out.println("12. Add a ride.");
            System.out.println("0. Exit.");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(input.nextLine());

                switch (choice) {
                    case 1: {
                        LinkedList<IRider> allRiders = system.getAllRiders();

                        if (allRiders.empty()) {
                            System.out.println("No riders found.");
                        } else {
                            allRiders.findFirst();

                            while (true) {
                                System.out.println(allRiders.retrieve());

                                if (allRiders.last()) {
                                    break;
                                }

                                allRiders.findNext();
                            }
                        }
                        break;
                    }

                    case 2: {
                        System.out.print("Enter home city: ");
                        String homeCity = input.nextLine();

                        LinkedList<IRider> riders
                                = system.searchRidersByHomeCity(homeCity);

                        if (riders.empty()) {
                            System.out.println("No riders found.");
                        } else {
                            riders.findFirst();

                            while (true) {
                                System.out.println(riders.retrieve());

                                if (riders.last()) {
                                    break;
                                }

                                riders.findNext();
                            }
                        }
                        break;
                    }

                    case 3: {
                        System.out.print("Enter rider full name: ");
                        String fullName = input.nextLine();

                        LinkedList<IRider> riders
                                = system.searchRidersByName(fullName);

                        if (riders.empty()) {
                            System.out.println("No riders found.");
                        } else {
                            riders.findFirst();

                            while (true) {
                                System.out.println(riders.retrieve());

                                if (riders.last()) {
                                    break;
                                }

                                riders.findNext();
                            }
                        }
                        break;
                    }

                    case 4: {
                        try {
                            System.out.print("Enter rider ID: ");
                            int id = Integer.parseInt(input.nextLine());

                            System.out.print("Enter rider name: ");
                            String name = input.nextLine();

                            System.out.print("Enter phone number: ");
                            String phoneNumber = input.nextLine();

                            System.out.print("Enter email: ");
                            String email = input.nextLine();

                            System.out.print("Enter home city: ");
                            String homeCity = input.nextLine();

                            Rider rider = new Rider(
                                    id, name, phoneNumber, email, homeCity
                            );

                            if (system.addRider(rider)) {
                                System.out.println("Rider added successfully.");
                            } else {
                                System.out.println("Rider could not be added: Duplicate ID or email.");
                            }

                        } catch (IllegalArgumentException e) {
                           System.out.println("Invalid rider information: " + e.getMessage());
                        }
                        break;
                    }

                    case 5: {   // List all drivers (alphabetical by name)
                        printDrivers(system.getAllDrivers());
                        break;
                    }

                    case 6: {   // Search for drivers by vehicle type
                        VehicleType vehicleType = readVehicleType(input);

                        if (vehicleType == null) {
                            System.out.println("Invalid vehicle type.");
                        } else {
                            printDrivers(system.searchDriversByVehicleType(vehicleType));
                        }
                        break;
                    }

                    case 7: {   // Add a driver
                        try {
                            System.out.print("Enter driver ID: ");
                            int id = Integer.parseInt(input.nextLine().trim());

                            System.out.print("Enter name: ");
                            String name = input.nextLine().trim();

                            System.out.print("Enter phone number (10 digits): ");
                            String phone = input.nextLine().trim();

                            System.out.print("Enter vehicle plate (e.g. ABC1234): ");
                            String plate = input.nextLine().trim();

                            VehicleType vehicleType = readVehicleType(input);

                            if (vehicleType == null) {
                                System.out.println("Invalid vehicle type.");
                                break;
                            }

                            Driver driver = new Driver(id, name, phone, plate, vehicleType);

                            if (system.addDriver(driver)) {
                                System.out.println("Driver added successfully.");
                            } else {
                                System.out.println("Driver could not be added "
                                        + "(duplicate ID or vehicle plate).");
                            }

                        } catch (IllegalArgumentException e) {
                            // also covers NumberFormatException (bad ID)
                            System.out.println("Invalid driver information: " + e.getMessage());
                        }
                        break;
                    }

                        
                    case 8: {
                        LinkedList<IRide> allRides = system.getAllRidesAlphabetically();

                        if (allRides.empty()) {
                            System.out.println("No rides found.");
                        } else {
                            allRides.findFirst();

                            while (allRides.retrieve() != null) {
                                System.out.println(allRides.retrieve());
                                allRides.findNext();
                            }
                        }
                        break;
                    }

                    case 9: {
                        System.out.print("Enter pickup location: ");
                        String pickup = input.nextLine();

                        LinkedList<IRide> found = system.searchRidesByPickupLocation(pickup);

                        if (found.empty()) {
                            System.out.println("No rides found.");
                        } else {
                            found.findFirst();

                            while (found.retrieve() != null) {
                                System.out.println(found.retrieve());
                                found.findNext();
                            }
                        }
                        break;
                    }

                    case 10: {
                        System.out.print("Enter rider full name: ");
                        String riderName = input.nextLine();

                        LinkedList<IRide> found = system.searchRidesByRiderName(riderName);

                        if (found.empty()) {
                            System.out.println("No rides found.");
                        } else {
                            found.findFirst();

                            while (found.retrieve() != null) {
                                System.out.println(found.retrieve());
                                found.findNext();
                            }
                        }
                        break;
                    }

                    case 11: {
                        System.out.print("Enter pickup location: ");
                        String pickup = input.nextLine();

                        LinkedList<IRider> participants
                                = system.getSharedRideParticipants(pickup);

                        if (participants.empty()) {
                            System.out.println("No participants found.");
                        } else {
                            participants.findFirst();

                            while (participants.retrieve() != null) {
                                System.out.println(participants.retrieve());
                                participants.findNext();
                            }
                        }
                        break;
                    }

                    case 12: {
                        try {
                            System.out.println("1. Private ride");
                            System.out.println("2. Shared ride");
                            System.out.print("Enter ride type: ");
                            int rideType = Integer.parseInt(input.nextLine());

                            if (rideType != 1 && rideType != 2) {
                                System.out.println("Invalid ride type.");
                                break;
                            }

                            System.out.print("Enter pickup location: ");
                            String pickup = input.nextLine();

                            System.out.print("Enter drop-off location: ");
                            String dropoff = input.nextLine();

                            String[] labels = {"pickup", "drop-off"};
                            IDateTime[] times = new IDateTime[2];

                            for (int i = 0; i < 2; i++) {
                                System.out.print("Enter " + labels[i] + " time (MM/DD/YYYY HH:MM): ");
                                String[] p = input.nextLine().trim().split("[/ :]");

                                if (p.length != 5) {
                                    throw new IllegalArgumentException("Invalid date and time.");
                                }

                                int month = Integer.parseInt(p[0]);
                                int day = Integer.parseInt(p[1]);
                                int year = Integer.parseInt(p[2]);
                                int hour = Integer.parseInt(p[3]);
                                int minute = Integer.parseInt(p[4]);

                                java.time.LocalDateTime.of(year, month, day, hour, minute); // validates ranges
                                times[i] = new DateTime(year, month, day, hour, minute);
                            }

                            System.out.print("Enter driver ID: ");
                            int driverId = Integer.parseInt(input.nextLine());

                            boolean scheduled;

                            if (rideType == 1) {
                                System.out.print("Enter rider ID: ");
                                int riderId = Integer.parseInt(input.nextLine());

                                scheduled = system.schedulePrivateRide(pickup, times[0],
                                        times[1], dropoff, riderId, driverId);
                            } else {
                                System.out.print("Enter rider IDs separated by commas: ");
                                String[] parts = input.nextLine().split(",");
                                int[] riderIds = new int[parts.length];

                                for (int i = 0; i < parts.length; i++) {
                                    riderIds[i] = Integer.parseInt(parts[i].trim());
                                }

                                scheduled = system.scheduleSharedRide(pickup, times[0],
                                        times[1], dropoff, riderIds, driverId);
                            }

                            System.out.println(scheduled
                                    ? "Ride added successfully."
                                    : "Ride could not be added.");

                        } catch (IllegalArgumentException | java.time.DateTimeException e) {
                            System.out.println("Invalid ride information.");
                        }
                        break;
                    }
                    
                    case 0:
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid choice.");
                choice = -1;
            }

        } while (choice != 0);

        input.close();
    }

    /** Prints every driver (one per line), or a message if the list is empty. */
    private static void printDrivers(LinkedList<IDriver> drivers) {
        if (drivers.isEmpty()) {
            System.out.println("No drivers found.");
            return;
        }

        drivers.findFirst();

        while (drivers.retrieve() != null) {
            System.out.println(drivers.retrieve());
            drivers.findNext();
        }
    }

    /**
     * Shows the vehicle types as a numbered menu and reads the choice.
     * Returns null if the choice is not a valid number from the menu.
     */
    private static VehicleType readVehicleType(Scanner input) {
        VehicleType[] types = VehicleType.values();

        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }

        System.out.print("Enter vehicle type: ");

        try {
            int choice = Integer.parseInt(input.nextLine().trim());

            if (choice >= 1 && choice <= types.length) {
                return types[choice - 1];
            }
        } catch (NumberFormatException e) {
            // fall through: invalid input
        }

        return null;
}}