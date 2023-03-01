package ru.myitschool.lesson20230214;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.myitschool.lesson20230214.databinding.ItemProductBinding;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private static final ArrayList<ProductData> data = new ArrayList<>();

    interface OnProductDataClickListener {
        void onProductClick(ViewHolder holder);
    }

    private final OnProductDataClickListener clickListener;

    public ProductAdapter(OnProductDataClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemProductBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false)
                .getRoot());
    }

    public void setData(List<ProductData> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onProductClick(holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeItemByPosition(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductBinding itemBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBinding = ItemProductBinding.bind(itemView);
        }

        public void bind(ProductData product) {
            itemBinding.title.setText(product.getName());
            String description = product.getDescription();
            if (description.isEmpty()) {
                itemBinding.description.setVisibility(View.GONE);
            } else {
                itemBinding.description.setVisibility(View.VISIBLE);
                itemBinding.description.setText(product.getDescription());
            }
            itemBinding.count.setText(String.valueOf(product.getCount()));
        }
    }
}
