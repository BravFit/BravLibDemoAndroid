package com.brav.scale.report.builders

import com.brav.bravlib.scale.model.BravScaleData
import com.brav.bravlib.types.BravGender
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem


class WaterCommonBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "water"
    override val nameKey = "report_item_water_rate"
    override val defaultName = "Water percentage"
    override val introKey = "report_desc_water_1002"
    override val standLevelIndex = 1
    override val isWeightUnit = false
    override val value = this.scaleData.waterRate
    override val unit = "%"

    override val min = 40.0
    override val max = 75.0

    override val boundaries =
        if (this.user.gender === BravGender.Male)
            arrayOf(50.0, 65.0).toDoubleArray()
        else arrayOf(45.0, 60.0).toDoubleArray()

    override val levels: Array<LevelItem>
        get() = arrayOf(
            LevelItem(
                color = colors.report_lower,
                name = i18n("report_level_low"),
                desc = i18n("report_desc_water_1002")
            ),
            LevelItem(
                color = colors.report_standard,
                name = i18n("report_level_standard"),
                desc = i18n("report_desc_water_1003")
            ),
            LevelItem(
                color = colors.report_higher,
                name = i18n("report_level_high"),
                desc = i18n("report_desc_water_1004")
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()

        return reportItem
    }


}