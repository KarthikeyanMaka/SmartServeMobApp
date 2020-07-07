package hcl.smartserve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

import static hcl.smartserve.R.string.hindi;

public class LanguageSelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] mlanguageArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        mlanguageArray = new String[]{getString(R.string.english),
                getString(hindi), getString(R.string.tamil),
                getString(R.string.telugu), getString(R.string.kannada),
                getString(R.string.bengali), getString(R.string.malayalam)};

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString(getString(R.string.language), "");
        if (!name.isEmpty()) {
            startActivity(new Intent(this, DashboardActivity.class));
        }

        Spinner spin = (Spinner) findViewById(R.id.language_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, mlanguageArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        int initialSelectedPosition = spin.getSelectedItemPosition();
        spin.setSelection(initialSelectedPosition, false);
        spin.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.language), mlanguageArray[position]);
        editor.apply();

        Locale locale = new Locale("en");
        switch (mlanguageArray[position]) {
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

        Toast.makeText(getApplicationContext(), "Language Selected:  " + mlanguageArray[position], Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, DashboardActivity.class));
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
