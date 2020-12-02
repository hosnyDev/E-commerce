package com.hosnydevtest.shopapp.fragment.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hosnydevtest.shopapp.R;
import com.hosnydevtest.shopapp.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>
        implements Filterable {

    private Context context;
    private List<CategoryModel> list;
    private List<CategoryModel> listFilter;

    public CategoryAdapter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.list = list;
        listFilter = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_format, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setName(list.get(position).getName());
        holder.setImage(list.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.category_image_item);
            textView = itemView.findViewById(R.id.category_name_item);
        }

        private void setName(String name) {
            textView.setText(name);
        }

        private void setImage(String url) {
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_product)
                    .into(imageView);
        }


    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterSearch = constraint.toString().toLowerCase().trim();

            if (filterSearch.isEmpty()) {

                list = listFilter;

            } else {

                List<CategoryModel> categoryModels = new ArrayList<>();

                for (CategoryModel model : listFilter) {

                    if (model.getName().toLowerCase().contains(filterSearch)) {
                        categoryModels.add(model);
                    }
                }
                list = categoryModels;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = list;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            list = (ArrayList<CategoryModel>) results.values;
            notifyDataSetChanged();

        }
    };

}
