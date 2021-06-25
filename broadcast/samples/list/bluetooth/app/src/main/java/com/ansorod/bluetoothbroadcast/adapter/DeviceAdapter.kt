package com.ansorod.bluetoothbroadcast.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ansorod.bluetoothbroadcast.R
import com.ansorod.bluetoothbroadcast.model.BluetoothDevice

class DeviceAdapter: RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {

    private var items: List<BluetoothDevice>? = null

    fun setData(data: List<BluetoothDevice>?) {
        items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.let { itemList ->
            val current = itemList[position]
            holder.deviceName.text = current.name
            holder.deviceAddress.text = current.macAddress
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val deviceName = view.findViewById<TextView>(R.id.deviceName)
        val deviceAddress = view.findViewById<TextView>(R.id.deviceAddress)
    }

}