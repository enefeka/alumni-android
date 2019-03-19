package com.example.alumnos.alumni.Api;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.alumnos.alumni.Fragments.CreateEventsFragments.EventsTypesFragment4;
import com.example.alumnos.alumni.Models.Comments;
import com.example.alumnos.alumni.Models.Event;
import com.example.alumnos.alumni.Models.JsonResponse;
import com.example.alumnos.alumni.Models.Notas;
import com.example.alumnos.alumni.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.alumnos.alumni.Activities.DetailEventActivity.idEvent;
import static com.example.alumnos.alumni.Activities.DetailEventActivity.keepDetailCommentText;
import static com.example.alumnos.alumni.Activities.DetailEventActivity.textDetailComment;
import static com.example.alumnos.alumni.Activities.EditProfileActivity.uri;
import static com.example.alumnos.alumni.Fragments.CreateEventsFragments.Events_fragment.search;
import static com.example.alumnos.alumni.Fragments.CreateEventsFragments.Events_fragment.type;

import static com.example.alumnos.alumni.Activities.ViewCommentsActivity.commentsArrayList;
import static com.example.alumnos.alumni.Fragments.CreateEventsFragments.EventTypesFragment.idTypeEvent;
import static com.example.alumnos.alumni.Fragments.CreateEventsFragments.EventsTypesFragment2.id_group;
import static com.example.alumnos.alumni.Fragments.CreateEventsFragments.EventsTypesFragment3.eventDescription;
import static com.example.alumnos.alumni.Fragments.CreateEventsFragments.EventsTypesFragment3.eventTile;
import static com.example.alumnos.alumni.Fragments.CreateEventsFragments.EventsTypesFragment3.keepAllDates;
import static com.example.alumnos.alumni.Fragments.CreateEventsFragments.Events_fragment.eventsArrayList;
import static com.example.alumnos.alumni.Activities.MainActivity.URL;
import static com.example.alumnos.alumni.Activities.ViewCommentsActivity.keepCommentText;
import static com.example.alumnos.alumni.Activities.ViewCommentsActivity.textComment;
import static com.example.alumnos.alumni.Fragments.PanelFragments.Notes_Fragment.notasArrayList;


public class GlobalRequests {

    ApiAlumni api;
    Retrofit retrofit;
    private SharedPreferences prefs;
    List<Comments> arrayComments = new ArrayList<>();
    List<Comments> ultimoComment = new ArrayList<>();
    String tokenHc = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJhZG1pbkBnbWFpbC5jb20iLCJ1c2VybmFtZSI6IkFkbWluQ2V2IiwicGFzc3dvcmQiOiJhZG1pbiIsImlkX3JvbCI6MSwiaWRfcHJpdmFjaXR5IjoxLCJncm91cCI6bnVsbH0.wxAOVrPLO4dJ1Zl85B25a0dX8Yj5sITHATKJ3ylhaWY";

    private MyRequestListener listener;

    private CommentsListener listenerComments;

    private NotasListener notasListener;

    public static String myImage;

    public static Integer tipo;

    public  static String lastComment;


    public interface MyRequestListener
    {
        public void onGetEventsFinish();

    }

    public interface NotasListener
    {
        public void onGetNotasFinish();
    }


    public interface CommentsListener
    {
        public void onGetCommentsFinish();

    }

    public void setGlobalRequestsListener(MyRequestListener listener) {
        this.listener = listener;
    }



    public void setCommentsListener(CommentsListener listenerComments){
        this.listenerComments = listenerComments;
    }

    public void setNotasListener(NotasListener notasListener){
        this.notasListener = notasListener;
    }

