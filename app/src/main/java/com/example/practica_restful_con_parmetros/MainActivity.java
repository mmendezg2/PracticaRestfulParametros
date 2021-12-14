package com.example.practica_restful_con_parmetros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btnRetroFit;
    Button btnVolley;

    private TextView lblJsonDatitos;
    EditText txtLong;
    EditText txtUser;
    TextView infobanco;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnVolley = findViewById(R.id.btnVolley);
        lblJsonDatitos = findViewById(R.id.lblDatitos);
        txtUser = findViewById(R.id.txtIduser);
        infobanco=findViewById(R.id.txtInfoBanco);
        requestQueue = Volley.newRequestQueue(this);
        btnVolley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Volley(txtUser.getText().toString());



            }
        });


    }





    private void Volley(String idbanco) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://api-uat.kushkipagos.com/transfer-subscriptions/v1/bankList";
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int tamanio = response.length();
                        for (int i = 0; i < tamanio; i++) {
                            try {

                                infobanco.append("Información del banco ingresado");
                                JSONObject json = new JSONObject(response.get(i).toString());
                                String idBanco, NombreBanquito;
                                idBanco=json.getString("code");
                                NombreBanquito=json.getString("name");
                                if(NombreBanquito.equals(idbanco))
                                {
                                String cadena="Codigo del banco: " +idBanco +"\n" + "Nombre de Banco: " + NombreBanquito +"\n\n\n";
                                lblJsonDatitos.append(cadena);
                                break;
                                }
                                else
                                    {
                                        lblJsonDatitos.append("Banco no encontrado");
                                        break;
                                    }
                            } catch (JSONException ex) {
                                lblJsonDatitos.append("No hay resultados");
                                System.out.println(ex.toString());
                            }
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError ex) {
                lblJsonDatitos.append("No hay resultados");
                System.out.println(ex.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("dataType", "json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Public-Merchant-Id", "84e1d0de1fbf437e9779fd6a52a9ca18");
                return headers;

            }
        };
        requestQueue.add(jsonRequest);

    }










}
