package com.brav.scale.report.builders

import com.brav.bravlib.scale.model.BravScaleData
import com.brav.bravlib.types.BravGender
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem


class SubfatBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "subfat"
    override val nameKey = "report_item_subfat"
    override val defaultName = "Subfat percentage"
    override val introKey = "report_desc_subfat_1002"
    override val standLevelIndex = 1
    override val isWeightUnit = false
    override val value = this.scaleData.subfatRate
    override val unit = "%"

    override val min = 5.0
    override val max = 45.0

    override val boundaries =
        if (this.user.gender === BravGender.Male)
            arrayOf(18.5, 26.7).toDoubleArray()
        else
            arrayOf(8.6, 16.7).toDoubleArray()

    override val levels: Array<LevelItem>
        get() = arrayOf(
            LevelItem(
                color = colors.report_lower,
                name = i18n("report_level_low"),
                desc = i18n("report_desc_subfat_1003")
            ),
            LevelItem(
                color = colors.report_standard,
                name = i18n("report_level_standard"),
                desc = i18n("report_desc_subfat_1004")
            ),
            LevelItem(
                color = colors.report_higher,
                name = i18n("report_level_high"),
                desc = i18n("report_desc_subfat_1005")
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()

        reportItem.min = boundaries[0] - 7
        reportItem.max = boundaries.last() + 7
        return reportItem
    }


}