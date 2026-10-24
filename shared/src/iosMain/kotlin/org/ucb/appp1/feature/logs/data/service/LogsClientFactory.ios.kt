package org.ucb.appp1.feature.logs.data.service

import com.develoop.logs.LogServiceClient

// Wire no incluye transporte gRPC para iOS (Kotlin/Native): por ahora no hay cliente.
actual fun createLogServiceClient(baseUrl: String): LogServiceClient? = null
