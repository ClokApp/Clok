package com.kingfu.clok.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kingfu.clok.navigation.AppNavHost
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.util.NotificationPermissionForAndroid13


const val TAG = "MainActivityLog"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            ClokTheme {
                NotificationPermissionForAndroid13()

                AppNavHost()
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}



