package com.localgo.localgo2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.localgo.localgo2.ui.theme.LocalGo2Theme
import com.localgo.localgo2.ui.root.RootScaffold

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocalGo2Theme {
                RootScaffold()
            }
        }
    }
}
