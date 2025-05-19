package com.averito.firito.data.managers

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class HealthConnectManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val healthConnectClient: HealthConnectClient
) {
    private val PERMISSIONS = setOf<String>(
        HealthPermission.getReadPermission(StepsRecord::class),
        HealthPermission.getReadPermission(DistanceRecord::class),
        HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class)
    )

    private val _permissionsGrantedFlow = MutableStateFlow(false)
    val permissionsGrantedFlow: StateFlow<Boolean> = _permissionsGrantedFlow

    suspend fun emitGranted() {
        _permissionsGrantedFlow.emit(true)
    }

    fun isAvailable(): Boolean = HealthConnectClient.getSdkStatus(context, "com.google.android.apps.healthdata") == HealthConnectClient.SDK_AVAILABLE

    suspend fun getGrantedPermissions(): Set<String> =
        healthConnectClient.permissionController.getGrantedPermissions()

    suspend fun getMissingPermissions(): Set<String> =
        PERMISSIONS - getGrantedPermissions()

    suspend fun hasAllPermissions(): Boolean =
        getMissingPermissions().isEmpty()

    fun checkAllPermissions(granted: Set<String>): Boolean = granted.containsAll(PERMISSIONS)

    fun registerPermissionLauncher(owner: ComponentActivity, onResult: (Set<String>) -> Unit): ActivityResultLauncher<Set<String>> {
        return owner.registerForActivityResult(PermissionController.createRequestPermissionResultContract(), onResult)
    }
}