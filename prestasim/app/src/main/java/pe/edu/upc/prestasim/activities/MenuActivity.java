package pe.edu.upc.prestasim.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import pe.edu.upc.prestasim.R;
import pe.edu.upc.prestasim.fragments.ConsultingFragment;
import pe.edu.upc.prestasim.fragments.MobileFragment;
import pe.edu.upc.prestasim.fragments.WebFragment;
import pe.edu.upc.prestasim.utils.Constants;
import pe.edu.upc.prestasim.utils.Utilities;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mSupportFragmentManager;
    private TextView nameLoggedUserTV, mailLoggedUserTV;
    int lastPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = this.getSharedPreferences(getString
                (R.string.preference_file_key), Context.MODE_PRIVATE);
        if(sharedPref.getInt(Constants.CURRENT_USER_ID, 0) > 0) {
            setContentView(R.layout.activity_menu);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            nameLoggedUserTV = (TextView) findViewById(R.id.nameLoggedUserTV);
            nameLoggedUserTV.setText(sharedPref.getString(Constants.CURRENT_USER_NAME, ""));
            mailLoggedUserTV = (TextView) findViewById(R.id.mailLoggedUserTV);
            mailLoggedUserTV.setText(sharedPref.getString(Constants.CURRENT_USER_MAIL, ""));

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setCheckedItem(R.id.nav_mobile);
            mSupportFragmentManager = getSupportFragmentManager();
            displayOption(R.id.nav_mobile);
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displayOption(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayOption(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_mobile:
                fragment = MobileFragment.newInstance();
                break;
            case R.id.nav_web:
                fragment = WebFragment.newInstance();
                break;
            case R.id.nav_cloud:
                fragment = ConsultingFragment.newInstance();
                break;
            case R.id.nav_close:
                closeSession();
                break;
        }

        if (fragment != null && lastPosition != id) {
            mSupportFragmentManager.beginTransaction()
                    .replace(R.id.flContent, fragment).commit();
        }
    }

    private void closeSession(){
        Utilities.updateSharedPreferences
                (this, getString(R.string.preference_file_key), 0, null, null);
        startActivity(new Intent(this, MainActivity.class));
    }
}
