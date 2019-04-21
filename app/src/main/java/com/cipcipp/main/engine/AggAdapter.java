package com.cipcipp.main.engine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cipcipp.main.R;

import java.util.List;

public class AggAdapter extends RecyclerView.Adapter<AggAdapter.ViewHolder> {
    private ItemClickListener mClickListener;
    private LayoutInflater mInflater;
    private List<String> nominal;
    private List<String> price;
    private List<String> provider_name;
    private List<String> provider_image_id;

    public AggAdapter(Context context, List<String> nominal, List<String> price, List<String> provider_name, List<String> provider_image_id) {
        this.mInflater = LayoutInflater.from(context);
        this.nominal = nominal;
        this.price = price;
        this.provider_name = provider_name;
        this.provider_image_id = provider_image_id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.agg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int image_id = Integer.parseInt(provider_image_id.get(position));
        String name = provider_name.get(position);
        String nomin = nominal.get(position);
        String pric = price.get(position);
        holder.provider_image.getResources().getDrawable(image_id);
        holder.provider_name.setText(name);
        holder.nominal.setText(nomin);
        holder.price.setText(pric);

    }

    @Override
    public int getItemCount() {
        return provider_image_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView provider_image;
        TextView nominal;
        TextView price;
        TextView provider_name;
        private ViewHolder(View itemView) {
            super(itemView);
            provider_image = itemView.findViewById(R.id.agg_provider_img);
            nominal = itemView.findViewById(R.id.agg_nominal);
            price = itemView.findViewById(R.id.agg_price);
            provider_name = itemView.findViewById(R.id.agg_provider_name);
//            itemView.findViewById(R.id.agg_provider_img).setOnClickListener(this);
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.agg_provider_img).setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mClickListener != null) mClickListener.onItemClick(view,getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if(mClickListener != null) mClickListener.onLongClick(view,getAdapterPosition());
            return true;
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);

        void onLongClick(View view, int position);
    }


    public Integer getItem(int id) {
        // convenience method for getting data at click position
        return Integer.parseInt(provider_image_id.get(id));
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        // allows clicks events to be caught
        this.mClickListener = itemClickListener;
    }
}
