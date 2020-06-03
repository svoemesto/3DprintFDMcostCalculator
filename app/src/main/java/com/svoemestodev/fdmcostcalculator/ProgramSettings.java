package com.svoemestodev.fdmcostcalculator;

import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProgramSettings implements Serializable {
    private UUID uuid = UUID.randomUUID();
    private String name = "Default Settings";   // название
    private float electricityCost = 4.47f;      // цена электничества кВт/ч
    private float printerCost = 130_000.00f;    // цена принтера
    private int printerPower = 200;            // мощность принтера, Вт
    private int depreciationYears = 3;          // за сколько лет произойдет амортизация
    private float finalCostToGram = 10.00f;     // цена за 1 грамм готового изделия

    transient public static String pathToFile;

    public ProgramSettings(String name, float electricityCost, float printerCost, int printerPower, int depreciationYears, float finalCostToGram) {

        this.name = name;
        this.electricityCost = electricityCost;
        this.printerCost = printerCost;
        this.printerPower = printerPower;
        this.depreciationYears = depreciationYears;
        this.finalCostToGram = finalCostToGram;
    }

    public ProgramSettings() {
    }


    public float getCostOneHourElectricity() {
        return electricityCost * ((float)printerPower / 1000);
    }

    public float getCostOneHourDepreciation() {
        return printerCost / (365*24*depreciationYears);
    }

    public static List<ProgramSettings> getDefaultList() {

        List<ProgramSettings> list = new ArrayList<>();

        ProgramSettings programSetting = new ProgramSettings();

        list.add(programSetting);

        return list;
    }


    public void save() {
        List<ProgramSettings> list = loadList();
        List<ProgramSettings> listNew = new ArrayList<>();
        boolean isFind = false;
        for (ProgramSettings programSettings : list) {
            if (programSettings.getUuid().equals(this.getUuid())) {
                isFind = true;
                listNew.add(this);
            } else {
                listNew.add(programSettings);
            }
        }
        if (!isFind) listNew.add(this);
        saveList(listNew);
    }

    public void delete() {
        List<ProgramSettings> list = loadList();
        List<ProgramSettings> listNew = new ArrayList<>();
        for (ProgramSettings programSettings : list) {
            if (!programSettings.getUuid().equals(this.getUuid())) {
                listNew.add(programSettings);
            }
        }
        saveList(listNew);
    }

    public static List<ProgramSettings> loadList() {
        List<ProgramSettings> list = new ArrayList<>();
        File file = new File(pathToFile);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            list = (List<ProgramSettings>) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            Log.e("ProgramSettings", "loadList. Ошибка десериализации. Возвращаем список по-умолчанию.");
            list = getDefaultList();
        }
        return list;
    }

    public static boolean saveList(List<ProgramSettings> list) {
        File file = new File(pathToFile);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(list);
            oos.close();
            return true;
        } catch (IOException e) {
            Log.e("ProgramSettings", "saveList. Ошибка сериализации");
            return false;
        }
    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getElectricityCost() {
        return electricityCost;
    }

    public void setElectricityCost(float electricityCost) {
        this.electricityCost = electricityCost;
    }

    public float getPrinterCost() {
        return printerCost;
    }

    public void setPrinterCost(float printerCost) {
        this.printerCost = printerCost;
    }

    public int getPrinterPower() {
        return printerPower;
    }

    public void setPrinterPower(int printerPower) {
        this.printerPower = printerPower;
    }

    public int getDepreciationYears() {
        return depreciationYears;
    }

    public void setDepreciationYears(int depreciationYears) {
        this.depreciationYears = depreciationYears;
    }

    public float getFinalCostToGram() {
        return finalCostToGram;
    }

    public void setFinalCostToGram(float finalCostToGram) {
        this.finalCostToGram = finalCostToGram;
    }
}
