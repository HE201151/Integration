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

    private static final String PSEUDO= "userLogin";
    private static final String MAIL = "userMail";
    private static final String PASSWORD = "userPassword";
    private static final String USERID = "userID";
    private static final String USERNAME= "userName";
    private static final String FIRSTNAME= "firstName";



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
                //lv.setText(s);
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

    public static int searchLogin(JSONArray users, boolean granted, String login, String mdp){
        int i = 0;
        String cLogin;
        String cMdp;
        int id;
        try{
            while(i<users.length() && !granted){
                JSONObject jsonObject = users.getJSONObject(i);
                cLogin = jsonObject.getString(PSEUDO);
                cMdp = jsonObject.getString(PASSWORD);
                id=jsonObject.getInt(USERID);
                if(cLogin.equals(login) && cMdp.equals(mdp)){
                    granted = true;
                    return id;
                }
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static user searchUser(JSONArray users, boolean granted, int id){
        int i = 0;
        int cId;
        String cLogin;
        String cMdp;
        String name;
        String firstname;

        try{
            while(i<users.length() && !granted){
                JSONObject jsonObject = users.getJSONObject(i);
                cId=jsonObject.getInt(USERID);
                if(cId==id){
                    granted = true;
                    cLogin = jsonObject.getString(PSEUDO);
                    cMdp = jsonObject.getString(PASSWORD);
                    name = jsonObject.getString(USERNAME);
                    firstname = jsonObject.getString(FIRSTNAME);
                    user user1 = new user(cLogin, cMdp,cId,name,firstname);

                    return user1;
                }
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}
