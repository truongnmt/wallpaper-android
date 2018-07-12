package com.example.truongnm.wallpaper.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.truongnm.wallpaper.Adapters.CollectionsAdapter;
import com.example.truongnm.wallpaper.Models.Collection;
import com.example.truongnm.wallpaper.R;
import com.example.truongnm.wallpaper.Utils.Functions;
import com.example.truongnm.wallpaper.Webservices.ApiInterface;
import com.example.truongnm.wallpaper.Webservices.ServiceGenerator;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by truongnm on 7/8/18.
 */

public class CollectionsFragment extends Fragment {
    private final String TAG = CollectionsFragment.class.getSimpleName();
    @BindView(R.id.fragment_collection_gridview)
    GridView gridView;
    @BindView(R.id.fragment_photos_progressBar)
    ProgressBar progressBar;

    private CollectionsAdapter collectionsAdapter;
    private List<Collection> collections = new ArrayList<>();
    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collections, container, false);
        unbinder = ButterKnife.bind(this, view);
        collectionsAdapter = new CollectionsAdapter(getActivity(), collections);
        gridView.setAdapter(collectionsAdapter);
        showProgressBar(true);
        getCollections();
        return view;
    }

    @OnItemClick(R.id.fragment_collection_gridview)
    public void setGridView(int position){
        Collection collection = collections.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("collectionId", collection.getId());
        CollectionFragment collectionFragment = new CollectionFragment();
        collectionFragment.setArguments(bundle);
        Functions.changeMainFragmentWithBack(getActivity(), collectionFragment);
    }

    private void getCollections(){
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Collection>> call = apiInterface.getCollections();
        call.enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if (response.isSuccessful()) {
                    collections.addAll(response.body());
                    collectionsAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Fail " + response.message());
                }
                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                Log.e(TAG, "Fail " + t.getMessage());
                showProgressBar(false );
            }
        });
    }

    private void showProgressBar(boolean isShow){
        if(isShow){
            progressBar.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}
