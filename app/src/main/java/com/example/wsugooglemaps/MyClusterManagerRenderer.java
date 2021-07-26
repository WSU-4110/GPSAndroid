package com.example.wsugooglemaps;

import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.content.Context;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.ui.IconGenerator;


public class MyClusterManagerRenderer {
    private IconGenerator iconGenerator;
    //private final ImageView imageView;
   // private final int markerWidth;
   // private final int markerHeight;

    public MyClusterManagerRenderer(Context context, GoogleMap map,
                                    ClusterManager<ClusterMarker> clusterManager) {
      //  super(context, map, ClusterManager);
        this.iconGenerator = iconGenerator;
       // this.imageView = imageView;



        iconGenerator = new IconGenerator(context.getApplicationContext());
        //imageView = new ImageView(context.getApplicationContext());
       // markerWidth = (int) context.getResources().getDimension(R.dimen.custom_marker_image);
       // markerHeight = (int) context.getResources().getDimension(R.dimen.custom_marker_image);
       // imageView.setLayoutParams(new ViewGroup.LayoutParams(markerWidth, markerHeight));
       // int padding = (int) context.getResources().getDimension(R.dimen.custom_marker_padding);
       // iconGenerator.setContentView(imageView);
    }
//}

    // @Override
    protected void onBeforeClusterItemRenderer(ClusterMarker item, MarkerOptions markerOptions) {
        //imageView.setImageResource(item.getIconPicture());
        Bitmap icon = iconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.getTitle());


    }
    //@Override
    protected boolean shouldRenderAsCluster(Cluster<ClusterMarker> cluster) {
     return false;
   }
}

