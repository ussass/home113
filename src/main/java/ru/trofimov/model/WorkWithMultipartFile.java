package ru.trofimov.model;

import org.springframework.web.multipart.MultipartFile;
import ru.trofimov.config.AppConfig;

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
    private String directory = AppConfig.getDirectory();
    private ArrayList<String> list = new ArrayList<>();
    private String[] resultPhotoName;

    public WorkWithMultipartFile(MultipartFile[] multipartFile, String recipeName) {
        this.multipartFile = multipartFile;
        this.recipeName = Crutch.toTranscript(recipeName);
    }

    public WorkWithMultipartFile() {
    }

    public void setResultPhotoName(String[] resultPhotoName) {
        this.resultPhotoName = resultPhotoName;
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

    public String saveFilesString(boolean forSteps){
        String[] strings = saveFiles(forSteps);
        StringBuilder builder = new StringBuilder();
        for (String value : strings) {
            builder.append(value).append("&*&");
        }
        return builder.toString();
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

    public void moveImg(int id){
        privateMoveImg(id, resultPhotoName);
    }

    public void moveImg(int id, Step[] steps){
        String[] photoName = new String[steps.length];
        for (int i = 0; i < steps.length; i ++)
            photoName[i] = steps[i].getPathToImage();
        privateMoveImg(id, photoName);
    }
    public void moveImg(int id, String steps){
        String[] photoName = steps.split("&\\*&");
        for (int i = 0; i < photoName.length; i++)
            photoName[i] = photoName[i].split("&%&")[1];
        privateMoveImg(id, photoName);
    }

    private void privateMoveImg(int id, String[] photoName){

        System.out.println("moveImg.id: " + id);
        for (String x : photoName)
            System.out.println("moveImg.x: " + x);

        File destFile = new File(directory + id);
        destFile.mkdir();
        File destFileMini = new File(directory + id + "/mini");
        destFileMini.mkdir();

        for (int i = 0; i < photoName.length; i ++){
            try{
                File src = new File(directory + "temp/" + photoName[i]);
                File target = new File(directory + id + "/" + photoName[i]);
                Files.move(src.toPath(), target.toPath());

                String format = photoName[i].split("\\.")[photoName[i].split("\\.").length-1];
                if (target.length() > 0){
                    resizeImg(target, new File(directory + id + "/mini/" + photoName[i]), 150, format);
                }
                else target.delete();

            }catch (IOException e){
                System.out.println("Exception: " + e);
            }
        }
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
