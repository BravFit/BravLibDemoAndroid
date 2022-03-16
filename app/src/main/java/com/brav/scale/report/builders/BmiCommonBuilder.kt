package com.brav.scale.report.builders


import com.brav.bravlib.scale.model.BravScaleData
import com.brav.bravlib.types.BravGender
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem


class BmiCommonBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "bmi"
    override val isValid = true
    override val nameKey = "brav_report_item_name_bmi"
    override val defaultName = "BMI"
    override val introKey = "report_desc_bmi_1001"
    override val standLevelIndex = 1
    override val isWeightUnit = false
    override val value = this.scaleData.bmi
    override val unit = ""

    override val min = 3.0
    override val max = 35.0

    override val boundaries = doubleArrayOf(
        18.5, 25.0,30.0
    )
    override val levels: Array<LevelItem>
        get() = arrayOf(
            LevelItem(
                color = colors.report_lower,
                name = i18n("report_level_under_weight"),
                desc = i18n("report_desc_bmi_1002")
            ),
            LevelItem(
                color = colors.report_standard,
                name = i18n("report_level_health_weight"),
                desc = if (user.gender == BravGender.Male)
                    i18n("report_desc_bmi_1003")
                else
                    i18n("report_desc_bmi_1004")
            ),
            LevelItem(
                color = colors.report_higher,
                name = i18n("report_level_overweight"),
                desc =i18n("report_desc_bmi_1005")
            ),
            LevelItem(
                color = colors.report_highest,
                name = i18n("report_level_obesity"),
                desc =i18n("report_desc_bmi_1005")
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()

        return reportItem
    }


}