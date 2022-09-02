package no.nav.aap.app.kafka

import no.nav.aap.domene.Søker
import no.nav.aap.modellapi.IverksettelseAvVedtakModellApi
import no.nav.aap.dto.kafka.IverksettelseAvVedtakKafkaDto

internal fun IverksettelseAvVedtakKafkaDto.håndter(søker: Søker) = toDto().håndter(søker)

private fun IverksettelseAvVedtakKafkaDto.toDto() = IverksettelseAvVedtakModellApi(
    iverksattAv = iverksattAv
)
