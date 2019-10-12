package recdata.cl.seguidorgps;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SeguidorGPS extends Service {
    public SeguidorGPS() {
    }

    public static String GPSIDactual, lat, lon, hoy;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Invoke background service onCreate method.", Toast.LENGTH_LONG).show();
        super.onCreate();


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Invoke background service onStartCommand method.", Toast.LENGTH_LONG).show();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //your method
                GPSTracker gt = new GPSTracker(getApplicationContext());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDateandTime = sdf.format(new Date());
                GPSIDactual = "movil1";
                Location l = gt.getLocation();
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                database.child(GPSIDactual).child("lat").setValue(l.getLatitude());
                database.child(GPSIDactual).child("lon").setValue(l.getLongitude());
                database.child(GPSIDactual).child("Fecha").setValue(currentDateandTime);
            }
        }, 0, 10000);


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Invoke background service onDestroy method.", Toast.LENGTH_LONG).show();
    }
}
