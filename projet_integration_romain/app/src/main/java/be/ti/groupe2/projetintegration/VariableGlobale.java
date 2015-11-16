package be.ti.groupe2.projetintegration;

import android.app.Application;
import android.content.Context;


public class VariableGlobale extends Application{


    private int iDUser;
    private String listEvent;
    private String listActu;
    private static Context context;
    private Event e;



        @Override
        public void onCreate(){
            super.onCreate();
            context = getApplicationContext();
            iDUser = 0;
            listEvent = null;
<<<<<<< HEAD
            e = null;

=======
<<<<<<< HEAD
            listActu = null;
=======
            pseudo = null ;
>>>>>>> origin/master
>>>>>>> origin/master

        }
    public static Context getContext(){
        return context;
    }

    public int getiDUser() {
        return iDUser;
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

<<<<<<< HEAD
    public Event getEvent() {
        return e;
    }
=======
    public String getlistActu() {
        return listActu;
    }

    public void setListActu(String listActu) {
        this.listActu = listActu;
    }

>>>>>>> origin/master

    public void setEvent(Event e) {
        this.e = e;
    }
}

