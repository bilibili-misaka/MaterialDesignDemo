package com.example.administrator.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/9.
 */
public class ItemFragment extends Fragment{

    public static final int TYPE_LIST = 1;
    public static final int TYPE_GRID = 2;
    public static final int TYPE_STAGGERED = 3;

    public int mLayoutType;
    private View mRootView;
    private List<ItemBean> mListDatas;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ItemAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLayoutType = getArguments().getInt("layoutType");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(mRootView==null){
            mRootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_item,container,false);
            swipeRefreshLayout = (SwipeRefreshLayout)mRootView.findViewById(R.id.swipe_refresh_layout);
            recyclerView = (RecyclerView)mRootView.findViewById(R.id.recycler_view);

            initSwipeRefresh();
            initRecyclerView();

            loadData();
        }else{
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if(parent !=null){
                parent.removeView(mRootView);
            }
        }

        return mRootView;
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager manager = null;
        switch (mLayoutType){
            case ItemFragment.TYPE_LIST:
                manager = new LinearLayoutManager(getActivity());
                break;
            case ItemFragment.TYPE_GRID:
                manager = new GridLayoutManager(getActivity(),3);
                break;
            case ItemFragment.TYPE_STAGGERED:
                manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                break;


        }

        recyclerView.setLayoutManager(manager);
        adapter = new ItemAdapter(mListDatas,getActivity(),mLayoutType);
        recyclerView.setAdapter(adapter);
    }

    private void initSwipeRefresh(){
        int[] colors = new int[]{
                android.R.color.holo_green_dark,
                android.R.color.holo_purple,
                android.R.color.holo_red_dark,
        };
        swipeRefreshLayout.setColorSchemeResources(colors);
        swipeRefreshLayout.setProgressViewOffset(false, 0, 50);
        swipeRefreshLayout.setRefreshing(true);

        // 用户下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });


    }

    private void loadData(){
        new AsyncTask<Void, String, String>() {
            @Override
            protected String doInBackground(Void... params) {
                SystemClock.sleep(1500);
                mListDatas = initData();
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                adapter.setData(mListDatas);
                swipeRefreshLayout.setRefreshing(false);
            }
        }.execute();
    }

    private List<ItemBean> initData(){
        List<ItemBean> data = new ArrayList<ItemBean>();
        for(int i=0;i<30;i++){
            ItemBean bean = new ItemBean();
            bean.name = "选项"+i;
            bean.icon = R.mipmap.p1+i;
            data.add(bean);
        }
        return data;
    }



}
