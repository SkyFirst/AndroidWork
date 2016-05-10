package com.example.lenovo.cargezi;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	public static final int RESULT_LOG = 52111;
	private Button mlogin;
	private Button mregister;
	private Button mdisplay;
	private TextView title;
	private EditText pass;
	private EditText name;
	private int change=0;
	public static SQLiteDatabase db;
	public static MySQLHelper helper;
	private Cursor cursor;
	private ImageView img;
	private ClipDrawable clipDrawable;
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg)
		{
			clipDrawable.setLevel(clipDrawable.getLevel()+400);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_act);
		mlogin=(Button)findViewById(R.id.login_log);
		mregister=(Button)findViewById(R.id.login_register);
		mdisplay=(Button)findViewById(R.id.login_display);
		helper=new MySQLHelper(this,"car",null,1);
		db=helper.getReadableDatabase();
		pass=(EditText)findViewById(R.id.login_pwd);
		name=(EditText)findViewById(R.id.login_name);
		img=(ImageView)findViewById(R.id.login_image);
		clipDrawable=new ClipDrawable(getResources().getDrawable(R.drawable.car1), Gravity.CENTER, ClipDrawable.HORIZONTAL);
		img.setImageDrawable(clipDrawable);
		clipDrawable.setLevel(0);
		new Thread()
		{
			public void run()
			{
				while(clipDrawable.getLevel()<10000)
				{
					try {
						Thread.sleep(300);
						handler.sendEmptyMessage(0);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			}
		}.start();
		title=(TextView)findViewById(R.id.login_title);
		title.setText(Html.fromHtml("<b><font color=green>欢迎来到</font><font color=yellow>登录界面</font></b>"));
	}
	public void click(View view)
	{
		
		if(view.getId()==R.id.login_display)
		{
			if(change==0)
			{
				mdisplay.setText("隐藏密码");
				change=1;
				pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			}
			else
			{
				mdisplay.setText("显示密码");
				change=0;
				pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			}
		}
		else if(view.getId()==R.id.login_log)
		{
			if(name.getText().toString().equals("")||pass.getText().toString().equals(""))
			{
				Toast.makeText(this, "请输入用户名或密码", Toast.LENGTH_LONG).show();
			}
			else
			{
				cursor=db.rawQuery("SELECT *FROM car_info", null);
				while(cursor.moveToNext())
				{
					if(name.getText().toString().equals(cursor.getString(0))&&pass.getText().toString().equals(cursor.getString(1)))
					{
						db.close();
						Intent it = new Intent();
						it.putExtra("LOG_NAME",name.getText().toString());
						it.putExtra("LOG_PWD",pass.getText().toString());
						setResult(RESULT_LOG,it);
						finish();
						return;
						
					}

				}
				Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_LONG).show();
				
			}
			
		}
		else
		{
			Intent intent=new Intent(this,RegisterActivity.class);
			startActivityForResult(intent, 0);
		}
	}
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==0&&resultCode==RESULT_OK&&data!=null)
		{
			name.setText(data.getStringExtra("name"));
			pass.setText(data.getStringExtra("pwd"));
		}
	}

	
}
