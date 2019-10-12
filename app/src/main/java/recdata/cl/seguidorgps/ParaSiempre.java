package recdata.cl.seguidorgps;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */


public class ParaSiempre extends IntentService {


    public static String GPSIDactual, lat, lon, hoy;
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "recdata.cl.seguidorgps.action.FOO";
    private static final String ACTION_BAZ = "recdata.cl.seguidorgps.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "recdata.cl.seguidorgps.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "recdata.cl.seguidorgps.extra.PARAM2";

    public ParaSiempre() {
        super("ParaSiempre");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Servicio empezó", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ParaSiempre.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ParaSiempre.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

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

        GPSIDactual = "moviles/movil1";

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


    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
