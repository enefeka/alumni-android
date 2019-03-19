package com.example.alumnos.alumni.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alumnos.alumni.Api.GlobalRequests;
import com.example.alumnos.alumni.Models.Comments;
import com.example.alumnos.alumni.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.alumnos.alumni.Activities.EditProfileActivity.uri;

public class CommentListAdapter extends BaseAdapter{



    private List<Comments> comments;

    private int layout;

    public static String desc;

    private Context context;

    String userName;

    public CommentListAdapter(Context context, int layout, List<Comments> comments){

        this.context = context;
        this.layout = layout;
        this.comments = comments;


    }

    @Override
    public int getCount() {
        return comments.size();

    }

    @Override
    public Comments getItem(int position) {
        return this.comments.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

//    private static class ViewHolder{
//        TextView description, title, timestamp, reply;
//
//        CircleImageView profileImage;
//    }

    public View getView(int position,View convertView,ViewGroup viewGroup) {


        View v = convertView;

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        v = layoutInflater.inflate(R.layout.listcomments_item, null);

        String currentUserName = String.valueOf(comments.get(position).getUsername());
        String currentDescription = String.valueOf(comments.get(position).getDescription());
        String currentImage = String.valueOf(comments.get(position).getImage());
        String currentDate = String.valueOf(comments.get(position).getDate());

        TextView textViewTitle = v.findViewById(R.id.comment_username);
        TextView textViewDescription = v.findViewById(R.id.description);
        TextView textViewDate = v.findViewById(R.id.comment_time_posted);
        Button deleteCommentBtn = v.findViewById(R.id.deleteCommentBtn);

        CircleImageView commentPerfilImage = (CircleImageView)v.findViewById(R.id.comment_profile_image);
        commentPerfilImage.setImageURI(uri);

        //Picasso.get().load(currentImage).into((commentPerfilImage));
        textViewTitle.setText(currentUserName);
        textViewDescription.setText(currentDescription);
        textViewDate.setText(currentDate);
        int  lastposition = getCount()-1;
        desc = comments.get(lastposition).getDescription();
        Log.d("posicion", desc);

        deleteCommentBtn.setTag(comments.get(position).getId());
        deleteCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer idComment = (Integer) v.getTag();
                delete(idComment);


            }

        });


        return v;
    }

    public void delete(Integer idComment){
        GlobalRequests globalRequests = new GlobalRequests();
        globalRequests.deleteComment(idComment);

        for (int i = 0; i < comments.size(); i++){

            int id = comments.get(i).getId();

            if(idComment == id)
            {
                comments.remove(i);
                notifyDataSetChanged();
                break;
            }


        }




    }









}
