package com.brav.scale.report.builders

import com.brav.bravlib.scale.model.BravScaleData
import com.brav.bravlib.types.BravGender
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem


class SkeletalMuscleBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "skeletal_muscle"
    override val nameKey = "report_item_skeletal_muscle_rate"
    override val defaultName = "Skeletal muscle percentage"
    override val introKey = "report_desc_skeletal_muscle_rate_1002"
    override val standLevelIndex = 1
    override val isWeightUnit = false
    override val value = this.scaleData.skeletalMuscleRate
    override val unit = "%"

    override val min = 5.0
    override val max = 30.0

    override val boundaries =
        if (this.user.gender === BravGender.Male)
            arrayOf(49.0, 59.0).toDoubleArray()
        else arrayOf(40.0, 50.0).toDoubleArray()

    override val levels: Array<LevelItem>
        get() = arrayOf(
            LevelItem(
                color = colors.report_lower,
                name = i18n("report_level_low"),
                desc = i18n("report_desc_skeletal_muscle_rate_1003")
            ),
            LevelItem(
                color = colors.report_standard,
                name = i18n("report_level_standard"),
                desc = i18n("report_desc_skeletal_muscle_rate_1004")
            ),
            LevelItem(
                color = colors.report_higher,
                name = i18n("report_level_high"),
                desc = i18n("report_desc_skeletal_muscle_rate_1005")
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()

        reportItem.min = boundaries[0] - 10
        reportItem.max = boundaries.last() + 10
        return reportItem
    }


}