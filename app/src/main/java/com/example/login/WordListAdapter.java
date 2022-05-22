package com.example.login;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private ArrayList<Post> posts;
    private LayoutInflater mInflater;
    static int mPosition;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef =  storage.getReference();
    public WordListAdapter(Context context, ArrayList<Post> posts) {
        mInflater = LayoutInflater.from(context);
        this.posts = posts;
    }


    class WordViewHolder extends RecyclerView.ViewHolder {
        public final TextView descrpcion;
        public final TextView nomUser;
        public final TextView nomJoc;
        public final ImageView iconJoc;
        final WordListAdapter mAdapter;

        public WordViewHolder(View itemView, WordListAdapter adapter) {
            super(itemView);
            descrpcion = itemView.findViewById(R.id.tvDescripcioPostPP);
            nomJoc = itemView.findViewById(R.id.tvNomJocPP);
            nomUser = itemView.findViewById(R.id.tvNomUserPP);
            iconJoc = itemView.findViewById(R.id.ivImatgePostPP);
            this.mAdapter = adapter;

        }


    }


    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                             int viewType) {
        View mItemView = mInflater.inflate(R.layout.wordlist_item,
                parent, false);

        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder,
                                 int position) {
        String descripcio = posts.get(position).getDecJoc();
        holder.descrpcion.setText(descripcio);
        String nomUser = posts.get(position).getNomUser();
        holder.nomUser.setText(nomUser);
        String nomJoc = posts.get(position).getNomJoc();
        holder.nomJoc.setText(nomJoc);
        String ComNomImge = nomJoc+nomUser;



        StorageReference photoReference= storageRef.child("imagesPost/"+ComNomImge);
        Log.d("LOG_TAG", ">>>>>>>>>>>>>>>>>>>>>>>>>gdyh>>>>>>>>>>>" + photoReference);

        final long ONE_MEGABYTE = 1024 * 1024;
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.iconJoc.setImageBitmap(bmp);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
        }

    @Override
    public int getItemCount() {
        return posts.size();

    }

}

