package com.epam.ilia_solovev.java.lesson3.task1.home_appliances;

import com.epam.ilia_solovev.java.lesson3.task1.exceptions.checked.ApplianceException;
import com.epam.ilia_solovev.java.lesson3.task1.utils.Brand;

public class TV extends HomeAppliances implements Connectible {

    private int screenSize;
    private int defaultScreenSize = 32;

    public TV(int powerConsumption, Brand brand, String model, int screenSize) throws ApplianceException {
        super(powerConsumption, brand, model);

        if (screenSize <= 0) {
            try {
                throw new ApplianceException();
            } finally {
                this.screenSize = defaultScreenSize;
            }
        } else {
            this.screenSize = screenSize;
        }
    }

    @Override
    public void doWork() {
        if (this.isOn()) {
            System.out.println("I'am a TV - Showing you Game of Thrones. It is so nice to watch it on a " + screenSize +
                    " inches screen, isn't?");
        } else {
            try {
                throw new ApplianceException();
            } catch (ApplianceException e) {
                e.turnMeOnException(this);
            } finally {
                this.turnOn();
            }
        }
    }

    @Override
    public void connectToWiFi() {
        System.out.println("The TV is connecting to the WiFi...");
    }
}