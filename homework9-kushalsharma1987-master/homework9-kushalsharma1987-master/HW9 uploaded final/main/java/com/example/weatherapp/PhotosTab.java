package com.example.weatherapp;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.load;

public class PhotosTab extends Fragment {


    private View root;
    private RecyclerView myrecyclerview;
    private List<CityImageItem> listItem;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.tab_photos, container, false);
        myrecyclerview = (RecyclerView) root.findViewById(R.id.recycler_view);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listItem);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recyclerViewAdapter);
//
        return root;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        DetailsActivity activity = (DetailsActivity) getActivity();
        ArrayList<String> listImageUrl = activity.getListImageUrl();

        listItem = new ArrayList<>();
        for (int i = 0; i < listImageUrl.size(); i++) {
            listItem.add(new CityImageItem(listImageUrl.get(i)));
        }

//        Picasso.with(getContext()).load(listImageUrl.get(i)).centerCrop().resize(300, 300).into((ImageView) getView().findViewById(R.id.city_photo));


//        listItem.add(new CityImageItem(R.drawable.photo1));
//        listItem.add(new CityImageItem(R.drawable.photo2));
//        listItem.add(new CityImageItem(R.drawable.photo3));
//        listItem.add(new CityImageItem(R.drawable.photo4));
//        listItem.add(new CityImageItem(R.drawable.photo5));
//        listItem.add(new CityImageItem(R.drawable.photo6));

    }

    //    private void initView( View view){
//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//
//        ListAdapter listAdapter = new ListAdapter();
//
//        recyclerView.setAdapter(listAdapter);
//
//
////        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//
//        ArrayList cityImages = prepareData();
//        ViewAdapter adapter = new ViewAdapter(GlobalApplication.getAppContext(),cityImages);
//        recyclerView.setAdapter(adapter);
//    }


//    private ArrayList prepareData(){
//
//        ArrayList city_images = new ArrayList<>();
//        for(int i=0;i<city_image_urls.length;i++){
//            CityImageItem cityImageItem = new CityImageItem();
//            cityImageItem.setcity_image_url(city_image_urls[i]);
//            city_images.add(cityImageItem);
//        }
//        return city_images;
//    }
}
