package com.example.lenovo.cargezi;

import android.app.Activity;
import android.content.ContentValues;
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

public class RegisterActivity extends Activity {

	private Button mregister;
	private Button mdisplay;
	private TextView title;
	private EditText pass1;
	private EditText pass2;
	private EditText name;
	private int change=0;
	private Cursor cursor;
	private ImageView img;
	private ClipDrawable clipDrawable;
	public  SQLiteDatabase db;
	public  MySQLHelper helper;
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
		setContentView(R.layout.register_login);
		mregister=(Button)findViewById(R.id.register_log);
		mdisplay=(Button)findViewById(R.id.register_display);
		pass1=(EditText)findViewById(R.id.register_pwd1);
		pass2=(EditText)findViewById(R.id.register_pwd2);
		name=(EditText)findViewById(R.id.register_name);
		title=(TextView)findViewById(R.id.register_title);
		title.setText(Html.fromHtml("<b><font color=green>欢迎来到</font><font color=yellow>注册界面</font></b>"));
		img=(ImageView)findViewById(R.id.register_image);
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
		
			helper=new MySQLHelper(this,"car",null,1);
			db=helper.getReadableDatabase();
		
		
	}
	public void click(View view)
	{
		if(view.getId()==R.id.register_display)
		{
			if(change==0)
			{
				mdisplay.setText("隐藏密码");
				change=1;
				pass1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				pass2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			}
			else
			{
				mdisplay.setText("显示密码");
				change=0;
				pass1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				pass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			}
		}
		else
		{
			if(name.getText().toString().equals("")||pass1.getText().toString().equals("")||pass2.getText().toString().equals(""))
			{
				Toast.makeText(this, "请输入用户名或密码", Toast.LENGTH_LONG).show();
				return;
			}
			else
			{
				if(!pass1.getText().toString().equals(pass2.getText().toString()))
				{
					Toast.makeText(this, "两次密码不一致", Toast.LENGTH_LONG).show();
					return;
				}
				cursor=db.rawQuery("SELECT *FROM car_info", null);
				while(cursor.moveToNext())
				{
					if(cursor.getString(0).equals(name.getText().toString()))
					{
						Toast.makeText(this, "该用户已被注册", Toast.LENGTH_LONG).show();
						return;
					}
				}
				ContentValues contentValues=new ContentValues();
				contentValues.put("name", name.getText().toString());
				contentValues.put("pwd", pass1.getText().toString());
				db.insert("car_info", null, contentValues);
				db.close();
				Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
				Intent intent=new Intent();
				intent.putExtra("name",name.getText().toString());
				intent.putExtra("pwd",pass1.getText().toString());
				setResult(RESULT_OK,intent);
				finish();
			}
		}
	}
}
