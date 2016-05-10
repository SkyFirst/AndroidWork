package com.example.lenovo.cargezi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.cheshouye.api.client.WeizhangClient;
import com.cheshouye.api.client.json.CarInfo;
import com.cheshouye.api.client.json.WeizhangResponseJson;

public class Car extends Activity {

	private String carNum;
	private String engNum;
	private TextView mTxt;
	private WeizhangResponseJson info;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resuly);

		Intent it = getIntent();
		carNum = it.getStringExtra("CAR_NUM");
		engNum = it.getStringExtra("ENG_NUM");
		mTxt = (TextView) findViewById(R.id.textView1);

		step4();
		//车首页API返回结果
        mTxt.setText(carNum);
	}

	public void step4() {

		new Thread() {
			@Override
			public void run() {
				CarInfo car = new CarInfo();
				car.setChepai_no(carNum);
				car.setChejia_no("123456");
				car.setEngine_no(engNum);
				car.setRegister_no("");
				car.setCity_id(109);
				info = WeizhangClient.getWeizhang(car);
				System.out.println(info.toJson());
				int status = info.getStatus();
				System.out.println(status);
				mTxt.setText(info.getTotal_money()+info.getCount());
			}
		}.start();

	}
}
