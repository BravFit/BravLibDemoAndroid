package com.brav.scale.report.builders

import com.brav.bravlib.scale.model.BravScaleData
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem


class VisfatCommonBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "visfat"
    override val nameKey = "report_item_visfat"
    override val defaultName = "Visfat"
    override val introKey = "report_desc_visfat_1002"
    override val standLevelIndex = 0
    override val isWeightUnit = false
    override val value = this.scaleData.visfat

    override val min = 0.0
    override val max = 30.0

    override val boundaries =
        arrayOf(6.0, 11.0, 14.0).toDoubleArray()


    override val levels: Array<LevelItem>
        get() = arrayOf(
            LevelItem(
                color = colors.report_sufficient,
                name = i18n("report_level_good"),
                desc = i18n("report_desc_visfat_1003")
            ),
            LevelItem(
                color = colors.report_standard,
                name = i18n("report_level_acceptable"),
                desc = i18n("report_desc_visfat_1004")
            ),
            LevelItem(
                color = colors.report_higher,
                name = i18n("report_level_high"),
                desc = i18n("report_desc_visfat_1005")
            ),
            LevelItem(
                color = colors.report_highest,
                name = i18n("report_level_severely_high"),
                desc = i18n("report_desc_visfat_1006")
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()
        return reportItem
    }


}