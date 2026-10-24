package org.ucb.appp1.feature.logs.data.service

import com.develoop.logs.GrpcLogServiceClient
import com.develoop.logs.LogServiceClient
import com.squareup.wire.GrpcClient
import okhttp3.OkHttpClient
import okhttp3.Protocol

actual fun createLogServiceClient(baseUrl: String): LogServiceClient? {
    val okHttp = if (baseUrl.startsWith("http://")) {
        // Servidor sin TLS (localhost / 10.0.2.2): hay que hablar HTTP/2 directamente.
        OkHttpClient.Builder()
            .protocols(listOf(Protocol.H2_PRIOR_KNOWLEDGE))
            .build()
    } else {
        // https (ngrok): HTTP/2 se negocia solo con TLS.
        OkHttpClient()
    }

    val grpcClient = GrpcClient.Builder()
        .client(okHttp)
        .baseUrl(baseUrl)
        .build()

    return GrpcLogServiceClient(grpcClient)
}
