package toussaint.projet_android;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import toussaint.projet_android.fragment.CarteGridFragment;
import toussaint.projet_android.fragment.CompareFragment;
import toussaint.projet_android.fragment.CompteFragment;
import toussaint.projet_android.fragment.HomeFragment;
import toussaint.projet_android.utils.NotificationPublisher;
import toussaint.projet_android.utils.Utils;

public class MainApp extends AppCompatActivity implements SensorEventListener {

    private static final String TWITTER_KEY = "LYlfjX5ZhXbevfSeAIVTotSdM";
    private static final String TWITTER_SECRET = "YlBdrGDuc2v8L9e8iu1qDKnedJVgL7M3C5dHmr8sa37uoL8GaK";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private SensorManager sensorManager;
    private Sensor light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
            }
        }

        Utils.onActivityCreateSetTheme(this);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        if(Utils.getCurrentTheme().equals("default")){
            tabLayout.setupWithViewPager(mViewPager);
            tabLayout.getTabAt(0).setIcon(R.drawable.home);
            tabLayout.getTabAt(1).setIcon(R.drawable.cart_default);
            tabLayout.getTabAt(2).setIcon(R.drawable.gift2);
            tabLayout.getTabAt(3).setIcon(R.drawable.account);
        }
        else if(Utils.getCurrentTheme().equals("night")){
            tabLayout.getTabAt(0).setIcon(R.drawable.homeblack);
            tabLayout.getTabAt(1).setIcon(R.drawable.cart_dark);
            tabLayout.getTabAt(2).setIcon(R.drawable.gift2black);
            tabLayout.getTabAt(3).setIcon(R.drawable.accountblack);
        }

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        scheduleNotification(30000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float value = event.values[0];
            if(value<10 && Utils.getCurrentTheme().equals("default") && Utils.getChangeAutomatic()){
                Utils.setCurrentThemeNight();
                Utils.changeToTheme(this, Utils.THEME_BLACK);
            }
            if(value>10 && Utils.getCurrentTheme().equals("night") && Utils.getChangeAutomatic()){
                Utils.setCurrentThemeDefault();
                Utils.changeToTheme(this, Utils.THEME_DEFAULT);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume() {
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this, light);
        super.onPause();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new HomeFragment().newInstance(position+1);
            }
            else if(position == 1){
                return new CompareFragment().newInstance(position+1);
            }
            else if(position == 2){
                return new CarteGridFragment().newInstance(position+1);
            }
            else if(position == 3){
                return new CompteFragment().newInstance(position+1);
            }
            else{
                return new HomeFragment().newInstance(position+1);
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Accueil";
                case 1:
                    return "Produits";
                case 2:
                    return "Cartes";
                case 3:
                    return "Compte";
            }
            return null;
        }
    }

    private void scheduleNotification(int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
}
