package com.mbpr.gengjian.playface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by gengjian on 15/12/29.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private SurfaceHolder holder;
    private Camera m_camera;

    private SurfaceHolder m_holderCamera;
    private int m_cameraPosition = 0;//0代表前置摄像头，1代表后置摄像头

    public CameraView(Context context) {
        super(context);

        holder = this.getHolder();
        holder.addCallback(this);

    }

    public void ChangeCamera() {
        //切换前后摄像头
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

        for(int i = 0; i < cameraCount; i++ ) {
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            if(m_cameraPosition == 1) {
                //现在是后置，变更为前置
                if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    //代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    m_camera.stopPreview();//停掉原来摄像头的预览
                    m_camera.release();//释放资源
                    m_camera = null;//取消原来摄像头

                    m_camera = Camera.open(i);//打开当前选中的摄像头
                    try {
                        m_camera.setPreviewDisplay(m_holderCamera);//通过surfaceview显示取景画面
                        m_camera.setDisplayOrientation(90);

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    m_camera.startPreview();//开始预览
                    m_cameraPosition = 0;
                    break;
                }
            } else {
                //现在是前置， 变更为后置
                if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    m_camera.stopPreview();//停掉原来摄像头的预览
                    m_camera.release();//释放资源
                    m_camera = null;//取消原来摄像头

                    m_camera = Camera.open(i);//打开当前选中的摄像头
                    try {
                        m_camera.setPreviewDisplay(m_holderCamera);//通过surfaceview显示取景画面
                        m_camera.setDisplayOrientation(90);

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    m_camera.startPreview();//开始预览
                    m_cameraPosition = 1;
                    break;
                }
            }

        }

        return;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            int nDefaultID = -1;
            int nNumberOfCameras = Camera.getNumberOfCameras();

            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            for (int i = 0; i < nNumberOfCameras; i++) {
                Camera.getCameraInfo(i, cameraInfo);
                if (Camera.CameraInfo.CAMERA_FACING_FRONT == cameraInfo.facing) {
                    nDefaultID = i;
                }
            }

            if (-1 == nDefaultID) {
                //Toast.makeText(getApplicationContext(), "未找到前置摄像头", Toast.LENGTH_SHORT).show();
            }

            // 打开指定摄像头
            m_cameraPosition = 0;
            m_camera = Camera.open(nDefaultID);
            m_camera.setPreviewDisplay(surfaceHolder);
            m_holderCamera = surfaceHolder;
            Camera.Parameters parameters = m_camera.getParameters();
            parameters.setPreviewSize(1280, 960);
            parameters.setPreviewFormat(ImageFormat.NV21);
            m_camera.setParameters(parameters);
            m_camera.setDisplayOrientation(90);
            m_camera.startPreview();
            //m_camera.setPreviewCallback(this);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        m_camera.stopPreview();
        m_camera.release();
        m_camera = null;
//        surfaceHolder = null;
//        m_holderCamera = null;
        holder = null;
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
    }
}
