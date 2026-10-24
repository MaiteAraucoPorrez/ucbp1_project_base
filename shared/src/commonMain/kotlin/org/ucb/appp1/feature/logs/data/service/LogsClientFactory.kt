package org.ucb.appp1.feature.logs.data.service

import com.develoop.logs.LogServiceClient

/**
 * Crea el cliente gRPC generado por Wire. Es expect/actual porque el transporte depende de la plataforma:
 *  - Android: OkHttp (HTTP/2).
 *  - iOS: Wire no incluye transporte gRPC para Kotlin/Native, así que devuelve null.
 */
expect fun createLogServiceClient(baseUrl: String): LogServiceClient?
