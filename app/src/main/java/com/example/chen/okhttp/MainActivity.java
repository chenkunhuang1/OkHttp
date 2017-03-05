package com.example.chen.okhttp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    final String str = "http://www.baidu.com";
    private Button mload;
    private TextView mtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String s = (String) msg.obj;

            mtv.setText(s);
        }
    };

    private void initView() {
        mtv = (TextView) findViewById(R.id.tv);
        mload = (Button) findViewById(R.id.load);
        mload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建一个okhttpcclient
                OkHttpClient client = new OkHttpClient();
                //创建一个request
                final Request request = new Request.Builder().url(str).build();
                //new call
                Call call = client.newCall(request);
                //请求加入调度
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Toast.makeText(MainActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(final Response response) throws IOException {
                        String htmlstr = response.body().string();
                        Message msg = new Message();
                        msg.obj = htmlstr;
                        handler.sendMessage(msg);

                    }
                });


            }
        });

    }
}
