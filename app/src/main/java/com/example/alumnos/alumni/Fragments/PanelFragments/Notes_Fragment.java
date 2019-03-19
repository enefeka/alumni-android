package com.example.alumnos.alumni.Fragments.PanelFragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.alumnos.alumni.Adapters.MyAdapter;
import com.example.alumnos.alumni.Api.GlobalRequests;
import com.example.alumnos.alumni.R;


import com.example.alumnos.alumni.Models.Notas;

import java.util.ArrayList;
import com.example.alumnos.alumni.Adapters.NotasListAdapter;



public class Notes_Fragment extends Fragment implements View.OnClickListener{

    Button evBtn;
    Button oneBtn;
    NotasListAdapter notasListAdapter;
    GlobalRequests globalRequests = new GlobalRequests();
    int backgroundSelected;
    int notBack;

    public Notes_Fragment() {
        // Required empty public constructor
    }

    public static ArrayList<Notas> notasArrayList = new ArrayList<Notas>();
    public static ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        backgroundSelected = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colordarkGrey);
        notBack = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.grey);
        listView = view.findViewById(R.id.listaNotas);
        evBtn = view.findViewById(R.id.btnev);
        oneBtn = view.findViewById(R.id.onebtn);

        globalRequests.setNotasListener ( new GlobalRequests.NotasListener () {
            @Override
            public void onGetNotasFinish() {
                notasListAdapter = new NotasListAdapter(getActivity().getApplicationContext(), R.layout.notes_item, notasArrayList);
                listView.setAdapter(notasListAdapter);
            }
        } );
        evBtn.setOnClickListener (this);
        oneBtn.setOnClickListener(this);
        globalRequests.getlistOfNotas();



        return view;
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnev:
                globalRequests.getNotasEval();
                notasListAdapter.notifyDataSetChanged();
                evBtn.setBackgroundColor(backgroundSelected);
                oneBtn.setBackgroundColor(notBack);

                break;
            case R.id.onebtn:
                globalRequests.getlistOfNotas();
                notasListAdapter.notifyDataSetChanged();
                evBtn.setBackgroundColor(notBack);
                oneBtn.setBackgroundColor(backgroundSelected);

                break;


        }
    }

}
