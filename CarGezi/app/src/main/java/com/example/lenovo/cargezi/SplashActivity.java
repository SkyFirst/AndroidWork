package com.example.lenovo.cargezi;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by lenovo on 2016/4/18.
 */
public class SplashActivity extends Activity
{
    private static final int SHOW_TIME_MIN = 1500;
    private static final int FAILURE = 0; // 失败
    private static final int SUCCESS = 1; // 成功
    private static final int OFFLINE = 2; // 如果支持离线阅读，进入离线模式

    private TextView mVersionNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);



        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected Integer doInBackground(Void... params)
            {
                int result;
                long startTime = System.currentTimeMillis();
                result = loadingCache();
                long loadingTime = System.currentTimeMillis() - startTime;
                if (loadingTime < SHOW_TIME_MIN) {
                    try {
                        Thread.sleep(SHOW_TIME_MIN - loadingTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return result;
            }

            @Override
            protected void onPostExecute(Integer result)
            {

                // ... ...
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);

                startActivity(intent);
                finish();
                //两个参数分别表示进入的动画,退出的动画
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        }.execute(new Void[]{});
    }

    private int loadingCache()
    {

        return SUCCESS;
    }
}
