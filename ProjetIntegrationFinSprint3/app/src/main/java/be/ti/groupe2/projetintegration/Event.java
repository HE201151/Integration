package be.ti.groupe2.projetintegration;

public class Event {

    private String nom;
    private String description;
    private int nb;
    private String autor;


    public Event(){
        this.autor = "test";
        this.description = "test";
        this.nb = 1;
        this.nom = "test";
    }

    public Event (String nom ,String description, int nb ,String autor){
        this.autor = autor;
        this.description = description;
        this.nb = nb;
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
