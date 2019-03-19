package com.example.alumnos.alumni.Activities;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.alumnos.alumni.Models.JsonResponse;
import com.example.alumnos.alumni.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import static com.example.alumnos.alumni.Activities.MainActivity.URL;

public class Register_Activity extends AppCompatActivity {

    ApiAlumni api;
    EditText email, password, repeatPassword;
    Button sendBtn;
    String userMail;
    String userPass;
    String repeatPass;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register_ );

        spinner= findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        email = (EditText) findViewById(R.id.emailTxt);
        password = (EditText) findViewById(R.id.passwordText);
        repeatPassword = (EditText) findViewById(R.id.repeatPasswordTxt);
        sendBtn = (Button) findViewById(R.id.registerBtn);


        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://profejuan.pe.hu/servicios/")
                .baseUrl(URL)
                .addConverterFactory( ScalarsConverterFactory.create())
                .addConverterFactory( GsonConverterFactory.create())
                .build();
        api = retrofit.create(ApiAlumni.class);

    }


    public void register(View view) {
        userMail = email.getText ().toString ();
        userPass = password.getText ().toString ();
        repeatPass = repeatPassword.getText ().toString ();


        spinner.setVisibility(View.VISIBLE);
        Call<JsonResponse> peticion = api.register( userMail, userPass, repeatPass);
        peticion.enqueue ( new Callback<JsonResponse> () {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                int code = response.body ().getCode ();

                switch (code) {
                    case 200:
                        String message = response.body ().getMessage ();
                        Toast.makeText ( Register_Activity.this, message, Toast.LENGTH_SHORT ).show ();
                        AlertDialog.Builder adb=new AlertDialog.Builder(Register_Activity.this);
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
                        String errorMessage = response.body ().getMessage ();
                        AlertDialog.Builder adbd=new AlertDialog.Builder(Register_Activity.this);
                        adbd.setTitle("Error!");
                        adbd.setMessage(errorMessage);
                        adbd.setPositiveButton("Ok", null);
                        adbd.show();
                        spinner.setVisibility(View.GONE);
                        break;
                    default:
                        String errrorMessage = response.body ().getMessage ();
                        AlertDialog.Builder adbdc=new AlertDialog.Builder(Register_Activity.this);
                        adbdc.setTitle("Error!");
                        adbdc.setMessage(errrorMessage);
                        adbdc.setPositiveButton("Ok", null);
                        adbdc.show();
                        spinner.setVisibility(View.GONE);
                }

            }


            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.d ( "Failture message", "FAILTUREEEEEEE CASEEEEEEEEEEEEEEEEEEE" );
                Toast.makeText(Register_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        } );


    }
    private void goToMenu()
    {
        Intent intent = new Intent(this,MainMenuActivity.class);
        startActivity(intent);
    }


    private void goToMain()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}







        //userMail = email.getText().toString();
        //userPass = password.getText().toString();
        //repeatPass = repeatPassword.getText().toString();