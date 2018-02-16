package com.pace.cs639spring.hw2;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pace.cs639spring.hw2.model.Animal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Constants.ANIMALDATA, View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Animal> mAnimalArrayList;
    private RecyclerView mRecyclerView;
    private AnimalDisplayRecyclerViewAdapter mAnimalDisplayRecyclerViewAdapter;


    private EditText addFactEditTextView;
    private TextView addfactTextView;

    private View mViewGreen,mViewBlue,mViewRed,mViewOrange,mViewPurple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        getWidgetReference();
        setWidgetEvents();
        initializations();
        populateAnimalData();
        populateRecyclerViewData();
    }

    private void setWidgetEvents() {
        addfactTextView.setOnClickListener(this);
        mViewGreen.setOnClickListener(this);
        mViewBlue.setOnClickListener(this);
        mViewRed.setOnClickListener(this);
        mViewOrange.setOnClickListener(this);
        mViewPurple.setOnClickListener(this);
    }

    private void populateRecyclerViewData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAnimalDisplayRecyclerViewAdapter = new AnimalDisplayRecyclerViewAdapter(this,mAnimalArrayList);
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(10));
        mRecyclerView.setAdapter(mAnimalDisplayRecyclerViewAdapter);
    }

    private void getWidgetReference() {
        mRecyclerView = findViewById(R.id.recycler_view);
        addfactTextView = findViewById(R.id.addfactTextView);
        addFactEditTextView = findViewById(R.id.addFactEditTextView);

        mViewGreen = findViewById(R.id.mViewGreen);
        mViewBlue = findViewById(R.id.mViewBlue);
        mViewRed = findViewById(R.id.mViewRed);
        mViewOrange = findViewById(R.id.mViewOrange);
        mViewPurple = findViewById(R.id.mViewPurple);
    }

    private void initializations() {
        mAnimalArrayList = new ArrayList<Animal>();
    }

    /*
    Populating arraylist from JSON File
     */
    private void populateAnimalData() {
        try {
            JSONObject jsonObject = new JSONObject(Utils.loadJSONFromAsset(this));
            Log.e(TAG,jsonObject.toString());
            JSONArray animalArray = jsonObject.getJSONArray("animal");
            for (int i = 0; i < animalArray.length(); i++) {
                Animal item = new Animal();

                JSONObject animalJSON = animalArray.getJSONObject(i);
                item.name = animalJSON.getString(NAME);
                item.isSelected = animalJSON.getBoolean(ISSELECTED);
                item.factIndex = animalJSON.getInt(FACTINDEX);
                item.image = animalJSON.getInt(IMAGE);
                item.color = animalJSON.getInt(COLOR);
                item.factsArrayList = getFactsFromJSON(animalJSON.getJSONArray(FACTS));
                mAnimalArrayList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getFactsFromJSON(JSONArray jsonArray) {
        ArrayList<String> facts = new ArrayList<String>();
        for(int i = 0;i < jsonArray.length();i++ ){
            try {
                facts.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return facts;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addfactTextView:
                String fact = addFactEditTextView.getText().toString();
                if(!TextUtils.isEmpty(fact)){
                    mAnimalDisplayRecyclerViewAdapter.addFactToListItem(fact);
                    addFactEditTextView.setText("");
                }
                break;
            case R.id.mViewGreen:
                mAnimalDisplayRecyclerViewAdapter.addColorToImage(ContextCompat.getColor(this,android.R.color.holo_green_dark));
                break;
            case R.id.mViewBlue:
                mAnimalDisplayRecyclerViewAdapter.addColorToImage(ContextCompat.getColor(this,android.R.color.holo_blue_light));
                break;
            case R.id.mViewRed:
                mAnimalDisplayRecyclerViewAdapter.addColorToImage(ContextCompat.getColor(this,android.R.color.holo_red_light));
                break;
            case R.id.mViewOrange:
                mAnimalDisplayRecyclerViewAdapter.addColorToImage(ContextCompat.getColor(this,android.R.color.holo_orange_light));
                break;
            case R.id.mViewPurple:
                mAnimalDisplayRecyclerViewAdapter.addColorToImage(ContextCompat.getColor(this,android.R.color.holo_purple));
                break;
        }

    }
}
