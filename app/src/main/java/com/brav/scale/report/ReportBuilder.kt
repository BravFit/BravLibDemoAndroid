package com.brav.scale.report


import com.brav.bravlib.scale.model.BravScaleData
import com.brav.bravlib.scale.model.BravScaleUser
import com.brav.bravlib.scale.model.BravScaleOriginData
import com.brav.bravlib.types.BravGender
import com.brav.bravlib.scale.typs.BravScaleAlgorithmMethod
import com.brav.scale.report.builders.*
import com.brav.scale.report.model.BravScaleReport
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.model.ReportItem
import com.brav.scale.report.model.ReportType
import java.lang.StringBuilder

object ReportBuilder {
    fun build(
        scaleData: BravScaleData,
        option: ReportBuilderOption = ReportBuilderOption()
    ): BravScaleReport {
        val report = BravScaleReport(scaleData);
        val builders = this.fetchBuilders(scaleData, option).filter { it.isValid }
        report.reportItemList = builders.map { it.build() }.toTypedArray()
        return report
    }

    private fun fetchBuilders(
        scaleData: BravScaleData,
        option: ReportBuilderOption
    ): Array<ReportItemBuilder> {
        if (option.reportType == ReportType.asia) {
            return arrayOf(
                WeightBuilder(scaleData, option),
                BmiBuilder(scaleData, option),
                BodyfatBuilder(scaleData, option),
                BoneBuilder(scaleData, option),
                LbmBuilder(scaleData, option),
                MuscleMassBuilder(scaleData, option),
                ProteinBuilder(scaleData, option),
                SkeletalMuscleBuilder(scaleData, option),
                SubfatBuilder(scaleData, option),
                VisfatBuilder(scaleData, option),
                WaterBuilder(scaleData, option),
                BmrBuilder(scaleData, option),
                BodyAgeBuilder(scaleData, option),
                )
        } else {
            return arrayOf(
                WeightCommonBuilder(scaleData, option),
                BmiCommonBuilder(scaleData, option),
                BodyfatCommonBuilder(scaleData, option),
                BoneCommonBuilder(scaleData, option),
                LbmBuilder(scaleData, option),
                MuscleMassCommonBuilder(scaleData, option),
                ProteinBuilder(scaleData, option),
                SkeletalMuscleBuilder(scaleData, option),
                SubfatBuilder(scaleData, option),
                VisfatCommonBuilder(scaleData, option),
                WaterCommonBuilder(scaleData, option),
                BmrBuilder(scaleData, option),
                BodyAgeBuilder(scaleData, option),
                )
        }

    }
}

fun printItem(item: ReportItem) {
    val stringBuilder = StringBuilder()
    stringBuilder.append("${item.name} ${item.targetLevel?.name ?: ""} ${item.valueString} ${item.unit}\n")
    if (item.isBarVisible) {
        stringBuilder.append(item.boundaries.joinToString() + "\n")
        stringBuilder.append(item.levels?.map { it.name }?.joinToString() + "\n")
    }
    stringBuilder.append(item.desc + "\n")
    stringBuilder.append(item.intro + "\n")
    stringBuilder.append("----------------------------\n")
    print(stringBuilder)
}

fun mockReport() {
    val scaleData = BravScaleData(
        BravScaleUser(
            BravGender.Male,
            172,
            32,
            0,
            BravScaleAlgorithmMethod.common
        ), BravScaleOriginData(78.0, 500)
    )
    scaleData.weight = scaleData.originData.weight

    scaleData.bmi = 26.0
    scaleData.bodyfatRate = 30.0
    scaleData.bone = 4.6
    scaleData.lbm = scaleData.weight * (100 - scaleData.bodyfatRate) / 100
    val report = ReportBuilder.build(scaleData)
    report.reportItemList.forEach { printItem(it) }
}

fun main() {
    mockReport();
}