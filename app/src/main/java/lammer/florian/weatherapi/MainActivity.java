package lammer.florian.weatherapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button getCityButton;
    public static TextView showJSONtextView;
    private EditText enterCityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCityButton = findViewById(R.id.queryButton);
        enterCityName = findViewById(R.id.cityText);
        showJSONtextView = findViewById(R.id.responseView);

        getCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeather querry = new getWeather(enterCityName.getText().toString());
                querry.execute();

            }
        });
    }
}
