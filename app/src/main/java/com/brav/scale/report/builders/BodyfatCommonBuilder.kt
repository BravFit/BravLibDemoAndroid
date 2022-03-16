package com.brav.scale.report.builders

import com.brav.bravlib.scale.model.BravScaleData
import com.brav.bravlib.types.BravGender
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem


class BodyfatCommonBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "bodyfat"
    override val nameKey = "report_item_bodyfat_rate"
    override val defaultName = "Bodyfat percentage"
    override val introKey = "report_desc_bodyfat_1002"
    override val standLevelIndex = 2
    override val isWeightUnit = false
    override val value = this.scaleData.bodyfatRate
    override val unit = "%"

    override val min = 5.0
    override val max = 45.0

    override val boundaries =
        if (this.user.gender === BravGender.Male)
            arrayOf(2.0, 6.0, 13.0, 17.0, 25.0).toDoubleArray()
        else arrayOf(10.0, 14.0, 21.0, 25.0, 32.0).toDoubleArray()

    override val levels: Array<LevelItem>
        get() = arrayOf(
            LevelItem(
                color = colors.report_lowest,
                name = i18n("report_level_extremely_low"),
                desc = i18n("report_desc_bodyfat_1003")
            ),
            LevelItem(
                color = colors.report_lower,
                name = i18n("report_level_thin"),
                desc = i18n("report_desc_bodyfat_1004")
            ),
            LevelItem(
                color = colors.report_sufficient,
                name = i18n("report_level_athletes"),
                desc = i18n("report_desc_bodyfat_1005")
            ),
            LevelItem(
                color = colors.report_standard,
                name = i18n("report_level_fitness"),
                desc = i18n("report_desc_bodyfat_1006")
            ),
            LevelItem(
                color = colors.report_higher,
                name = i18n("report_level_acceptable"),
                desc = i18n("report_desc_bodyfat_1007")
            ),
            LevelItem(
                color = colors.report_higher,
                name = i18n("report_level_obesity"),
                desc = i18n("report_desc_bodyfat_1008")
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()
        
        return reportItem
    }


}