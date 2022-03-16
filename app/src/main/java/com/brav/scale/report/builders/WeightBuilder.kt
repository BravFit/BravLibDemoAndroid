package com.brav.scale.report.builders

import com.brav.bravlib.utils.BravUtils
import com.brav.bravlib.scale.model.BravScaleData
import com.brav.bravlib.types.BravGender
import com.brav.bravlib.scale.typs.BravScaleUnit
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem
import com.brav.scale.report.toTargetWeightUnit


class WeightBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "weight"
    override val isValid = true
    override val nameKey = "brav_report_item_name_weight"
    override val defaultName = "体重"
    override val introKey = "brav_report_item_intro_weight"
    override val standLevelIndex = 2
    override val isWeightUnit = true
    override val unit: String
        get() = this.option.targetWeightUnit.name
    override val value = this.scaleData.weight.toTargetUnit()
    override val boundaries: DoubleArray
        get() {
            val standWeight =
                getStandWeight(this.scaleData.user.gender, this.scaleData.user.height);
            val boundaries = doubleArrayOf(
                standWeight * 0.8,
                standWeight * 0.9,
                standWeight * 1.1,
                standWeight * 1.2,
            ).map {
                BravUtils.toPrecision(it,2).toTargetWeightUnit(BravScaleUnit.kg, targetUnit)
            }
            return boundaries.toDoubleArray()
        }

    override val levels: Array<LevelItem>?
        get() = arrayOf(
            LevelItem(
                name = i18n("report_level_severely_low"),
                color = colors.report_lowest,
                desc =
                "体重严重偏低，建议您通过少吃多餐来补充能量，多补充些蛋白质，让多余的能量转化为肌肉和脂肪，同时均衡营养，坚持锻炼，身材会更好。",
            ),
            LevelItem(
                color = colors.report_lower,
                name = "偏瘦",
                desc =
                "体重偏瘦，建议您通过少吃多餐来补充能量，多补充些蛋白质，让多余的能量转化为肌肉和脂肪，同时均衡营养，坚持锻炼，身材会更好。",
            ),
            LevelItem(
                color = colors.report_standard,
                name = "标准",
                desc =
                "体重在合理的范围内。注意保持健康的生活方式，适量进行锻炼，保持标准体重",
            ),
            LevelItem(
                color = colors.report_higher,
                name = "偏高",
                desc =
                "体重偏高，需要保持关注。建议您进行适当减重，可以尝试每周进行锻炼，严格控制食物摄入，减少高油高热量实物，增加高纤维粗粮比例。努力恢复健康和好身材。",
            ),
            LevelItem(
                color = colors.report_highest,
                name = "严重偏高",
                desc =
                "体重严重超标，需要引起高度重视。建议每周进行锻炼，严格控制食物摄入，立刻远离高由高热量食物，餐后记得走一走，有效控制体重和脂肪。努力恢复健康和好身材。",
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()
        reportItem.intro = "体重为裸体或穿着已知重量的工作衣称量得到的身体重量。"
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