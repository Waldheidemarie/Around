package site.shawnxxy.eventreporter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ShawnX on 9/10/17.
 */

//public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder>{
public class EventListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Event> eventList;
    // CardView admob
    private Context context;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ADS = 1;
    private AdLoader.Builder builder;
    private LayoutInflater inflater;
    private DatabaseReference databaseReference;
    private Map<Integer, NativeExpressAdView> map = new HashMap<Integer, NativeExpressAdView>();

    //constructor
    public EventListAdapter(List<Event> events, final Context context) {
//        eventList = events;

        databaseReference = FirebaseDatabase.getInstance().getReference();
        eventList = new ArrayList<Event>();
        int count = 0;
        for (int i = 0; i < events.size(); i++) {
            if (i % 2 == 1) {
                //Use a set to record advertisement position
                map.put(i + count, new NativeExpressAdView(context));
                count++;
                eventList.add(new Event());
            }
            eventList.add(events.get(i));
        }

        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public Map<Integer, NativeExpressAdView> getMap() {
        return map;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    //Compare this to view holder against ListView adapter
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView location;
        public TextView description;
        public TextView time;
        public ImageView imgview;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            title = (TextView) v.findViewById(R.id.event_item_title);
            location = (TextView) v.findViewById(R.id.event_item_location);
            description = (TextView) v.findViewById(R.id.event_item_description);
            time = (TextView) v.findViewById(R.id.event_item_time);
            imgview = (ImageView) v.findViewById(R.id.event_item_img);
        }
    }
    public class ViewHolderAds extends RecyclerView.ViewHolder {
        public ViewHolderAds(View v) {
            super(v);
        }
    }
    @Override
    public int getItemViewType(int position) {
        return map.containsKey(position) ? TYPE_ADS : TYPE_ITEM;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case TYPE_ITEM:
                ViewHolder viewHolderItem = (ViewHolder) holder;
                configureItemView(viewHolderItem, position);
                break;
            case TYPE_ADS:
                ViewHolderAds viewHolderAds = (ViewHolderAds) holder;
                configureAdsView(viewHolderAds, position);
                break;
        }

//        final Event event = eventList.get(position);
//        holder.title.setText(event.getTitle());
//        String[] locations = event.getAddress().split(",");
//        holder.location.setText(locations[1] + "," + locations[2]);
//        holder.description.setText(event.getDescription());
//        holder.time.setText(Utils.timeTransformer(event.getTime()));
//
//        if (event.getImgUri() != null) {
//            final String url = event.getImgUri();
//            holder.imgview.setVisibility(View.VISIBLE);
//            new AsyncTask<Void, Void, Bitmap>(){
//                @Override
//                protected Bitmap doInBackground(Void... params) {
//                    return Utils.getBitmapFromURL(url);
//                }
//
//                @Override
//                protected void onPostExecute(Bitmap bitmap) {
//                    holder.imgview.setImageBitmap(bitmap);
//                }
//            }.execute();
//        } else {
//            holder.imgview.setVisibility(View.GONE);
//        }
    } // End of onBindVieHolder()

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return eventList.size();
    }

    // Return the view back to listview
    public void add(int position, Event event) {
        eventList.add(position, event);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        eventList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        switch (viewType) {
            case TYPE_ITEM:
                v = inflater.inflate(R.layout.event_list_item, parent, false);
                viewHolder = new ViewHolder(v);
                break;
            case TYPE_ADS:
                v = inflater.inflate(R.layout.ads_container,
                        parent, false);
                viewHolder = new ViewHolderAds(v);
                break;
        }
        return viewHolder;
    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(
//                parent.getContext());
//        View v = inflater.inflate(R.layout.event_list_item, parent, false);
//        ViewHolder vh = new ViewHolder(v);
//        return vh;
//    }

    private void configureItemView(final ViewHolder holder, final int position) {
        final Event event = eventList.get(position);
        holder.title.setText(event.getTitle());
        String[] locations = event.getAddress().split(",");
        holder.location.setText(locations[1] + "," + locations[2]);
        holder.description.setText(event.getDescription());
        holder.time.setText(Utils.timeTransformer(event.getTime()));


        if (event.getImgUri() != null) {
            final String url = event.getImgUri();
            holder.imgview.setVisibility(View.VISIBLE);
            new AsyncTask<Void, Void, Bitmap>(){
                @Override
                protected Bitmap doInBackground(Void... params) {
                    return Utils.getBitmapFromURL(url);
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    holder.imgview.setImageBitmap(bitmap);
                }
            }.execute();
        } else {
            holder.imgview.setVisibility(View.GONE);
        }
    }

    private void configureAdsView(final ViewHolderAds adsHolder, final int position) {
        ViewHolderAds nativeExpressHolder =
                (ViewHolderAds) adsHolder;
        if (!map.containsKey(position)) {
            return;
        }
        NativeExpressAdView adView = map.get(position);
        ViewGroup adCardView = (ViewGroup) nativeExpressHolder.itemView;
        if (adCardView.getChildCount() > 0) {
            adCardView.removeAllViews();
        }

        if (adView.getParent() != null) {
            ((ViewGroup) adView.getParent()).removeView(adView);
        }

        adCardView.addView(adView);
    }

}