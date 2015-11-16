package be.ti.groupe2.projetintegration;

public class Event {

    String nom;
    int nb;
    int idAuthor;
    String description;
    String localite;

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localité) {
        this.localite = localité;
    }

    String mdp;




    public Event(){
        this.description = "test";
        this.nb = 1;
        this.nom = "test";
    }

    public Event (String nom ,String description, int nb ,String autor){
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


}
