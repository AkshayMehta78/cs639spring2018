package com.pace.cs639spring.hw2;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pace.cs639spring.hw2.model.Animal;

import java.util.ArrayList;

/**
 * Created by kachi on 2/7/18.
 */

public class AnimalDisplayRecyclerViewAdapter extends RecyclerView.Adapter {

    private static final String TAG = AnimalDisplayRecyclerViewAdapter.class.getSimpleName();
    private final TypedArray imagesListMap;
    Activity mActivity;
    ArrayList<Animal> mAnimalArrayList;

    public AnimalDisplayRecyclerViewAdapter(Activity activity, ArrayList<Animal> animalArrayList) {
        mActivity = activity;
        mAnimalArrayList = animalArrayList;
        imagesListMap = activity.getResources().obtainTypedArray(R.array.animal_images);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.animal_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        Animal item = mAnimalArrayList.get(position);
        viewHolder.clickedPosition = position;

        viewHolder.titleTextView.setText(item.name);
        viewHolder.factTextView.setText(item.factsArrayList.get(item.factIndex));

        viewHolder.animalImage.setImageResource(R.drawable.cat);

        if(item.color!=0){
            viewHolder.animalImage.setColorFilter(item.color, PorterDuff.Mode.SRC_IN);
        }else{
            viewHolder.animalImage.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
        }

        viewHolder.animalImage.setImageResource(imagesListMap.getResourceId(position,-1));

        if (item.isSelected) {
            viewHolder.descriptionLayout.setVisibility(View.VISIBLE);
            viewHolder.actionLayout.setVisibility(View.VISIBLE);
        } else {
            viewHolder.descriptionLayout.setVisibility(View.INVISIBLE);
            viewHolder.actionLayout.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return mAnimalArrayList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView animalImage;
        LinearLayout descriptionLayout, actionLayout;
        TextView titleTextView, factTextView, nextFactTextView, deleteFactTextView;
        int clickedPosition;

        public MyViewHolder(View view) {
            super(view);
            animalImage = view.findViewById(R.id.animalImage);
            actionLayout = view.findViewById(R.id.actionLayout);
            descriptionLayout = view.findViewById(R.id.descriptionLayout);
            titleTextView = view.findViewById(R.id.titleTextView);
            factTextView = view.findViewById(R.id.factTextView);
            nextFactTextView = view.findViewById(R.id.nextFactTextView);
            deleteFactTextView = view.findViewById(R.id.deleteFactTextView);
            view.setOnClickListener(this);
            nextFactTextView.setOnClickListener(this);
            deleteFactTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == nextFactTextView) {
                Animal item = mAnimalArrayList.get(clickedPosition);
                if(item.factIndex == item.factsArrayList.size()-1){
                    item.factIndex = 0;
                }else{
                    item.factIndex = item.factIndex+1;
                }
                notifyDataSetChanged();
            } else if (view == deleteFactTextView) {
                Animal item = mAnimalArrayList.get(clickedPosition);
                if(item.factsArrayList.size() == 1){
                    Utils.showToast(mActivity,mActivity.getString(R.string.factmin));
                }else{
                    item.factsArrayList.remove(item.factIndex);
                    if(item.factIndex >= item.factsArrayList.size()){
                        item.factIndex = 0;
                    }
                }
                notifyDataSetChanged();
            } else {
                for (int i = 0; i < mAnimalArrayList.size(); i++) {
                    if (i == clickedPosition) {
                        mAnimalArrayList.get(i).isSelected = !mAnimalArrayList.get(i).isSelected;
                    } else {
                        mAnimalArrayList.get(i).isSelected = false;
                    }
                }
                notifyDataSetChanged();
            }
        }
    }


    public void addFactToListItem(String newfact) {
        int selectedIndex = findSelectedIndex();
        if(selectedIndex!=-1){
            mAnimalArrayList.get(selectedIndex).factsArrayList.add(newfact);
            notifyDataSetChanged();
        }else{
            Utils.showToast(mActivity,mActivity.getString(R.string.no_animal_selected));
        }
    }

    public void addColorToImage(int color) {
        int selectedIndex = findSelectedIndex();
        if(selectedIndex!=-1){
            mAnimalArrayList.get(selectedIndex).color = color;
            notifyDataSetChanged();
        }else{
            Utils.showToast(mActivity,mActivity.getString(R.string.no_animal_selected));
        }
    }


    private int findSelectedIndex() {
        for(int i = 0 ;i < mAnimalArrayList.size(); i++){
            if(mAnimalArrayList.get(i).isSelected){
                return i;
            }
        }
        return -1;
    }
}
