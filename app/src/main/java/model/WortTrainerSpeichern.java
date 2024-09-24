package model;

import java.awt.HeadlessException;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Arrays;

public class WortTrainerSpeichern {
    
    public void safe() throws IOException, HeadlessException, JSONException {
        boolean mehr = true;
        JSONArray ja = new JSONArray();
        JSONObject jo;
        boolean check = true;

        String name;
        String url;
        do {
            jo = new JSONObject();
            JOptionPane.showMessageDialog(null, "Wort hinzufügen!");

            name = JOptionPane.showInputDialog(null, "Bild Name: ");
            if (!name.isEmpty()) { jo.put("name", name);} else { check = false; }

            url = JOptionPane.showInputDialog(null, "URL: ");
            if (!url.isEmpty()) { jo.put("url", url); } else { check = false; }

            if (check) { ja.put(jo); } else { JOptionPane.showMessageDialog(null, "Falscher Input"); }

            if (JOptionPane.showConfirmDialog(null, "Noch eins?", "Keines mehr", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                mehr = false;
            }
        } while (mehr);

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Dateien", "json");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showSaveDialog(null);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            
            String filePath = chooser.getSelectedFile().getAbsolutePath();
            
            if (!filePath.endsWith(".json")) { filePath += ".json"; }

            try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) { out.write(ja.toString(4)); }

            System.out.println("Daten wurden erfolgreich gespeichert: " + filePath);

        } else { System.out.println("Speichern wurde abgebrochen."); }
    }

    
    public ArrayList<String[]> load() throws IOException, JSONException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Dateien", "json");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(null); 

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filePath = chooser.getSelectedFile().getAbsolutePath();
            ArrayList<String[]> eintrag = doLoad(filePath);

            System.out.println("Daten wurden erfolgreich geladen: " + filePath);
            return eintrag;
        } else {
            System.out.println("Laden wurde abgebrochen.");
            return null;
        }
    }

    public ArrayList<String[]> doLoad(String pathString) throws IOException, JSONException {
        File file = new File(pathString);
        ArrayList<String[]> eintrag = new ArrayList<>();

        if (!file.exists()) {
            System.out.println("Datei existiert nicht: " + pathString);
            return null;
        }

        StringBuilder jsonStringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(pathString))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }
        }

        String jsonData = jsonStringBuilder.toString();
        JSONArray ja = new JSONArray(jsonData);

        
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.getJSONObject(i);
            String[] temp = new String[2]; 
            temp[0] = jo.getString("name");
            temp[1] = jo.getString("url");

            eintrag.add(temp);
        }

        for (String[] entry : eintrag) {
            System.out.println(Arrays.toString(entry));  
        }
        return eintrag;
    }
}
