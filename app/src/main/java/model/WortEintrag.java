package model;

/**
 * WortEintrag Klasse um den Namen und den Link in einem Objekt zu speichern
 * @author Sebastian Sailer
 * @version 30.09.2024
 */

public class WortEintrag {
    private String name;
    private String url;

    public WortEintrag(String name, String url){
        this.name = name;
        this.url = url;
    }

    public String getName(){
        return this.name;
    }
    public String getUrl(){
        return this.url;
    }

    public String toString() {
        return "Eintrag{name='" + name + "', url='" + url + "'}";
    }
}
