package com.brav.scale.report.builders


import com.brav.bravlib.scale.model.BravScaleData
import com.brav.bravlib.types.BravGender

import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem


class BoneCommonBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "bone"
    override val nameKey = "brav_report_item_name_bone"
    override val defaultName = "Bone mass"
    override val introKey = "brav_report_item_intro_bone"
    override val standLevelIndex = 1
    override val isWeightUnit = true
    override val value = this.scaleData.bone.toTargetUnit()
    override val min = 0.5
    override val max = 4.0

    override val boundaries: DoubleArray
        get() =
            if (this.user.gender === BravGender.Male)
                arrayOf(3.0, 5.0).map { it.toTargetUnit() } .toDoubleArray()
            else arrayOf(2.5, 4.0).map { it.toTargetUnit() }.toDoubleArray()


    override val levels: Array<LevelItem>
        get() = arrayOf(
            LevelItem(
                color = colors.report_lower,
                name = i18n("report_level_below_average"),
                desc = i18n("report_desc_bone_1003")
            ),
            LevelItem(
                color = colors.report_standard,
                name = i18n("report_level_average"),
                desc = i18n("report_desc_bone_1004")
            ),
            LevelItem(
                color = colors.report_higher,
                name = i18n("report_level_above_average"),
                desc = i18n("report_desc_bone_1005")
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()

        return reportItem
    }


}