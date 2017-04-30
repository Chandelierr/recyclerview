package com.chandelier.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<picRes> data;
    private LayoutInflater inflater;
    //private RecyclerView mRecyclerView;//用来计算Child位置
    private Context context;

    public myAdapter(Context context,List<picRes> data) {
        this.context = context;
        this.data = data;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv;

        public myViewHolder(View itemView) {
            super(itemView);
            iv= (ImageView) itemView.findViewById(R.id.pic_id);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Picasso.with(context).load(data.get(position).getUrl()).into(((myViewHolder)holder).iv);
    }

    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }

    /**
     * 适配器绑定到RecyclerView 的时候，回将绑定适配器的RecyclerView 传递过来
     */
   /* @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        //mRecyclerView=recyclerView;
    }*/

}