package com.brav.scale.report.model

data class ReportItem(
    /**
     * 指标的ID，各项指标都必须唯一，同样的指标需要保持一致
     */
    val id: String,
    /**
     * 指标的名称，已经国际化了
     */
    var name: String = "",
    var value: Double = 0.0,
    var valueString: String = "",
    /**
     * 各级别之前的边界值
     */
    var boundaries: DoubleArray = DoubleArray(0),
    var min: Double = 0.0,
    var max: Double = 0.0,
    var levels: Array<LevelItem>? = null,
    var levelIndex: Int = 0,
    var isStandard: Boolean = false,
    var unit: String = "",
    var desc: String = "",

    var intro: String = "",
    var isWeightUnit: Boolean = false,
    var icon: String = ""


) {
    val isBarVisible: Boolean
        get() = this.levels != null;

    val targetLevel: LevelItem?
        get() =
            this.levels?.get(levelIndex)

}