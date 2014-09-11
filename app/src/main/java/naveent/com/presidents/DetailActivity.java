package naveent.com.presidents;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import naveent.com.presidents.R;

public class DetailActivity extends ActionBarActivity {
    private ImageView detailImage;
    private TextView detailName;
    private TextView detailBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();
        President president = (President)extras.getSerializable("president");
        detailImage = (ImageView) findViewById(R.id.detailImage);
        detailName = (TextView) findViewById(R.id.detailName);
        detailBio = (TextView) findViewById(R.id.detailBio);
        detailBio.setText(president.getBio());
        detailName.setText(president.getName());
        detailImage.setImageResource(president.getImage());
        detailBio.setMovementMethod(new ScrollingMovementMethod());
    }
}
