package model;

import java.awt.HeadlessException;
import java.io.*;
import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.swing.filechooser.FileNameExtensionFilter;


public class WortTrainerSpeichern {

    private int rVersuche = 0;
    private int gVersuche = 0;
    private WortTrainer wt;

    public void save() throws IOException, HeadlessException, JSONException {
        
        JSONArray ja = new JSONArray();
        
        
        JSONObject statistik = new JSONObject();
        statistik.put("insgesamt", this.gVersuche);
        statistik.put("richtig", this.rVersuche);

        for(WortEintrag eintrag : this.wt.getList()){
            JSONObject eintragJson = new JSONObject();
            eintragJson.put("name", eintrag.getName());
            eintragJson.put("url", eintrag.getUrl());
            ja.put(eintragJson);
        }

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

    
    public void load(WortTrainer wt) throws IOException, JSONException {
        this.wt = wt;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Dateien", "json");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(null); 

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filePath = chooser.getSelectedFile().getAbsolutePath();
            doLoad(filePath, this.wt);
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
    public void loadDefault(WortTrainer wt){
        this.wt = wt;
        String currentPath = "";
        try { 
            currentPath = new java.io.File(".").getCanonicalPath();
            currentPath += "/src/main/resources/example.json";
            doLoad(currentPath, this.wt);
        } catch (HeadlessException | IOException | JSONException e) { e.printStackTrace(); }
    }

    public void woerterHinzufuegen(WortTrainer wt) throws JSONException{
        this.wt = wt;
        boolean mehr = true;
        String name;
        String url;
        do {
            JOptionPane.showMessageDialog(null, "Wort hinzufügen!");

            name = JOptionPane.showInputDialog(null, "Bild Name: ");
            url = JOptionPane.showInputDialog(null, "URL: ");
            
            if(name!=null&&url!=null){
                this.wt.addEintrag(name, url);
            }

            if (JOptionPane.showConfirmDialog(null, "Noch eins?", "Wort hinzufügen", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                mehr = false;
            }
        } while (mehr);
    }

    public void addTrue(){
        this.rVersuche += 1;
        this.gVersuche += 1;
    }
    public void addFalse(){
        this.gVersuche += 1;
    }
    public int getVersuche(){
        return this.gVersuche;
    }
    public int getRVersuche(){
        return this.rVersuche;
    }
}
