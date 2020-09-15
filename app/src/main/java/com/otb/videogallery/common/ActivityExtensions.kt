package com.otb.videogallery.common

import android.app.Activity
import android.widget.Toast

/**
 * Created by Mohit Rajput on 14/9/20.
 */

fun Activity.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}