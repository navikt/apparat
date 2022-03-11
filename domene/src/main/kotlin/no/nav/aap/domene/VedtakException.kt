package no.nav.aap.domene

/**
 * TODO: for dead letter queue
 */
class VedtakException : RuntimeException {
    constructor(msg: Any) : super(msg.toString())
    constructor(msg: Any, cause: Throwable) : super(msg.toString(), cause)
}

internal fun vedtakException(message: Any): Nothing = throw VedtakException(message.toString())
internal fun vedtakException(message: Any, cause: Throwable): Nothing = throw VedtakException(message.toString(), cause)
