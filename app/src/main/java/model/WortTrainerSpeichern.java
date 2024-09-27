package model;

import java.awt.HeadlessException;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.swing.filechooser.FileNameExtensionFilter;


public class WortTrainerSpeichern {

    private int rVersuche;
    private int gVersuche;
    private WortTrainer wt;

    public void safe(int gVersuche, int rVersuche) throws IOException, HeadlessException, JSONException {
        this.rVersuche = rVersuche;
        this.gVersuche = gVersuche;

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

            if (JOptionPane.showConfirmDialog(null, "Noch eins?", "Wort hinzufügen", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                mehr = false;
            }
        } while (mehr);

        JSONObject statistik = new JSONObject();
        statistik.put("insgesamt", this.gVersuche);
        statistik.put("richtig", this.rVersuche);

        JSONObject mainJson = new JSONObject();
        mainJson.put("statistik", statistik);
        mainJson.put("eintraege", ja);

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Dateien", "json");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showSaveDialog(null);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            
            String filePath = chooser.getSelectedFile().getAbsolutePath();
            
            if (!filePath.endsWith(".json")) { filePath += ".json"; }

            try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
                out.write(mainJson.toString(4));
            }

            System.out.println("Daten wurden erfolgreich gespeichert: " + filePath);

        } else { 
            System.out.println("Speichern wurde abgebrochen.");
        }
    }

    
    public void load() throws IOException, JSONException {
        System.out.println("jafile");
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Dateien", "json");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(null); 

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filePath = chooser.getSelectedFile().getAbsolutePath();
            doLoad(filePath, wt);
            System.out.println("Daten wurden erfolgreich geladen: " + filePath);

        } else {
            System.out.println("Laden wurde abgebrochen.");
        }
    }

    public void doLoad(String pathString, WortTrainer wt) throws IOException, JSONException {
        this.wt = wt;
        File file = new File(pathString);

        if (!file.exists()) {
            System.out.println("Datei existiert nicht: " + pathString);
        }
        else{
            StringBuilder jsonStringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(pathString))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonStringBuilder.append(line);
                }
            }

            String jsonData = jsonStringBuilder.toString();
            JSONObject mainJson = new JSONObject(jsonData);

            JSONObject statistik = mainJson.getJSONObject("statistik");
            this.gVersuche = statistik.getInt("insgesamt");
            this.rVersuche = statistik.getInt("richtig");

            System.out.println("Statistik - Insgesamt: " + this.gVersuche + ", Richtig: " + this.rVersuche);

            JSONArray ja = mainJson.getJSONArray("eintraege");
            
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                wt.addEintrag(jo.getString("name"), jo.getString("url"));
            }
        }
    }
}
