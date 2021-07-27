package com.example.wsugooglemaps;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;

public class User {
    // private static final String TAG = ;
    // User class to store user data for firebase

    // String variables for user data
    public String name, age, email;

    // Empty public constructor to access variables
    public User() {

    }

    // Constructor for arguments
    public User(String Name, String Age, String EmailAddress) {
        // initialize variables
        this.name = Name;
        this.age = Age;
        this.email = EmailAddress;
    }

    private ClusterManager mClusterManager;
    private MyClusterManagerRenderer mClusterManagerRenderer;
    private ArrayList<ClusterMarker> mClusterMarkers = new ArrayList<>();

    private void addMapMarkers() {
        GoogleMap mGoogleMap = null;
        Log log;
        if(mGoogleMap != null) {
            if (mClusterManager == null) {
              //  ClusterManager<ClusterMarker> mClusterManger = new ClusterManager<ClusterMarker>(getActivity().getApplicationContext(), mGoogleMap);
            }
            if (mClusterManagerRenderer == null) {
                //      mClusterManagerRenderer = new MyClusterManagerRenderer(
                //     getActivity(),
                //        mGoogleMap,
                //         mClusterManager
                //   );
                //   mClusterManager.setRenderer(mClusterManagerRenderer);
            }
            // for(UserLocation userLocation: mUserLocation){
            //  log.d(TAG "addMapMarkers: location: " + userLocation.getGeo_pointer().toString());
            //    try)
            String snippet = "";
            //  if(userLocation.getUser().getUser_id().equals(FirstbaseAuth.getInstance().getUid())){
            snippet = "This must be you";
        } else {
            //      snippet = "Determine route to " + userLocation.getUser().getUsername() + "?";
        }
        int avatar = R.drawable.amu_bubble_mask;
        try {
            //       avatar = Integer.parseInt(userLocation.getUser().getAvatar());
        } catch (NumberFormatException e) {
            //   Log.d (TAG, "addMarkers: no avatar for " + userLocation.getUser(),getUsername() + ", setting default" );}

            //  ClusterMarker newClusterMarker = new CLusterMarker(
            //         new LatLng(userLocation.getGeo_pointer().getLatitude(), userLocation.getGeo_pointer(),getLongtitude()),
            //         userLocation.getUser().getUsername(),
            //         snippet,
            //             avatar,
            //         userLocation.getUser()
            //    );
            //   mClusterManager.addItem(newClusterMarker);
            //    mClusterMarkers.add(newClusterMarker);
        }
//catch(NullPointerException e) }

        // private Context getActivity() {
    /*
    }
       log.e(TAG, "addMarkers: NullPointerException: " + e.getMessage() );
    */
        //}

//}
//mClusterManager.cluster();
        //  void setCameraView();
//}


    }
}