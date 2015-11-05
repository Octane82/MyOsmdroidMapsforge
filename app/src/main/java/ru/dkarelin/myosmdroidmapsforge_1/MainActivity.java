package ru.dkarelin.myosmdroidmapsforge_1;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.IRegisterReceiver;
import org.osmdroid.tileprovider.MapTileProviderArray;
import org.osmdroid.tileprovider.modules.MapTileModuleProviderBase;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.osmdroid.util.GeoPoint;

import java.io.File;

import ru.dkarelin.myosmdroidmapsforge_1.osm.MFMapView;
import ru.dkarelin.myosmdroidmapsforge_1.osm.MFTileModuleProvider;
import ru.dkarelin.myosmdroidmapsforge_1.osm.MFTileSource;

// http://osmbonuspack.googlecode.com/svn/trunk/OSMBonusPackTuto/src/com/example/osmbonuspacktuto/MainActivity.java
public class MainActivity extends ActionBarActivity implements IRegisterReceiver{

    MFMapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Создаём Instance mapsforge
        AndroidGraphicFactory.createInstance(this.getApplication());

        try {
            // OSMdroid
            ResourceProxy resourceProxy = new DefaultResourceProxyImpl(this);

            // Create a custom tile source
            final ITileSource tileSource = new MFTileSource(8,20, 256,
                    Environment.getExternalStorageDirectory() + "/DolphinLocation"
                            + "/map/moskovskaya.map",
                    Environment.getExternalStorageDirectory() + "/DolphinLocation"
                            + "/renderthemes/detailed.xml"
                    , this);

            // OSMDroid
            final IRegisterReceiver registerReceiver = new SimpleRegisterReceiver(this);

            MFTileModuleProvider moduleProvider;
            moduleProvider = new MFTileModuleProvider(this, new File(""), (MFTileSource)tileSource);



            MapTileModuleProviderBase[] pTileProviderArray;
            pTileProviderArray = new MapTileModuleProviderBase[] { moduleProvider};

            //
            final MapTileProviderArray tileProviderArray = new MapTileProviderArray(
                    tileSource, registerReceiver, pTileProviderArray);

            mapView = new MFMapView(this, tileProviderArray.getTileSource().getTileSizePixels(),
                    resourceProxy, tileProviderArray);


            mapView.setBuiltInZoomControls(false);
            mapView.setMultiTouchControls(true);

            final LinearLayout mapLinearLayout = (LinearLayout) this.findViewById(R.id.mapLinearLayout);

            mapLinearLayout.addView(mapView);


            mapView.setCurrentAsCenter();
            mapView.addMyLocationOverlay();

            mapView.setBuiltInZoomControls(true);

            // My map tests
            // Отображение маркеров, маршрутов и т.д. в классе MFMapView


        } catch (Exception ex) {
            ex.printStackTrace();
        }


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
