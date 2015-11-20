package com.example.utd.edu;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Swathi and Ranjani on 11/3/2015.
 * Class description: Contains all the file input and output operations
 */
public class FileOperations {
    File file = new File("/sdcard/contacts1.txt");

    /*
    * Created by: Swathi Varadharajan and Ranjani Suresh
    * Description: Reads data from the file
    * */
    public ArrayList<ContactManagerVO> readFile() throws IOException {
        ArrayList<ContactManagerVO> data;
        data = new ArrayList<ContactManagerVO>();
        String line;
        Log.i("FileOperations: ", file.getAbsolutePath() + " seocnd " + file.getPath() + " thir " + file.getCanonicalPath());
        if (!file.exists()) {
            file.createNewFile();
        }
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null && !line.equals("")) {
                String[] rowData = line.split("\t");
                Log.i("FileOperations:", "Length: " + rowData.length);
                if (!(rowData.length == 0)) {
                    if (rowData.length == 1) {
                        ContactManagerVO contact = new ContactManagerVO();
                        contact.setFirstName(rowData[0]);
                        contact.setLastName("");
                        contact.setPhoneNumber("");
                        contact.setEmail("");
                        data.add(contact);

                    } else if (rowData.length == 2) {
                        ContactManagerVO contact = new ContactManagerVO();
                        contact.setFirstName(rowData[0]);
                        contact.setLastName(rowData[1]);
                        contact.setPhoneNumber("");
                        contact.setEmail("");
                        data.add(contact);

                    } else if (rowData.length == 3) {
                        ContactManagerVO contact = new ContactManagerVO();
                        contact.setFirstName(rowData[0]);
                        contact.setLastName(rowData[1]);
                        contact.setPhoneNumber(rowData[2]);
                        contact.setEmail("");
                        data.add(contact);

                    } else {
                        ContactManagerVO contact = new ContactManagerVO();
                        contact.setFirstName(rowData[0]);
                        contact.setLastName(rowData[1]);
                        contact.setPhoneNumber(rowData[2]);
                        contact.setEmail(rowData[3]);
                        data.add(contact);

                    }
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;

    }

    /*
     * Created by: Swathi Varadharajan and Ranjani Suresh
     * Description: Writes the data into file
     * */
    public void writeFile(ArrayList<ContactManagerVO> data) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < data.size(); i++) {
                bufferedWriter.append(data.get(i).getFirstName()).append("\t");
                bufferedWriter.append(data.get(i).getLastName()).append("\t");
                bufferedWriter.append(data.get(i).getPhoneNumber()).append("\t");
                bufferedWriter.append(data.get(i).getEmail()).append("\t");
                bufferedWriter.append("\n");
            }

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
