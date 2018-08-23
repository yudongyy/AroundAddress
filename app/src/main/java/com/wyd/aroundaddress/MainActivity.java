package com.wyd.aroundaddress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author wyd
 * @version 1.0
 * @description
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_select_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AroundAddressActivity.newInstance(MainActivity.this));
            }
        });
    }
}
