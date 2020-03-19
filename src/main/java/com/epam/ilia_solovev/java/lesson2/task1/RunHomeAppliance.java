/*
 * Ilia Solovev. Task 2-1
 *
 * Спроектировать объектную модель для заданной предметной области.
 * Домашняя техника. Определить иерархию ДТ. Включить некоторые в
 * розетку. Посчитать потребляемую мощность. Провести сортировку
 * приборов в квартире на основе одного из параметров. Найти кухонный
 * прибор в квартире, соответствующий заданному диапазону параметров.
 */

package com.epam.ilia_solovev.java.lesson2.task1;

import com.epam.ilia_solovev.java.lesson2.task1.home_appliances.*;
import com.epam.ilia_solovev.java.lesson2.task1.utils.Brand;
import com.epam.ilia_solovev.java.lesson2.task1.utils.SortArray;

public class RunHomeAppliance {

    public static void main(String[] args) {
        RunHomeAppliance app = new RunHomeAppliance();
        app.startApp();
    }

    //Running an application
    public void startApp() {

        int currentPowerConsumption;
        int powerLessOrEqual = 300;

        HomeAppliances[] homeAppliances = new HomeAppliances[3];
        createArrayOfHomeAppliance(homeAppliances);
        currentPowerConsumption = makeThemWorkAndCountPowerConsumption(homeAppliances);

        // 600 is because the washing machine turned off automatically after finishing its work.
        System.out.println("Current power consumption is : " + currentPowerConsumption);

        showThingsWithPowerLessOrEqualThanParam(homeAppliances, powerLessOrEqual);

        //Lets sort home appliances by power consumption
        SortArray.bubbleSortByPowerConsumption(homeAppliances);

        //And output the result with class names
        System.out.println("Sorted list of home appliances by power consumption (ascending):");
        for (HomeAppliances thing : homeAppliances) {
            System.out.println(thing.getClass().getSimpleName());
        }
    }

    private void createArrayOfHomeAppliance(HomeAppliances[] homeAppliances) {

        TV tv = new TV(200, Brand.Samsung, "UE50NU7097U", 47);
        WashingMachine washingMachine = new WashingMachine(150, Brand.Indesit, "IWUB 4085", 1000);
        Computer computer = new Computer(400, Brand.DELL, "Vostro 3670");

        homeAppliances[0] = tv;
        homeAppliances[1] = washingMachine;
        homeAppliances[2] = computer;
    }

    private int makeThemWorkAndCountPowerConsumption(HomeAppliances[] homeAppliances) {

        int currentPowerConsumption = 0;

        for (HomeAppliances thing : homeAppliances) {
            //Every thing needs to be turned on - otherwise it won't work
            thing.turnOn();
            //Connecting every connectible thing to the WiFi
            thing.doWork();
            if (thing instanceof Connectible) {
                ((Connectible) thing).connectToWiFi();
            }
            //counting current home power consumption
            currentPowerConsumption += thing.getPowerConsumptionWhenOn();
        }
        return currentPowerConsumption;
    }

    private void showThingsWithPowerLessOrEqualThanParam(HomeAppliances[] homeAppliances, int powerLessOrEqualThanThat) {
        //Lets find all home appliances which are turned on and which power less or equal then param
        for (HomeAppliances thing : homeAppliances) {
            if (thing.isOn() && thing.getPowerConsumption() <= powerLessOrEqualThanThat) {
                System.out.println("Home appliance with power less or equal than " + powerLessOrEqualThanThat + " is: "
                        + thing.getClass().getSimpleName() + " " + thing.getBrand() + " " + thing.getModel());
            }
        }
    }

}
