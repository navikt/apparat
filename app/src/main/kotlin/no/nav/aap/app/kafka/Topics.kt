package no.nav.aap.app.kafka

import no.nav.aap.app.modell.InntekterKafkaDto
import no.nav.aap.app.modell.JsonSøknad
import no.nav.aap.app.modell.ManuellKafkaDto
import no.nav.aap.app.modell.SøkereKafkaDto
import no.nav.aap.kafka.KafkaConfig
import no.nav.aap.kafka.serde.avro.AvroSerde
import no.nav.aap.kafka.serde.json.JsonSerde
import no.nav.aap.kafka.streams.Topic
import no.nav.aap.avro.medlem.v1.Medlem as AvroMedlem

class Topics(config: KafkaConfig) {
    val søknad = Topic("aap.soknad-sendt.v1", JsonSerde.jackson<JsonSøknad>())
    val søkere = Topic("aap.sokere.v1", JsonSerde.jackson<SøkereKafkaDto>())
    val medlem = Topic("aap.medlem.v1", AvroSerde.specific<AvroMedlem>(config))
    val manuell = Topic("aap.manuell.v1", JsonSerde.jackson<ManuellKafkaDto>())
    val inntekter = Topic("aap.inntekter.v1", JsonSerde.jackson<InntekterKafkaDto>())
}
