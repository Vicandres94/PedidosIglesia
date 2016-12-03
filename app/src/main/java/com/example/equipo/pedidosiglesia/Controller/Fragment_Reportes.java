package com.example.equipo.pedidosiglesia.Controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.equipo.pedidosiglesia.R;

public class Fragment_Reportes extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment__reportes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MainPageAdapter());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_barra__menu_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


                return super.onOptionsItemSelected(item);


    }


    class MainPageAdapter extends PagerAdapter {

        private LinearLayout page1;
        private LinearLayout page2;
        private LinearLayout page3;
        private final int[] titles = {R.string.msg_nombre_Reporte1, R.string.msg_nombre_Reporte2, R.string.msg_nombre_Reporte3};

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(titles[position]);
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            View page = null;
            switch (position) {
                case 0:
                    if (page1 == null) {
                        page1 = (LinearLayout) LayoutInflater.from(Fragment_Reportes.this).inflate(R.layout
                                .fragment__reporte1, collection, false);
                    }
                    page = page1;
                    break;
                case 1:
                    if (page2 == null) {
                        page2 = (LinearLayout) LayoutInflater.from(Fragment_Reportes.this).inflate(R.layout
                                .fragment__reporte2, collection, false);
                    }
                    page = page2;
                    break;
                case 2:
                    if (page3 == null) {
                        page3 = (LinearLayout) LayoutInflater.from(Fragment_Reportes.this).inflate(R.layout
                                .fragment__reporte3, collection, false);
                    }
                    page = page3;
                    break;
            }

            collection.addView(page, 0);

            return page;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

    }

}

