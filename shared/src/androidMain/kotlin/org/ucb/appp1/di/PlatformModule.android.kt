package org.ucb.appp1.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.ucb.appp1.config.AppDatabase
import org.ucb.appp1.config.getDatabaseBuilder
import org.ucb.appp1.config.getRoomDatabase

actual fun platformModule(): Module = module {
    single<AppDatabase> {
        getRoomDatabase(getDatabaseBuilder(get()))
    }
}