package ridesharingsystem;
public abstract class Person implements IPerson {
protected final int id;
protected String name;
protected String phoneNumber;

protected LinkedList<IRide> rideHistory;

// Schedule will be initialized using the BST implementation.
protected IBST<ITimeSlot, IRide> schedule;

public Person(int id, String name, String phoneNumber) {

    this.id = id;
    this.name = name;

    setPhoneNumber(phoneNumber);

    rideHistory = new LinkedList<IRide>();
    schedule = null;
}

@Override
public int getId() {
    return id;
}

@Override
public String getName() {
    return name;
}

// Kept for compatibility with Phase 1.
public void setName(String name) {
    this.name = name;
}

@Override
public String getPhoneNumber() {
    return phoneNumber;
}

@Override
public void setPhoneNumber(String phoneNumber) {

    if (phoneNumber == null || phoneNumber.length() != 10) {
        throw new IllegalArgumentException(
                "Phone number must be exactly 10 digits.");
    }

    for (int i = 0; i < phoneNumber.length(); i++) {

        if (phoneNumber.charAt(i) < '0'
                || phoneNumber.charAt(i) > '9') {

            throw new IllegalArgumentException(
                    "Phone number must contain only numeric characters.");
        }
    }

    this.phoneNumber = phoneNumber;
}

@Override
public IBST<ITimeSlot, IRide> getSchedule() {
    return schedule;
}

// Used to initialize the schedule after creating the BST.
public void setSchedule(IBST<ITimeSlot, IRide> schedule) {

    if (schedule == null) {
        throw new IllegalArgumentException(
                "Schedule cannot be null.");
    }

    this.schedule = schedule;
}

// Kept for compatibility with Phase 1.
public LinkedList<IRide> getRideHistory() {
    return rideHistory;
}

@Override
public String toString() {
    return "ID: " + id
            + ", Name: " + name
            + ", Phone: " + phoneNumber;
}
}