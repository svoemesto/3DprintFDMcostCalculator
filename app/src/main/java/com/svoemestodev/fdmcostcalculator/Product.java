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

public class Product implements Serializable {
    private UUID uuid = UUID.randomUUID();
    private String name;
    private List<Part> parts = new ArrayList<>();

    transient public static String pathToFile;

    public Product(String name, List<Part> parts) {
        this.name = name;
        this.parts = parts;
    }

    public Product() {
    }

    public int getPartsCount() {
        return parts.size();
    }

    public int getTimeSeconds() {
        int result = 0;
        for (Part part : parts) {
            result += part.getTimeSeconds();
        }
        return result;
    }

    public String getTimeString() {
        return Utils.convertSecondsToHHMM(getTimeSeconds());
    }

    public float getWeight() {
        float result = 0.0f;
        for (Part part : parts) {
            result += part.getWeight();
        }
        return result;
    }

    public float getElectricityCost() {
        float result = 0.0f;
        for (Part part : parts) {
            result += part.getElectricityCost();
        }
        return result;
    }

    public float getDepreciationCost() {
        float result = 0.0f;
        for (Part part : parts) {
            result += part.getDepreciationCost();
        }
        return result;
    }

    public float getFilamentCost() {
        float result = 0.0f;
        for (Part part : parts) {
            result += part.getFilamentCost();
        }
        return result;
    }

    public float getNetCost() {
        float result = 0.0f;
        for (Part part : parts) {
            result += part.getNetCost();
        }
        return result;
    }

    public float getTotalCost() {
        float result = 0.0f;
        for (Part part : parts) {
            result += part.getTotalCost();
        }
        return result;
    }

    public float getProfitCost() {
        float result = 0.0f;
        for (Part part : parts) {
            result += part.getProfitCost();
        }
        return result;
    }

    public static Product getItemByName(String name) {
        Product result = null;
        for (Product product: loadList()) {
            if (product.getName().equals(name)) {
                result = product;
                break;
            }
        }
        return result;
    }

    public void save() {
        List<Product> list = loadList();
        List<Product> listNew = new ArrayList<>();
        boolean isFind = false;
        for (Product product : list) {
            if (product.getUuid().equals(this.getUuid())) {
                isFind = true;
                listNew.add(this);
            } else {
                listNew.add(product);
            }
        }
        if (!isFind) listNew.add(this);
        saveList(listNew);
    }

    public void delete() {
        List<Product> list = loadList();
        List<Product> listNew = new ArrayList<>();
        for (Product product : list) {
            if (!product.getUuid().equals(this.getUuid())) {
                listNew.add(product);
            }
        }
        saveList(listNew);
    }

    public static List<Product> loadList() {
        List<Product> list = new ArrayList<>();
        File file = new File(pathToFile);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            list = (List<Product>) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            Log.e("Product", "loadList. Ошибка десериализации. Возвращаем список по-умолчанию.");
//            list = getDefaultList();
        }
        return list;
    }

    public static boolean saveList(List<Product> list) {
        File file = new File(pathToFile);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(list);
            oos.close();
            return true;
        } catch (IOException e) {
            Log.e("Product", "saveList. Ошибка сериализации");
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

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
