package ru.trofimov.model;

import com.ibm.icu.text.Transliterator;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WorkWithMultipartFile {

    private MultipartFile[] multipartFile;
    private String recipeName;
    private String directory = "D:/Java Project/NewProjects/home113/target/home113/upload/temp/";
    private ArrayList<String> list = new ArrayList<>();

    public WorkWithMultipartFile(MultipartFile[] multipartFile, String recipeName) {
        this.multipartFile = multipartFile;
        this.recipeName = toTranscript(recipeName);
    }

    public String[] saveFiles(boolean forSteps){
        for (int i = 0; i < multipartFile.length; i++){
            if (multipartFile[i].getSize() == 0) continue;
            try {
                saveFile(i, forSteps);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String[] result = new String[list.size()];
        return list.toArray(result);
    }

    private void saveFile(int count, boolean forSteps) throws IOException {
        String imageName = changeName(count);
        if (forSteps) imageName = "St&" + imageName;
        File file = new File(directory + imageName);
        multipartFile[count].transferTo(file);
        list.add(imageName);
    }

    private String changeName(int count){
        return recipeName + "-" + count + "." + multipartFile[count].getContentType().split("/")[1];
    }

    private String toTranscript(String ruText){
        Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");
        return toLatinTrans.transliterate(ruText).replaceAll(" ", "-");
    }

}
