package com.visium.fieldservice.service;

import android.content.Intent;
import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class FieldServiceInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
