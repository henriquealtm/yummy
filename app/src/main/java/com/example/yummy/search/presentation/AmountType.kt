package com.example.yummy.search.presentation

import androidx.annotation.StringRes
import com.example.yummy.R

enum class AmountType(@StringRes val stringId: Int) {

    KG(R.string.kg),
    LITER(R.string.liter),
    UNIT(R.string.unit)

}