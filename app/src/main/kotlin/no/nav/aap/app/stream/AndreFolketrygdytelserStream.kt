package no.nav.aap.app.stream

import no.nav.aap.app.kafka.Topics
import no.nav.aap.app.kafka.toJson
import no.nav.aap.app.kafka.toModellApi
import no.nav.aap.dto.kafka.AndreFolketrygdytelserKafkaDto
import no.nav.aap.dto.kafka.SøkereKafkaDto
import no.nav.aap.kafka.streams.extension.consume
import no.nav.aap.kafka.streams.extension.filterNotNullBy
import no.nav.aap.kafka.streams.extension.join
import no.nav.aap.kafka.streams.extension.produce
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.KTable

internal fun StreamsBuilder.andreFolketrygdytelserStream(søkere: KTable<String, SøkereKafkaDto>) {
    consume(Topics.andreFolketrygdsytelser)
        .filterNotNullBy("andre-folketrygdytelser-filter-tombstones-og-responses") { ytelser -> ytelser.response }
        .join(Topics.andreFolketrygdsytelser with Topics.søkere, søkere, håndterAndreFolketrygdytelser)
        .produce(Topics.søkere, "produced-soker-med-handtert-andre-folketrygdytelser")
}

private val håndterAndreFolketrygdytelser = { andreFolketrygdytelser: AndreFolketrygdytelserKafkaDto, søkereKafkaDto: SøkereKafkaDto ->
    val søker = søkereKafkaDto.toModellApi()
    val (endretSøker) = andreFolketrygdytelser.toModellApi().håndter(søker)
    endretSøker.toJson()
}
