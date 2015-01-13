package com.example.codetribe1.constructionappsuite;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.codetribe1.constructionappsuite.dialogs.ProjectDialog;
import com.example.codetribe1.constructionappsuite.dto.ProjectDTO;
import com.example.codetribe1.constructionappsuite.dto.transfer.RequestDTO;
import com.example.codetribe1.constructionappsuite.dto.transfer.ResponseDTO;
import com.example.codetribe1.constructionappsuite.util.ErrorUtil;
import com.example.codetribe1.constructionappsuite.util.SharedUtil;
import com.example.codetribe1.constructionappsuite.util.Statics;
import com.example.codetribe1.constructionappsuite.util.WebSocketUtil;

import java.util.List;
import java.util.Locale;


public class OperationsPager extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    Context ctx;
    ProgressBar progressBar;
    ResponseDTO response;
    int NumPages = 8;
    int currentPageIndex;
    ViewPager mViewerPager;
    private List<ProjectDTO> projectList;
    private ListView drawerListView;
    private CharSequence titles;
    private List<PageFragment> pageFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations_pager);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.imgpm);
        actionBar.show();
        actionBar.setHomeAsUpIndicator(R.drawable.back_vector48);

        FragmentPagerAdapter DevicePageAdapter = null;
        mViewerPager = (ViewPager) findViewById(R.id.pager);
        DevicePageAdapter = new myPagerAdapter(getSupportFragmentManager());
        mViewerPager.setAdapter(DevicePageAdapter);
        mViewerPager.setPageTransformer(true, new ZoomOutPageTransformer());


    }


    //get Company data
    public void getCompanyData() {

        final RequestDTO p = new RequestDTO();

        p.setRequestType(RequestDTO.GET_COMPANY_DATA);
        p.setCompanyID(SharedUtil.getCompany(ctx).getCompanyID());

        progressBar.setVisibility(View.VISIBLE);

        WebSocketUtil.sendRequest(ctx, Statics.COMPANY_ENDPOINT, p, new WebSocketUtil.WebSocketListener() {

            @Override
            public void onMessage(ResponseDTO r) {

                progressBar.setVisibility(View.GONE);

                if (!ErrorUtil.checkServerError(ctx, r)) {

                    return;
                }

                response = r;
                projectList = r.getCompany().getProjectList();

            }

            @Override
            public void onClose() {

            }

            @Override
            public void onError(String message) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_operations_pager, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return false;
    }

    //inner class
    public class myPagerAdapter extends FragmentPagerAdapter {
        public myPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            //instantiate fragments
            ProjectListFragment projectListFragment = new ProjectListFragment();
            StaffListFragment staffListFragment = new StaffListFragment();
            ClientListFragment clientListFragment = new ClientListFragment();
            BeneficiaryFragment beneficiaryFragment = new BeneficiaryFragment();
            TaskFragment taskFragment = new TaskFragment();
            TaskStatusListFragment taskStatusListFragment = new TaskStatusListFragment();
            EngineerFragment engineerFragment = new EngineerFragment();
            ProjectStatustTypeListFragment projectStatustTypeListFragment = new ProjectStatustTypeListFragment();
            GeorgeFragment georgeFragment = new GeorgeFragment();
            ProjectDialog pd = new ProjectDialog();
            switch (position) {

                case 0:

                    return projectListFragment.newInstance(0);
                case 1:
                    return staffListFragment.newInstance(1);
                case 2:
                    return clientListFragment.newInstance(2);
                case 3:
                    return taskStatusListFragment.newInstance(3);
                case 4:
                    return projectStatustTypeListFragment.newInstance(4);
                case 5:
                    return taskFragment.newInstance(5);
                case 6:
                    return engineerFragment.newInstance(6);
                case 7:
                    return beneficiaryFragment.newInstance(7);

                default:
                    return null;

            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);

                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
                case 4:
                    return getString(R.string.title_section5).toUpperCase(l);
                case 5:
                    return getString(R.string.title_section6).toUpperCase(l);
                case 6:
                    return getString(R.string.title_section7).toUpperCase(l);
                case 7:
                    return getString(R.string.title_section8).toUpperCase(l);

            }
            return null;
        }

        @Override
        public int getCount() {
            return NumPages;
        }
    }


}













