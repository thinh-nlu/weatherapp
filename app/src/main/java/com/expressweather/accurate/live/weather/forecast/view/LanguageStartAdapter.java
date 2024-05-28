// đảm bảo vai trò quản lý dữ liệu và tạo ViewHolder cho từng mục trong List
package com.expressweather.accurate.live.weather.forecast.view.language.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expressweather.accurate.live.weather.forecast.R;
import com.expressweather.accurate.live.weather.forecast.model.LanguageModel;

import java.util.List;
// Tạo Adapter để liên kết dữ liệu với ViewHolder.
public class LanguageStartAdapter extends RecyclerView.Adapter<LanguageStartAdapter.LanguageViewHolder> {
    private final List<LanguageModel> languageModelList; // lấy ds các language
    private final ILanguageItem iLanguageItem; // lấy từng item của ds
    private final Context context;

    public LanguageStartAdapter(List<LanguageModel> languageModelList, ILanguageItem listener, Context context) {
        this.languageModelList = languageModelList;
        this.iLanguageItem = listener;
        this.context = context;
    }
// tạo 1 viewHolder mới bằng cách inflate layout này và trả về viewHolder này
    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_language, parent, false);
        return new LanguageViewHolder(view);
    }

    // được gọi khi RecyclerView cần hiện thị data tại 1 pos cụ thể
    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
        LanguageModel languageModel = languageModelList.get(position);
        if (languageModel == null) {
            return;
        }
        holder.tvLang.setText(languageModel.getName());
        if (languageModel.getActive()) {
            holder.imgActive.setImageResource(R.drawable.ic_radio_selected);
        } else {
            holder.imgActive.setImageResource(R.drawable.ic_radio_non_select);
        }

        switch (languageModel.getCode()) {
            case "fr":
                Glide.with(context).asBitmap()
                        .load(R.drawable.ic_french)
                        .into(holder.icLang);
                break;
            case "es":
                Glide.with(context).asBitmap()
                        .load(R.drawable.ic_spanish)
                        .into(holder.icLang);
                break;
            case "hi":
                Glide.with(context).asBitmap()
                        .load(R.drawable.ic_hindi)
                        .into(holder.icLang);
                break;
            case "pt":
                Glide.with(context).asBitmap()
                        .load(R.drawable.ic_portuguese)
                        .into(holder.icLang);
                break;
            case "en":
                Glide.with(context).asBitmap()
                        .load(R.drawable.ic_english)
                        .into(holder.icLang);
                break;
        }

        holder.layoutItem.setOnClickListener(v -> {
            setCheck(languageModel.getCode());
            iLanguageItem.onClickItemLanguage(languageModel.getCode());
            notifyDataSetChanged();
        });

    }
// trả về số lượng item trong list
    @Override
    public int getItemCount() {
        if (languageModelList != null) {
            return languageModelList.size();
        } else {
            return 0;
        }
    }

    public void setCheck(String code) {
        for (LanguageModel item : languageModelList) {
            item.setActive(item.getCode().equals(code));
        }
        notifyDataSetChanged();
    }

    public interface ILanguageItem {
        void onClickItemLanguage(String code);
    }

    // tạo class LanguageViewHolder để quản lý các view hiển thị từng mục trong danh sách
    public static class LanguageViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvLang;
        private final ImageView imgActive;
        private final ImageView icLang;
        private final View layoutItem;

        public LanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            icLang = itemView.findViewById(R.id.img_language);
            tvLang = itemView.findViewById(R.id.tv_language);
            imgActive = itemView.findViewById(R.id.img_active);
            layoutItem = itemView.findViewById(R.id.view_item_language);
        }
    }
}
