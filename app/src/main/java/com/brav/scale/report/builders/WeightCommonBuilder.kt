package com.brav.scale.report.builders

import com.brav.bravlib.scale.model.BravScaleData
import com.brav.bravlib.types.BravGender
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem


class WeightCommonBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "weight"
    override val isValid = true
    override val nameKey = "report_item_body_weight"
    override val defaultName = "Weight"
    override val introKey = "report_desc_weight_1002"
    override val standLevelIndex = 1
    override val isWeightUnit = true
    override val unit: String
        get() = this.option.targetWeightUnit.name
    override val value = this.scaleData.weight.toTargetUnit()

    override val levels: Array<LevelItem>
        get() = arrayOf(
            LevelItem(
                color = colors.report_lower,
                name = i18n("report_level_low"),
                desc = i18n("report_desc_weight_1003")
            ),
            LevelItem(
                color = colors.report_standard,
                name = i18n("report_level_standard"),
                desc = i18n("report_desc_weight_1004")
            ),
            LevelItem(
                color = colors.report_higher,
                name = i18n("report_level_high"),
                desc = i18n("report_desc_weight_1005")
            ),
            LevelItem(
                color = colors.report_highest,
                name = i18n("report_level_severely_high"),
                desc = i18n("report_desc_weight_1006")
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()

        val bmiBuilder = BmiCommonBuilder(this.scaleData, this.option)
        val bmiItem = bmiBuilder.build()
        val height = user.height

        reportItem.levelIndex = bmiItem.levelIndex
        val boundaries =
            bmiItem.boundaries.map { (height * height * it) / 10000 }.toDoubleArray()

        reportItem.boundaries = boundaries
        reportItem.min = boundaries[0] * 0.5
        reportItem.max = boundaries.last() * 1.5

        return reportItem
    }

    fun getStandWeight(gender: BravGender, height: Int): Double {
        if (gender == BravGender.Female) {
            return (height * 1.37 - 110) * 0.45;
        }
        return (height - 80) * 0.7;
    }


}