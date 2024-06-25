package com.kingfu.clok.core

import java.util.Locale


fun String.formatEnumName() = this.replace(oldChar = '_', newChar = ' ')
    .lowercase(locale = Locale.getDefault())
    .replaceFirstChar { it.uppercase() }