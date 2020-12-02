package com.hosnydevtest.shopapp.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hosnydevtest.shopapp.R;
import com.hosnydevtest.shopapp.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;

    private ProductAdapter adapter;
    private List<ProductModel> list;

    private FirebaseFirestore firestore;

    private String getCategoryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Toolbar toolbar = findViewById(R.id.toolbar_product);
        refreshLayout = findViewById(R.id.swipe_product);
        TextView title_product = findViewById(R.id.title_product);
        SearchView searchView = findViewById(R.id.search_product);
        RecyclerView recyclerView = findViewById(R.id.recycler_product);
        progressBar = findViewById(R.id.progressbar_product);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;

        Intent intent = getIntent();

        getCategoryId = intent.getStringExtra("id");
        String getCategoryName = intent.getStringExtra("name");

        getSupportActionBar().setTitle(getCategoryName);
        title_product.setText(getCategoryName + " Category");

        toolbar.setNavigationOnClickListener(v -> finish());

        firestore = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        list = new ArrayList<>();
        adapter = new ProductAdapter(this, list);
        recyclerView.setAdapter(adapter);

        getProductData(0);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        refreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.colorPrimaryDark),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary)
        );

        refreshLayout.setOnRefreshListener(() -> {
            list.clear();
            getProductData(1);
        });

    }

    private void getProductData(int stateSwipe) {

        progressBar.setVisibility(View.VISIBLE);
        firestore.collection("product")
                .orderBy("time", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {

                    if (error == null) {

                        if (value != null) {

                            for (DocumentChange documentChange : value.getDocumentChanges()) {


                                String id = documentChange.getDocument().getString("id_category");

                                assert id != null;
                                if (id.equals(getCategoryId)) {

                                    ProductModel productModel = documentChange.getDocument().toObject(ProductModel.class);
                                    list.add(productModel);
                                    adapter.notifyDataSetChanged();
                                }

                            }

                            progressBar.setVisibility(View.GONE);

                            if (stateSwipe == 1)
                                refreshLayout.setRefreshing(false);

                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(this, "No data in product", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });


    }


}