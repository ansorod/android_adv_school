package com.ansorod.bluetoothbroadcast

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ansorod.bluetoothbroadcast.adapter.DeviceAdapter
import com.ansorod.bluetoothbroadcast.model.BluetoothDevice
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var deviceList = ArrayList<BluetoothDevice>()
    private val adapter = DeviceAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        startScanButton.setOnClickListener {
            deviceList.clear()
            BluetoothAdapter.getDefaultAdapter().startDiscovery()
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        deviceRecyclerView.layoutManager = layoutManager
        deviceRecyclerView.adapter = adapter
    }


    private val bluetoothReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let { receivedIntent ->
                when(receivedIntent.action) {
                    BluetoothAdapter.ACTION_DISCOVERY_STARTED -> showProgress()
                    BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> hideProgress()
                    android.bluetooth.BluetoothDevice.ACTION_FOUND -> onDeviceFound(receivedIntent.getParcelableExtra(android.bluetooth.BluetoothDevice.EXTRA_DEVICE))
                }
            }
        }
    }

    private fun registerReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        intentFilter.addAction(android.bluetooth.BluetoothDevice.ACTION_FOUND)

        registerReceiver(bluetoothReceiver, intentFilter)
    }

    private fun unregisterReceiver() {
        unregisterReceiver(bluetoothReceiver)
    }

    private fun onDeviceFound(device: android.bluetooth.BluetoothDevice?) {
        device?.let { btDevice ->
            val name = if(btDevice.name == null) getString(R.string.name_unknown) else btDevice.name
            val deviceInfo = BluetoothDevice(name, btDevice.address)
            if(deviceList.none { bluetoothDevice -> bluetoothDevice.macAddress == device.address }) {
                deviceList.add(deviceInfo)
                adapter.setData(deviceList)
            }
        }
    }

    private fun showProgress() {
        startScanButton.visibility = View.GONE
        scanProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        startScanButton.visibility = View.VISIBLE
        scanProgress.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver()
    }

    private fun checkForPermission() {
        val result = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        if(result == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == LOCATION_REQUEST && PackageManager.PERMISSION_GRANTED != grantResults[0]) {
            Toast.makeText(this, "Location permission needed", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver()
        checkForPermission()
    }

    companion object {
        private const val LOCATION_REQUEST = 0
    }
}