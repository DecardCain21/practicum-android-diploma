package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.common.ImageAndTextHelperImpl
import ru.practicum.android.diploma.util.ImageAndTextHelper

val utilModule = module {

    single<ImageAndTextHelper> {
        ImageAndTextHelperImpl()
    }

}
