package com.worldofuiux.fashionprofileuikit.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.renderscript.RenderScript;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.worldofuiux.fashionprofileuikit.MainActivity;
import com.worldofuiux.fashionprofileuikit.R;
import com.worldofuiux.fashionprofileuikit.model.collection;

import java.util.List;
import java.util.MissingFormatArgumentException;

/**
 * Created by World Of UI/UX on 17/4/19.
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.MyViewHolder> {

    private Context mContext;
    private List<collection> albumList;
    private Intent intent;
    private Activity activity;
    private boolean canPay;
    int[] myImageList = {R.drawable.gradient1, R.drawable.gradient2, R.drawable.gradient3,
            R.drawable.gradient4, R.drawable.gradient5}; // 컬랙션 배경 색깔

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

    public void setPay(boolean canPay) {
        this.canPay = canPay;
    }

    public CollectionAdapter(Context mContext, List<collection> albumList, Intent intent, Activity activity) {
        this.mContext = mContext;
        this.albumList = albumList;
        this.intent = intent;
        this.activity = activity;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_collection, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        collection collection = albumList.get(position);
        holder.layout.setTag(String.valueOf(collection.getTag()));
        holder.imgThumbnail.setBackgroundDrawable(mContext.getApplicationContext().getDrawable(collection.getImage()));
        holder.Tagtext.setTag(String.valueOf(collection.getTag()));
        holder.Tagtext.setText(String.valueOf(collection.getTag()));
        holder.tvTitle.setText(collection.getTitle());
        holder.tvDescription.setText(collection.getDescription());

        holder.Tagtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) view.findViewById(R.id.Tag);
                String s = textView.getText().toString();
                if (canPay == true) {
                    if (s.equals("0")) {
                        intent.putExtra("card", "현대카드");
                        intent.putExtra("date", __Data().toString());
                    } else if (s.equals("1")) {
                        intent.putExtra("card", "국민카드");
                        intent.putExtra("date", __Data().toString());
                    } else if (s.equals("2")) {
                        intent.putExtra("card", "비자카드");
                        intent.putExtra("date", __Data().toString());
                    }
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "결제 on/off 스위치를 on으로 변경해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
    public String __Data() {
        java.util.Date currentDate = new java.util.Date();
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy년MM월dd일 HH시mm분ss초");
        String dateString = format.format(currentDate);
        System.out.println(dateString);
        return dateString;
    }

}