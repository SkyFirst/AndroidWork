package com.example.lenovo.cargezi;


import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.InputFilter;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.cheshouye.api.client.WeizhangClient;
import com.cheshouye.api.client.WeizhangIntentService;
import com.cheshouye.api.client.json.CityInfoJson;
import com.cheshouye.api.client.json.InputConfigJson;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private TextView mTextName;
    private TextView mWelcome;

    private static String logName = "未登录";
    private static int IS_LOG = 0;
    private String logPwd  = "";
    private int themeColor = R.color.csy_green;

    private NavigationView navigationView;
    private ImageButton mHeader;
    private TextView short_name;
    private TextView query_city;
    private TabHost mtabhost;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    private View view;

    private EditText chepai_number;
    private EditText chejia_number;
    private EditText engine_number;
    // 行驶证图示
    private View popXSZ;


    private int[] icons = new int[]{android.R.drawable.ic_menu_add, android.R.drawable.ic_menu_mylocation, android.R.drawable.ic_menu_manage,
            android.R.drawable.ic_menu_search};
    private String[] tags = new String[]{"0", "1", "2", "3"};
    private String[] titles = new String[]{"预约加油", "实时导航", "维护信息", "违章信息"};

    private static final int MENU_MUSIC = Menu.FIRST,
            MENU_PLAY_MUSIC = Menu.FIRST + 1,
            MENU_STOP_MUSIC = Menu.FIRST + 2,
            MENU_ABOUT = Menu.FIRST + 3;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public static String getLogName()
    {
        return logName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.addHeaderView(view);

        mHeader = (ImageButton)view.findViewById(R.id.mHeadBtn);
        mWelcome = (TextView)view.findViewById(R.id.textWel);
        mTextName = (TextView)view.findViewById(R.id.textName);

        mHeader.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(IS_LOG == 0)
                {
                    Intent it = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(it, 0);
                    IS_LOG = 1;
                }
                else
                {
                    Intent it = new Intent(MainActivity.this, InfoActivity.class);
                    it.putExtra("UserName",logName);
                    it.putExtra("UserPwd",logPwd);
                    it.putExtra("themeColor",themeColor);
                    startActivityForResult(it, 0);
                }
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) this.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(titles[0]).setIcon(icons[0]));
        tabLayout.addTab(tabLayout.newTab().setText(titles[1]).setIcon(icons[1]));
        tabLayout.addTab(tabLayout.newTab().setText(titles[2]).setIcon(icons[2]));
        tabLayout.addTab(tabLayout.newTab().setText(titles[3]).setIcon(icons[3]));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    class PagerAdapter extends FragmentStatePagerAdapter
    {

        int numOfTabs;

        public PagerAdapter(FragmentManager fm, int numOfTabs)
        {
            super(fm);
            this.numOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position)
        {

            switch (position)
            {
                case 0:
                    Frag1 tab1 = new Frag1();
                    return tab1;
                case 1:
                    Frag2 tab2 = new Frag2();
                    return tab2;
                case 2:
                    Frag3 tab3 = new Frag3();
                    return tab3;
                case 3:
                    Frag tab4 = new Frag();
                    return tab4;
                default:
                    return null;
            }
        }

        @Override
        public int getCount()
        {
            return numOfTabs;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        SubMenu subMenu = menu.addSubMenu(0, MENU_MUSIC, 0, "背景音乐").setIcon(android.R.drawable.ic_media_ff);
        subMenu.add(0, MENU_PLAY_MUSIC, 0, "播放");
        subMenu.add(0, MENU_STOP_MUSIC, 1, "停止");
        menu.add(0, MENU_ABOUT, 1, "关于...").setIcon(android.R.drawable.ic_dialog_info);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case MENU_PLAY_MUSIC:
                Intent it = new Intent(MainActivity.this, MediaPlayService.class);
                startService(it);
                break;
            case MENU_STOP_MUSIC:
                it = new Intent(MainActivity.this, MediaPlayService.class);
                stopService(it);
                break;
            case MENU_ABOUT:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("关于车阁子")
                        .setMessage("制作团队：芫晖研发小组")
                        .setCancelable(false)
                        .setIcon(android.R.drawable.star_big_on)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_info)
        {
            Intent it = new Intent(MainActivity.this, InfoActivity.class);
            it.putExtra("UserName",logName);
            it.putExtra("UserPwd",logPwd);
            startActivityForResult(it, 0);

            // Handle the camera action
        } else if (id == R.id.nav_car) {

        } else if (id == R.id.nav_msg) {

        }
        else if (id == R.id.nav_share)
        {
            Uri uri = Uri.fromFile(new File("src/main/res/mipmap-xxxhdpi/ic_launcher.png"));
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
            intent.putExtra(Intent.EXTRA_TEXT, "我发现了一款很不错的汽车应用——车阁子，快来试试吧！");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(intent, getTitle()));
            return true;

        } else if (id == R.id.nav_exit) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("提示")
                    .setMessage("亲，确定要退出么？")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==0&&resultCode==LoginActivity.RESULT_LOG&&data!=null)
        {
            logName=data.getStringExtra("LOG_NAME");
            logPwd=data.getStringExtra("LOG_PWD");

            mTextName.setText(logName);
            mWelcome.setText("欢迎，"+logName);
            return;
        }

    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
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
                "Main Page", // TODO: Define a title for the content shown.
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
