package recdata.cl.seguidorgps;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static String GPSIDactual, lat, lon, hoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("HRgestion.cl - Seguidor GPS");


        Button startBackService = (Button) findViewById(R.id.start_background_service_button);
        startBackService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start android service.
                Intent startServiceIntent = new Intent(MainActivity.this, ParaSiempre.class);
                startService(startServiceIntent);


            }
        });

        Button stopBackService = (Button) findViewById(R.id.stop_background_service_button);
        stopBackService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Stop android service.
                Intent stopServiceIntent = new Intent(MainActivity.this, ParaSiempre.class);
                stopService(stopServiceIntent);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GPSTracker gt = new GPSTracker(getApplicationContext());

                Location l = gt.getLocation();

                if (l == null) {
                    Toast.makeText(getApplicationContext(), "GPS unable to get Value", Toast.LENGTH_SHORT).show();
                } else {
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    Toast.makeText(getApplicationContext(), "GPS Lat = " + lat + "\n lon = " + lon, Toast.LENGTH_SHORT).show();
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDateandTime = sdf.format(new Date());

                GPSIDactual = "movil1";

                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        //your method
                        GPSTracker gt = new GPSTracker(getApplicationContext());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentDateandTime = sdf.format(new Date());

                        Location l = gt.getLocation();
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                        database.child(GPSIDactual).child("lat").setValue(l.getLatitude());
                        database.child(GPSIDactual).child("lon").setValue(l.getLongitude());
                        database.child(GPSIDactual).child("Fecha").setValue(currentDateandTime);
                    }
                }, 0, 10000);


                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
