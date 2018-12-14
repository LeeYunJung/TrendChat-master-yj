package com.google.firebase.codelab.friendlychat;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import android.support.annotation.NonNull;

import org.w3c.dom.Text;

public class My10Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mytitle;
        TextView mydescriptioin;
        TextView mydate;
        TextView mylink;


        MyViewHolder(View v) {
            super(v);

            mytitle = v.findViewById(R.id.title);
            mydescriptioin = v.findViewById(R.id.description);
            mydate = v.findViewById(R.id.date);
            mylink = v.findViewById(R.id.link);
        }
    }

    private ArrayList<Item> MyItemArrayList;
    // Provide a suitable constructor (depends on the kind of dataset)
    My10Adapter(ArrayList<Item> ItemArrayList) {
        MyItemArrayList = ItemArrayList;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_board10s, parent, false);

        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.mytitle.setText(MyItemArrayList.get(position).title);
        myViewHolder.mydescriptioin.setText(MyItemArrayList.get(position).description);
        myViewHolder.mydate.setText(MyItemArrayList.get(position).date);
        myViewHolder.mylink.setText(MyItemArrayList.get(position).link);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
//        System.out.println("몇개 " + MyItemArrayList.size());
        return MyItemArrayList.size();
    }

}