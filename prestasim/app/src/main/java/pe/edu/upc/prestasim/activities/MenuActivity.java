package pe.edu.upc.prestasim.activities;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.view.View;
import android.widget.TextView;

import pe.edu.upc.prestasim.PrestasimApplication;
import pe.edu.upc.prestasim.R;
import pe.edu.upc.prestasim.fragments.UserFragment;
import pe.edu.upc.prestasim.fragments.RequestFragment;
import pe.edu.upc.prestasim.fragments.HistoryFragment;
import pe.edu.upc.prestasim.models.User;
import pe.edu.upc.prestasim.services.UserService;
import pe.edu.upc.prestasim.utils.Utilities;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mSupportFragmentManager;
    private TextView nameLoggedUserTV, mailLoggedUserTV;
    private UserService userService;
    private User user;
    private ProgressDialog mProgressDialog;
    int lastPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userService = ((PrestasimApplication) getApplication()).getUserService();
        user = userService.obtainCurrentUser();
        if(!Utilities.isNullOrEmpty(user)) {
            setContentView(R.layout.activity_menu);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setCheckedItem(R.id.nav_new_request);
            mSupportFragmentManager = getSupportFragmentManager();

            View headerView = navigationView.inflateHeaderView(R.layout.nav_header_menu);
            nameLoggedUserTV = (TextView) headerView.findViewById(R.id.nameLoggedUserTV);
            nameLoggedUserTV.setText(user.getName());
            mailLoggedUserTV = (TextView) headerView.findViewById(R.id.mailLoggedUserTV);
            mailLoggedUserTV.setText(user.getEmail());
            displayOption(R.id.nav_new_request);
            loadProgressDialog();
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
            case R.id.nav_new_request:
                fragment = RequestFragment.newInstance();
                break;
            case R.id.nav_history_request:
                fragment = HistoryFragment.newInstance();
                break;
            case R.id.nav_my_info:
                fragment = UserFragment.newInstance();
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
        userService.deleteUser(user);
        startActivity(new Intent(this, MainActivity.class));
    }

    public void setActionBarTitle(String title){
        if(getActionBar() != null){
            getActionBar().setTitle(title);
        } else {
            getSupportActionBar().setTitle(title);
        }
    }

    private void loadProgressDialog() {
        mProgressDialog = new ProgressDialog(MenuActivity.this);
        mProgressDialog.setMessage(getResources().getString(R.string.loading));
    }

    public void showDialog(){

    }

}
