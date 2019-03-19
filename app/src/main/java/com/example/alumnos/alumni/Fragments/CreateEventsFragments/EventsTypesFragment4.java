package com.example.alumnos.alumni.Fragments.CreateEventsFragments;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.telephony.mbms.FileInfo;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alumnos.alumni.Api.ApiAlumni;
import com.example.alumnos.alumni.Models.JsonResponse;
import com.example.alumnos.alumni.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.app.Activity.RESULT_OK;
import static com.example.alumnos.alumni.Activities.MainActivity.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsTypesFragment4 extends Fragment implements View.OnClickListener {

    Button uploadImg;
    ImageView imageViewEvent;
    String imagePath;
    public static Uri imageUri;

    public static Bitmap bitmap;
    private final int IMG_REQUEST = 1;
    ApiAlumni api;




    public EventsTypesFragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_events_types_fragment4, container, false);

        imageViewEvent = v.findViewById(R.id.imageEventype);
        uploadImg = v.findViewById(R.id.uploadImageBtn);
        uploadImg.setOnClickListener(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ApiAlumni.class);


        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.uploadImageBtn:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
//                  dispatchTakePictureIntent();
                break;
        }

    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType(("image/*"));
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (data == null){
                Log.d("siuh", "sepudo");
            }

            Uri imageUri = data.getData();
            imagePath =  getRealPathFromURI(imageUri);
            File file = new File(imagePath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
            Call<JsonResponse> call = api.uploadFileWithPartMap(body);
            call.enqueue(new Callback<JsonResponse>() {
                @Override
                public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                    int code = response.body().getCode();
                    JsonResponse json = response.body();
                    Log.d ( "Respuesta del servidor", response.body ().getMessage () );

                    switch (code) {
                        case 200:
                            String message = response.body().getMessage();
                            Log.d("RESOUESTA 200::", message);
                            Log.d("REQUEST STATE", "FIN DE LA PETICION");

                            //listener.onGetEventsFinish ();

                            break;
                        case 400:
                            // Toast.makeText ( MainActivity.this, errorMessage, Toast.LENGTH_SHORT ).show ();
                            String errorMessage = response.body().getMessage();
                            Log.d("RESOUESTA 200::", errorMessage);
                            break;

                        default:
                            //Toast.makeText ( MainActivity.this, errrorMessage, Toast.LENGTH_SHORT ).show ();
                            String defaultmsg = response.body().getMessage();
                            Log.d("RESOUESTA 200::", defaultmsg);
                    }
                }

                @Override
                public void onFailure(Call<JsonResponse> call, Throwable t) {
                    Log.d ("Fallo mensaje", "ON FAILTUREEEEEEEEEEE");
                    Log.d ("EL FALLO ES", "hola ", t);
                }
            });



        }
    }

//    public void openGalery()
//    {
//
//        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        startActivityForResult(gallery, PICK_IMAGE);
//
//
//    }
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//
//        super.onActivityResult(requestCode,resultCode, data);
//        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
//
//            Uri pacienteO = data.getData();
//            //imageUri = data.getData();
//            imageUri = Uri.fromFile(new File(getRealPathFromURI(pacienteO)));
//
//            imageViewEvent.setImageURI(pacienteO);
//        }
//
//    }
//
//
//    public static void checkPhoto()
//    {
//        Log.d("Photo", String.valueOf(imageUri));
//    }
//
//
//
    private String getRealPathFromURI(Uri uri)
    {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_idx);
        cursor.close();

        return result;
    }


//
//
//
//
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        imageViewEvent.setImageURI(imageUri);
//
//        Log.d("imagen uri", String.valueOf(imageUri));
//
//
//    }
}