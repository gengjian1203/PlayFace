package com.mbpr.gengjian.playface;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by gengjian on 15/12/23.
 */
public class WelcomeAct extends Activity {
    private static boolean m_bFirstIn = false;
    private static final int m_nDelayTime = 2000;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;

    private void init() {
        SharedPreferences perPreferences = getSharedPreferences("PF", MODE_PRIVATE);
        m_bFirstIn = perPreferences.getBoolean("FirstIn", true);
        if (!m_bFirstIn) {
            myHandler.sendEmptyMessageDelayed(GO_HOME, m_nDelayTime);
        } else {
            myHandler.sendEmptyMessageDelayed(GO_GUIDE, m_nDelayTime);
            SharedPreferences.Editor editor = perPreferences.edit();
            editor.putBoolean("FirstIn", false);
            editor.commit();
        }
    }

    private void goHome() {
        Intent i = new Intent(WelcomeAct.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void goGuide() {
        Intent i = new Intent(WelcomeAct.this, Guide.class);
        startActivity(i);
        finish();
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        init();
    }


}
