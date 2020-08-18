package ru.trofimov.model;

import com.ibm.icu.text.Transliterator;
import org.springframework.web.multipart.MultipartFile;
import ru.trofimov.config.AppConfig;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Crutch {

    public static String toUTF8(String string) throws UnsupportedEncodingException {
        return new String(string.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    public static String toTranscript(String ruText){
        Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");
        return toLatinTrans.transliterate(ruText).replaceAll(" ", "-");
    }

    public static int categoryStringToInt (String category){
        switch (category){
            case "breakfast": return 1;
            case "soups": return 2;
            case "main-dishes": return 3;
            case "baking-and-Desserts": return 4;
            case "sandwiches": return 5;
            case "pasta-and-Pizza": return 6;
            case "salads": return 7;
            case "sauces": return 8;
            case "beverages": return 9;
            default: return  0;
        }
    }

    public static void recursiveDelete(File file) {
        // до конца рекурсивного цикла
        if (!file.exists())
            return;

        //если это папка, то идем внутрь этой папки и вызываем рекурсивное удаление всего, что там есть
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                // рекурсивный вызов
                recursiveDelete(f);
            }
        }
        // вызываем метод delete() для удаления файлов и пустых(!) папок
        file.delete();
    }

    public static String[] removeImage(String oldImage, MultipartFile[] newImage, int[] delMainPhoto, int id, String newName){
        String[] oldImagNames = oldImage.split("!%!");
        if (oldImagNames.length == 0) return new String[]{};

        int count = newImage.length;
        if (Objects.equals(newImage[0].getResource().getFilename(), "")) count = 0;

        List<String> list = new ArrayList<>();
        List<String> listDel = new ArrayList<>();

        for (int i = 0; i < oldImagNames.length; i++){
            if (delMainPhoto[i] == 0) list.add(oldImagNames[i]);
            else listDel.add(oldImagNames[i]);
        }

        String directory = AppConfig.getDirectory();
        newName = Crutch.toTranscript(newName);
        String[] result = new String[list.size()];


        for (int i = 0; i < list.size(); i ++){
            try{
                File src = new File(directory + id + "/" + list.get(i));

                String format = "." + oldImagNames[i].split("\\.")[oldImagNames[i].split("\\.").length-1];
                File target = new File(directory + "temp/" + newName + "-" + (i + count) + format);
                result[i] = newName + "-" + (i + count) + format;

                Files.move(src.toPath(), target.toPath());

            }catch (IOException e){
                System.out.println("Exception: " + e);
                return new String[]{};
            }
        }

        return result;
    }

    public static String[] twoArraysIntoOne (String[] one, String[] two){
        String[] result = new String[one.length + two.length];

        int count = 0;
        for(int i = 0; i<one.length; i++) {
            result[i] = one[i];
            count++;
        }
        for(int j = 0;j<two.length;j++) {
            result[count++] = two[j];
        }
        return result;
    }
}
