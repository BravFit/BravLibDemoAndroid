package com.brav.scale.report.builders

import com.brav.bravlib.scale.model.BravScaleData
import com.brav.bravlib.types.BravGender
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem


class BodyfatBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "bodyfat"
    override val nameKey = "brav_report_item_name_bodyfat"
    override val defaultName = "体脂率"
    override val introKey = "brav_report_item_intro_bodyfat"
    override val standLevelIndex = 1
    override val isWeightUnit = false
    override val value = this.scaleData.bodyfatRate
    override val unit = "%"

    override val min = 5.0
    override val max = 45.0

    override val boundaries =
        if (this.user.gender === BravGender.Male)
            arrayOf(11.0, 21.0, 26.0).toDoubleArray()
        else arrayOf(21.0, 31.0, 36.0).toDoubleArray()

    override val levels: Array<LevelItem>
        get() = arrayOf(
            LevelItem(
                color = colors.report_lower,
                name = "偏低",
                desc = "当前体脂率偏低。当脂肪不足以给人体日常活动供能时，会转而消耗蛋白质，过多的蛋白质消耗会损害人体组织。",
            ),
            LevelItem(
                color = colors.report_standard,
                name = "标准",
                desc = "您的体脂肪水平标准。保持健康的体脂肪水平可以帮助身体更好地进行代谢，保护脆弱的骨头，减少骨折机率；提高抗寒、抗病能力",
            ),
            LevelItem(
                color = colors.report_higher,
                name = "偏高",
                desc = "您的体脂肪水平偏高，这会导致冠心病、高血压、高血脂、高血糖、脂肪肝等慢性代谢疾病的患病风险大大增高，请保证每天适当的运动量，并调整膳食结构。",
            ),
            LevelItem(
                color = colors.report_highest,
                name = "严重偏高",
                desc = "您的体脂肪水平偏高，这会导致冠心病、高血压、高血脂、高血糖、脂肪肝等慢性代谢疾病的患病风险大大增高，请保证每天适当的运动量，并调整膳食结构。",
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()

        reportItem.intro = "体脂率是指人体内的脂肪组织占体重的百分比。体重高不等于胖，但脂肪率高则是肥胖的信号，减肥的根本是减脂。"
        return reportItem
    }


}