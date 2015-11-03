package be.ti.groupe2.projetintegration;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class functions extends VariableGlobale{

    private static final String USERNAME= "userLogin";
    private static final String MAIL = "userEmail";
    private static final String PASSWORD = "userPassword";




    static String ev;

    public static void getJSON(String url, final TextView lv) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];

                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine()) != null){
                        sb.append(json+"\n");
                    }
                    System.out.println("CA MARCHE LE N'INTENET");

                    return sb.toString().trim();
                }catch (Exception e){
                    System.out.println("CA MARCHE PAS LE N'INTENET");
                    return null;
                }
            }

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
               //loading = ProgressDialog.show(this,"Loading","Please wait...");
            }

            @Override
            protected void onPostExecute(String s){
                super.onPreExecute();
             //   loading.dismiss();
                lv.setText(s);
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);
    }

    public static JSONArray extractJson(String myJson){
        try{
            JSONArray users = new JSONArray(myJson);
            return users;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void searchLogin(JSONArray users, boolean granted, String login, String mdp){
        int i = 0;
        String cLogin;
        String cMdp;

        try{
            while(i<users.length() && !granted){
                JSONObject jsonObject = users.getJSONObject(i);
                cLogin = jsonObject.getString(USERNAME);
                cMdp = jsonObject.getString(PASSWORD);
                if(cLogin.equals(login) && cMdp.equals(mdp)){
                    granted = true;
                }
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
