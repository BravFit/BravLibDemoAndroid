package com.brav.scale.report.builders

import com.brav.bravlib.scale.model.BravScaleData
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem


class BmrBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "bmr"
    override val nameKey = "report_item_bmr"
    override val defaultName = "BMR"
    override val introKey = "report_desc_bmr_1001"
    override val isWeightUnit = false
    override val value = this.scaleData.bmr.toDouble()
    override val unit = "kcal"
    override val standLevelIndex = 1
    override val min = 1.0
    override val max = 2000.0
    override val boundaries: DoubleArray
        get() = arrayOf(this.scaleData.bmr.toDouble()).toDoubleArray()

    override val levels: Array<LevelItem>
        get() = arrayOf(
            LevelItem(
                color = colors.report_lower,
                name = i18n("report_level_not_standard"),
                desc = i18n("report_desc_bmr_1002", null, this.value.toInt().toString())
            ),
            LevelItem(
                color = colors.report_standard,
                name = i18n("report_level_standard"),
                desc = i18n("report_desc_bmr_1003", null, this.value.toInt().toString())
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()

        return reportItem
    }


}