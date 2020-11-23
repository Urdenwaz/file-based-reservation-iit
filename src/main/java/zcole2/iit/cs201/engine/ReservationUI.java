package zcole2.iit.cs201.engine;

import zcole2.iit.cs201.Main;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI for File-Based Reservation Project
 * @author zak-cole
 * @author Khushin30
 * @version 11.22.2020
 */

public class ReservationUI {

    // jframe for putting things in
    private JFrame frame;

    // panels
    private JPanel chooseHousePanel;
    private JPanel chooseDayPanel;
    private JPanel checkoutPanel;
    private JPanel confirmationPanel;

    // house fields
    private List<House> houses;
    private int chosenHouseIndex;

    // other fields
    private JTextField nameField;

    // constants
    private static final String[] DAYS = { "Friday", "Saturday", "Sunday" };

    // constructor
    public ReservationUI(List<House> houses) {
        // init frame
        frame = new JFrame();
        frame.setBackground(Color.LIGHT_GRAY);

        // read in houses
        this.houses = houses;

        // set initial frame
        setHousingPanel();

        // change frame properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("File-Based Reservation IIT");
        frame.pack();
        frame.setVisible(true);
    }

    // set panel for choosing house
    public void setHousingPanel() {
        // init panel
        chooseHousePanel = new JPanel();

        // init labels
        List<JLabel> labels = new ArrayList<>();
        labels.add(new JLabel("Which house would you like to rent?"));
        for (int i = 0; i < houses.size(); i++) {
            String message = "House " + (i + 1) + " has " + houses.get(i).getBedroomCount() + " bedrooms and "
                    + houses.get(i).getBathroomCount() + " bathrooms.";
            labels.add(new JLabel(message));
        }

        // init buttons
        JButton button1 = new JButton("House 1");
        for (String tenant : houses.get(0).getTenants()) {
            // make sure house has vacancies
            if (tenant == null) {
                button1.addActionListener(e -> {
                    // update frame
                    frame.remove(chooseHousePanel);
                    frame.repaint();

                    // change panel
                    chosenHouseIndex = 0;
                    setChoicePanel();
                });
                break;
            } else {
                button1.setText("House 1 - Currently Fully Booked");
            }
        }
        JButton button2 = new JButton("House 2");
        for (String tenant : houses.get(1).getTenants()) {
            // make sure house has vacancies
            if (tenant == null) {
                button2.addActionListener(e -> {
                    // update frame
                    frame.remove(chooseHousePanel);
                    frame.repaint();

                    // change panel
                    chosenHouseIndex = 1;
                    setChoicePanel();
                });
                break;
            } else {
                button2.setText("House 2 - Currently Fully Booked");
            }
        }


        // init chooseHousePanel
        chooseHousePanel = new JPanel();
        chooseHousePanel.setBorder(BorderFactory.createEmptyBorder(120, 120, 120, 120));
        chooseHousePanel.setLayout(new GridLayout(0, 1));
        labels.forEach(label -> chooseHousePanel.add(label));

        chooseHousePanel.add(new JLabel());
        chooseHousePanel.add(button1);
        chooseHousePanel.add(new JLabel());
        chooseHousePanel.add(button2);

        // add chooseHousePanel to frame
        frame.add(chooseHousePanel, BorderLayout.CENTER);
    }

