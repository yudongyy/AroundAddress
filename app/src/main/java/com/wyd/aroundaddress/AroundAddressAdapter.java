package com.wyd.aroundaddress;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


public class AroundAddressAdapter extends BaseQuickAdapter<AroundAddressBean.PoisBean,BaseViewHolder> {
    private OnItemClickListener mOnItemClickListener = null;
    private Context mContext = null;
    private boolean mIsSearch;

    public interface OnItemClickListener {
        void onItemClick(AroundAddressBean.PoisBean dataBean);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public AroundAddressAdapter(Context context) {
        super(R.layout.item_around_address, null);
        this.mContext = context;

    }

    public void setData(boolean isSearch) {
        this.mIsSearch = isSearch;
    }


    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final AroundAddressBean.PoisBean dataBean) {
        if (null == dataBean)
            return;
        Resources resources = mContext.getResources();

        if (resources == null) {
            return;
        }

        TextView tvName = baseViewHolder.getView(R.id.tv_mall_search_item_title);
        TextView tvAddress = baseViewHolder.getView(R.id.tv_mall_search_item_address);
        tvName.setText(dataBean.name);
        tvAddress.setText(dataBean.address);

        LinearLayout llMallSearchItemLayout = baseViewHolder.getView(R.id.ll_mall_search_item_layout);
        llMallSearchItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == mOnItemClickListener)
                    return;

                mOnItemClickListener.onItemClick(dataBean);
            }
        });
    }
}