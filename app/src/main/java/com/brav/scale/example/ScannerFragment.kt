package com.brav.scale.example

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.brav.bravlib.BravApiProvider
import com.brav.bravlib.api.BravBleApi
import com.brav.bravlib.api.BravBleEventListener
import com.brav.bravlib.model.BravDevice
import com.brav.bravlib.model.BravScanOptions
import com.brav.bravlib.types.BravBleEnableState
import com.brav.bravlib.types.BravDeviceConnectionState
import com.brav.scale.example.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ScannerFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val deviceList = mutableListOf<BravDevice>()

    private lateinit var bleApi: BravBleApi

    private var isScanning = false

    private val bleEventListener = object : BravBleEventListener {
        override fun onBleEnableStateChange(state: BravBleEnableState) {
            Log.d("ble", "onBleEnableStateChange：" + state)
            setupBleStateTv()
            if (state == BravBleEnableState.Disable) {
                isScanning = false
                binding.scanBtn.text = if (isScanning) "Stop scan" else "Start scan"
            }
        }

        override fun onBravDeviceFound(device: BravDevice) {
            val d = deviceList.find { it.deviceId == device.deviceId }
            if (d == null) {
                deviceList.add(device)
                deviceAdapter.notifyDataSetChanged()
            }

        }

        override fun onConnectionChange(deviceId: String, state: BravDeviceConnectionState) {
            Log.d("ble", "onConnectionChange：FirstFragment " + state)
        }

    }

    private fun nativeToDeviceFragment(bravDevice: BravDevice) {
        val args = Bundle()
        args.putString("deviceId", bravDevice.deviceId)
        findNavController().navigate(R.id.action_FirstFragment_to_MeasureFrament, args)
    }

    private val deviceAdapter = object : BaseAdapter() {
        override fun getCount() = deviceList.size

        override fun getItem(position: Int) = deviceList[position]

        override fun getItemId(position: Int) = 0L

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val holder: ViewHolder
            var targetView: View
            if (convertView != null) {
                holder = convertView.tag as ViewHolder
                targetView = convertView
            } else {
                targetView = View.inflate(context, R.layout.item_device, null)
                holder = ViewHolder()
                targetView.tag = holder
                holder.macTv = targetView.findViewById(R.id.macTv)
                holder.connectBtn = targetView.findViewById(R.id.connectBtn)
            }
            val device = deviceList[position]
            holder.macTv?.text = device.mac
            holder.connectBtn?.setOnClickListener {
                nativeToDeviceFragment(device)
            }
            return targetView
        }
    }

    private class ViewHolder {
        var macTv: TextView? = null
        var connectBtn: Button? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scanBtn.setOnClickListener {
            if (isScanning) {
                this.bleApi.stopScan()
                this.isScanning = false
            } else {
                val result = this.bleApi.startScan(BravScanOptions())
                this.deviceList.clear()
                this.deviceAdapter.notifyDataSetChanged()
                if (result.isSuccess) {
                    this.isScanning = true
                } else {
                    Log.e("scanning", "fail " + result.code)
                }
            }
            binding.scanBtn.text = if (isScanning) "Stop scan" else "Start scan"
        }
        binding.deviceListView.adapter = deviceAdapter

        this.bleApi = BravApiProvider.sharedBleApi()
        this.bleApi.setBleEventListener(this.bleEventListener)
//        findNavController().navigate(R.id.action_FirstFragment_to_MeasureFrament)
        this.setupBleStateTv()
    }

    private fun setupBleStateTv() {
        if (this.bleApi.bleEnableState == BravBleEnableState.Enable) {
            binding.bluetoothStateTv.text = "Ble enable"
        } else {
            binding.bluetoothStateTv.text = "Ble disable"
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
//        this.bleApi.setBleEventListener(null)
//        this.bleApi.close()
    }
}