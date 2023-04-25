package com.donay.orcamento;

import android.hardware.Camera;
import android.util.Log;

public class Camera3 {
    private boolean safeCameraOpen(int id) {
        boolean qOpened = false;
        Camera camera;
        try {

            camera = Camera.open(id);

            qOpened = (camera != null);
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }

        return qOpened;
    }

    private String getString(int app_name) {
        return null;
    }


    private void releaseCameraAndPreview() {

    }
}
