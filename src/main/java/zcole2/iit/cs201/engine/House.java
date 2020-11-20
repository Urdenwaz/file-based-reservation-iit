package zcole2.iit.cs201.engine;

import java.util.Arrays;

public class House {

    /**
     * Class to encapsulate House Object
     * @version 11.19.2020
     */

    // fields for house specifications
    private final int bedroomCount;
    private final int bathroomCount;

    // manipulable fields
    private double rentalPrice;

    // array containing tenants for each day
    // [friday, saturday, sunday]
    private String[] tenants;

    // constructor with all arguments
    public House(final int bedroomCount, final int bathroomCount, double rentalPrice) {
        this.bedroomCount = bedroomCount;
        this.bathroomCount = bathroomCount;
        this.rentalPrice = rentalPrice;
        tenants = new String[3];
    }

    // constructor
    public House(int bedroomCount, int bathroomCount) {
        this(bedroomCount, bathroomCount, 100);
    }

    /**
     * method for reserving a house
     * @param day the desired rental day, 0 = friday, 1 = saturday, 2 = sunday
     * @param name name of the
     * @return true if the reservation is successful, false if unsuccessful
     */
    public boolean reserve(String name, int day) {
        // check if house is taken
        if (tenants[day] != null) {
            return false;
        } else {
            tenants[day] = name;
            return true;
        }
    }

    // getters and setters for constants
    public int getBedroomCount() {
        return bedroomCount;
    }

    public int getBathroomCount() {
        return bathroomCount;
    }

    // string representation of object
    // also probably how the file will be formatted
    @Override
    public String toString() {
        return bathroomCount + "\n" +
                bedroomCount + "\n" +
                rentalPrice + "\n" +
                Arrays.toString(tenants);
    }

}
