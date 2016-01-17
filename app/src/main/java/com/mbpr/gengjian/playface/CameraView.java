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


    public CameraView(Context context) {
        super(context);

        holder = this.getHolder();
        holder.addCallback(this);

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
            m_camera = Camera.open(nDefaultID);
            m_camera.setPreviewDisplay(surfaceHolder);
            Camera.Parameters parameters = m_camera.getParameters();
            parameters.setPreviewSize(320, 240);
            parameters.setPreviewFormat(ImageFormat.NV21);
            m_camera.setParameters(parameters);
            m_camera.setDisplayOrientation(90);
            m_camera.startPreview();
            m_camera.setPreviewCallback(this);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
    }
}
