package com.otb.videogallery

import android.app.Application
import com.youtility.utils.SharedPreferenceHelper

/**
 * Created by Mohit Rajput on 15/9/20.
 */
class VideoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferenceHelper.init(this)
    }
}