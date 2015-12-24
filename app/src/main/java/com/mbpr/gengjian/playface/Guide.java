package com.mbpr.gengjian.playface;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by gengjian on 15/12/23.
 */
public class Guide extends Activity implements ViewPager.OnPageChangeListener{

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private ImageView[] dots;
    private int[] ids = {R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4};
    private Button btn_start;

    private void StartMainActivity() {
        Intent i = new Intent(Guide.this, MainActivity.class);
        startActivity(i);
        finish();
    }
    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.btn_start:
                    StartMainActivity();
                    break;
                default:
                    break;

            }
        }
    };

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);

        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.guide_1, null));
        views.add(inflater.inflate(R.layout.guide_2, null));
        views.add(inflater.inflate(R.layout.guide_3, null));
        views.add(inflater.inflate(R.layout.guide_4, null));

        vpAdapter = new ViewPagerAdapter(views, this);
        //btn_start = new Button();

        vp = (ViewPager) findViewById(R.id.viewpager);
        btn_start = (Button) views.get(3).findViewById(R.id.btn_start);

        vp.setAdapter(vpAdapter);
        vp.setOnPageChangeListener(this);
        btn_start.setOnClickListener(myClickListener);
    }

    private void initDots() {
        dots = new ImageView[views.size()];
        for (int i = 0; i <views.size(); i++)
        {
            dots[i] = (ImageView)findViewById(ids[i]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.guide);
        initViews();
        initDots();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < ids.length; i++)
        {
            if (position == i)
            {
                dots[i].setImageResource(R.drawable.login_point_select);
            }
            else
            {
                dots[i].setImageResource(R.drawable.login_point);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
