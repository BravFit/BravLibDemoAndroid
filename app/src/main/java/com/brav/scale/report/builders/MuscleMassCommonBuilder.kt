package com.brav.scale.report.builders

import com.brav.bravlib.scale.model.BravScaleData
import com.brav.bravlib.types.BravGender
import com.brav.bravlib.scale.typs.BravScaleUnit
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem
import com.brav.scale.report.toTargetWeightUnit


class MuscleMassCommonBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "muscle_mass"
    override val nameKey = "report_item_muscle_mass"
    override val defaultName = "Muscle Mass"
    override val introKey = "report_desc_muscle_mass_1004"
    override val standLevelIndex = 1
    override val isWeightUnit = true
    override val value = this.scaleData.muscleMass.toTargetUnit()


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
                name = i18n("report_level_low"),
                desc = i18n("report_desc_muscle_mass_1002")
            ),LevelItem(
                color = colors.report_standard,
                name = i18n("report_level_normal"),
                desc = i18n("report_desc_muscle_mass_1003")
            ),LevelItem(
                color = colors.report_higher,
                name = i18n("report_level_adequate"),
                desc = i18n("report_desc_muscle_mass_1003")
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()

        reportItem.min = boundaries[0] - 5;
        reportItem.max = boundaries.last() + 5;
        return reportItem
    }


}