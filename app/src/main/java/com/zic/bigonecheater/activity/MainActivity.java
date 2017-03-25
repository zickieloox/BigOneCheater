package com.zic.bigonecheater.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zic.bigonecheater.R;
import com.zic.bigonecheater.adapter.SectionsPagerAdapter;
import com.zic.bigonecheater.fragment.AccountRecyclerFragment;
import com.zic.bigonecheater.fragment.SettingsFragment;
import com.zic.bigonecheater.utils.ShellUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);

        setSupportActionBar(toolbar);

        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                snackBarAction();
                            }
                        }).show();
            }
        });

        if (!ShellUtils.isSuAvailable()) {
            Toast.makeText(this, getString(R.string.toast_err_root), Toast.LENGTH_SHORT).show();
        }

    }

    public void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AccountRecyclerFragment(), "Account");
        adapter.addFragment(new SettingsFragment(), "Settings");
        viewPager.setAdapter(adapter);
    }

    public void snackBarAction() {
        Toast.makeText(this, "Action", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_exit:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
