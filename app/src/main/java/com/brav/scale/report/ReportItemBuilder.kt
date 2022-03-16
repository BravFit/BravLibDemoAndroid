package com.brav.scale.report


import com.brav.bravlib.scale.model.BravScaleData
import com.brav.scale.report.model.LevelItem
import com.brav.scale.report.model.ReportBuilderOption
import com.brav.scale.report.model.ReportItem

abstract class ReportItemBuilder(
    val scaleData: BravScaleData,
    val option: ReportBuilderOption
) {

    abstract val id: String
    abstract val nameKey: String
    open val defaultName: String = ""
    abstract val introKey: String
    open val isValid: Boolean = this.scaleData.bodyfatRate > 0
    open val value: Double = 0.0
    open val valueString: String
        get() = value.toString()
    open val min = 0.0
    open val max = 0.0
    open val unit: String
        get() {
            return if (isWeightUnit) this.targetUnit.name else ""
        }
    open val isWeightUnit = false
    open val boundaries = DoubleArray(0)
    open val levels: Array<LevelItem>? = null
    open val standLevelIndex = 0;

    val valueUnit = this.scaleData.weightUnit
    val targetUnit = this.option.targetWeightUnit
    fun Double.toTargetUnit(): Double {
        if (!isWeightUnit) {
            return this
        }
        return this.toTargetWeightUnit(
            valueUnit,
            targetUnit
        )
    }

    val user = this.scaleData.user


    protected fun initAndInjectFields(): ReportItem {
        val reportItem = ReportItem(id = id)
        reportItem.name = i18n(nameKey, defaultName)
        reportItem.intro = i18n(introKey, "")
        reportItem.min = min;
        reportItem.max = max;
        reportItem.unit = unit;
        reportItem.isWeightUnit = isWeightUnit;
        reportItem.value = value
        reportItem.valueString = valueString
        reportItem.boundaries = boundaries;
        reportItem.levels = levels

        if (reportItem.levels !== null && reportItem.boundaries.isNotEmpty()) {
            reportItem.levelIndex =
                calcLevelIndex(value, reportItem.boundaries, standLevelIndex)
            reportItem.isStandard = reportItem.levelIndex == standLevelIndex
            reportItem.desc = reportItem.targetLevel!!.desc

        }
        return reportItem
    }


    protected fun i18n(key: String, defValue: String? = null, param: String? = null): String {
        var tartValue = this.option.i18n?.get(key)
        if (tartValue == null) {
            tartValue = defValue ?: "Miss [ ${key} ]"
        }
        if (param != null) {
            tartValue = tartValue.replace("XXX", param)
        }
        return tartValue

    }

    abstract fun build(): ReportItem


    /**
     * 计算指标的等级索引值
     * @param {number} value - 指标的数值
     * @param {Array<number>} boundaries - 界定等级的边界值
     * @param {number} standLevelIndex - 定义的标准值得索引
     */
    protected fun calcLevelIndex(
        value: Double,
        boundaries: DoubleArray,
        standLevelIndex: Int,
    ): Int {
        var level = 0;
        for (i in boundaries.indices) {
            if (
                value < boundaries[i] ||
                (value == boundaries[i] && level == standLevelIndex)
            ) {
                break;
            }
            level += 1;
        }
        return level
    }

    object colors {
        val background = "#f8f8f8"
        val partLine = "#e5e5e5"
        val master = "#7370aa"
        val second = "#8d77df"
        val report_lowest = "#aa8ee4"
        val report_lower = "#00c1e4"
        val report_standard = "#a7cb40"
        val report_higher = "#fbc13d"
        val report_highest = "#f74142"
        val report_sufficient = "#3ea42c"
        val report_orange_highest = "#ff8c00"
        val report_claret_sufficient = "#7f1734"
    }
//    val
}