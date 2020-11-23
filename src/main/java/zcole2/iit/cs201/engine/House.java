package zcole2.iit.cs201.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * House Class for File-Based Reservation Project
 * @author zak-cole
 * @author Khushin30
 * @version 11.22.2020
 */

public class House implements Serializable {

    // constants
    private static final double DEFAULT_PRICE = 250;
    private static final String[] DAYS = { "Friday", "Saturday", "Sunday" };

    // fields for house specifications
    private final int bedroomCount;
    private final int bathroomCount;

    // manipulable fields
    private double[] rentalPrice;

    // array containing tenants for each day
    // [friday, saturday, sunday]
    private String[] tenants;

    // constructor with all arguments
    public House(final int bedroomCount, final int bathroomCount) {
        this.bedroomCount = bedroomCount;
        this.bathroomCount = bathroomCount;

        // initialize rental price array, default price in case of errors
        rentalPrice = new double[]{ DEFAULT_PRICE, DEFAULT_PRICE, DEFAULT_PRICE };

        // initialize tenants array
        tenants = new String[3];
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

    // method to update prices
    public void updatePrice(int day, double updatedPrice) {
        // check if house is already rented
        if (tenants[day] == null) {
            rentalPrice[day] = updatedPrice;
        } else {
            System.out.println("House already has a tenant for " + DAYS[day]);
        }
    }

    // get prices
    public double[] getPrices() {
        return rentalPrice;
    }

    // get tenants for each day
    public String[] getTenants() {
        return tenants;
    }

    // get availability of houses
    public List<String> getAvailability() {
        // initialize list
        List<String> availability = new ArrayList<>();

        // write available days to an array
        for (int i = 0; i < tenants.length; i++) {
            if (tenants[i] == null) {
                availability.add(DAYS[i]);
            }
        }

        // return availability
        return availability;
    }

    // get available prices
    public List<Double> getAvailablePrices() {
        // initialize list
        List<Double> availablePrices = new ArrayList<>();

        // write available prices to array
        for (int i = 0; i < tenants.length; i++) {
            if (tenants[i] == null) {
                availablePrices.add(rentalPrice[i]);
            }
        }

        // return available prices
        return availablePrices;
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
