package com.sict.mobile.vks.utils;

import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.sict.mobile.vks.libs.LibSVM;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import okhttp3.ResponseBody;

public class FileUtils {
    private static final Logger LOGGER = new Logger();
    public static final String ROOT =
                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "VKS";

    public static final String DATA_FILE = "data";
    public static final String MODEL_FILE = "model";
    public static final String LABEL_FILE = "label";

    public static void makeRoot(){
        File root = new File(ROOT);
        if(!root.exists()) root.mkdir();
    }

    public static void appendText(String text, String filename) {
        try(FileWriter fw = new FileWriter(ROOT + File.separator + filename, true);
            PrintWriter out = new PrintWriter(new BufferedWriter(fw))) {
            out.println(text);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
            LOGGER.e(e, "IOException!");
        }
    }

    public static ArrayList<String> readLabel(String filename) throws FileNotFoundException{
        Scanner s = new Scanner(new File(ROOT + File.separator + filename));
        ArrayList<String> list = new ArrayList<>();
        while (s.hasNextLine()){
            list.add(s.nextLine());
        }
        s.close();

        return list;
    }

    public static boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            File dataFile = new File(LibSVM.MODEL_PATH);
            if(dataFile.exists()) dataFile.delete();
            dataFile.createNewFile();

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(dataFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
