package com.brav.scale.report.model

import com.brav.bravlib.scale.model.BravScaleData

class BravScaleReport(val scaleData: BravScaleData) {
    var reportItemList: Array<ReportItem> = emptyArray()
}