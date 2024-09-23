package control;

import javax.swing.JOptionPane;

import model.WortEintrag;
import model.WortTrainer;

public class WortTrainerControl {
    public static void main(String[] args) {
        boolean runtime=true;
        WortTrainer wt = new WortTrainer();
        wt.load();

        do{
            System.out.println("test");
            runtime=false;
        }while(runtime);
    }

}