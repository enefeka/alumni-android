package com.example.alumnos.alumni.Fragments.CreateEventsFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.alumnos.alumni.Activities.MainActivity;
import com.example.alumnos.alumni.Api.GlobalRequests;
import com.example.alumnos.alumni.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment5 extends Fragment implements View.OnClickListener {



    Button buttonCreateEvent;
    GlobalRequests globalRequests;

    public EventsFragment5() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d ( "hola que ase","EVENTS TYPES FRAGMENT 6" );

        View view = inflater.inflate(R.layout.fragment_events_fragment5, container, false);

        buttonCreateEvent = view.findViewById ( R.id.botonCrearEvento);
        buttonCreateEvent.setOnClickListener (this);

        globalRequests = new GlobalRequests ();


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.botonCrearEvento:
                Log.d ( "CREAR EVENT","SE VA A CREAR EL EVENTO" );

                globalRequests.CreateEvent ();
                goToMain();


                break;
        }
    }

    private void goToMain()
    {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}