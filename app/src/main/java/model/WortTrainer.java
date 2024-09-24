package model;

import java.awt.HeadlessException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

public class WortTrainer {
    private ArrayList wt;
    private WortTrainerSpeichern data = new WortTrainerSpeichern();

    public void load(){
        
        try { 
            //data.safe();
            wt = data.load();
        
        } catch (HeadlessException | IOException | JSONException e) { e.printStackTrace(); }
        
    }
}
