package model;

import java.awt.HeadlessException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Image;
import javax.imageio.ImageIO;

import org.json.JSONException;

public class WortTrainer {
    
    private WortTrainerSpeichern wts = new WortTrainerSpeichern();
    private ArrayList<WortEintrag> list = new ArrayList<>();

    public WortTrainer(){
        boolean runtime=true;
        
        if (JOptionPane.showConfirmDialog(null, "Wollen Sie einen Speicherstand laden?", "Laden", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                wts.load();
            } catch (IOException | JSONException e) { 
                System.out.println("Laden hat nicht funktioniert"); 
            } 
        }
        else{ loadDefault(); }

        for (WortEintrag eintrag : this.list) {
            System.out.println(eintrag.toString());
        }

        
        
        //do{
            // try {
            //     showImage();
            //     wt.check(JOptionPane.showInputDialog(null,"Wort eingeben: "));

            // } catch (URISyntaxException e) { e.printStackTrace(); }

            // if (JOptionPane.showConfirmDialog(null, "Noch eins?", "Keines mehr", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
            //     runtime = false;
            // }
        //}while(runtime);
        
    }

    public static void showImage(String link) throws URISyntaxException{
        try {
            URL imageUrl = new URI(link).toURL();
            Image image = ImageIO.read(imageUrl);
            ImageIcon imageIcon = new ImageIcon(image);
            JOptionPane.showMessageDialog(null, "", "Bildanzeige", JOptionPane.INFORMATION_MESSAGE, imageIcon); //Bild anezigen
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Bild konnte nicht geladen werden.", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadDefault(){
        String currentPath = "";
        try { 
            currentPath = new java.io.File(".").getCanonicalPath();
            currentPath += "/app/src/main/resources/example.json";
            this.wts.doLoad(currentPath, this);
        } catch (HeadlessException | IOException | JSONException e) { e.printStackTrace(); }
    }

    public void addEintrag(String name, String url){
        this.list.add(new WortEintrag(name,url));
    }

}
