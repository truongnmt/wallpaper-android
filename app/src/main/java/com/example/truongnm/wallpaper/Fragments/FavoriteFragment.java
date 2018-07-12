package com.example.truongnm.wallpaper.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.truongnm.wallpaper.Adapters.PhotosAdapter;
import com.example.truongnm.wallpaper.Models.Photo;
import com.example.truongnm.wallpaper.R;
import com.example.truongnm.wallpaper.Utils.RealmController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by truongnm on 7/8/18.
 */

public class FavoriteFragment extends Fragment {
    @BindView(R.id.fragment_favorite_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_favorite_notification)
    TextView notification;

    private PhotosAdapter photosAdapter;
    private List<Photo> photos = new ArrayList<>();
    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        unbinder = ButterKnife.bind(this, view);

        // RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        photosAdapter = new PhotosAdapter(getActivity(), photos);
        recyclerView.setAdapter(photosAdapter);
        getPhotos();
        return view;
    }

    private void getPhotos() {
        RealmController realmController = new RealmController();
        photos.addAll(realmController.getPhotos());

        if(photos.size() == 0){
            notification.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            photosAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
