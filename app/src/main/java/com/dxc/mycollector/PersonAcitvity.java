package com.dxc.mycollector;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by gospel on 2017/8/18.
 * About PersonWellcom
 */
public class PersonAcitvity extends FragmentActivity implements
        AdapterView.OnItemClickListener {

    /**
     * 抽屉布局
     */
    private DrawerLayout mDrawerLayout;
    /**
     * 抽屉布局中的listview
     */
    private ListView mDrawerList;
    /**
     *
     */
    private ActionBarDrawerToggle mDrawerToggle;
    /**
     * actionbar上显示原有的标题
     */
    private CharSequence mDrawerTitle;
    /**
     * actionbar上显示itemlist的标题
     */
    private CharSequence mTitle;
    private String[] mPlanetTitles={"个人信息","任务管理","数据管理","测量设置","系统升级","关于系统"};
    private int[] imagesId = {R.drawable.pserson, R.drawable.task_manage,
            R.drawable.data_manage, R.drawable.settings, R.drawable.update_app, R.drawable.about_app};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //
        mTitle = mDrawerTitle = getTitle();

        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        initDrawerLayout();
        initDrawerList();

//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);

        //使用ActionBarDrawerToggle作为监听器
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.more, R.string.open,
                R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
//                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
//                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void initDrawerList() {
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        BaseAdapter adapter = new BaseAdapter() {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO 自动生成的方法存根
                View layout = View.inflate(PersonAcitvity.this, R.layout.menu_list_item, null);

                ImageView face = (ImageView) layout.findViewById(R.id.face);
                TextView name = (TextView) layout.findViewById(R.id.name);
                face.setImageResource(imagesId[position]);
                name.setText(mPlanetTitles[position]);

                return layout;
            }

            @Override
            public long getItemId(int position) {
                // TODO 自动生成的方法存根
                return position;
            }

            @Override
            public Object getItem(int position) {
                // TODO 自动生成的方法存根
                return mPlanetTitles[position];
            }

            @Override
            public int getCount() {
                // TODO 自动生成的方法存根
                return mPlanetTitles.length;
            }
        };

        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(this);
    }

    private void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.more,
                GravityCompat.START);
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
//        return super.onPrepareOptionsMenu(menu);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
//        switch (item.getItemId()) {
//            case R.id.action_websearch:
//                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//                intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(this, R.string.app_not_available,
//                            Toast.LENGTH_LONG).show();
//                }
//                return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        selectItem(position);
    }

    private void selectItem(int position) {
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_layout, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public PlanetFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_planet,
                    container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources()
                    .getStringArray(R.array.planets_array)[i];

            int imageId = getResources().getIdentifier(
                    planet.toLowerCase(Locale.getDefault()), "drawable",
                    getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image))
                    .setImageResource(imageId);
            getActivity().setTitle(planet);
            return rootView;
        }
    }
}

