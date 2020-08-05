package ru.trofimov.model;

import com.ibm.icu.text.Transliterator;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class WorkWithMultipartFile {

    public static String getFileNameOfMultipartFile(MultipartFile file, String recipeName){

        return "";
    }

    private MultipartFile[] multipartFile;
    private String recipeName;
    private String directory = "D:/Java Project/NewProjects/home113/target/home113/upload/temp/";
    private ArrayList<String> list = new ArrayList<>();

    public WorkWithMultipartFile(MultipartFile[] multipartFile, String recipeName) {
        this.multipartFile = multipartFile;
        this.recipeName = toTranscript(recipeName);
    }

    private String changeName(int count){
        return recipeName + "-" + count + "." + multipartFile[count].getContentType().split("/")[1];
    }

    private void saveFile(int count) throws IOException {
        String imageName = changeName(count);
        File file = new File(directory + imageName);
        multipartFile[count].transferTo(file);
        list.add(imageName);
    }

    public String[] saveFiles(){
        for (int i = 0; i < multipartFile.length; i++){
            if (multipartFile[i].getSize() == 0) continue;
            try {
                saveFile(i);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String[] result = new String[list.size()];
        return list.toArray(result);
    }


    void sdd(MultipartFile file) throws IOException {
        Date now = new Date();
        String addName = "" + now.getTime()/1000;
//        String resultPathName = pathName + File.separator + addName.substring(5) + file.getOriginalFilename();
        String name = addName.substring(5) + file.getOriginalFilename();
        String resultPathName = directory + addName.substring(5) + file.getOriginalFilename();
        System.out.println("resultPathName: " + resultPathName);

        File file1 = new File(resultPathName);
        file.transferTo(file1);
    }

    private String toTranscript(String ruText){
        Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");
        return toLatinTrans.transliterate(ruText).replaceAll(" ", "-");
    }




}
