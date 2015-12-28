package com.mbpr.gengjian.playface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // 数据变量
    private long m_nExitTime;

    // 控件对象
    private Button m_btnMaker;
    private Button m_btnOcr;
    private Button m_btnLearn;
    private Button m_btnAbout;
    private TextView m_textVersion;

    private View.OnTouchListener myTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Button btn = (Button) view;
            switch (btn.getId())
            {
                case R.id.btn_maker:
                    if (MotionEvent.ACTION_DOWN == motionEvent.getAction())
                    {
                        btn.setBackgroundResource(R.drawable.btn_maker_select);
                    }
                    else if (MotionEvent.ACTION_UP == motionEvent.getAction())
                    {
                        btn.setBackgroundResource(R.drawable.btn_maker);
                    }
                    break;
                case R.id.btn_ocr:
                    if (MotionEvent.ACTION_DOWN == motionEvent.getAction())
                    {
                        btn.setBackgroundResource(R.drawable.btn_ocr_select);
                    }
                    else if (MotionEvent.ACTION_UP == motionEvent.getAction())
                    {
                        btn.setBackgroundResource(R.drawable.btn_ocr);
                    }
                    break;
                case R.id.btn_learn:
                    if (MotionEvent.ACTION_DOWN == motionEvent.getAction())
                    {
                        btn.setBackgroundResource(R.drawable.btn_learn_select);
                    }
                    else if (MotionEvent.ACTION_UP == motionEvent.getAction())
                    {
                        btn.setBackgroundResource(R.drawable.btn_learn);
                    }
                    break;
                case R.id.btn_about:
                    if (MotionEvent.ACTION_DOWN == motionEvent.getAction())
                    {
                        btn.setBackgroundResource(R.drawable.btn_about_select);
                    }
                    else if (MotionEvent.ACTION_UP == motionEvent.getAction())
                    {
                        btn.setBackgroundResource(R.drawable.btn_about);
                    }
                    break;
                default:
                    break;
            }

            return false;
        }
    };

    private void EnterClientActivity(int nIDbtn) {
        Intent i;
        switch (nIDbtn)
        {
            case R.id.btn_maker:
                i = new Intent(MainActivity.this, MakerActivity.class);
                startActivity(i);
                break;
            case R.id.btn_ocr:
                i = new Intent(MainActivity.this, OcrActivity.class);
                startActivity(i);
                break;
            case R.id.btn_learn:
                i = new Intent(MainActivity.this, LearnActivity.class);
                startActivity(i);
                break;
            case R.id.btn_about:
                i = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }

    }

    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.btn_maker:
                    m_textVersion.setText("Click~ Maker~~");
                    EnterClientActivity(view.getId());
                    break;
                case R.id.btn_ocr:
                    m_textVersion.setText("Click~ Ocr~~");
                    EnterClientActivity(view.getId());
                    break;
                case R.id.btn_learn:
                    m_textVersion.setText("Click~ Learn~~");
                    EnterClientActivity(view.getId());
                    break;
                case R.id.btn_about:
                    m_textVersion.setText("Click~ About~~");
                    EnterClientActivity(view.getId());
                    break;
                default:
                    break;

            }
        }
    };

    private void Init () {
        // 初始化数据变量
        m_nExitTime = 0;

        // 初始化控件对象
        m_btnMaker = (Button) findViewById(R.id.btn_maker);
        m_btnMaker.setOnClickListener(myClickListener);
        m_btnMaker.setOnTouchListener(myTouchListener);

        m_btnOcr = (Button) findViewById(R.id.btn_ocr);
        m_btnOcr.setOnClickListener(myClickListener);
        m_btnOcr.setOnTouchListener(myTouchListener);

        m_btnLearn = (Button) findViewById(R.id.btn_learn);
        m_btnLearn.setOnClickListener(myClickListener);
        m_btnLearn.setOnTouchListener(myTouchListener);

        m_btnAbout = (Button) findViewById(R.id.btn_about);
        m_btnAbout.setOnClickListener(myClickListener);
        m_btnAbout.setOnTouchListener(myTouchListener);

        m_textVersion = (TextView) findViewById(R.id.text_version);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Init();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 2秒内再次点击退出键才会真正退出程序
        if ((KeyEvent.KEYCODE_BACK == keyCode) && (KeyEvent.ACTION_DOWN == event.getAction())) {
            long nNowTime = System.currentTimeMillis();
            if ((nNowTime - m_nExitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                m_nExitTime = nNowTime;
            }
            else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
