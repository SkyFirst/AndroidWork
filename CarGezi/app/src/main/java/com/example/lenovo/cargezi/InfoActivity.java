package com.example.lenovo.cargezi;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class InfoActivity extends AppCompatActivity {

    private TextView mUserName;
    private TextView mTitle;
    private Button mBtnUser;
    private Button mBtnPwd;
    private TextView mTextColor;
    private Button mBtnColor;
    private ImageButton mHeadIcon;
    private String userName;
    private String userPwd;
    private int themeColor;

    private Dialog mUserDlg;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        userName = getIntent().getStringExtra("UserName");
        userPwd = getIntent().getStringExtra("UserPwd");
        setTitle("");
        themeColor = getIntent().getIntExtra("themeColor",R.color.csy_green);
        mUserName = (TextView) findViewById(R.id.textUser2);
        mUserName.setText(userName);

        mTitle = (TextView)findViewById(R.id.textTitle);
        mTitle.setText(userName);

        mBtnUser = (Button) findViewById(R.id.buttonUser);
        mBtnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDlg = new Dialog(InfoActivity.this);
                mUserDlg.setTitle("修改昵称");
                mUserDlg.setCancelable(false);
                mUserDlg.setContentView(R.layout.dlglayout);
                final EditText mEditUser = (EditText) mUserDlg.findViewById(R.id.editUser);
                Button mOK = (Button) mUserDlg.findViewById(R.id.OK);
                mOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userName = mEditUser.getText().toString();
                        mTitle.setText(userName);
                        mUserName.setText(userName);
                        mUserDlg.cancel();
                    }
                });
                Button mCancel = (Button)mUserDlg.findViewById(R.id.cancel);
                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mUserDlg.cancel();
                    }
                });
                mUserDlg.show();
            }
        });

        mBtnPwd = (Button)findViewById(R.id.buttonPwd);
        mBtnPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDlg = new Dialog(InfoActivity.this);
                mUserDlg.setTitle("修改密码");
                mUserDlg.setCancelable(false);
                mUserDlg.setContentView(R.layout.pwdlayout);
                final EditText mOldPwd = (EditText) mUserDlg.findViewById(R.id.editPwd);
                final EditText mNewPwd = (EditText) mUserDlg.findViewById(R.id.editPwd1);
                final EditText mTwoPwd = (EditText) mUserDlg.findViewById(R.id.editPwd2);
                Button mOK = (Button) mUserDlg.findViewById(R.id.OK);
                mOK.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(!mOldPwd.getText().toString().equals(userPwd))
                        {
                            Toast.makeText(InfoActivity.this,"旧密码输入错误！",Toast.LENGTH_LONG).show();
                        }
                        else if(!mNewPwd.getText().toString().equals(mTwoPwd.getText().toString()))
                        {
                            Toast.makeText(InfoActivity.this,"两次新密码输入不一致！",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            userPwd = mNewPwd.getText().toString();
                            Toast.makeText(InfoActivity.this,"修改密码成功！",Toast.LENGTH_LONG).show();
                            mUserDlg.cancel();
                        }
                    }
                });
                Button mCancel = (Button)mUserDlg.findViewById(R.id.cancel);
                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mUserDlg.cancel();
                    }
                });
                mUserDlg.show();
            }
        });

        mBtnColor = (Button) findViewById(R.id.buttonColor);
        mBtnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDlg = new Dialog(InfoActivity.this);
                mUserDlg.setTitle("修改主题颜色");
                mUserDlg.setCancelable(false);
                mUserDlg.setContentView(R.layout.colorlayout);
                final RadioGroup mRadio = (RadioGroup) mUserDlg.findViewById(R.id.radioGroup);
                Button mOK = (Button) mUserDlg.findViewById(R.id.OK);
                mOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(mRadio.getCheckedRadioButtonId()==R.id.radioBlue)
                        {
                            themeColor = R.color.csy_green;
                        }
                        if(mRadio.getCheckedRadioButtonId()==R.id.radioGreen)
                        {
                            themeColor = R.color.green;
                        }
                        if(mRadio.getCheckedRadioButtonId()==R.id.radioPink)
                        {
                            themeColor = R.color.pink;
                        }
                        if(mRadio.getCheckedRadioButtonId()==R.id.radioRed)
                        {
                            themeColor = R.color.red;
                        }
                        mUserDlg.cancel();
                    }
                });
                Button mCancel = (Button)mUserDlg.findViewById(R.id.cancel);
                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mUserDlg.cancel();
                    }
                });
                mUserDlg.show();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "修改头像请点击左上方头像", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Info Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.lenovo.cargezi/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Info Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.lenovo.cargezi/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
