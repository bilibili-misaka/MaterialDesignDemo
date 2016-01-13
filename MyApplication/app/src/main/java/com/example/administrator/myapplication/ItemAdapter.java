package com.example.administrator.myapplication;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/1/9.
 */
public class ItemAdapter extends RecyclerView.Adapter{

    private List<ItemBean> mListData;
    private Context context;
    private int layoutType;

    public ItemAdapter(List<ItemBean> mListData,Context context,int layoutType){
        this.mListData = mListData;
        this.context = context;
        this.layoutType = layoutType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout = 0;
        switch (layoutType){
            case ItemFragment.TYPE_LIST:
                layout = R.layout.item_list;
                break;
            case ItemFragment.TYPE_GRID:
                layout = R.layout.item_grid;
                break;
            case ItemFragment.TYPE_STAGGERED:
                layout = R.layout.item_staggered_grid;
                break;
        }

        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        MyHolderView holderView = new MyHolderView(view);
        holderView.img = (ImageView)view.findViewById(R.id.iv_image);
        holderView.text = (TextView)view.findViewById(R.id.tv_item);

        return holderView;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyHolderView holderView = (MyHolderView) holder;
        final ItemBean bean = mListData.get(position);

        holderView.img.setBackgroundResource(bean.icon);
        holderView.text.setText(bean.name);
        holderView.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData==null?0:mListData.size();
    }

    //刷新列表数据
    public void setData(List<ItemBean> data){
        mListData = data;
        notifyDataSetChanged();
    }

    public class MyHolderView extends RecyclerView.ViewHolder{

        public ImageView img;
        public TextView text;

        public MyHolderView(View itemView) {
            super(itemView);
        }
    }


}
