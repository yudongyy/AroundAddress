package com.wyd.aroundaddress;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wyd.aroundaddress.mgson.MGson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author wyd
 * @version 1.0
 * @description
 */
public class AroundAddressActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener {
    private RecyclerView mRvAroundAddressContent = null;
    private AroundAddressAdapter mAroundAddressAdapter = null;
    private String key = "8a376bc362036b5d4681a7f24538ffe6";
    private String outputType = "json";
    private String location = "116.279853,40.049879";
    private int radius = 2000;
    private String sortrule = "distance";
    private String city = "北京";
    private String types = "050000|060000|070000|080000|090000|100000|110000|120000|190000";
    private String extensions = "all";
    private int offset = 15;
    private int page = 1;
    private AddressService addressService = null;

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, AroundAddressActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_address);
        initData();
        request();
    }

    private void request() {
        if (addressService == null) {
            return;
        }

        Call<String> addressData = addressService.getAddressData(key, outputType, location, radius, sortrule, city, types, extensions, offset, page);
        addressData.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    AroundAddressBean aroundAddressBean = MGson.newGson().fromJson(response.body(), AroundAddressBean.class);
                    updateView(aroundAddressBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("response", "onResponse: " + t.getMessage());
            }
        });
    }

    protected void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://restapi.amap.com")
                .addConverterFactory(StringConverterFactory.create())
                .build();

        addressService = retrofit.create(AddressService.class);
        mRvAroundAddressContent = findViewById(R.id.recyclerView);
        initCommonRecyclerView(createAdapter(), null);
        mRvAroundAddressContent.setAdapter(createAdapter());
        mAroundAddressAdapter.setEnableLoadMore(true);
        mAroundAddressAdapter.setOnLoadMoreListener(this, mRvAroundAddressContent);
    }

    private BaseQuickAdapter createAdapter() {
        mAroundAddressAdapter = new AroundAddressAdapter(this);
        return mAroundAddressAdapter;
    }

    public RecyclerView initCommonRecyclerView(BaseQuickAdapter adapter, RecyclerView
            .ItemDecoration decoration) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    private void updateView(AroundAddressBean aroundAddressBean) {
        if (null == aroundAddressBean) {
            return;
        }
        List<AroundAddressBean.PoisBean> poisBeans = aroundAddressBean.pois;
        if (poisBeans == null) {
            return;
        }
        if (page == 1) {
            mAroundAddressAdapter.setNewData(poisBeans);
        } else {
            if (poisBeans.size() > 0) {
                mAroundAddressAdapter.addData(poisBeans);
                mAroundAddressAdapter.loadMoreComplete();
            } else {
                mAroundAddressAdapter.loadMoreEnd();
            }
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        request();
    }
}
