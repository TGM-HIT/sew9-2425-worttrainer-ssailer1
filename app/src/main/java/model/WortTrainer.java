package model;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.*;
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
        if (JOptionPane.showConfirmDialog(null, "Bevor wir beginnen, möchten Sie Woerter hinzufuegen?", "Wort hinzufuegen", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try { this.wts.woerterHinzufuegen(this); } 
            catch (JSONException e) { e.printStackTrace(); }
        }
        if (JOptionPane.showConfirmDialog(null, "Wollen Sie einen Speicherstand laden?", "Laden", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            CountDownLatch cdl = new CountDownLatch(1);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        wts.load(WortTrainer.this); 
                    } catch (IOException | JSONException e) {
                        System.out.println("Laden hat nicht funktioniert");
                    }
                    cdl.countDown();
                }
            });
            try {
                cdl.await();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        else{ this.wts.loadDefault(this); }

        // for (WortEintrag eintrag : this.list) {
        //     System.out.println(eintrag.toString());
        // }

        
        do{
            newItem();
            if (JOptionPane.showConfirmDialog(null, "Noch eins?", "Keines mehr", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                runtime = false;
            }
        }while(runtime);
        String temp = "Aktuelle Statistik: Insgesamt - "+this.wts.getVersuche()+" | davon richtig - "+this.wts.getRVersuche();
        JOptionPane.showMessageDialog(null, temp );
        if (JOptionPane.showConfirmDialog(null, "Möchten Sie ihren Fortschritt speichern?", "Keines mehr", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            CountDownLatch cdl = new CountDownLatch(1);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        wts.save();
                    } catch (IOException | JSONException e) {
                        System.out.println("Laden hat nicht funktioniert");
                    }
                    cdl.countDown();
                }
            });
            try {
                cdl.await();
            } catch (InterruptedException e) { e.printStackTrace(); }
            
        }
        
    }

    public static void showImage(String link) throws URISyntaxException {
        try {
            URL imageUrl = new URI(link).toURL();
            Image image = ImageIO.read(imageUrl);
            
            int maxWidth = 700;
            int maxHeight = 700;
            
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);
            
            
            double widthScale = (double) maxWidth / imageWidth;
            double heightScale = (double) maxHeight / imageHeight;
            double scaleFactor = Math.min(widthScale, heightScale);
            
            if (scaleFactor < 1) {
                int newWidth = (int) (imageWidth * scaleFactor);
                int newHeight = (int) (imageHeight * scaleFactor);
                image = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            }
    
            ImageIcon imageIcon = new ImageIcon(image);
            JOptionPane.showMessageDialog(null, "", "Bildanzeige", JOptionPane.INFORMATION_MESSAGE, imageIcon); // Bild anzeigen
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Bild konnte nicht geladen werden.", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    public void newItem(){
        int rand = (int)(Math.random() * this.list.size());
        try {
            showImage(this.list.get(rand).getUrl());
        } catch (URISyntaxException e) {
            System.out.println("Image nicht gefunden");
        }

        check(JOptionPane.showInputDialog(null, "Was war das?"),this.list.get(rand).getName());
    }


    public void check(String f, String t){
        if(f!=null&&t!=null&&f.toLowerCase().equals(t.toLowerCase())){
            JOptionPane.showMessageDialog(null, "Richtig! :)");
            this.wts.addTrue();
        }
        else{
            String temp = "Leider Falsch, die richtige Antworte wäre "+t;
            JOptionPane.showMessageDialog(null, temp);
            this.wts.addFalse();
        }
    }

    

    public void addEintrag(String name, String url){
        this.list.add(new WortEintrag(name,url));
    }

    public ArrayList<WortEintrag> getList(){
        return this.list;
    }

}
