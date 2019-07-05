package by.itech.kimbar.util;

import java.io.File;
import java.io.IOException;



public class DirectoryCreator {
    private DirectoryCreator(){}

    public static void createFileDirectory() throws IOException {
        File file = new File(PathPropertyReader.readFilePath());

        //if there is no dir with such name createFileDirectory it
        if(!file.isDirectory()) {
            file.mkdir();
        }
    }

    public static void createFileSubFolderForUser(String userId) throws IOException {
        File file = new File(PathPropertyReader.readFilePath() + File.separator + userId);
        //if there is no dir with such name createFileDirectory it
        if(!file.isDirectory()) {
            file.mkdir();
        }
    }


    public static void createPhotoDirectory() throws IOException {
        File file = new File(PathPropertyReader.readPhotoPath());

        //if there is no dir with such name createFileDirectory it
        if(!file.isDirectory()) {
            file.mkdir();
        }
    }

    public static void createPhotoSubFolderForUser(String userId) throws IOException {
        File file = new File(PathPropertyReader.readPhotoPath() + File.separator + userId);
        //if there is no dir with such name createFileDirectory it
        if(!file.isDirectory()) {
            file.mkdir();
        }
    }

}
