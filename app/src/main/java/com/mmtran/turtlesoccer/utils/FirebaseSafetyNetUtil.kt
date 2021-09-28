package com.mmtran.turtlesoccer.utils

import android.content.Context
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.google.firebase.FirebaseApp

object FirebaseSafetyNetUtil {
    @JvmStatic
    fun init(context: Context?) {
        FirebaseApp.initializeApp(context!!)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            SafetyNetAppCheckProviderFactory.getInstance()
        )
    }
}
