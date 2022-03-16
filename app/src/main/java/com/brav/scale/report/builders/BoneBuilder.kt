package com.brav.scale.report.builders


import com.brav.bravlib.scale.model.BravScaleData
import com.brav.bravlib.types.BravGender
import com.brav.bravlib.scale.typs.BravScaleUnit

import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem
import com.brav.scale.report.toTargetWeightUnit


class BoneBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "bone"
    override val nameKey = "brav_report_item_name_bone"
    override val defaultName = "骨量"
    override val introKey = "brav_report_item_intro_bone"
    override val standLevelIndex = 1
    override val isWeightUnit = true
    override val value = this.scaleData.bone.toTargetUnit()
    override val min = 0.5
    override val max = 4.0

    override val boundaries: DoubleArray
        get() {
            val boundaries: Array<Double>;

            if (this.user.gender == BravGender.Male) {
                if (this.scaleData.weight <= 60) {
                    boundaries = arrayOf(2.3, 2.7)
                } else if (this.scaleData.weight < 75) {
                    boundaries = arrayOf(2.7, 3.1)
                } else {
                    boundaries = arrayOf(3.0, 3.4)
                }
            } else if (this.scaleData.weight <= 45) {
                boundaries = arrayOf(1.6, 2.0)
            } else if (this.scaleData.weight < 60) {
                boundaries = arrayOf(2.0, 2.4)
            } else {
                boundaries = arrayOf(2.3, 2.7)
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
                desc = "您的骨量水平偏低，这会导致腰背疼痛、驼背、易骨折等症状。注意控制酒精、咖啡和浓茶的摄入，这些都会加速骨头里钙质的流失。",
            ),
            LevelItem(
                color = colors.report_standard,
                name = "标准",
                desc = "您的骨量水平标准。骨量在短期内不会出现明显的变化，您只要保证健康的饮食和适当的锻炼，就可以维持稳定健康的骨量水平。",
            ),
            LevelItem(
                color = colors.report_higher,
                name = "偏高",
                desc = "您的骨量水平偏高，这说明您的生活习惯比较健康，营养摄入合理。由于骨量在短期内不会出现明显的变化，您只要保证健康的饮食和适当的锻炼，就可以维持稳定健康的骨量水平。",
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()

        reportItem.intro = "骨量是指是单位体积内，骨组织，包括骨矿物质（钙、磷等）和骨基质（骨胶原、蛋白质、无机盐等等）含量。"
        return reportItem
    }


}