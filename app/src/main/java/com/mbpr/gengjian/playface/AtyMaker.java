package com.mbpr.gengjian.playface;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import com.faceplusplus.api.FaceDetecter;
import com.faceplusplus.api.FaceDetecter.Face;


import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * Created by gengjian on 15/12/27.
 */
public class AtyMaker extends Activity implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private Camera m_camera;
    private SurfaceView m_camerasurface = null;
    private FaceMask m_mask = null;
    private FaceDetecter m_facedetecter = null;

    private HandlerThread m_handleThread = null;
    private Handler m_detectHandler = null;

    private int m_width = 320;
    private int m_height = 240;
    private int m_maskWidth = -1;
    private int m_maskHeight = -1;
    private int m_cameraPosition = -1;
    private SurfaceHolder m_holderCamera;

    private Button m_btnHome;
    private Button m_btnCamera;

    private View.OnTouchListener myBtnTouchListen = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Button btn = (Button) view;
            switch (btn.getId()) {
                case R.id.btn_Maker_Home:
                    if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                        btn.setBackgroundResource(R.drawable.maker_home_select);
                    } else if (MotionEvent.ACTION_UP == motionEvent.getAction()) {
                        btn.setBackgroundResource(R.drawable.maker_home);
                    }
                    break;
                case R.id.btn_Maker_Camera:
                    if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                        btn.setBackgroundResource(R.drawable.maker_camera_select);
                    } else if (MotionEvent.ACTION_UP == motionEvent.getAction()) {
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

    public void ChangeCamera() {
        if (m_camera != null) {
            m_handleThread.quit();
            m_camera.setPreviewCallback(null);
            m_camera.stopPreview();
            m_camera.release();
            m_camera = null;
        }
        return;
    }

    private View.OnClickListener myBtnClickListen = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_Maker_Home:
                    BacktoMain();
                    break;
                case R.id.btn_Maker_Camera:
                    ChangeCamera();
                    break;
                default:
                    break;
            }
        }
    };

    private void Init() {
        //
        m_handleThread = new HandlerThread("dt");
        m_handleThread.start();
        //
        m_detectHandler = new Handler(m_handleThread.getLooper());

        // 设置layout
        setContentView(R.layout.activity_maker);
        // 标题栏
        m_btnHome = (Button) findViewById(R.id.btn_Maker_Home);
        m_btnHome.setOnTouchListener(myBtnTouchListen);
        m_btnHome.setOnClickListener(myBtnClickListen);
        m_btnCamera = (Button) findViewById(R.id.btn_Maker_Camera);
        m_btnCamera.setOnTouchListener(myBtnTouchListen);
        m_btnCamera.setOnClickListener(myBtnClickListen);

        // 摄像头预览
        m_camerasurface = (SurfaceView) findViewById(R.id.View_Maker_Surface);
        m_camerasurface.getHolder().addCallback(this);
        m_camerasurface.setKeepScreenOn(true);
        // 绘制结果视图
        m_mask = (FaceMask) findViewById(R.id.View_Maker_Mask);
        // 获取宽高
        ViewTreeObserver vto = m_mask.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                m_mask.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                m_maskHeight = m_mask.getHeight();
                m_maskWidth = m_mask.getWidth();

                Log.e("aaaaaaaaaa", "Width:" + m_maskWidth + " - Height:" + m_maskHeight);
            }
        });

        // 识别引擎face++
        m_facedetecter = new FaceDetecter();
        if (!m_facedetecter.init(this, "97e45433bcb0fa6aacaf17b04c6a576b")) {
            Log.e("FacePlusPlus", "Init Error");
        }
        m_facedetecter.setTrackingMode(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 开启前置摄像头
        int nNumberOfCameras = Camera.getNumberOfCameras();

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < nNumberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (Camera.CameraInfo.CAMERA_FACING_FRONT == cameraInfo.facing) {
                m_cameraPosition = i;
            }
        }

        if (-1 == m_cameraPosition) {
            Toast.makeText(getApplicationContext(), "未找到前置摄像头", Toast.LENGTH_SHORT).show();
        }

        m_camera = Camera.open(m_cameraPosition);
        Camera.Parameters para = m_camera.getParameters();
        para.setPreviewSize(m_width, m_height);
        para.setPreviewFormat(ImageFormat.NV21);
        m_camera.setParameters(para);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (m_camera != null) {
            m_camera.setPreviewCallback(null);
            m_camera.stopPreview();
            m_camera.release();
            m_camera = null;
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        m_facedetecter.release(this);
        m_handleThread.quit();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        try {
            m_camera.setPreviewDisplay(surfaceHolder);
            m_holderCamera = surfaceHolder;
        } catch (IOException e) {
            e.printStackTrace();
        }
        m_camera.setDisplayOrientation(90);
        m_camera.startPreview();
        m_camera.setPreviewCallback(this);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onPreviewFrame(final byte[] bytes, Camera camera) {
        camera.setPreviewCallback(null);
        m_detectHandler.post(new Runnable() {
            @Override
            public void run() {
                final byte[] ori = new byte[m_width * m_height];
                int is = 0;
                for (int x = m_width - 1; x >= 0; x--) {

                    for (int y = m_height - 1; y >= 0; y--) {

                        ori[is] = bytes[y * m_width + x];

                        is++;
                    }

                }
                final Face[] faceinfo = m_facedetecter.findFaces( ori.clone(), m_height, m_width);

                YuvImage imageRecv = new YuvImage(bytes, ImageFormat.NV21, m_width, m_height, null);
                if ((imageRecv == null) || (m_maskHeight == -1) || (m_maskWidth == -1) )
                {
                    return;
                }
                // 格式转换
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageRecv.compressToJpeg(new Rect(0, 0, m_width, m_height), 80, stream);
                Bitmap bitmap = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
                Bitmap resize = Bitmap.createScaledBitmap(bitmap, m_maskHeight, m_maskWidth, true);
                Matrix m = new Matrix();
                m.setRotate(-90);
                m.postScale(-1, 1);   //镜像水平翻转
                final Bitmap b = Bitmap.createBitmap(resize, 0, 0, m_maskHeight, m_maskWidth, m, true);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {m_mask.setFaceInfo(b, faceinfo);
                    }
                });
                AtyMaker.this.m_camera.setPreviewCallback(AtyMaker.this);
            }
        });
    }
}
