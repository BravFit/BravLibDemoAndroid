package com.brav.scale.report

import com.brav.bravlib.utils.BravUtils
import com.brav.bravlib.scale.typs.BravScaleUnit



fun Double.toTargetWeightUnit(valueUnit: BravScaleUnit, targetUnit: BravScaleUnit): Double {
    if (valueUnit == targetUnit) {
        return this
    }
    if (valueUnit == BravScaleUnit.kg && targetUnit == BravScaleUnit.jin) {
        return BravUtils.kg2jin(this)
    } else if (valueUnit == BravScaleUnit.kg && targetUnit == BravScaleUnit.lb) {
        return BravUtils.kg2lb(this)
    } else if (valueUnit == BravScaleUnit.jin && targetUnit == BravScaleUnit.kg) {
        return BravUtils.jin2kg(this)
    } else if (valueUnit == BravScaleUnit.jin && targetUnit == BravScaleUnit.lb) {
        return BravUtils.jin2kg(this)
    } else if (valueUnit == BravScaleUnit.lb && targetUnit == BravScaleUnit.kg) {
        return BravUtils.lb2kg(this)
    } else if (valueUnit == BravScaleUnit.lb && targetUnit == BravScaleUnit.jin) {
        return BravUtils.lb2jin(this)
    } else {
        return this
    }

}
