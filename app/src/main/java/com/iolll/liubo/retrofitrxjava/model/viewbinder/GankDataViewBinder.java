package com.iolll.liubo.retrofitrxjava.model.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iolll.liubo.retrofitrxjava.R;
import com.iolll.liubo.retrofitrxjava.model.bean.GankData;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by LiuBo on 2018/5/25.
 */
public class GankDataViewBinder extends ItemViewBinder<GankData, GankDataViewBinder.ViewHolder> {

    private Context context;
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_gank_data, parent, false);
        context = parent.getContext();
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GankData gankData) {
        holder.title.setText(gankData.getDesc());
        holder.name.setText(gankData.getWho());
        holder.type.setText(gankData.getType());
        if (null!=gankData.getImages() && 0!=gankData.getImages().size()) {
            Glide.with(context).load(gankData.getImages().get(0)).asBitmap().into(holder.imageView);
        }else{
            holder.imageView.setImageBitmap(null);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.type)
        TextView type;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
