package org.ucb.appp1.di

import org.koin.core.module.Module

fun sharedModules(): List<Module> = listOf(
    dataModule,
    domainModule,
    presentationModule
)
