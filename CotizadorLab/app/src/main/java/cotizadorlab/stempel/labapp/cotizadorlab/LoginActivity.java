package cotizadorlab.stempel.labapp.cotizadorlab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import cotizadorlab.stempel.labapp.cotizadorlab.repository.APIController;
import cotizadorlab.stempel.labapp.cotizadorlab.repository.volley.VolleySingleton;

public class LoginActivity extends AppCompatActivity {

    private String userTxt, passwordTxt;

    private EditText user, password;
    private Button makeLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initOnClick();
    }

    private void initView() {
        user = findViewById(R.id.userLogin);
        password = findViewById(R.id.passwordLogin);
        makeLogin = findViewById(R.id.btnLogin);
    }

    private void initOnClick() {
        makeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userTxt = user.getText().toString();
                passwordTxt = password.getText().toString();

                makeLogin(userTxt, passwordTxt);
            }
        });
    }

    private void makeLogin(String userTxt, String passwordTxt) {

        JSONObject jsonObjectResquest = createJSONRequest(userTxt, passwordTxt);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, APIController.urlLogin, jsonObjectResquest, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("REQ_SUCCESS", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("REQ_ERROR", error.getMessage());

                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private JSONObject createJSONRequest(String userTxt, String passwordTxt) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user", userTxt);
            jsonObject.put("password", passwordTxt);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
