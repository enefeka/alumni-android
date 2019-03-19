package com.example.alumnos.alumni.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.alumnos.alumni.Api.ApiAlumni;
import com.example.alumnos.alumni.Api.GlobalRequests;
import com.example.alumnos.alumni.Models.JsonResponse;
import com.example.alumnos.alumni.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import static com.example.alumnos.alumni.Activities.MainActivity.URL;



public class RecoverActivity extends AppCompatActivity  {

    ApiAlumni api;
    private ProgressBar spinner;
    EditText email;
    Button recoverBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_recover );
        spinner= findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        email = findViewById(R.id.emailTxt);
        recoverBtn = findViewById(R.id.recoverBtn);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ApiAlumni.class);

    }
    public void recover(View view) {
        spinner.setVisibility(View.VISIBLE);
        Call<JsonResponse> peticion = api.recover (email.getText().toString());
        peticion.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                Log.d("holia", "Esta es la respuesta" + response);
                int code = response.body ().getCode ();
                JsonResponse json = response.body();

                Log.d ( "Respuesta del servidor", response.body ().getMessage () );
                Log.d ( "Respuesta del servidor", " " + code );
                Log.d ( "INTENTANDO....", " "+ json );

                switch (code) {
                    case 200:

                        String message = response.body ().getMessage ();
                        Log.d ( "KAPACHAO", " "+ message );
                        AlertDialog.Builder adb=new AlertDialog.Builder(RecoverActivity.this);
                        adb.setTitle("Registrado!");
                        adb.setMessage(message);

                        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                goToMain();
                            }
                        });
                        adb.show();
                        spinner.setVisibility(View.GONE);

                        break;
                    case 400:
                        spinner.setVisibility(View.GONE);
                        String errorMessage = response.body ().getMessage ();
                        AlertDialog.Builder adbd=new AlertDialog.Builder(RecoverActivity.this);

                        adbd.setTitle("Error");
                        adbd.setMessage(errorMessage);
                        adbd.setNegativeButton("Ok", null);
                        adbd.show();
                        break;

                    default:
                        spinner.setVisibility(View.GONE);
                        String errrorMessage = response.body ().getMessage ();
                        AlertDialog.Builder adbc=new AlertDialog.Builder(RecoverActivity.this);
                        adbc.setTitle("Error");
                        adbc.setMessage(errrorMessage);
                        adbc.setPositiveButton("Ok", null);
                        adbc.show();
                }

            }


            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

                Log.d ("Failture message", "FAILTUREEEEEEE CASEEEEEEEEEEEEEEEEEEE");
                String error = "Failtuure :algo ha salido mal al captar code 400:";
                Toast.makeText(RecoverActivity.this, error, Toast.LENGTH_SHORT).show();
                // Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d ("Failture message",t.getMessage ().toString ());

            }

        });
    }

    private void goToMain()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
