package com.albumsgenerator.app.di.modules

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.Qualifier
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@BindingContainer
@Suppress("unused")
object DispatchersModule {
    @SingleIn(AppScope::class)
    @Provides
    @IO
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @SingleIn(AppScope::class)
    @Provides
    @Main
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IO

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Main
