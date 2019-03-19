package com.example.alumnos.alumni.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alumnos.alumni.Models.Event;
import com.example.alumnos.alumni.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import static com.example.alumnos.alumni.Adapters.CommentListAdapter.desc;

public class  MyAdapter extends BaseAdapter {

    private Context context;
    private  int layout;

    //private List<String> names;
    private List<Event> events;
    public MyAdapter(Context context, int layout, List<Event> events){

        this.context = context;
        this.layout = layout;
        this.events = events;

    }


    @Override
    public int getCount() {
        return this.events.size();
    }

    @Override
    public Object getItem(int position) {
        return this.events.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View v = convertView;
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        v = layoutInflater.inflate(R.layout.list_item, null);
        String currentName = String.valueOf(events.get(position).getTitle());
        String description = String.valueOf(events.get(position).getDescription());
        String currentImage = String.valueOf(events.get(position).getImage());
        Integer type = Integer.valueOf(events.get(position).getId_type());
        Integer typeLogo = type;
        CardView  cardView = (CardView)v.findViewById(R.id.cardView);
        ImageView imageView = v.findViewById(R.id.imageTypeEvent);
        Log.d("TIPASOS", "tipinis" +type);


        switch(type) {
            case 1:
                cardView.setCardBackgroundColor(0xFF5E797A);
                imageView.setBackgroundResource(R.drawable.location);
                break;
            case 2:
                cardView.setCardBackgroundColor(0xFF40C0C6);
                imageView.setBackgroundResource(R.drawable.jobs);
                break;
            case 3:
                cardView.setCardBackgroundColor(0xFF4F5E74);
                imageView.setBackgroundResource(R.drawable.notifif);
                break;
            case 4:
                cardView.setCardBackgroundColor(0xFF62BD91);
                imageView.setBackgroundResource(R.drawable.news);
                break;

            default:

        }

        //ImageView imagen = null;
        //ImageView newImage = Picasso.get().load(currentImage).into((imagen);

        TextView textViewTitle = (TextView)v.findViewById(R.id.titleEvent);
        TextView textViewDescript = (TextView)v.findViewById(R.id.descriptionEvent);
        ImageView eventImage = (ImageView)v.findViewById(R.id.imageListEvent);


        //SET ALL ELEMENTS OF LIST
            Picasso.get().load(currentImage).into((eventImage));
            textViewDescript.setText(currentName);
            textViewTitle.setText(description);
        return  v;

    }
}
