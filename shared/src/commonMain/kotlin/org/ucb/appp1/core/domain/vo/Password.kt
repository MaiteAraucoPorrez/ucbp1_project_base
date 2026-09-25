package org.ucb.appp1.core.domain.vo

@JvmInline
value class Password(val value: String) {
    fun isValid(): Boolean = value.length >= 6
}
