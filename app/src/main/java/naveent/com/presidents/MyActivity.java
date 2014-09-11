package naveent.com.presidents;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MyActivity extends ActionBarActivity {

    private static Context appContext;
    public static ImageLoader imageLoader;
    static List<President> presidents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = this;
        setContentView(R.layout.activity_my);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .writeDebugLogs()
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements AdapterView.OnItemClickListener{
        private ListView presidentsListView;
        ProgressDialog mProgressDialog;

        President president;
        private String[] presidentnames;
        private TypedArray presidentimages;
        private String[] presidentbios;
        private PresidentsDataSource dataSource;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);
            presidents = new ArrayList<President>();
            presidentsListView = (ListView) rootView.findViewById(R.id.presidentsList);
            presidentimages = getResources()
                    .obtainTypedArray(R.array.president_images);
            presidentnames = getResources().getStringArray(R.array.president_names);
            presidentbios = getResources().getStringArray(R.array.president_bio);
            presidentsListView.setOnItemClickListener(this);
            //presidentsList.setAdapter(new PresidentsAdapter());
            //dataSource = new PresidentsDataSource(MyActivity.appContext);
            //dataSource.open();
            new GraphAPICall().execute();
            //dataSource.close();
            return rootView;
        }
        @Override
        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
            President president = presidents.get(position);
            Intent detailIntent = new Intent(appContext, DetailActivity.class);
            detailIntent.putExtra("president",president);
            startActivity(detailIntent);
        }

        class GraphAPICall extends AsyncTask<String, Void, Void> {



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Create a progressdialog
                mProgressDialog = new ProgressDialog(MyActivity.appContext);
                // Set progressdialog title
                mProgressDialog.setTitle("Loading President Bios...");
                // Set progressdialog message
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                // Show progressdialog
                mProgressDialog.show();

            }

            protected Void doInBackground(String... ids) {
                for(int i = 0; i < presidentnames.length; i++){
                    president = new President(i,presidentnames[i],presidentimages.getResourceId(i, -1),presidentbios[i]);
                    presidents.add(president);
                }
                return null;
            }

            protected void onPostExecute(Void param) {
                mProgressDialog.dismiss();
                PresidentsAdapter adapter = new PresidentsAdapter(MyActivity.appContext, presidents);
                presidentsListView.setAdapter(adapter);

            }
        }
    }
}