    // panel for choosing day
    private void setChoicePanel() {
        // init new panel
        chooseDayPanel = new JPanel();
        chooseDayPanel.setBorder(BorderFactory.createEmptyBorder(120, 120, 120, 120));
        chooseDayPanel.setLayout(new GridLayout(0, 1));

        // add label for choosing day
        chooseDayPanel.add(new JLabel("Which day would you like to rent the house?"));
        chooseDayPanel.add(new JLabel());

        // add buttons for each day
        List<JButton> dayButtons = new ArrayList<>();
        String[] temp = houses.get(chosenHouseIndex).getTenants();

        // initialize buttons
        JButton fridayButton = null;
        JButton saturdayButton = null;
        JButton sundayButton = null;

        // add buttons based on availability
        if (temp[0] == null) { // friday is available
            fridayButton = new JButton(DAYS[0] + " - $" + houses.get(chosenHouseIndex).getPrices()[0]);
            fridayButton.addActionListener(e -> {
                // update frame
                frame.remove(chooseDayPanel);
                frame.repaint();

                // change panel
                setCheckoutPanel(0);
            });
        }
        if (temp[1] == null) { // saturday is available
            saturdayButton = new JButton(DAYS[1] + " - $" + houses.get(chosenHouseIndex).getPrices()[1]);
            saturdayButton.addActionListener(e -> {
                // update frame
                frame.remove(chooseDayPanel);
                frame.repaint();

                // change panel
                setCheckoutPanel(1);
            });
        }
        if (temp[2] == null) { // sunday is available
            sundayButton = new JButton(DAYS[2] + " - $" + houses.get(chosenHouseIndex).getPrices()[2]);
            sundayButton.addActionListener(e -> {
                // update frame
                frame.remove(chooseDayPanel);
                frame.repaint();

                // change panel
                setCheckoutPanel(2);
            });
        }

        // add buttons to panel
        if (fridayButton != null) chooseDayPanel.add(fridayButton); chooseDayPanel.add(new JLabel());
        if (saturdayButton != null) chooseDayPanel.add(saturdayButton); chooseDayPanel.add(new JLabel());
        if (sundayButton != null) chooseDayPanel.add(sundayButton); chooseDayPanel.add(new JLabel());

        // add panel to frame
        frame.add(chooseDayPanel);
        frame.pack();
    }

    // method for checkout panel
    public void setCheckoutPanel(int dayIndex) {
        // init panel
        checkoutPanel = new JPanel();
        checkoutPanel.setBorder(BorderFactory.createEmptyBorder(120, 120, 120, 120));
        checkoutPanel.setLayout(new GridLayout(0, 1));

        // add label
        checkoutPanel.add(new JLabel("Please enter your name."));
        checkoutPanel.add(new JLabel());

        // add text box for name
        nameField = new JTextField();
        nameField.setToolTipText("Name");
        checkoutPanel.add(nameField);
        checkoutPanel.add(new JLabel());

        // add button for confirmation
        JButton button = new JButton("Confirm Reservation");
        button.addActionListener(e -> {
            // update frame
            frame.remove(checkoutPanel);
            frame.repaint();

            // change panel
            confirmReservation(nameField.getText(), dayIndex);
        });
        checkoutPanel.add(button);

        // add panel to frame
        frame.add(checkoutPanel);
        frame.pack();
    }

    // confirmation page
    public void confirmReservation(String name, int dayIndex) {
        // init panel
        confirmationPanel = new JPanel();
        confirmationPanel.setBorder(BorderFactory.createEmptyBorder(120, 120, 120, 120));
        confirmationPanel.setLayout(new GridLayout(0, 1));

        // rent out house
        boolean success = houses.get(chosenHouseIndex).reserve(name, dayIndex);

        // check if rental was successful
        if (success) {
            JLabel successLabel = new JLabel("Rental Successful!");
            JLabel houseLabel = new JLabel("You rented the house with " +
                    houses.get(chosenHouseIndex).getBedroomCount() + " bedrooms and " +
                    houses.get(chosenHouseIndex).getBathroomCount() + " bathrooms.");
            confirmationPanel.add(successLabel);
            confirmationPanel.add(houseLabel);

            // try exceptions
            try {
                Main.writeHousesToFile(houses);
                Main.writeHouseStatus(houses);
            } catch (IOException e) {
                System.out.println("Error Occurred. File reset required.");
            }
        } else {
            // rental is unsuccessful
            JLabel label = new JLabel("House unsuccessfully rented. No changes were made.");
            confirmationPanel.add(label);
        }

        // add end button
        JButton button = new JButton("Click me to exit!");
        button.addActionListener(e -> System.exit(0));
        confirmationPanel.add(button);

        // add panel to frame
        frame.add(confirmationPanel);
        frame.pack();
    }

}
