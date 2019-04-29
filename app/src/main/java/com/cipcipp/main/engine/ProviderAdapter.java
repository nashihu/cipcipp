package com.cipcipp.main.engine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.cipcipp.main.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ViewHolder> {
    private ItemClickListener mClickListener;
    private LayoutInflater mInflater;
    private List<String> provider_title;
    private List<String> provider_image_id;

    public ProviderAdapter(Context context, List<String> provider_title, List<String> provider_image_id) {
        this.mInflater = LayoutInflater.from(context);
        this.provider_title = provider_title;
        this.provider_image_id = provider_image_id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.provider,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProviderAdapter.ViewHolder holder, int position) {
        int image = R.mipmap.ic_launcher;
        String title = provider_title.get(position);
        String url = provider_image_id.get(position);
        Log.v("asdfasdf ProviderAdapte",url);
        holder.prov_image.getResources().getDrawable(image);
        holder.prov_text.setText(title);
        Picasso
                .get()
                .load(url)
                .resize(75,75)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.prov_image)
        ;
    }

    @Override
    public int getItemCount() {
        return provider_image_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView prov_image;
        TextView prov_text;
        private ViewHolder(View itemView) {
            super(itemView);
            prov_image = itemView.findViewById(R.id.provider_ic);
            prov_text = itemView.findViewById(R.id.provider_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mClickListener != null) mClickListener.onItemClick(view,getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public String getItem(int id) {
        // convenience method for getting data at click position
        return provider_title.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        // allows clicks events to be caught
        this.mClickListener = itemClickListener;
    }
}