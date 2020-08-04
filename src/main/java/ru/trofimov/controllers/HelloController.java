package ru.trofimov.controllers;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.trofimov.entity.ImgDescription;
import ru.trofimov.model.MyFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String sayHello(){
        return "hello";
    }

    @GetMapping("/add")
    public String add(Model model){
        MyFile myFile = MyFile.getInstance();
        model.addAttribute("posts", myFile.list());
        return "add";
    }

    @PostMapping("/add")
    public String addPost(Model model, HttpServletRequest request){
//        System.out.println(a);
        MyFile myFile = MyFile.getInstance();

        model.addAttribute("posts", myFile.list());




        ImgDescription imgDescription = new ImgDescription();
        try
        {
            ServletFileUpload uploader = new ServletFileUpload(new DiskFileItemFactory());
            List<FileItem> multiparts = uploader.parseRequest(request);

            for(FileItem item : multiparts)
            {
                String foldName = item.getFieldName();
                if (item.getName() == null){
                    String value = item.getString("UTF-8");
//                    switch (foldName){
//                        case ("recipeName"):recipe.setRecipeName(value); break;
//                        case ("category"):recipe.setCategory(Integer.parseInt(value)); break;
//                        case ("listportion"):recipe.setPortion(Integer.parseInt(value)); break;
//                        case ("listhour"): hour = Integer.parseInt(value); break;
//                        case ("listminut"): min = Integer.parseInt(value); break;
//                        case ("ingName"): ingredients += value; ingredients += "!%!"; break;
//                        case ("quantity"): quantity += value; quantity += "!%!"; break;
//                        case ("measure"): measure += value; measure += "!%!"; break;
//                        case ("step"): steps += value; steps += "!%!"; break;
//                    }
                    imgDescription.setDescription(value);
                }
                if(!item.isFormField())

                {
                    String imgType = item.getContentType().split("/")[1];
                    if (imgType.equals("png") || imgType.equals("gif") || imgType.equals("jpeg")){
                        String[] itemParts = item.getName().split("\\.");
                        String imgName = "";
                        for (int i = 0; i < itemParts.length - 1; i++)
                            imgName += itemParts[i];
                        imgType = itemParts[itemParts.length-1];

                        if(imgName.indexOf("\\") >= 0){
                            byte[] her = imgName.getBytes();
                            imgName = "";
                            for (byte b : her) {
                                imgName += (char) b;
                                if (b == 92) imgName = "";
                            }
                        }


                        Date now = new Date();
                        String name = imgName + "-" + now.getTime()/1000 + "." + imgType;
                        item.write(new File("D:/Java Project/NewProjects/home113/src/main/webapp/WEB-INF/images/" + File.separator +name));

                        imgDescription.setFileName(name);
//                        imgDescription.setFileName("batman-logo.gif");
                    }
                }
            }
            myFile.add(imgDescription);


        }
        catch(Exception ex)
        {
            System.out.println("file uploaded failed " + ex);
        }






        List<ImgDescription> img = myFile.list();
        System.out.println("vvvvvvvvvvvvvvvvvvvvvv");
        for (ImgDescription x : img){
            System.out.println(x.getDescription());
            System.out.println(x.getFileName());
        }
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^");

        return "redirect:/add";
    }

    @PostMapping("/uploadFile")
    public String submit(@RequestParam MultipartFile file,@RequestParam String text,  Model model) throws IOException {
//        String pathName = "D:/Java Project/NewProjects/home113/src/main/upload/images/";
        String pathName = "D:/Java Project/NewProjects/home113/target/home113/upload/images/";
//        String pathName = System.getProperty("user.dir") + "\\";

//        System.out.println(file.getResource().getURI().getPath());

        Date now = new Date();
        String addName = "" + now.getTime()/1000;
//        String resultPathName = pathName + File.separator + addName.substring(5) + file.getOriginalFilename();
        String name = addName.substring(5) + file.getOriginalFilename();
        String resultPathName = pathName + addName.substring(5) + file.getOriginalFilename();
        System.out.println("resultPathName: " + resultPathName);

        File file1 = new File(resultPathName);
        file.transferTo(file1);
        MyFile myFile = MyFile.getInstance();
        ImgDescription imgDescription = new ImgDescription();
        imgDescription.setFileName(name);
        imgDescription.setDescription(text);
        myFile.add(imgDescription);

        List<ImgDescription> img = myFile.list();
        System.out.println("vvvvvvvvvvvvvvvvvvvvvv");
        for (ImgDescription x : img){
            System.out.println(x.getDescription());
            System.out.println(x.getFileName());
        }
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^");

//        System.out.println("file://" + System.getProperty("user.dir").replaceAll("\\\\", "/") + "/src/main/upload/");
//        System.out.println("file://");
//        System.out.println(System.getProperty("user.dir"));
//        System.out.println("/src/main/upload/");
//
//        String nameX = System.getProperty("user.dir").replaceAll("\\\\", "/");
//        System.out.println(nameX);


        model.addAttribute("posts", myFile.list());
        return "redirect:/add";
    }


}
