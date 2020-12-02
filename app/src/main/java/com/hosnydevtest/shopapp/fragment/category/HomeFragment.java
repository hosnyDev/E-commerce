package com.hosnydevtest.shopapp.fragment.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hosnydevtest.shopapp.R;
import com.hosnydevtest.shopapp.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;

    private CategoryAdapter adapter;
    private List<CategoryModel> list;

    private FirebaseFirestore firestore;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_category);
        SearchView searchView = view.findViewById(R.id.search_category);
        progressBar = view.findViewById(R.id.progress_category);
        refreshLayout = view.findViewById(R.id.swipe_category);

        firestore = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        list = new ArrayList<>();
        adapter = new CategoryAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);

        getCategoryData(0);

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
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimaryDark)
        );

        refreshLayout.setOnRefreshListener(() -> {
            list.clear();
            getCategoryData(1);
        });


    }


    private void getCategoryData(int stateSwipe) {

        progressBar.setVisibility(View.VISIBLE);

        firestore.collection("category")
                .orderBy("time", Query.Direction.ASCENDING)
                .addSnapshotListener((value, e) -> {

                    if (e == null) {

                        if (value != null) {

                            for (DocumentChange documentChange : value.getDocumentChanges()) {

                                CategoryModel categoryModel = documentChange.getDocument().toObject(CategoryModel.class);
                                list.add(categoryModel);
                                adapter.notifyDataSetChanged();

                            }

                            progressBar.setVisibility(View.GONE);
                            if (stateSwipe == 1)
                                refreshLayout.setRefreshing(false);

                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Data is Empty", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Error \n " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                });

    }

}