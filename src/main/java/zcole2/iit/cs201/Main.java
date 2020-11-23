package zcole2.iit.cs201;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import zcole2.iit.cs201.engine.House;
import zcole2.iit.cs201.engine.ReservationUI;

import javax.swing.*;

/**
 * Main Class for File-Based Reservation Project
 * @author zak-cole
 * @author Khushin30
 * @version 11.22.2020
 */

public class Main {

    // array to keep days in check
    private static final String[] DAYS = { "Friday", "Saturday", "Sunday" };

    // constants
    private static final String UPDATED_PRICES_FILENAME = "updated_prices.txt";
    private static final String HOUSE_FILENAME = "houses.txt";
    private static final String HOUSE_STATUS_FILENAME = "house_status.txt";

    public static void main(String[] args) {
        // read in houses from house file
        List<House> houses = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(HOUSE_FILENAME))) {
            Object o;
            while ((o = ois.readObject()) != null) {
                houses.add((House)o);
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("House file not found. Running init method.");
            houses = initHouses();
        } catch (EOFException e) {
            System.out.println("End of file reached.");
        } catch (IOException e) {
            System.out.println("IO Exception encountered.");
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {}

        // run management side
        managementSide(houses);

        // run customer side
        new ReservationUI(houses);
    }

    // method to hold code for when customer side is running
    public static void updatePrices(List<House> houses) {
        // file containing updated prices
        // order of updated prices must be the same as the order of houses stored in house storage file
        // prices will be in double format
        File prices = new File(UPDATED_PRICES_FILENAME);

        // check if file exists
        if (prices.exists() && prices.length() != 0) {
            // read data
            // file format will be house#: price
            // see example_updated_pricing.txt
            try (BufferedReader rdr = new BufferedReader(new FileReader(prices))) {
                // read in data from file
                String line;
                while ((line = rdr.readLine()) != null) {
                    // split line into array
                    String[] tokens = line.split(" ");

                    // get house number you are working with
                    int houseNum = Integer.parseInt(tokens[0].charAt(5) + "");

                    // new price is in file
                    if (tokens.length != 1) {
                        for (int i = 1; i < tokens.length - 1; i++) {
                            switch (tokens[i]) {
                                case "Friday" -> houses.get(houseNum - 1).updatePrice(0, Double.parseDouble(tokens[i + 1]));
                                case "Saturday" -> houses.get(houseNum - 1).updatePrice(1, Double.parseDouble(tokens[i + 1]));
                                case "Sunday" -> houses.get(houseNum - 1).updatePrice(2, Double.parseDouble(tokens[i + 1]));
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("We done ran into an error in updating prices. Aw shucks!");
                e.printStackTrace();
            }
        }
    }

    // method to handle management side
    public static void managementSide(List<House> houses) {
        // update prices of each house
        updatePrices(houses);

        // write house statuses to file and write objects to file
        try {
            writeHouseStatus(houses);
            writeHousesToFile(houses);
        } catch (IOException e) {
            System.out.println("We done ran into an error writing to files. Aw shucks!");
            e.printStackTrace();
        }
    }

    /*
     * text based implementation of customer side
     * deprecated v11.22.2020
     */
    @Deprecated
    public static void customerSide(List<House> houses) {
        // initialize scanner for user input
        Scanner userInput = new Scanner(System.in);

        // welcome message
        System.out.println("Welcome to File-Based Reservation @ IIT!");
        System.out.println("Which house would you like to rent?");

        // print house info
        for (int i = 0; i < houses.size(); i++) {
            System.out.println("House " + (i + 1) + " has " + houses.get(i).getBedroomCount() + " bedrooms and "
                    + houses.get(i).getBathroomCount() + " bathrooms.");
        }

        // get user input for desired house
        int houseToRent = Integer.parseInt(userInput.nextLine());

        // if user gives invalid input
        if (houseToRent != 1 && houseToRent != 2) {
            System.out.println("Invalid input given. Shutting down...");
            System.exit(-1);
        }

        // display availability and prices
        System.out.println("Current availability: " + houses.get(houseToRent - 1).getAvailability());
        System.out.println("Prices for Each Day: " + houses.get(houseToRent - 1).getAvailablePrices());
        System.out.println("Which day would you like to rent for?");

        // get user input for day
        String day = userInput.nextLine().toLowerCase();
        int dayIndex = -1;
        switch (day) {
            case "friday" -> dayIndex = 0;
            case "saturday" -> dayIndex = 1;
            case "sunday" -> dayIndex = 2;
        }

        // if invalid day is given
        if (dayIndex == -1) {
            System.out.println("Error Occurred: Invalid Day Given. Shutting down...");
            System.exit(-1);
        }

        // get customer name
        System.out.println("Please enter your name:");
        String name = userInput.nextLine();

        // rent out house
        boolean success = houses.get(houseToRent - 1).reserve(name, dayIndex);

        // check if house rental was successful
        if (success) {
            System.out.println("House successfully rented!");

            // try exceptions
            try {
                writeHousesToFile(houses);
                writeHouseStatus(houses);
            } catch (IOException e) {
                System.out.println("Error Occurred. File reset required.");
            }
        } else {
            System.out.println("House unsuccessfully rented. No changes were made.");
        }
    }

    // method to initialize houses if house file is not found
    public static List<House> initHouses() {
        // initialize list and add houses
        List<House> houses = new ArrayList<>();
        houses.add(new House(3, 3));
        houses.add(new House(1, 1));

        // return new list
        return houses;
    }

    // write house status to file
    public static void writeHouseStatus(List<House> houses) throws IOException {
        try (PrintWriter prntwrtr = new PrintWriter(new FileWriter(HOUSE_STATUS_FILENAME))) {
            for (int i = 0; i < houses.size(); i++) {
                prntwrtr.println("House " + (i + 1) + ": ");
                prntwrtr.println(Arrays.toString(houses.get(i).getPrices()));
                prntwrtr.println(Arrays.toString(houses.get(i).getTenants()));
                prntwrtr.println();
            }
        }
    }

    // method to write houses to file
    public static void writeHousesToFile(List<House> houses) throws IOException {
        // try-with-resources with object output stream
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HOUSE_FILENAME))) {
            for (House h : houses) {
                oos.writeObject(h);
            }
        }
    }

    // method to read houses from file
    public static List<House> readHousesFromFile() throws IOException {
        // initialize list to read houses into
        List<House> houses = new ArrayList<>();

        // read in houses
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(HOUSE_FILENAME))) {
            Object o;
            while ((o = ois.readObject()) != null) {
                houses.add((House)o);
            }
        } catch (ClassNotFoundException ignored) {}

        // return list
        return houses;
    }

}
