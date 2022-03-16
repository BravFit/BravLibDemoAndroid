package com.brav.scale.report

/**
 * 图标中的多语言选项，多语言切换由外部负责，SDK内只是取数据
 */
interface I18n {
    operator fun get(key: String): String?
}