    public GlobalRequests() {

        this.listener = null;
        this.listenerComments = null;
        this.notasListener = null;

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ApiAlumni.class);
    }


    public void CreateEvent(){

        keepAllDates();

        Call<JsonResponse> peticion = api.createEvent(eventTile,myImage,eventDescription,idTypeEvent,id_group,tokenHc);
        peticion.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                int code = response.body().getCode();
                JsonResponse json = response.body();
                Log.d ( "Respuesta del servidor", response.body ().getMessage () );

                switch (code) {
                    case 200:
                        String message = response.body ().getMessage ();
                        Log.d("RESOUESTA 200::", message);
                        Log.d ( "REQUEST STATE", "FIN DE LA PETICION");

                        //listener.onGetEventsFinish ();

                        break;
                    case 400:
                        // Toast.makeText ( MainActivity.this, errorMessage, Toast.LENGTH_SHORT ).show ();
                        String errorMessage = response.body ().getMessage ();
                        Log.d("RESOUESTA 200::", errorMessage);
                        break;

                    default:
                        //Toast.makeText ( MainActivity.this, errrorMessage, Toast.LENGTH_SHORT ).show ();
                        String defaultmsg = response.body ().getMessage ();
                        Log.d("RESOUESTA 200::", defaultmsg);
                }

            }
            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

                Log.d ("Failture message", "ON FAILTUREEEEEEEEEEE");
                Log.d ("EL FALLO ES", String.valueOf(t));

            }

        });
    }



    public void getlistOfEvents()
    {

        Call<JsonResponse> peticion = api.getEventsList (type,tokenHc);
        peticion.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                int code = response.body().getCode();
                JsonResponse json = response.body();
                Log.d ( "Respuesta del servidor", response.body ().getMessage () );

                switch (code) {
                    case 200:
                        String message = response.body ().getMessage ();
                        eventsArrayList = (ArrayList<Event>) response.body().getData().getEvent();
                        Integer size = eventsArrayList.size();

                        Collections.reverse(eventsArrayList);
                        listener.onGetEventsFinish ();

                        break;
                    case 400:
                        // Toast.makeText ( MainActivity.this, errorMessage, Toast.LENGTH_SHORT ).show ();
                        String errorMessage = response.body ().getMessage ();
                        break;

                    default:
                        //Toast.makeText ( MainActivity.this, errrorMessage, Toast.LENGTH_SHORT ).show ();
                        String defaultmsg = response.body ().getMessage ();
                }

            }
            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

                Log.d ("EL FALLO ES", String.valueOf(t));

            }

        });

    }

    public void deleteEvent()
    {

        Call<JsonResponse> peticion = api.delete (idEvent,tokenHc);
        peticion.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                int code = response.body().getCode();
                JsonResponse json = response.body();
                Log.d ( "Respuesta del servidor", response.body ().getMessage () );

                switch (code) {
                    case 200:
                        String message = response.body ().getMessage ();
                        Log.d("RESOUESTA 200::", message);
                        Log.d ( "REQUEST STATE", "FIN DE LA PETICION");

                        //listener.onGetEventsFinish ();

                        break;
                    case 400:
                        // Toast.makeText ( MainActivity.this, errorMessage, Toast.LENGTH_SHORT ).show ();
                        String errorMessage = response.body ().getMessage ();
                        Log.d("RESOUESTA 200::", errorMessage);
                        break;

                    default:
                        //Toast.makeText ( MainActivity.this, errrorMessage, Toast.LENGTH_SHORT ).show ();
                        String defaultmsg = response.body ().getMessage ();
                        Log.d("RESOUESTA 200::", defaultmsg);
                }

            }
            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

                Log.d ("Failture message", "ON FAILTUREEEEEEEEEEE");
                Log.d ("EL FALLO ES", String.valueOf(t));

            }

        });

    }

    public void getlistOfComments()
    {
        Log.d("****", "ID EVENTO"+idEvent);

        Call<JsonResponse> peticion = api.getCommentList (idEvent,tokenHc);
        peticion.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                int code = response.body().getCode();
                Log.d("holi", "holi" + code);
                JsonResponse json = response.body();
                Log.d ( "Respuesta del ES", response.body ().getMessage () );

                switch (code) {
                    case 200:
                        String message = response.body ().getMessage ();
                        Log.d("RESPUESTA 200::", message);

                        commentsArrayList = (ArrayList<Comments>) response.body().getData().getComments();
                        listenerComments.onGetCommentsFinish();
                        Collections.reverse(commentsArrayList);

                        Integer size = commentsArrayList.size();

                        Log.d("size",""+size);
                        if (size <=0){

                            lastComment = "Aún no hay comentarios";
                            return;

                        }else{

                            lastComment = commentsArrayList.get(size-1).getDescription();

                            Log.d("Ultimo comment",""+commentsArrayList.get(size-1).getDescription());


                        }



                        break;
                    case 400:
                        // Toast.makeText ( MainActivity.this, errorMessage, Toast.LENGTH_SHORT ).show ();
                        String errorMessage = response.body ().getMessage ();
                        Log.d("RESPUESTA 400::", errorMessage);
                        break;

                    default:
                        //Toast.makeText ( MainActivity.this, errrorMessage, Toast.LENGTH_SHORT ).show ();
                        String defaultmsg = response.body ().getMessage ();
                        Log.d("RESOUESTA DEFAULT::", defaultmsg);
                }

            }
            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

                Log.d ("Failture message", "ON FAILTUREEEEEEEEEEE");
                Log.d ("EL FALLO ES", String.valueOf(t));

            }

        });


    }

    public void deleteComment(Integer idComment)
    {

        Call<JsonResponse> peticion = api.deleteComment(idComment,tokenHc);
        peticion.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                int code = response.body().getCode();
                JsonResponse json = response.body();
                Log.d ( "Respuesta del servidor", response.body ().getMessage () );

                switch (code) {
                    case 200:
                        String message = response.body ().getMessage ();
                        Log.d("RESOUESTA 200::", message);
                        Log.d ( "REQUEST STATE", "FIN DE LA PETICION");

                        //listener.onGetEventsFinish ();

                        break;
                    case 400:
                        // Toast.makeText ( MainActivity.this, errorMessage, Toast.LENGTH_SHORT ).show ();
                        String errorMessage = response.body ().getMessage ();
                        Log.d("RESOUESTA 200::", errorMessage);
                        break;

                    default:
                        //Toast.makeText ( MainActivity.this, errrorMessage, Toast.LENGTH_SHORT ).show ();
                        String defaultmsg = response.body ().getMessage ();
                        Log.d("RESOUESTA 200::", defaultmsg);
                }

            }
            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

                Log.d ("Failture message", "ON FAILTUREEEEEEEEEEE");
                Log.d ("EL FALLO ES", String.valueOf(t));

            }

        });

    }
    public void searchEvent()
    {

        Call<JsonResponse> peticion = api.searchEvent (search,0, tokenHc);
        peticion.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                Log.d("respuesta", response.body().getMessage());

                int code = response.body().getCode();
                JsonResponse json = response.body();
                Log.d ( "Respuesta del servidor", response.body ().getMessage () );

                switch (code) {
                    case 200:
                        String message = response.body ().getMessage ();
                        eventsArrayList = (ArrayList<Event>) response.body().getData().getEvent();
                        Integer size = eventsArrayList.size();

                        Collections.reverse(eventsArrayList);
                        listener.onGetEventsFinish ();

                        break;
                    case 400:
                        // Toast.makeText ( MainActivity.this, errorMessage, Toast.LENGTH_SHORT ).show ();
                        String errorMessage = response.body ().getMessage ();
                        break;

                    default:
                        //Toast.makeText ( MainActivity.this, errrorMessage, Toast.LENGTH_SHORT ).show ();
                        String defaultmsg = response.body ().getMessage ();
                }

            }
            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

                Log.d ("EL FALLO ES", String.valueOf(t));

            }

        });

    }


    public void createComment(){
        keepCommentText();
        Call<JsonResponse> peticion = api.createComment("Titulo", textComment, idEvent, tokenHc);
        peticion.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                int code = response.body().getCode();
                JsonResponse json = response.body();
                Log.d ( "Respuesta del servidor", response.body ().getMessage () );
                switch (code) {
                    case 200:
                        String message = response.body ().getMessage ();

                        Log.d("200 CREAR COMENTARIO:", message);
                        Log.d ( "STATE CREAR COMENTARIO", "FIN DE LA PETICION");
                        break;
                    case 400:
                        String errorMessage = response.body ().getMessage ();
                        Log.d("400 CREAR COMENTARIO::", errorMessage);
                        break;
                    default:
                        String defaultmsg = response.body ().getMessage ();
                        Log.d("DEFAULT CREAR COMMENT::", defaultmsg);
                }

            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.d ("Failure CRCOMENT", "ON FAILTUREEEEEEEEEEE");
                Log.d ("EL FALLO EN CRCOMENT", String.valueOf(t));
            }
        });
    }

    public void getlistOfNotas()
    {

        Call<JsonResponse> peticion = api.getNotasList (tokenHc);
        peticion.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                int code = response.body().getCode();
                JsonResponse json = response.body();
                Log.d ( "Respuesta del servidor", response.body ().getMessage () );

                switch (code) {
                    case 200:
                        String message = response.body ().getMessage ();
                        notasArrayList = (ArrayList<Notas>) response.body().getData().getNota();
                        Integer size = notasArrayList.size();

                        notasListener.onGetNotasFinish();

                        break;
                    case 400:
                        // Toast.makeText ( MainActivity.this, errorMessage, Toast.LENGTH_SHORT ).show ();
                        String errorMessage = response.body ().getMessage ();
                        break;

                    default:
                        //Toast.makeText ( MainActivity.this, errrorMessage, Toast.LENGTH_SHORT ).show ();
                        String defaultmsg = response.body ().getMessage ();
                }

            }
            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

                Log.d ("EL FALLO ES", String.valueOf(t));

            }

        });

    }
    public void createDetailComment(){
        keepDetailCommentText();
        Call<JsonResponse> peticion = api.createDetailComment("Titulo", textDetailComment, idEvent, tokenHc);
        peticion.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                int code = response.body().getCode();
                JsonResponse json = response.body();
                Log.d ( "Respuesta del servidor", response.body ().getMessage () );
                switch (code) {
                    case 200:
                        String message = response.body ().getMessage ();
                        Log.d("200 CREAR COMENTARIO:", message);
                        Log.d ( "STATE CREAR COMENTARIO", "FIN DE LA PETICION");
                        break;
                    case 400:
                        String errorMessage = response.body ().getMessage ();
                        Log.d("400 CREAR COMENTARIO::", errorMessage);
                        break;
                    default:
                        String defaultmsg = response.body ().getMessage ();
                        Log.d("DEFAULT CREAR COMMENT::", defaultmsg);
                }

            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.d ("Failure CRCOMENT", "ON FAILTUREEEEEEEEEEE");
                Log.d ("EL FALLO EN CRCOMENT", String.valueOf(t));
            }
        });
    }

    public void getNotasEval()
    {

        Call<JsonResponse> peticion = api.getNotasEval (tokenHc);
        peticion.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                int code = response.body().getCode();
                JsonResponse json = response.body();
                Log.d ( "Respuesta del servidor", response.body ().getMessage () );

                switch (code) {
                    case 200:
                        String message = response.body ().getMessage ();
                        notasArrayList = (ArrayList<Notas>) response.body().getData().getNota();
                        Integer size = notasArrayList.size();

                        notasListener.onGetNotasFinish();

                        break;
                    case 400:
                        // Toast.makeText ( MainActivity.this, errorMessage, Toast.LENGTH_SHORT ).show ();
                        String errorMessage = response.body ().getMessage ();
                        break;

                    default:
                        //Toast.makeText ( MainActivity.this, errrorMessage, Toast.LENGTH_SHORT ).show ();
                        String defaultmsg = response.body ().getMessage ();
                }

            }
            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

                Log.d ("EL FALLO ES", String.valueOf(t));

            }

        });

    }





}