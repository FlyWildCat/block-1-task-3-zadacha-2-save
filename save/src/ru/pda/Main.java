package ru.pda;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        final String saveDirPath = "E:\\Games\\savegames";
        final String saveFile1 = saveDirPath + "\\save1.dat";
        final String saveFile2 = saveDirPath + "\\save2.dat";
        final String saveFile3 = saveDirPath + "\\save3.dat";
        final String zipFilePath = saveDirPath + "\\allsave.zip";

        GameProgress save1 = new GameProgress(100,50,45,1000.00);
        GameProgress save2 = new GameProgress(200,150,95,5500.00);
        GameProgress save3 = new GameProgress(30,10,15,9.00);

        saveGame(saveFile1, save1);
        saveGame(saveFile2, save2);
        saveGame(saveFile3, save3);

        List<String> filesInDirectory = new ArrayList<>();
        filesInDirectory.add(saveFile1);
        filesInDirectory.add(saveFile2);
        filesInDirectory.add(saveFile3);

        zipFiles(zipFilePath, filesInDirectory);

    }

    static void saveGame(String savePath, GameProgress save) {
        try (FileOutputStream fos = new FileOutputStream(savePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(save);
            } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    static void zipFiles(String zipFilePath, List<String> filesList) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (String item : filesList) {
                File file = new File(item);
                ZipEntry ze = new ZipEntry(file.getName());
                zos.putNextEntry(ze);
                try (FileInputStream fis = new FileInputStream(item)) {
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);
                    file.deleteOnExit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            zos.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
