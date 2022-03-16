package com.brav.scale.report.builders

import com.brav.bravlib.scale.model.BravScaleData
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem


class VisfatBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "visfat"
    override val nameKey = "brav_report_item_name_visfat"
    override val defaultName = "内脏脂肪等级"
    override val introKey = "brav_report_item_intro_visfat"
    override val standLevelIndex = 0
    override val isWeightUnit = false
    override val value = this.scaleData.visfat

    override val min = 0.0
    override val max = 30.0

    override val boundaries =
        arrayOf(9.0, 14.0).toDoubleArray()


    override val levels: Array<LevelItem>
        get() = arrayOf(
            LevelItem(
                color = colors.report_standard,
                name = "标准",
                desc = "内脏脂肪水平正常，请继续保持健康的饮食和适当的运动！",
            ),
            LevelItem(
                color = colors.report_higher,
                name = "偏高",
                desc = "您的皮下脂肪率处于正常范围内。坚持合理的饮食习惯，进行适量运动，就可以使身材和健康状况都保持在良好的范围内。",
            ),
            LevelItem(
                color = colors.report_highest,
                name = "严重偏高",
                desc = "内脏脂肪水平严重偏高，要警惕脂肪肝、高血脂等疾病啦。请改变久坐不动、经常熬夜等不良生活和工作习惯。保持饮食清淡，少吃高热量食物；坚持运动，饭后步行30分钟，有利于促进食物的消化，带动能量消耗！",
            ),
        )

    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()

        reportItem.intro =
            "内脏脂肪是人体脂肪的一种，与皮下脂肪不同，主要存在于腹腔内，围绕人脏器。一定的脂肪可以起到支撑、稳定、保护内脏的作用。内脏脂肪等级也称之为内脏脂肪指数，是评估腹部内脏器官周围脂肪含量的一项指标。"
        return reportItem
    }


}