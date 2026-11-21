package ridesharingsystem;


public class DateTime implements IDateTime {

    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

   
    public DateTime(int year, int month, int day, int hour, int minute) {
        if (year < 1 || month < 1 || month > 12||
                 day < 1  ||day > daysInMonth(year, month)||
                 hour < 0 || hour > 23  ||  minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Invalid date/time: "
                    + month + "/" + day + "/" + year + " " + hour + ":" + minute);
        }

        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    /** Returns the number of days in the given month, handling leap years. */
    private static int daysInMonth(int year, int month) {
        switch (month) {
            case 2:
                boolean leap = (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
                return leap ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public int getHour() {
        return hour;
    }

    @Override
    public int getMinute() {
        return minute;
    }

    /** Formats as MM/DD/YYYY HH:MM. */
    @Override
    public String format() {
        return String.format("%02d/%02d/%04d %02d:%02d",
                month, day, year, hour, minute);
    }

    /**
     * Chronological order: year, then month, then day, then hour, then minute.
     */
    @Override
    public int compareTo(IDateTime other) {
        if (year != other.getYear()) {
            return Integer.compare(year, other.getYear());
        }
        if (month != other.getMonth()) {
            return Integer.compare(month, other.getMonth());
        }
        if (day != other.getDay()) {
            return Integer.compare(day, other.getDay());
        }
        if (hour != other.getHour()) {
            return Integer.compare(hour, other.getHour());
        }
        return Integer.compare(minute, other.getMinute());
    }
}