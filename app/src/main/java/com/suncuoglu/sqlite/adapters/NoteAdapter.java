package com.suncuoglu.sqlite.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suncuoglu.sqlite.R;
import com.suncuoglu.sqlite.models.User;

import java.util.ArrayList;

/*
12.01.2021 by Şansal Uncuoğlu
*/

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    public Context context;
    public ArrayList<User> userArrayList;
    public View.OnClickListener mOnItemClickListener;

    public NoteAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note_layout, parent, false);
        return new NoteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        holder.user_name.setText(userArrayList.get(position).getNote_name()+" "+userArrayList.get(position).getNote_surname());
        holder.user_nickname.setText(userArrayList.get(position).getNote_username());
        holder.user_birthday.setText(userArrayList.get(position).getNote_birthday());

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_name;
        TextView user_nickname;
        TextView user_birthday;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user_name = itemView.findViewById(R.id.user_name);
            user_nickname = itemView.findViewById(R.id.user_nickname);
            user_birthday = itemView.findViewById(R.id.user_birthday);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }
}