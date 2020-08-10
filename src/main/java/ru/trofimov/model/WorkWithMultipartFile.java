package ru.trofimov.model;

import com.ibm.icu.text.Transliterator;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class WorkWithMultipartFile {

    private MultipartFile[] multipartFile;
    private String recipeName;
    private String directory = "D:/Java Project/NewProjects/home113/target/home113/upload/";
    private ArrayList<String> list = new ArrayList<>();
    private String[] resultPhotoName;

    public WorkWithMultipartFile(MultipartFile[] multipartFile, String recipeName) {
        this.multipartFile = multipartFile;
        this.recipeName = Crutch.toTranscript(recipeName);
    }

    public String[] saveFiles(boolean forSteps){
        for (int i = 0; i < multipartFile.length; i++){
            if (multipartFile[i].getSize() == 0 && !forSteps) continue;
            try {
                saveFile(i, forSteps);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String[] result = new String[list.size()];
        this.resultPhotoName = list.toArray(result);
        return this.resultPhotoName;
    }

    private void saveFile(int count, boolean forSteps) throws IOException {
        String imageName = changeName(count);
        if (forSteps) imageName = "St&" + imageName;
        File file = new File(directory + "temp/" + imageName);
        multipartFile[count].transferTo(file);
        list.add(imageName);
    }

    private String changeName(int count){
        return recipeName + "-" + count + "." + multipartFile[count].getContentType().split("/")[1];
    }



    public boolean moveImg(int id){

        System.out.println("moveImg.id: " + id);
        for (String x : resultPhotoName)
            System.out.println("moveImg.x: " + x);

        File destFile = new File(directory + id);
        destFile.mkdir();
        File destFileMini = new File(directory + id + "/mini");
        destFileMini.mkdir();

        for (int i = 0; i < resultPhotoName.length; i ++){
            try{
                File src = new File(directory + "temp/" + resultPhotoName[i]);
                File target = new File(directory + id + "/" + resultPhotoName[i]);
                Files.move(src.toPath(), target.toPath());

                String format = resultPhotoName[i].split("\\.")[resultPhotoName[i].split("\\.").length-1];
                resizeImg(target, new File(directory + id + "/mini/" + resultPhotoName[i]), 150, format);

            }catch (IOException e){
                System.out.println("Exception: " + e);
                return false;
            }
        }
        return true;
    }

    private void resizeImg(File originalImg, File resizeImg, int height, String format){
        try {
            BufferedImage original = ImageIO.read(originalImg);
            int width = original.getWidth() * height / original.getHeight();
            BufferedImage resized = new BufferedImage(width, height, original.getType());
            Graphics2D g2 = resized.createGraphics();
            g2.drawImage(original, 0, 0 ,width, height, null);
            g2.dispose();
            ImageIO.write(resized, format, resizeImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
