package no.nav.aap.dto.kafka

import java.time.LocalDateTime

data class Innstilling_11_6(
    val vurdertAv: String,
    val tidspunktForVurdering: LocalDateTime,
    val harBehovForBehandling: Boolean,
    val harBehovForTiltak: Boolean,
    val harMulighetForÅKommeIArbeid: Boolean
)
