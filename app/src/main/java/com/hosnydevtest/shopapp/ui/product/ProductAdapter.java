package com.hosnydevtest.shopapp.ui.product;

import android.content.Context;
import android.content.Intent;
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
import com.hosnydevtest.shopapp.model.ProductModel;
import com.hosnydevtest.shopapp.ui.DetailsProductActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
        implements Filterable {

    private Context context;
    private List<ProductModel> list;
    private List<ProductModel> listFilter;

    public ProductAdapter(Context context, List<ProductModel> list) {
        this.context = context;
        this.list = list;
        listFilter = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.format_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setName(list.get(position).getName());
        holder.setPrice(list.get(position).getPrice());
        holder.setLogo(list.get(position).getLogo());

        holder.btnDetails.setOnClickListener(v -> {

            Intent intent = new Intent(context, DetailsProductActivity.class);

            intent.putExtra("name", list.get(position).getName());
                intent.putExtra("image1", list.get(position).getImage1());
            intent.putExtra("image2", list.get(position).getImage2());
            intent.putExtra("image3", list.get(position).getImage3());
            intent.putExtra("details", list.get(position).getDetails());
            intent.putExtra("price", list.get(position).getPrice());

            context.startActivity(intent);


        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productName, productPrice;

        private TextView btnDetails, btnBuy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.image_product_item);
            productName = itemView.findViewById(R.id.name_product_item);
            productPrice = itemView.findViewById(R.id.price_product_item);

            btnDetails = itemView.findViewById(R.id.details_product_item);
            btnBuy = itemView.findViewById(R.id.buy_product_item);

        }

        private void setName(String name) {
            productName.setText(name);
        }

        private void setPrice(int price) {
            productPrice.setText(price + "LE");
        }

        private void setLogo(String url) {
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_product)
                    .into(productImage);
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

                List<ProductModel> ProductModels = new ArrayList<>();

                for (ProductModel model : listFilter) {

                    if (model.getName().toLowerCase().contains(filterSearch)) {
                        ProductModels.add(model);
                    }
                }
                list = ProductModels;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = list;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            list = (ArrayList<ProductModel>) results.values;
            notifyDataSetChanged();

        }
    };

}
