package no.nav.aap.app.stream

import no.nav.aap.app.kafka.*
import no.nav.aap.dto.kafka.SykepengedagerKafkaDto
import no.nav.aap.dto.kafka.SøkereKafkaDto
import no.nav.aap.kafka.streams.concurrency.RaceConditionBuffer
import no.nav.aap.kafka.streams.extension.*
import no.nav.aap.modellapi.BehovModellApi
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.KTable

internal fun StreamsBuilder.sykepengedagerStream(
    søkere: KTable<String, SøkereKafkaDto>,
    buffer: RaceConditionBuffer<String, SøkereKafkaDto>
) {
    val søkerOgBehov = consume(Topics.sykepengedager)
        .filterNotNullBy("filter-sykepengedager-response") { kafkaDto -> kafkaDto.response }
        .join(Topics.sykepengedager with Topics.søkere, søkere, buffer) { løsning, søker ->
            håndter(løsning, søker)
        }

    søkerOgBehov
        .firstPairValue("sykepengedager-hent-ut-soker")
        .produce(Topics.søkere, buffer, "produced-soker-med-sykepengedager")

    søkerOgBehov
        .secondPairValue("sykepengedager-hent-ut-behov")
        .flatten("sykepengedager-flatten-behov")
        .sendBehov("sykepengedager")
}

private fun håndter(
    sykepengedagerKafkaDto: SykepengedagerKafkaDto,
    søkereKafkaDto: SøkereKafkaDto
): Pair<SøkereKafkaDto, List<BehovModellApi>> {
    val søker = søkereKafkaDto.toModellApi()
    val response = requireNotNull(sykepengedagerKafkaDto.response) { "response==null skal være filtrert vekk her." }
    val (endretSøker, dtoBehov) = response.håndter(søker)
    return endretSøker.toJson(søkereKafkaDto.sekvensnummer) to dtoBehov
}
