package ru.myitschool.lesson20230214.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.myitschool.lesson20230214.data.ProductData;
import ru.myitschool.lesson20230214.databinding.ItemProductBinding;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable {
    private static List<ProductData> data = new ArrayList<>();
    private static List<ProductData> filterData =new ArrayList<>() ;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filterData = data;
                } else {
                    List<ProductData> filteredList = new ArrayList<>();
                    for (ProductData row : data) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())
                                || row.getDescription().contains(charString)) {
                            filteredList.add(row);
                        }
                    }
                    filterData = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                filterData = (ArrayList<ProductData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

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
       // holder.bind(filterData.get(position));
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
       // return filterData.size();
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
