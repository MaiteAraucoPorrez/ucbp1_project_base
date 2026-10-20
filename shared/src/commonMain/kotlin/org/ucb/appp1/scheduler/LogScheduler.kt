package org.ucb.appp1.scheduler

/**
 * Programa tareas en segundo plano. Cada plataforma tiene su propia implementación (expect/actual).
 * En Android el parámetro "context" debe ser un android.content.Context.
 */
expect class LogScheduler(context: Any?) {
    /** Tarea periódica: se ejecuta cada 15 minutos (el mínimo de WorkManager) y solo con internet. */
    fun schedulePeriodicalUpload()

    /** Tarea de una sola vez: sirve para probar sin esperar 15 minutos. */
    fun scheduleOneTimeUpload()
}
