package com.brav.scale.report.builders

import com.brav.bravlib.scale.model.BravScaleData
import com.brav.bravlib.types.BravGender
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem


class WaterBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "water"
    override val nameKey = "brav_report_item_name_water"
    override val defaultName = "体水分率"
    override val introKey = "brav_report_item_intro_water"
    override val standLevelIndex = 1
    override val isWeightUnit = false
    override val value = this.scaleData.waterRate
    override val unit = "%"

    override val min = 40.0
    override val max = 75.0

    override val boundaries =
        if (this.user.gender === BravGender.Male)
            arrayOf(55.0, 65.0).toDoubleArray()
        else arrayOf(45.0, 60.0).toDoubleArray()

    override val levels: Array<LevelItem>
        get() = arrayOf(
            LevelItem(
                color = colors.report_lower,
                name = "偏低",
                desc = "您体内的水分含量偏低，保持充足的水分可以促进身体的代谢，带走体内的废物和毒素。体重降低时，若水分降低但体脂无变化，减轻的部分可能是体内的水分。",
            ),
            LevelItem(
                color = colors.report_standard,
                name = "标准",
                desc = "水分达标，请注意保持规律的饮食和作息。每天八杯水就能保持正常水平。如有进行运动锻炼，请注意补充水分，弥补出汗过多导致的水分流失。",
            ),
            LevelItem(
                color = colors.report_higher,
                name = "偏高",
                desc = "当前属于水肿体质，原因是体内的水分不足，无法促进代谢，体内的多余微量元素排泄不出，滞留在体内。注意补充水分，促进身体代谢，水分的排泄可以带走体内的微量元素和废物垃圾，室内环境保持健康循环！",
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()

        reportItem.intro =
            "水分是指人体内的成分中水分占体重的百分比。充足的水分可以促进体内的新陈代谢。"

        return reportItem
    }


}