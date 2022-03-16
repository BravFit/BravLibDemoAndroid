package com.brav.scale.report.model


import com.brav.bravlib.scale.typs.BravScaleUnit
import com.brav.scale.report.I18n

data class ReportBuilderOption(
    val targetWeightUnit: BravScaleUnit = BravScaleUnit.kg,
    val i18n: I18n? = null,
    val reportType: ReportType = ReportType.common
)