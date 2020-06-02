package com.svoemestodev.fdmcostcalculator;


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

public class Part implements Serializable {

    private UUID uuid = UUID.randomUUID();
    private String name;
    private Filament filament;
    private ProgramSettings programSettings;
    private int printHours;
    private int printMinutes;
    private int filamentLength;

    transient public static String pathToFile;

    public Part(String name, Filament filament, ProgramSettings programSettings, int printHours, int printMinutes, int filamentLength) {
        this.name = name;
        this.filament = filament;
        this.programSettings = programSettings;
        this.printHours = printHours;
        this.printMinutes = printMinutes;
        this.filamentLength = filamentLength;
    }

    public Part() {
    }

    public int getTimeSeconds() {
        return this.printHours*60*60 + this.printMinutes*60;
    }

    public String getTimeString() {
        return Utils.convertSecondsToHHMM(getTimeSeconds());
    }

    public float getWeight() {
        if (filament != null) {
            return filamentLength * filament.getWeightOneMeter() / 1000;
        } else {
            return 0.0f;
        }

    }

    public float getElectricityCost() {
        if (programSettings != null) {
            return getTimeSeconds() * programSettings.getCostOneHourElectricity() / 3600;
        } else {
            return 0.0f;
        }
    }

    public float getDepreciationCost() {
        if (programSettings != null) {
            return getTimeSeconds() * programSettings.getCostOneHourDepreciation() / 3600;
        } else {
            return 0.0f;
        }
    }

    public float getFilamentCost() {
        if (filament != null) {
            return getWeight() * filament.getCostOneKilogram() / 1000;
        } else {
            return 0.0f;
        }
    }

    public float getNetCost() {
        return getElectricityCost() + getDepreciationCost() + getFilamentCost();
    }

    public float getTotalCost() {
        if (programSettings != null) {
            return getWeight() * programSettings.getFinalCostToGram();
        } else {
            return 0.0f;
        }
    }

    public float getProfitCost() {
        return getTotalCost() - getNetCost();
    }

    public static Part getItemByName(String name) {
        Part result = null;
        for (Part part: loadList()) {
            if (part.getName().equals(name)) {
                result = part;
                break;
            }
        }
        return result;
    }

    public void save() {
        List<Part> list = loadList();
        List<Part> listNew = new ArrayList<>();
        boolean isFind = false;
        for (Part part : list) {
            if (part.getUuid().toString().equals(this.getUuid().toString())) {
                isFind = true;
                listNew.add(this);
            } else {
                listNew.add(part);
            }
        }
        if (!isFind) listNew.add(this);
        saveList(listNew);
    }

    public void delete() {
        List<Part> list = loadList();
        List<Part> listNew = new ArrayList<>();
        for (Part part : list) {
            if (!part.getUuid().toString().equals(this.getUuid().toString())) {
                listNew.add(part);
            }
        }
        saveList(listNew);
    }

    public static List<Part> loadList() {

        List<Part> list = new ArrayList<>();
        File file = new File(pathToFile);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            list = (List<Part>) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            Log.e("Part", "loadList. Ошибка десериализации. Возвращаем пустой список.");
//            list = getDefaultList();
        }
        return list;
    }

    public static boolean saveList(List<Part> list) {
        File file = new File(pathToFile);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(list);
            oos.close();
            return true;
        } catch (IOException e) {
            Log.e("Part", "saveList. Ошибка сериализации");
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

    public Filament getFilament() {
        return filament;
    }

    public void setFilament(Filament filament) {
        this.filament = filament;
    }

    public ProgramSettings getProgramSettings() {
        return programSettings;
    }

    public void setProgramSettings(ProgramSettings programSettings) {
        this.programSettings = programSettings;
    }

    public int getPrintHours() {
        return printHours;
    }

    public void setPrintHours(int printHours) {
        this.printHours = printHours;
    }

    public int getPrintMinutes() {
        return printMinutes;
    }

    public void setPrintMinutes(int printMinutes) {
        this.printMinutes = printMinutes;
    }

    public int getFilamentLength() {
        return filamentLength;
    }

    public void setFilamentLength(int filamentLength) {
        this.filamentLength = filamentLength;
    }
}
