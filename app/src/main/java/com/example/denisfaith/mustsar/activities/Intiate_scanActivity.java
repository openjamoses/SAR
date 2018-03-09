package com.example.denisfaith.mustsar.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.denisfaith.mustsar.R;
import com.example.denisfaith.mustsar.fragments.LecturerFragment;
import com.example.denisfaith.mustsar.fragments.StudentFragment;

import java.util.ArrayList;
import java.util.List;

public class Intiate_scanActivity extends AppCompatActivity {
    private Context context = this;
    private static final String TAG = "US_MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
            setSupportActionBar(toolbar);
            int[] icons = {R.mipmap.ic_dashboard,
                    R.mipmap.ic_action_city,
                    R.mipmap.ic_atm
            };
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            ViewPager viewPager = (ViewPager) findViewById(R.id.main_tab_content);
            setupViewPager(viewPager);

            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setText("LECTURER");
            tabLayout.getTabAt(1).setText("STUDENT");
            for (int i = 0; i < icons.length-1; i++) {
                tabLayout.getTabAt(i).setIcon(icons[i]);
            }
            tabLayout.getTabAt(0).select();

            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        if (id == R.id.action_logout) {
            try{
               // new SessionManager(context).logoutUser();
            }catch (Exception e){
                e.printStackTrace();
            }
            //startActivity(new Intent(context, LoginActivity.class));
            //Toast.makeText(context,"Not Implemented...!",Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.insertNewFragment(new StudentFragment());
        adapter.insertNewFragment(new LecturerFragment());
        //adapter.insertNewFragment(new Menu_Fragment());
        // adapter.insertNewFragment(new SearchFragment());
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<android.support.v4.app.Fragment> mFragmentList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void insertNewFragment(android.support.v4.app.Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }




}
