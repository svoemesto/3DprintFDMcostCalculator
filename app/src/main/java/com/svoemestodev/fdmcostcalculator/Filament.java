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

public class Filament implements Serializable {

    private UUID uuid = UUID.randomUUID();
    private String name;
    private String color;
    private Plastic plastic;
    private float diameter = 1.75f;
    private float spoolWeight = 0.750f;
    private float spoolCost = 1790.00f;

    transient public static String pathToFile;

    public Filament(String name, String color, Plastic plastic, float diameter, float spoolWeight, float spoolCost) {
        this.name = name;                   // название
        this.color = color;                 // цвет
        this.plastic = plastic;             // пластик
        this.diameter = diameter;           // диаметр нити, мм
        this.spoolWeight = spoolWeight;     // вес катушки, кг
        this.spoolCost = spoolCost;         // цена катушки
    }

    public Filament() {
    }

    public float getSpoolLength() {

        float result = 0;
        float density = 0.0f;
        if (plastic != null) {
            density = plastic.getDensity(); // плотность, г/см3
        }
        float volOneMm = (float) (Math.PI * (diameter / 2) * (diameter / 2 )); // объем 1 мм нити, мм3
        float weightOneMm = volOneMm * density / 1000;                          // вес 1 мм нити, г
        result = ((spoolWeight * 1000) / weightOneMm) / 1000;
        return result;
    }

    public float getWeightOneMeter() {
        if (getSpoolLength() == 0) {
            return 0.0f;
        } else {
            return spoolWeight * 1000 / getSpoolLength();
        }
    }

    public float getCostOneMeter() {
        if (getSpoolLength() == 0) {
            return 0.0f;
        } else {
            return spoolCost / getSpoolLength();
        }
    }

    public float getCostOneKilogram() {
        return spoolCost / spoolWeight;
    }

    public static List<Filament> getDefaultList() {

        List<Filament> list = new ArrayList<>();

        Filament filament = new Filament("PLA White", "White", new Plastic("PLA", 1.24f),1.75f,0.750f, 1790.00f);

        list.add(filament);

        return list;
    }

    public void save() {
        List<Filament> list = loadList();
        List<Filament> listNew = new ArrayList<>();
        boolean isFind = false;
        for (Filament filament : list) {
            if (filament.getUuid().toString().equals(this.getUuid().toString())) {
                isFind = true;
                listNew.add(this);
            } else {
                listNew.add(filament);
            }
        }
        if (!isFind) listNew.add(this);
        saveList(listNew);
    }

    public void delete() {
        List<Filament> list = loadList();
        List<Filament> listNew = new ArrayList<>();
        for (Filament filament : list) {
            if (!filament.getUuid().toString().equals(this.getUuid().toString())) {
                listNew.add(filament);
            }
        }
        saveList(listNew);
    }


    public static List<Filament> loadList() {
        List<Filament> list = new ArrayList<>();
        File file = new File(pathToFile);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            list = (List<Filament>) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            Log.e("Filament", "loadList. Ошибка десериализации. Возвращаем список по-умолчанию.");
            list = getDefaultList();
        }
        return list;
    }

    public static boolean saveList(List<Filament> list) {
        File file = new File(pathToFile);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(list);
            oos.close();
            return true;
        } catch (IOException e) {
            Log.e("Filament", "saveList. Ошибка сериализации");
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Plastic getPlastic() {
        return plastic;
    }

    public void setPlastic(Plastic plastic) {
        this.plastic = plastic;
    }

    public float getDiameter() {
        return diameter;
    }

    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }

    public float getSpoolWeight() {
        return spoolWeight;
    }

    public void setSpoolWeight(float spoolWeight) {
        this.spoolWeight = spoolWeight;
    }

    public float getSpoolCost() {
        return spoolCost;
    }

    public void setSpoolCost(float spoolCost) {
        this.spoolCost = spoolCost;
    }
}
