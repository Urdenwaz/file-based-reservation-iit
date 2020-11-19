package zcole2.iit.cs201.engine;

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
    private String currentTenant;

    // boolean array to represent if house is rented
    // [friday, saturday, sunday]
    private boolean[] isOccupied;

    // constructor with all arguments
    public House(final int bedroomCount, final int bathroomCount, double rentalPrice,
                 String currentTenant) {
        this.bedroomCount = bedroomCount;
        this.bathroomCount = bathroomCount;
        this.rentalPrice = rentalPrice;
        this.currentTenant = currentTenant;
        isOccupied = new boolean[3];
    }

    // constructor
    public House(int bedroomCount, int bathroomCount) {
        this(bedroomCount, bathroomCount, 100, null);
    }

}
