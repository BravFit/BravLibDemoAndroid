package com.brav.scale.report.builders

import com.brav.bravlib.scale.model.BravScaleData
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.ReportItem


class LbmBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "lbm"
    override val nameKey = "report_item_lbm"
    override val defaultName = "Lean fat body mass"
    override val introKey = "report_desc_lbm_1002"
    override val standLevelIndex = 1
    override val isWeightUnit = true
    override val value = this.scaleData.lbm.toTargetUnit()

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()

        return reportItem
    }


}