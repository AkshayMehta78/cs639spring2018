package com.pace.cs639spring.hw1;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by kachi on 1/31/18.
 */

public class AnimalDisplayFragment extends Fragment implements View.OnClickListener{

    private ImageView birdImageView,catImageView,dogImageView;
    private TextView birdTextView,catTextView,dogTextView;

    int mSelector = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.animal_display, container, false);
        initialization(view);
        setListenerOnViews();

        return view;
    }



    private void initialization(View view) {
        birdImageView = view.findViewById(R.id.birdImageView);
        catImageView = view.findViewById(R.id.catImageView);
        dogImageView = view.findViewById(R.id.dogImageView);
        birdTextView = view.findViewById(R.id.birdTextView);
        catTextView = view.findViewById(R.id.catTextView);
        dogTextView = view.findViewById(R.id.dogTextView);
    }


    private void setListenerOnViews() {
        birdImageView.setOnClickListener(this);
        catImageView.setOnClickListener(this);
        dogImageView.setOnClickListener(this);
        birdTextView.setOnClickListener(this);
        catTextView.setOnClickListener(this);
        dogTextView.setOnClickListener(this);
    }

    public void onColorPicked(int color) {
        if(mSelector == -1){
            Toast.makeText(getActivity(),getString(R.string.string_no_bird),Toast.LENGTH_SHORT).show();
        }else if(mSelector == 1){
            birdImageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }else  if(mSelector == 2){
            catImageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }else if(mSelector == 3){
            dogImageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.birdImageView:
                mSelector = 1;
                break;
            case R.id.catImageView:
                mSelector = 2;
                break;
            case R.id.dogImageView:
                mSelector = 3;
                break;
        }

        updateAnimalBio();
    }

    private void updateAnimalBio() {
        if(mSelector==1){
            if(birdTextView.getVisibility()==View.GONE){
                birdTextView.setVisibility(View.VISIBLE);
            }else{
                mSelector = -1;
                birdTextView.setVisibility(View.GONE);
            }
            catTextView.setVisibility(View.GONE);
            dogTextView.setVisibility(View.GONE);
        }else  if(mSelector==2){
            birdTextView.setVisibility(View.GONE);
            if(catTextView.getVisibility()==View.GONE){
                catTextView.setVisibility(View.VISIBLE);
            }else{
                mSelector = -1;
                catTextView.setVisibility(View.GONE);
            }
            dogTextView.setVisibility(View.GONE);
        }else if(mSelector==3){
            birdTextView.setVisibility(View.GONE);
            catTextView.setVisibility(View.GONE);
            if(dogTextView.getVisibility()==View.GONE){
                dogTextView.setVisibility(View.VISIBLE);
            }else{
                mSelector = -1;
                dogTextView.setVisibility(View.GONE);
            }
        }
    }
}
