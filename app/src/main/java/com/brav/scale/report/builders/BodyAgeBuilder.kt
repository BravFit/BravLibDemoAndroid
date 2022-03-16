package com.brav.scale.report.builders

import com.brav.bravlib.scale.model.BravScaleData
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.ReportItemBuilder
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportItem


class BodyAgeBuilder(
    scaleData: BravScaleData,
    option: ReportBuilderOption
) : ReportItemBuilder(scaleData, option) {


    override val id = "lbm"
    override val nameKey = "report_item_body_age"
    override val defaultName = ""
    override val introKey = "report_desc_body_age_1002"
    override val isWeightUnit = false
    override val value = this.scaleData.bodyAge.toDouble()


    override fun build(): ReportItem {
        val reportItem = super.initAndInjectFields()

        return reportItem
    }


}