package com.brav.scale.example

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.brav.bravlib.BravApiProvider
import com.brav.bravlib.api.BravBleConnectionChangeListener
import com.brav.bravlib.scale.BravScaleEventListener
import com.brav.bravlib.scale.broadcast.BroadcastScaleHandler
import com.brav.bravlib.scale.model.BravScaleData
import com.brav.bravlib.scale.model.BravScaleDataOptions
import com.brav.bravlib.scale.model.BravScaleOriginData
import com.brav.bravlib.scale.model.BravScaleUser
import com.brav.bravlib.scale.typs.BravScaleAlgorithmMethod
import com.brav.bravlib.scale.typs.BravScaleUnit
import com.brav.bravlib.types.BravDeviceConnectionState
import com.brav.bravlib.types.BravGender
import com.brav.scale.example.databinding.FragmentSecondBinding
import com.brav.scale.report.I18n
import com.brav.scale.report.ReportBuilder
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.model.ReportItem
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MeasureFragment : Fragment() {
    init {

    }

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val bleApi = BravApiProvider.sharedBleApi()
    private var deviceId: String = ""
    private var scaleHandler: BroadcastScaleHandler? = null
    private var reportItemList: Array<ReportItem> = emptyArray()

    private var i18nValue: JSONObject? = null
    private val reportI18n: I18n = object : I18n {
        override fun get(key: String): String? {
            try {
                return i18nValue?.get(key) as? String

            } catch (e: java.lang.Exception) {
                return null
            }
        }
    }

    private val scaleEventLister = object : BravScaleEventListener {
        override fun onGetUnsteadyWeight(deviceId: String, weight: Double) {
            if (!isAdded) {
                return
            }
            binding.weightTv.text = String.format("%.2f kg", weight)
        }

        override fun onMeasureComplete(deviceId: String, scaleData: BravScaleData) {
            Log.d(tag, "onMeasureComplete: " + scaleData.bodyfatRate)

            turnToReport(scaleData)

        }

        override fun onGetOfflineData(deviceId: String, scaleDataList: Array<BravScaleOriginData>) {
            Log.d(tag, "onGetOfflineData " + scaleDataList.toString())
        }
    }

    val connectionChangeListener =
        BravBleConnectionChangeListener { deviceId, state ->
            Log.d(tag, "onConnectionStateChange " + state)

            binding.connectBtn.text =
                if (state == BravDeviceConnectionState.Connected) "Disconnect" else "Connect"
            if (state == BravDeviceConnectionState.Disconnected) {
                scaleHandler = null
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadLocalI18Json()
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.connectBtn.setOnClickListener {
            if (scaleHandler == null) {
                doConnect()
            } else {
                doDisconnect()
            }
        }
        binding.reportItemLv.adapter = this.reportAdapter
        deviceId = arguments?.getString("deviceId", "") ?: ""

        if (deviceId.isNotEmpty()) {
            this.doConnect()
        } else {
            mockReport()
        }
    }

    override fun onPause() {
        super.onPause()
        this.bleApi.disconnectDevice(deviceId)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun mockReport() {
        val scaleData = BravScaleData(
            BravScaleUser(
                BravGender.Male,
                172,
                32,
                0, BravScaleAlgorithmMethod.common
            ), BravScaleOriginData(78.0, 500)
        )
        turnToReport(scaleData)
    }

    private fun turnToReport(scaleData: BravScaleData) {
        val report = ReportBuilder.build(scaleData, ReportBuilderOption(i18n = this.reportI18n))
        this.reportItemList = report.reportItemList
        this.reportAdapter.notifyDataSetChanged()
    }

    private fun doConnect() {
        val result = this.bleApi.connectDevice(
            this.deviceId,
            BravScaleDataOptions(
                BravScaleUser(
                    BravGender.Male,
                    170,
                    30
                ), BravScaleUnit.kg
            ),
            scaleEventLister,
            connectionChangeListener
        )
        if (result.isSuccess) {
            scaleHandler = result.data as BroadcastScaleHandler

        } else {
            scaleHandler = null;
            Log.e(tag, "connect fail")

        }
    }

    private fun doDisconnect() {
        this.bleApi.disconnectDevice(deviceId)
        scaleHandler = null
    }

    private data class ViewHolder(
        var itemNameTv: TextView?,
        var levelNameTv: TextView?,
        var valueTv: TextView?,
        var unitTv: TextView?,
        var barTv: TextView?,
        var boundariesTv: TextView?,
        var descTv: TextView?,
        var introTv: TextView?,
    )

    private val reportAdapter = object : BaseAdapter() {
        override fun getCount() = reportItemList.size

        override fun getItem(position: Int) = reportItemList[position]

        override fun getItemId(position: Int) = 0L

        @SuppressLint("Range")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val holder: ViewHolder
            val targetView: View
            if (convertView != null) {
                holder = convertView.tag as ViewHolder
                targetView = convertView
            } else {
                targetView = View.inflate(context, R.layout.item_report, null)
                holder = ViewHolder(
                    itemNameTv = targetView.findViewById(R.id.itemNameTv),
                    levelNameTv = targetView.findViewById(R.id.levelNameTv),
                    valueTv = targetView.findViewById(R.id.valueTv),
                    unitTv = targetView.findViewById(R.id.unitTv),
                    boundariesTv = targetView.findViewById(R.id.boundariesTv),
                    barTv = targetView.findViewById(R.id.barTv),
                    descTv = targetView.findViewById(R.id.descTv),
                    introTv = targetView.findViewById(R.id.introTv),
                )

                targetView.tag = holder
            }

            val item = reportItemList[position]
            holder.itemNameTv?.text = item.name
            holder.levelNameTv?.text = item.targetLevel?.name ?: ""
            holder.valueTv?.text = item.valueString
            holder.unitTv?.text = item.unit
            if (item.isBarVisible) {
                holder.levelNameTv?.setTextColor(Color.parseColor(item.targetLevel!!.color))
                holder.barTv?.visibility = View.VISIBLE
                holder.boundariesTv?.visibility = View.VISIBLE
                holder.boundariesTv?.text = item.boundaries.joinToString()
                holder.barTv?.text = item.levels?.map { it.name }?.joinToString()
            } else {
                holder.boundariesTv?.visibility = View.GONE
                holder.barTv?.visibility = View.GONE
            }
            holder.descTv?.text = item.desc
            holder.introTv?.text = item.intro
            return targetView
        }

    }

    private fun loadLocalI18Json() {
        if (i18nValue != null) {
            return
        }
        var inputStream: InputStream? = null
        var bos: ByteArrayOutputStream? = null
        try {
            inputStream = requireContext().assets.open("en.json")
            bos = ByteArrayOutputStream()
            val bytes = ByteArray(4 * 1024)
            var len = 0
            while (inputStream.read(bytes).also { len = it } != -1) {
                bos.write(bytes, 0, len)
            }
            val json: String = String(bos.toByteArray())
            this.i18nValue = JSONObject(json)
            
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (inputStream != null) inputStream.close()
                if (bos != null) bos.close()
            } catch (e: IOException) {
                Log.e("report", "getStates", e)
            }
        }
    }
}