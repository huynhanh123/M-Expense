package vn.edu.greenwich.dbsqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

import vn.edu.greenwich.dbsqlite.Trip.TripDetailActivity;

public class DrawerNav extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;


    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_nav, null);
        FrameLayout container = drawerLayout.findViewById(R.id.action_container);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()){
            case R.id.nav_home:
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0,0);
                break;

            case R.id.nav_add:
                startActivity(new Intent(this, TripDetailActivity.class));
                overridePendingTransition(0,0);
                break;

            case R.id.nav_setting:
                startActivity(new Intent(this, SettingActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_about_us:
                

        }
        return false;
    }

    protected void allocateActivityTitle (String titleString) {
        if (getSupportActionBar() != null ) {
            getSupportActionBar().setTitle(titleString);
        }
    }
}