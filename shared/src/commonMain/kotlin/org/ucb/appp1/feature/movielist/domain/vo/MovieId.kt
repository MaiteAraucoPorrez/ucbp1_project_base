package org.ucb.appp1.feature.movielist.domain.vo

import kotlin.jvm.JvmInline
import kotlinx.serialization.Serializable

/**
 * VO: un id de película no es "cualquier String" (así como Email o
 * Password). Al ser @Serializable, además puede viajar directo como
 * parámetro de ruta en Navigation Compose (NavRoute.MovieDetail).
 */
@Serializable
@JvmInline
value class MovieId(val value: String)
