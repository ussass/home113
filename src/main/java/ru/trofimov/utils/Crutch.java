package ru.trofimov.utils;

import com.ibm.icu.text.Transliterator;
import org.springframework.web.multipart.MultipartFile;
import ru.trofimov.config.AppConfig;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Crutch {

    public static String toUTF8(String string) throws UnsupportedEncodingException {
        return new String(string.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    public static String toTranscript(String ruText) {
        Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");
        return toLatinTrans.transliterate(ruText).replaceAll(" ", "-");
    }

    public static int categoryStringToInt(String category) {
        switch (category) {
            case "breakfast":
                return 1;
            case "soups":
                return 2;
            case "main-dishes":
                return 3;
            case "baking-and-Desserts":
                return 4;
            case "sandwiches":
                return 5;
            case "pasta-and-Pizza":
                return 6;
            case "salads":
                return 7;
            case "sauces":
                return 8;
            case "beverages":
                return 9;
            default:
                return 0;
        }
    }

    public static void recursiveDelete(File file) {
        if (!file.exists())
            return;

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                recursiveDelete(f);
            }
        }
        file.delete();
    }

    public static String[] removStepeImage(String[] oldImage, MultipartFile[] newImage, int[] delStepPhoto, int id, String newName, boolean forStep, int stepLength) {
        String oldImageString = "";
        for (String s : oldImage) {
            oldImageString += s;
            oldImageString += "!%!";
        }
        return removeImage(oldImageString, newImage, delStepPhoto, id, newName, forStep, stepLength);
    }

    public static String[] removeImage(String oldImage, MultipartFile[] newImage, int[] delMainPhoto, int id, String newName, boolean forStep, int stepLength) {
        String[] oldImagNames = oldImage.split("!%!");
        if (oldImagNames.length == 0){
            String[] notImg = new String[stepLength];
            Arrays.fill(notImg, "0");
            return notImg;
        }

        int count;
        if (Objects.equals(newImage[0].getResource().getFilename(), "") && !forStep) count = 0;
        else {
            count = 0;
            for (MultipartFile file : newImage) {
                if (!Objects.equals(file.getResource().getFilename(), "")) count++;
            }
        }

        List<String> list = new ArrayList<>();
        List<String> listDel = new ArrayList<>();

        for (int i = 0; i < oldImagNames.length; i++) {
            if (delMainPhoto[i] == 0) list.add(oldImagNames[i]);
            else listDel.add(oldImagNames[i]);
        }

        String directory = AppConfig.getDirectory();
        newName = Crutch.toTranscript(newName);
        String[] result = new String[list.size()];


        for (int i = 0; i < list.size(); i++) {
            try {
                File src = new File(directory + id + "/" + list.get(i));

                String format = "." + oldImagNames[i].split("\\.")[oldImagNames[i].split("\\.").length - 1];
                String name = newName;
                if (forStep) name = "St&" + name;
                File target = new File(directory + "temp/" + name + "-" + (i + count) + format);
                result[i] = name + "-" + (i + count) + format;

                Files.move(src.toPath(), target.toPath());

            } catch (IOException e) {
                System.out.println("Exception: " + e);
            }
        }

        if (!forStep) return result;
        else {
            String[] stepResult = new String[stepLength];
            for (int i = 0, j = 0; i < stepResult.length; i++) {
                if (result.length == 0) {
                    stepResult[i] = "0";
                    continue;
                }
                String lastPart = list.get(j).split("-")[list.get(j).split("-").length - 1];
                if (lastPart.equals("")) {
                    stepResult[i] = "0";
                    continue;
                }
                String lastButOne = list.get(j).split("\\.")[list.get(j).split("\\.").length - 2];
                int num = Integer.parseInt(lastButOne.split("-")[lastButOne.split("-").length - 1]);  //NumberFormatException
                if (num == i) {
                    stepResult[i] = result[j];
                    if (j < result.length - 1) j++;
                } else stepResult[i] = "0";

            }
            System.out.println(26);
            String[] tempName = new String[stepResult.length];
            for (int i = 0; i < stepResult.length; i++) {
                if (stepResult[i].equals("0")) continue;
                String format = "." + stepResult[i].split("\\.")[stepResult[i].split("\\.").length - 1];
                File oldfile = new File(directory + "temp/" + stepResult[i]);
                File newfile = new File(directory + "temp/-" + i + format);
                oldfile.renameTo(newfile);
                tempName[i] = "-" + i + format;
            }
            System.out.println(27);

            for (int i = 0; i < tempName.length; i++) {
                if (stepResult[i].equals("0")) continue;
                File oldfile = new File(directory + "temp/" + tempName[i]);
                File newfile = new File(directory + "temp/St&" + newName + tempName[i]);
                oldfile.renameTo(newfile);
                stepResult[i] = "St&" + newName + tempName[i];
            }


            return stepResult;
        }
    }

    public static String[] twoArraysIntoOne(String[] one, String[] two) {
        String[] result = new String[one.length + two.length];

        int count = 0;
        for (int i = 0; i < one.length; i++) {
            result[i] = one[i];
            count++;
        }
        for (int j = 0; j < two.length; j++) {
            result[count++] = two[j];
        }
        return result;
    }

    public static String[] twoArraysIntoOne2(String[] one, String[] big) {

        for (String x: one)
            System.out.println("one: " + x);
        for (String x : big)
            System.out.println("big: " + x);


        for (int i = 0; i < big.length; i++) {
            if (big[i].equals("0") && !one[i].split("\\.")[one[i].split("\\.").length - 1].equals("octet-stream"))
                big[i] = one[i];
        }
        return big;
    }

    public static void deleteAllFilesFolder(int id) {
        String path;
        if (id == -1) path = AppConfig.getDirectory() + "temp";
        else path = AppConfig.getDirectory() + id;

        System.out.println("path: " + path);
        try{
            for (File myFile : new File(path).listFiles()) {
                if (myFile.isFile()) myFile.delete();
                if (myFile.isDirectory())
                    for (File myFileInside : new File(myFile.getAbsolutePath()).listFiles())
                        if (myFileInside.isFile()) myFileInside.delete();
            }
        }catch (Exception e){
            System.out.println("Exception: " + e.toString());
        }
    }
}
