package model;

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
