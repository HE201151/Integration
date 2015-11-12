package be.ti.groupe2.projetintegration;

import android.app.Application;
import android.content.Context;


public class VariableGlobale extends Application{


    private int iDUser;
    private String listEvent;
    private static Context context;
    private String pseudo;



        @Override
        public void onCreate(){
            super.onCreate();
            context = getApplicationContext();
            iDUser = 0;
            listEvent = null;
            pseudo = null ;

        }
    public static Context getContext(){
        return context;
    }

    public int getiDUser() {
        return iDUser;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
    }

    public void setiDUser(int iDUser) {
        this.iDUser = iDUser;
    }

    public String getlistEvent() {
        return listEvent;
    }

    public void setListEvent(String listEvent) {
        this.listEvent = listEvent;
    }


}

