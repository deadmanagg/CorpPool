package com.example.corppool.controller;

/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 */


        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.example.corppool.db.SQLiteHandler;
        import com.example.corppool.model.User;
        import com.example.corppool.server.ServerInterface;
        import com.example.corppool.util.SessionUtil;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.Calendar;
        import java.util.HashMap;
        import java.util.Map;

     
public class RegisterActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionUtil session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionUtil(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {

            //logout and ask again to login

            session.setLogin(false);
            db.deleteUsers();
            // User is already logged in. Take him to main activity
            /*Intent intent = new Intent(RegisterActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();*/
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    registerUser(name, email, password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");

        new Register().execute(email,password);
        //showDialog();

    }


    private class Register extends AsyncTask<String, String, JSONObject> {
        //private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog();

        }

        private void showDialog() {
            if (!pDialog.isShowing())
                pDialog.show();
        }

        private void hideDialog() {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            String email = args[0];
            String pass = args[1];

            User user = new User();
            user.setUserid(email);
            user.setPassword(pass);

            // Getting JSON from URL
            String jsonStr = ServerInterface.register(user);
            JSONObject jsonArr = new JSONObject();
            try {
                jsonArr = ServerInterface.convertResponseToJSon(jsonStr);
            }catch(JSONException e){
                e.printStackTrace();
            }
            return jsonArr;
        }
        @Override
        protected void onPostExecute(JSONObject jObj) {

            try {
                hideDialog();

                boolean error = false;

                try{
                    error = jObj.getString("status").equals("Failure");
                }catch(Exception e){

                }

                // Check for error node in json
                if (!error) {
                    // user successfully logged in
                    // Create login session
                    //session.setLogin(true);

                    /*// Now store the user in SQLite
                    String uid = jObj.getString("uid");


                    String name = jObj.getString("name");
                    */
                    String name = inputFullName.getText().toString().trim();
                    String email = inputEmail.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();
                    String created_at = Calendar.getInstance().getTime().toString();

                    // Inserting row in users table
                    db.addUser(name, email, null, created_at);

                    String successMsg = "Successfully register. Please login to continue";
                    Toast.makeText(getApplicationContext(),
                            successMsg, Toast.LENGTH_LONG).show();

                    // Launch main activity
                    Intent intent = new Intent(RegisterActivity.this,
                            LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Error in login. Get the error message
                    //TODO return proper error from server
                    String errorMsg = "Unable to register, try different combination";
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}