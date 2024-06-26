package core;

import business.FacilityManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CheckBoxPanel extends JPanel {
    FacilityManager facilityManager;
    public CheckBoxPanel() {
        this.facilityManager = new FacilityManager();
        setLayout(new GridLayout(0, 1));
        getCheckBoxValuesFromDatabase();
    }

    private void getCheckBoxValuesFromDatabase() {
        ArrayList<String> facilityNames = facilityManager.getNamesFromDatabase();
        for (String facilityName : facilityNames) {
            JCheckBox checkBox = new JCheckBox(facilityName);
            add(checkBox);

        }
    }
}