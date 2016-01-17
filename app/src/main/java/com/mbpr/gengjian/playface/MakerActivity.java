package com.mbpr.gengjian.playface;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;


/**
 * Created by gengjian on 15/12/27.
 */
public class MakerActivity extends Activity {

    private SurfaceView m_surface;
    private CameraView m_viewCamera;


    private void Init() {
        m_viewCamera = new CameraView(this);
        m_surface = (SurfaceView)findViewById(R.id.View_Maker_Surface);
        m_surface.getHolder().addCallback(m_viewCamera);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maker);
        //setContentView(new CameraView(this));
        Init();
    }

}
