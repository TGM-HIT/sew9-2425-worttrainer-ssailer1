package model;

import java.awt.HeadlessException;
import java.io.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WortTrainerSpeichern {
    public void load(){
        System.out.println("test2");
    }

    
    public void safe(String pathString) throws IOException, HeadlessException, JSONException{
        boolean mehr = true;
        JSONArray ja = new JSONArray();
        JSONObject jo;

        boolean check = true;

        String name;
        String url;
        do{
            jo = new JSONObject();
            JOptionPane.showMessageDialog(null, "Wort hinzufügen!");

            name = JOptionPane.showInputDialog(null, "Bild Name: ");
            if (name!="") { jo.put("name", name); } else { check = false; }

            url = JOptionPane.showInputDialog(null, "URL: ");
            if (name!="") { jo.put("url", url); } else { check = false; }

            if(check) {ja.put(jo);} else { JOptionPane.showMessageDialog(null, "Falscher Input");            }

            if (JOptionPane.showConfirmDialog(null, "Noch eins", "Keines mehr", 
            JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) { mehr = false; } 

        }while(mehr);
        //PrintWriter out = new PrintWriter((new FileWriter(pathString)));
        System.out.println(ja);
    }
    public void safe() throws HeadlessException, IOException, JSONException{
        safe("../../resources/output.json");

    }
}
