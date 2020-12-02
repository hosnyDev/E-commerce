package com.hosnydevtest.shopapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hosnydevtest.shopapp.R;
import com.hosnydevtest.shopapp.model.ProductModel;
import com.hosnydevtest.shopapp.ui.product.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class OffersFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;

    private List<ProductModel> list;
    private ProductAdapter adapter;

    private FirebaseFirestore firestore;

    public OffersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout = view.findViewById(R.id.swipe_offers);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_offers);
        progressBar = view.findViewById(R.id.progress_offers);

        firestore = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        list = new ArrayList<>();
        adapter = new ProductAdapter(getActivity(), list);

        recyclerView.setAdapter(adapter);

        getProductData(0);

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

                                boolean isOffer = documentChange.getDocument().getBoolean("isOffer").booleanValue();


                                if (isOffer){
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
                            Toast.makeText(getActivity(), "No data in product", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });


    }

}