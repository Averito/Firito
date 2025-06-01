package com.averito.firito

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.lifecycleScope
import com.averito.firito.data.managers.HealthConnectManager
import com.averito.firito.data.managers.PermissionManager
import com.averito.firito.ui.shared.ui.app_navigation.AppNavGraph
import com.averito.firito.ui.theme.FiritoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var healthConnectManager: HealthConnectManager
    @Inject lateinit var permissionManager: PermissionManager

    private lateinit var activityPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var healthPermissionLauncher: ActivityResultLauncher<Set<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        initializeLaunchers()
        checkAndRequestPermissions()

        setContent {
            FiritoTheme {
                AppNavGraph()
            }
        }
    }

    private fun initializeLaunchers() {
        activityPermissionLauncher = permissionManager.registerSinglePermissionLauncher(this) { isGranted ->
            when {
                !isGranted -> Toast.makeText(this, "Нет доступа к физической активности", Toast.LENGTH_LONG).show()
                else -> checkAndRequestHealthPermissions()
            }
        }
        healthPermissionLauncher = healthConnectManager.registerPermissionLauncher(this) { granted ->
            lifecycleScope.launch {
                if (!healthConnectManager.checkAllPermissions(healthConnectManager.getGrantedPermissions()))
                    Toast.makeText(this@MainActivity, "Разрешения Health Connect не предоставлены", Toast.LENGTH_LONG).show()
                else healthConnectManager.emitGranted()
            }
        }
    }
    private fun checkAndRequestPermissions() {
        when {
            !permissionManager.isPermissionGranted(android.Manifest.permission.ACTIVITY_RECOGNITION) -> {
                activityPermissionLauncher.launch(android.Manifest.permission.ACTIVITY_RECOGNITION)
            }
            else -> checkAndRequestHealthPermissions()
        }
    }
    private fun checkAndRequestHealthPermissions() {
        if (!healthConnectManager.isAvailable()) return

        lifecycleScope.launch {
            val missingPermissions = healthConnectManager.getMissingPermissions()
            if (missingPermissions.isEmpty()) return@launch
            healthPermissionLauncher.launch(healthConnectManager.getMissingPermissions())
        }
    }
}
