package com.example.user010.vero_project.network;


import android.graphics.Bitmap;

import org.json.JSONObject;

/**
 * NetworkResListener interface
 */
public interface NetworkResListener {
    /**
     * callback method which called when the resources update is started
     */
    public void onPreUpdate();

    /**
     * callback method which called after resources update is finished
     * @param  res  - the data
     * @param status - the status of the update process
     */
    public void onPostUpdate(byte[] res, ResStatus status);

    public void onPostUpdate(JSONObject res, ResStatus status);

    public void onPostUpdate(Bitmap res, ResStatus status);
}
