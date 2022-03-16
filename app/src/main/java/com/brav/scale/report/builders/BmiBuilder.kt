package com.brav.scale.report.builders


import com.brav.bravlib.scale.model.BravScaleData
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem


class BmiBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "bmi"
    override val isValid = true
    override val nameKey = "brav_report_item_name_bmi"
    override val defaultName = "BMI"
    override val introKey = "brav_report_item_intro_bmi"
    override val standLevelIndex = 1
    override val isWeightUnit = false
    override val value = this.scaleData.bmi
    override val unit = ""

    override val min = 3.0
    override val max = 35.0

    override val boundaries = doubleArrayOf(
        18.5, 25.0
    )
    override val levels: Array<LevelItem>?
        get() = arrayOf(
            LevelItem(
                color = colors.report_lower,
                name = "偏低",
                desc = "目前BMI指数较低，属于偏瘦人群，离标准身材还有一点距离。",
            ),
            LevelItem(
                color = colors.report_standard,
                name = "标准",
                desc = "目前BMI指数正常，请继续保持健康的生活方式。",
            ),
            LevelItem(
                color = colors.report_higher,
                name = "偏高",
                desc = "BMI指数严重偏高，心脏病、高血压等疾病的发病风险较高，请注意改善生活习惯。当前需要严格控制摄入，请远离高热量的食物同时增加运动量有助于消耗脂肪，体重指数较大时不要选择对膝盖压力较大的运动如跑步、跳动。",
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()

        reportItem.intro = "即身体质量指数,称简体质指数。是用体重公斤数除以身高米数平方得出的数字,是目前国际上常用的衡量人体胖瘦程度以及是否健康的一个标准。"
        return reportItem
    }


}