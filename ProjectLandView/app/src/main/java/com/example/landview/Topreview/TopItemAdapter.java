package com.example.landview.Topreview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.landview.R;
import com.example.landview.TopReviewDetail;

import java.io.Serializable;
import java.util.List;

public class TopItemAdapter extends RecyclerView.Adapter<TopItemAdapter.TopItemViewholder>{
    Context mcontext;
    List<TopItem>topItemList;

    public TopItemAdapter(Context mcontext, List<TopItem> topItemList) {
        this.mcontext = mcontext;
        this.topItemList = topItemList;
    }

    @NonNull
    @Override
    public TopItemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_topreview,parent,false);
        return new TopItemViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopItemViewholder holder, int position) {
        final TopItem item = topItemList.get(position);
        holder.background.setImageResource(item.getBackground());
        holder.imgTym.setImageResource(item.getIcon());
        holder.txtName.setText(item.getName());
        holder.txtDes.setText(item.getTextDescription());
        //sét sự kienj chuyển data
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, TopReviewDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("topItem", item);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(topItemList!=null)
        {
            return topItemList.size();
        }
        return 0;
    }

    public class TopItemViewholder extends RecyclerView.ViewHolder{
        ImageView background,imgTym;
        TextView txtName,txtDes;
        LinearLayout linearLayout;
        public TopItemViewholder(@NonNull View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.imgTop);
            imgTym = itemView.findViewById(R.id.iconToptym);
            txtName = itemView.findViewById(R.id.textTop);
            txtDes = itemView.findViewById(R.id.txtDesTop);
            linearLayout = itemView.findViewById(R.id.layoutLinear);
        }
    }
}
