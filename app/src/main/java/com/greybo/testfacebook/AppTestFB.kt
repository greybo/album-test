package com.greybo.testfacebook

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.greybo.testfacebook.db.HelperFactory

class AppTestFB : Application() {

    override fun onCreate() {
        super.onCreate()
        HelperFactory.setHelper(getApplicationContext());
    }

    override fun onTerminate() {
        HelperFactory.releaseHelper()
        super.onTerminate()
    }
}