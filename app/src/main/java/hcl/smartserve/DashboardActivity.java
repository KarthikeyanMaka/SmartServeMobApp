package hcl.smartserve;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.VideoView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

import static hcl.smartserve.R.string.hindi;

public class DashboardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        playVideo();
    }

    private void playVideo() {
        String mUrl = "http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
        String mUrl2 = "http://dev.hpac.dev-site.org/sites/default/files/videos/about/mobile.mp4";

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test);
        VideoView mVideoView = (VideoView) findViewById(R.id.videoview);
        mVideoView.setVideoURI(uri);
        mVideoView.start();

        MediaController videoMediaController = new MediaController(this);
        videoMediaController.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(videoMediaController);
//        DisplayMetrics dm = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int height = dm.heightPixels;
//        int width = dm.widthPixels;
//        mVideoView.setMinimumWidth(width);
//        mVideoView.setMinimumHeight(height);
//        mVideoView.setVideoPath(mUrl2);
//
////        mVideoView.setOnErrorListener(mOnErrorListener);
////        mVideoView.requestFocus();
//        mVideoView.start();
    }

//    private MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {
//
//        @Override
//        public boolean onError(MediaPlayer mp, int what, int extra) {
//            // Your code goes here
//            return true;
//        }
//    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);

        MenuItem item = menu.findItem(R.id.action_spinner);
        Spinner spinner = (Spinner) item.getActionView();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getString(R.string.language), selectedItem);
                editor.apply();

                Locale locale = new Locale("en");
                switch (selectedItem) {
                    case "தமிழ்":
                        locale = new Locale("ta");
                        break;
                    case "हिन्दी":
                        locale = new Locale("hi");
                        break;
                    case "తెలుగు":
                        locale = new Locale("te");
                        break;
                    case "ಕನ್ನಡ":
                        locale = new Locale("kn");
                        break;
                    case "മലയാളം":
                        locale = new Locale("ml");
                        break;
                    case "বাংলা":
                        locale = new Locale("bn");
                        break;
                    default:
                        locale = new Locale("en");
                        break;
                }
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//                recreate();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] mlanguageArray = new String[]{getString(R.string.english),
                getString(hindi), getString(R.string.tamil),
                getString(R.string.telugu), getString(R.string.kannada),
                getString(R.string.bengali), getString(R.string.malayalam)};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, mlanguageArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
