package zcole2.iit.cs201;

import zcole2.iit.cs201.engine.House;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    /**
     * Main Client Class
     * v11.19.2020
     * @author zak-cole
     * @author Khushin30
     */

    public static void main(String[] args) {
        // initialize Scanner (this is only for the console version)
        Scanner userInput = new Scanner(System.in);
        List<House> houses = new ArrayList<>();
    }

    // method to hold code for when customer side is running
    public static void managementSide() {
        // file containing updated prices
        // order of updated prices must be the same as the order of houses stored in house storage file
        // prices will be in double format
        File prices = new File("updated_prices.txt");
    }

}
