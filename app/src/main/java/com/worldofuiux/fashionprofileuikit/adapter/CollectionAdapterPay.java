package com.worldofuiux.fashionprofileuikit.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.worldofuiux.fashionprofileuikit.R;
import com.worldofuiux.fashionprofileuikit.model.collectionpay;

import java.util.List;

public class CollectionAdapterPay extends RecyclerView.Adapter<CollectionAdapterPay.MyViewHolder> {

    private Context mContext;
    private List<collectionpay> albumList;

    int[] myImageList = {R.drawable.gradient1, R.drawable.gradient2, R.drawable.gradient3,
            R.drawable.gradient4, R.drawable.gradient5};

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvDescription, Tagtext;
        public ImageView imgThumbnail;
        public View viewGradient;
        RelativeLayout layout;

        public MyViewHolder(View view) {
            super(view);
            Tagtext = (TextView) view.findViewById(R.id.Tag);
            tvTitle = (TextView) view.findViewById(R.id.tvtitle);
            tvDescription = (TextView) view.findViewById(R.id.tvdesc);
            imgThumbnail = (ImageView) view.findViewById(R.id.imgitem);
            viewGradient = (View) view.findViewById(R.id.viewgradient);
            layout = (RelativeLayout) view.findViewById(R.id.rlmain);
        }
    }
    public CollectionAdapterPay(Context mContext, List<collectionpay> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_collection, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        collectionpay collection = albumList.get(position);

        holder.Tagtext.setText(" ");
        holder.tvTitle.setText(collection.getTitle());
        holder.tvDescription.setText(collection.getDescription());
        holder.viewGradient.setBackgroundResource(myImageList[position % myImageList.length]);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { }
        });

        holder.imgThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
