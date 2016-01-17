package com.mbpr.gengjian.playface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;


/**
 * Created by gengjian on 15/12/27.
 */
public class MakerActivity extends Activity {

    private SurfaceView m_surface;
    private CameraView m_viewCamera;

    private Button m_btnHome;
    private Button m_btnCamera;

    private View.OnTouchListener myBtnTouchListen = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Button btn = (Button)view;
            switch(btn.getId())
            {
                case R.id.btn_Maker_Home:
                    if (MotionEvent.ACTION_DOWN == motionEvent.getAction())
                    {
                        btn.setBackgroundResource(R.drawable.maker_home_select);
                    }
                    else if (MotionEvent.ACTION_UP == motionEvent.getAction())
                    {
                        btn.setBackgroundResource(R.drawable.maker_home);
                    }
                    break;
                case R.id.btn_Maker_Camera:
                    if (MotionEvent.ACTION_DOWN == motionEvent.getAction())
                    {
                        btn.setBackgroundResource(R.drawable.maker_camera_select);
                    }
                    else if (MotionEvent.ACTION_UP == motionEvent.getAction())
                    {
                        btn.setBackgroundResource(R.drawable.maker_camera);
                    }
                    break;
                default:
                    break;
            }

            return false;
        }
    };

    private void BacktoMain() {
        finish();
        return;
    }
    private View.OnClickListener myBtnClickListen = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.btn_Maker_Home:
                    BacktoMain();
                    break;
                case R.id.btn_Maker_Camera:
                    m_viewCamera.ChangeCamera();
                    break;
                default:
                    break;
            }
        }
    };

    private void Init() {
        // 标题栏
        m_btnHome = (Button) findViewById(R.id.btn_Maker_Home);
        m_btnHome.setOnTouchListener(myBtnTouchListen);
        m_btnHome.setOnClickListener(myBtnClickListen);
        m_btnCamera = (Button) findViewById(R.id.btn_Maker_Camera);
        m_btnCamera.setOnTouchListener(myBtnTouchListen);
        m_btnCamera.setOnClickListener(myBtnClickListen);

        // 摄像头显示类
        m_viewCamera = new CameraView(this);
        m_surface = (SurfaceView)findViewById(R.id.View_Maker_Surface);
        m_surface.getHolder().addCallback(m_viewCamera);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maker);
        Init();
    }


}
