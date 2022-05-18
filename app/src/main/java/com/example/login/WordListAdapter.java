package com.example.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private  String[] wordList;
    private LayoutInflater mInflater;
    static int mPosition;




    public WordListAdapter(Context context,String[] wordList) {
        mInflater = LayoutInflater.from(context);


        this.wordList = wordList;


    }

    public int getmPosition() {
        return mPosition;
    }


    class WordViewHolder extends RecyclerView.ViewHolder {
        public final TextView descrpcion;
        final WordListAdapter mAdapter;

        public WordViewHolder(View itemView, WordListAdapter adapter) {
            super(itemView);
            descrpcion = itemView.findViewById(R.id.tvDescripcioPostPP);
            this.mAdapter = adapter;

        }





        private void makeToastmsg(String element, View itemView) {
            CharSequence text = element;
            Toast.makeText(itemView.getContext(), text, Toast.LENGTH_SHORT).show();
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
        String mCurrent = wordList[position];
        holder.descrpcion.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return wordList.length;

    }

}

