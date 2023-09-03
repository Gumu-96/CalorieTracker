package com.gumu.core.usecase

import javax.inject.Inject

class FilterOutDigits @Inject constructor() {
    operator fun invoke(str: String): String {
        return str.filter { it.isDigit() }
    }
}
