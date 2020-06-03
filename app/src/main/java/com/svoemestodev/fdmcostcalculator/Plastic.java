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

public class Plastic implements Serializable {
    private UUID uuid = UUID.randomUUID();
    private String name;    // название
    private float density;  // плотность, г/см3

    transient public static String pathToFile;

    public Plastic(String name, float density) {
        this.name = name;
        this.density = density;
    }

    public Plastic() {
    }

    public static List<Plastic> getDefaultList() {

        List<Plastic> list = new ArrayList<>();

        list.add(new Plastic("PLA", 1.24f));
        list.add(new Plastic("PLA Pro", 1.24f));
        list.add(new Plastic("ABS", 1.04f));
        list.add(new Plastic("HIPS (polystyrene)", 1.03f));
        list.add(new Plastic("PETG (polyethylene)", 1.27f));
        list.add(new Plastic("PETT (T-Glass)", 1.45f));
        list.add(new Plastic("PP (polypropylene)", 0.90f));
        list.add(new Plastic("PC (polycarbonate)", 1.30f));
        list.add(new Plastic("EP", 1.10f));
        list.add(new Plastic("Timberfill Wood", 1.28f));
        list.add(new Plastic("Taulman Nylon 680 FDA", 1.28f));
        list.add(new Plastic("NylonStrong", 1.52f));
        list.add(new Plastic("BendLay", 1.05f));
        list.add(new Plastic("FilaFlex", 1.21f));
        list.add(new Plastic("Reflect-O-Lay", 2.31f));
        list.add(new Plastic("Proto-Pasta Conductive", 1.15f));
        list.add(new Plastic("Proto-Pasta Carbon Fiber", 1.30f));
        list.add(new Plastic("Proto-Pasta Magnetic", 2.00f));
        list.add(new Plastic("Proto-Pasta Stainless Steel", 2.70f));

        return list;
    }


    public void save() {
        List<Plastic> list = loadList();
        List<Plastic> listNew = new ArrayList<>();
        boolean isFind = false;
        for (Plastic plastic : list) {
            if (plastic.getUuid().toString().equals(this.getUuid().toString())) {
                isFind = true;
                listNew.add(this);
            } else {
                listNew.add(plastic);
            }
        }
        if (!isFind) listNew.add(this);
        saveList(listNew);
    }

    public void delete() {
        List<Plastic> list = loadList();
        List<Plastic> listNew = new ArrayList<>();
        for (Plastic plastic : list) {
            if (!plastic.getUuid().toString().equals(this.getUuid().toString())) {
                listNew.add(plastic);
            }
        }
        saveList(listNew);
    }

    public static List<Plastic> loadList() {
        List<Plastic> list = new ArrayList<>();
        File file = new File(pathToFile);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            list = (List<Plastic>) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            Log.e("Plastic", "loadList. Ошибка десериализации. Возвращаем список по-умолчанию.");
            list = getDefaultList();
        }
        return list;
    }

    public static boolean saveList(List<Plastic> list) {
        File file = new File(pathToFile);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(list);
            oos.close();
            return true;
        } catch (IOException e) {
            Log.e("Plastic", "saveList. Ошибка сериализации");
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

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }
}
