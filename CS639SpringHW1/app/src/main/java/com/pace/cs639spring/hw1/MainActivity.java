package com.pace.cs639spring.hw1;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private AnimalDisplayFragment mAnimalFragment;
    private View mViewGreen,mViewBlue,mViewRed,mViewOrange,mViewPurple;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setWidgetReferences();
        setWidgetEvents();
        
        mAnimalFragment = new AnimalDisplayFragment();
        addFragment(R.id.container,mAnimalFragment,getString(R.string.fragment_animal));
    }

    /**
     * Set up Widget Events
     */
    private void setWidgetEvents() {
        mViewGreen.setOnClickListener(this);
        mViewBlue.setOnClickListener(this);
        mViewRed.setOnClickListener(this);
        mViewOrange.setOnClickListener(this);
        mViewPurple.setOnClickListener(this);
    }

    /**
     * Setting up widget references
     */
    private void setWidgetReferences() {
        mViewGreen = findViewById(R.id.mViewGreen);
        mViewBlue = findViewById(R.id.mViewBlue);
        mViewRed = findViewById(R.id.mViewRed);
        mViewOrange = findViewById(R.id.mViewOrange);
        mViewPurple = findViewById(R.id.mViewPurple);
    }


    /**
     * Add Fragment to activity
     * @param containerViewId
     * @param fragment
     * @param fragmentTag
     */
    protected void addFragment(int containerViewId,
                               Fragment fragment,
                               String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mViewGreen:
                mAnimalFragment.onColorPicked(ContextCompat.getColor(this, android.R.color.holo_green_light));
                break;
            case R.id.mViewBlue:
                mAnimalFragment.onColorPicked(ContextCompat.getColor(this, android.R.color.holo_blue_light));
                break;
            case R.id.mViewRed:
                mAnimalFragment.onColorPicked(ContextCompat.getColor(this, android.R.color.holo_red_light));
                break;
            case R.id.mViewOrange:
                mAnimalFragment.onColorPicked(ContextCompat.getColor(this, android.R.color.holo_orange_light));
                break;
            case R.id.mViewPurple:
                mAnimalFragment.onColorPicked(ContextCompat.getColor(this, android.R.color.holo_purple));
                break;
        }
    }
}
