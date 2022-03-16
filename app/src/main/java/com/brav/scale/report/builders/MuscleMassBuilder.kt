package com.brav.scale.report.builders

import com.brav.bravlib.scale.model.BravScaleData
import com.brav.bravlib.types.BravGender
import com.brav.bravlib.scale.typs.BravScaleUnit
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem
import com.brav.scale.report.toTargetWeightUnit


class MuscleMassBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "muscle_mass"
    override val nameKey = "brav_report_item_name_muscle_mass"
    override val defaultName = "肌肉量"
    override val introKey = "brav_report_item_intro_muscle_mass"
    override val standLevelIndex = 1
    override val isWeightUnit = true
    override val value = this.scaleData.muscleMass.toTargetUnit()
    override val min = 0.5
    override val max = 4.0

    override val boundaries: DoubleArray
        get() {
            val boundaries: Array<Double>;
            val height = this.user.height
            if (this.user.gender == BravGender.Male) {
                if (height < 160) {
                    boundaries = arrayOf(38.5, 46.5)
                } else if (height <= 170) {
                    boundaries = arrayOf(44.0, 52.4)
                } else {
                    boundaries = arrayOf(49.4, 59.4)
                }
            } else {
                if (height < 150) {
                    boundaries = arrayOf(29.1, 34.7)
                } else if (height <= 160) {
                    boundaries = arrayOf(32.9, 37.5)
                } else {
                    boundaries = arrayOf(36.5, 42.5)
                }
            }
            return boundaries.map {
                it.toTargetWeightUnit(
                    BravScaleUnit.kg,
                    targetUnit
                )
            }.toDoubleArray();
        }

    override val levels: Array<LevelItem>
        get() = arrayOf(
            LevelItem(
                color = colors.report_lower,
                name = "偏低",
                desc = "运动过少和节食是肌肉流失的重要原因。肌肉是能量消耗的主力军，增加肌肉能提高热量消耗，以健康的方式减掉多余脂肪。",
            ),
            LevelItem(
                color = colors.report_standard,
                name = "标准",
                desc = "肌肉含量标准，请继续保持！",
            ),
            LevelItem(
                color = colors.report_sufficient,
                name = "优秀",
                desc = "目前您的肌肉量比较充足，请继续保持适当的运动量和合理的饮食。",
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()

        reportItem.intro = "指人体中所有肌肉的含量，包括骨骼肌、平滑肌（如心肌和消化肌）以及這些肌肉中所含的水分。肌肉率越高，BMR越大，消耗的热量越多，就不容易发胖。"
        reportItem.min = boundaries[0] - 5;
        reportItem.max = boundaries.last() + 5;
        return reportItem
    }


}