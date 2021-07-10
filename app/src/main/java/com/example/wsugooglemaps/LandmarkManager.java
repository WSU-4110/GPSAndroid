package com.example.wsugooglemaps;

class LandmarkManager {

    private static LandmarkManager instance;

    private int Landmarks[];

    private LandmarkManager() {}

    public static LandmarkManager getInstance()
    {
        if(instance == null)
        {
            instance = new LandmarkManager();
        }
        return instance;
    }

    public void createLandmark()
    {

    }

    public void getAllLandmarks()
    {

    }


}
