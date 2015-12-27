package com.mbpr.gengjian.playface;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

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
                        btn.setText("Maker..");
                    }
                    else if (MotionEvent.ACTION_UP == motionEvent.getAction())
                    {
                        btn.setText("");
                    }
                    break;
                case R.id.btn_ocr:
                    if (MotionEvent.ACTION_DOWN == motionEvent.getAction())
                    {
                        btn.setText("Ocr..");
                    }
                    else if (MotionEvent.ACTION_UP == motionEvent.getAction())
                    {
                        btn.setText("");
                    }
                    break;
                case R.id.btn_learn:
                    if (MotionEvent.ACTION_DOWN == motionEvent.getAction())
                    {
                        btn.setText("Learn..");
                    }
                    else if (MotionEvent.ACTION_UP == motionEvent.getAction())
                    {
                        btn.setText("");
                    }
                    break;
                case R.id.btn_about:
                    if (MotionEvent.ACTION_DOWN == motionEvent.getAction())
                    {
                        btn.setText("About..");
                    }
                    else if (MotionEvent.ACTION_UP == motionEvent.getAction())
                    {
                        btn.setText("");
                    }
                    break;
                default:
                    break;
            }

            return false;
        }
    };

    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.btn_maker:
                    m_textVersion.setText("Click~ Maker~~");
                    break;
                case R.id.btn_ocr:
                    m_textVersion.setText("Click~ Ocr~~");
                    break;
                case R.id.btn_learn:
                    m_textVersion.setText("Click~ Learn~~");
                    break;
                case R.id.btn_about:
                    m_textVersion.setText("Click~ About~~");
                    break;
                default:
                    break;

            }
        }
    };

    private void Init () {
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


}
