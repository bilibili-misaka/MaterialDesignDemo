package com.example.administrator.myapplication;

import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout)findViewById(R.id.tab);
        viewPager = (ViewPager)findViewById(R.id.view_pager);


        //设置toolbar
        setSupportActionBar(toolbar);
        toolbar.setTitle("材料设计");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示左上角的图标

        //设置DrawerLayout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,0,0);
        actionBarDrawerToggle.syncState();  //关联toggle与drawerlayout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //设置侧滑菜单
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();    //隐藏侧滑菜单
                navigationView.setCheckedItem(item.getItemId());
                return true;
            }
        });

        //设置viewpager
        String[] titles = {"列表","网格","瀑布流"};

        int[] layoutTypes = {
                ItemFragment.TYPE_LIST,
                ItemFragment.TYPE_GRID,
                ItemFragment.TYPE_STAGGERED
        };

        ItemFragment[] fragments = new ItemFragment[3];
        for(int i=0;i<titles.length;i++){
            ItemFragment fragment = new ItemFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("layoutType",layoutTypes[i]);
            fragment.setArguments(bundle);
            fragments[i] = fragment;
        }

        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
        tabLayout.setupWithViewPager(viewPager);

        //floatingActionBar

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    snackbar = Snackbar.make(coordinatorLayout,"这是snackbar", Snackbar.LENGTH_INDEFINITE);
                    snackbar.show();
                }else{
                    snackbar.dismiss();
                }
                flag = !flag;
            }
        });


    }
    boolean flag = true;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            actionBarDrawerToggle.onOptionsItemSelected(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
