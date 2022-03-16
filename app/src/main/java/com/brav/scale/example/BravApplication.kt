package com.brav.scale.example

import android.app.Application
import com.brav.bravlib.BravApiProvider
import com.brav.bravlib.utils.BravLogger
import com.brav.bravlib.api.BravLoggerListener

class BravApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        BravApiProvider.initSharedBleApi(this)

        BravLogger.loggerLevel = BravLogger.LOG_LEVEL_ALL
        BravLogger.setLoggerListener(object :
            BravLoggerListener {
            override fun onLog(tag: String?, text: String?) {
                //do save log
            }

            override fun onError(error: String?, text: String?) {
                //do save log
            }

        })
    }
}