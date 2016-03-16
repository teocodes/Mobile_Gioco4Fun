package com.example.matteo.file;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matteo on 16/03/2016.
 */
public class FileOperation {

    private InputStream is = null;
    private InputStreamReader isr = null;
    private BufferedReader br = null;
    private File file = null;
    private FileInputStream fis = null;
    private FileReader fr = null;

    public List<String> readFile(Context context, int id) {
        List<String> list = new ArrayList<>();

        try {
            is = context.getResources().openRawResource(id);

            if (is != null) {
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);
                String receiveString = "";

                while ((receiveString = br.readLine()) != null) {
                    list.add(receiveString);
                }
                is.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

}
