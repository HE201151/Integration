package be.ti.groupe2.projetintegration;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Inscription extends Activity {

    TextView defaut = null;
    EditText nom = null;
    EditText prenom = null;
    EditText mail = null;
    EditText mdp = null;
    EditText Cmdp = null;
    Button enregistrer = null;
    String erreur = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //afficher GUI
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //récupération des vues qu'on a besoin
        defaut = (TextView)findViewById(R.id.defaut);
        nom = (EditText)findViewById(R.id.nom);
        prenom = (EditText)findViewById(R.id.prenom);
        mail = (EditText)findViewById(R.id.email);
        mdp = (EditText)findViewById(R.id.mdp);
        Cmdp = (EditText)findViewById(R.id.Cmdp);
        enregistrer = (Button)findViewById(R.id.enregistrer);

    }

   /*private View.OnClickListener envoyerListener = new View.OnClickListener() {
        private String s = null;
        private String n = null;
        private String p = null;
        private String m = null;
        private String mp = null;
        private String Cmp = null;
        @Override
        public void onClick(View v) {
            n = nom.getText().toString();
            p = prenom.getText().toString();
            m = mail.getText().toString();
            mp = mdp.getText().toString();
            Cmp = Cmdp.getText().toString();
            boolean error = false;
            boolean errorMdp = false;

            if(n != null && !n.isEmpty())
                if(p != null && !p.isEmpty())
                    if(m != null && !m.isEmpty())
                        if(mp != null && !mp.isEmpty())
                            if(Cmp != null && !Cmp.isEmpty())
                                error = false;
                            else
                                error = true;
                        else
                            error = true;
                    else
                        error = true;
                else
                    error = true;
            else
                error = true;

            if(!Cmp.equals(mp))
                errorMdp = true;
            else
                errorMdp = false;

            if(error)
                s = "Erreur les champs doivent être rempli";

            else if (errorMdp)
                s = "Mot de passe différents";
            else
                s = "enregistré";

            defaut.setText(s);
        }
    };



     */

        /*

    public void EnvoyerData() {
        btn_enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, iURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("login", l);
                        parameters.put("pwd", mp);
                        parameters.put("email", m);

                        return parameters;
                    }
                };
                requestQueue.add(request);
            }
        });


    }






    public void envoyerInscription(View v){

        recupererVue(v);

        n = nom.getText().toString();
        p = prenom.getText().toString();
        l = ilogin.getText().toString();
        m = mail.getText().toString();
        mp = iMdp.getText().toString();
        Cmp = iCMdp.getText().toString();

        if(n != null && !n.isEmpty())
            if(p != null && !p.isEmpty())
                if(m != null && !m.isEmpty())
                    if(mp != null && !mp.isEmpty())
                        if(Cmp != null && !Cmp.isEmpty())
                            error = false;
                        else
                            error = true;
                    else
                        error = true;
                else
                    error = true;
            else
                error = true;
        else
            error = true;


        if(!Cmp.equals(mp))
            errorMdp = true;
        else
            errorMdp = false;

        if(error)
            s = "Erreur,les champs doivent être remplis";

        else if (errorMdp)
            s = "Mot de passe différents";
        else
            s = "Enregistré";

        defaut.setText(s);
        //searchExistant();
        EnvoyerData();


    }









    private void searchExistant(){
        int i = 0;
        String cMail;
        String cLogin;

        try{
            while(i<users.length() && present){
                JSONObject jsonObject = users.getJSONObject(i);
                cMail = jsonObject.getString(MAIL);
                cLogin = jsonObject.getString(USERNAME);
                System.out.println("mail : "+ cMail);
                System.out.println("login : "+ cLogin);
                if(!cMail.equals(m) && !cLogin.equals(l)){
                    present = false;
                }
    i++;
}
} catch (JSONException e) {
        e.printStackTrace();
        }
        }

















       //==============================================================================================
    //=========================================Inscription==========================================
    //==============================================================================================

    public static final String iURL = "http://projet_groupe2.hebfree.org/inscription.php";

    EditText nom;
    EditText prenom;
    EditText ilogin;
    EditText mail;
    EditText iMdp;
    EditText iCMdp;

    TextView defaut;
    TextView outputView;

    Button btn_enregistrer;
    RequestQueue requestQueue;

    String s = null;
    String n = null;
    String p = null;
    String l = null;
    String m = null;
    String mp = null;
    String Cmp = null;
    boolean error = false;
    boolean errorMdp = false;
    Boolean present = true;


    public void VersInscription(View view) {
        setContentView(R.layout.inscription);
    }

    public void recupererVue(View v){
        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        ilogin = (EditText) findViewById(R.id.login);
        mail = (EditText) findViewById(R.id.email);
        iMdp = (EditText) findViewById(R.id.mdp);
        iCMdp = (EditText) findViewById(R.id.Cmdp);

        defaut = (TextView) findViewById(R.id.defaut);
        outputView = (TextView) findViewById(R.id.outputView);

        btn_enregistrer = (Button) findViewById(R.id.enregistrer);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }
 */

}
