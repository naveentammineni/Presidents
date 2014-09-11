package naveent.com.presidents;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NaveenT on 9/10/14.
 */
public class PresidentsAdapter extends BaseAdapter {
    private Context thisContext;
    private LayoutInflater inflater;
    private List<President> presidentsList = null;
    private ArrayList<President> arraylist;

    public PresidentsAdapter(Context context, List<President> presidentsList) {
        thisContext = context;
        inflater = LayoutInflater.from(thisContext);
        this.presidentsList = presidentsList;
        this.arraylist = new ArrayList<President>();
        this.arraylist.addAll(presidentsList);
    }

    @Override
    public int getCount() {
        return presidentsList.size();
    }

    @Override
    public President getItem(int position) {
        return presidentsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        PresidentsHolder holder;
        try {
            if (view == null) {
                view = inflater.inflate(R.layout.presidents_list_item, null);
                holder = new PresidentsHolder(view);
                view.setTag(holder);
            } else {
                holder = (PresidentsHolder) view.getTag();
            }
            President president = presidentsList.get(position);
            holder.nameTextView.setText(president.getName());
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .resetViewBeforeLoading(true)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                    .bitmapConfig(Bitmap.Config.ARGB_8888)
                    .build();
            if(holder.profileImageView != null) {
                String imageUri = "drawable://" +president.getImage();
                MyActivity.imageLoader.displayImage(imageUri, holder.profileImageView, options);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return view;
    }

    class PresidentsHolder {
        public TextView nameTextView;
        public ImageView profileImageView;

        public PresidentsHolder(View convertView){
            // UI references
            nameTextView= (TextView) convertView.findViewById(R.id.nameTV);
            profileImageView= (ImageView) convertView.findViewById(R.id.profileimageIV);
        }
    }

}
