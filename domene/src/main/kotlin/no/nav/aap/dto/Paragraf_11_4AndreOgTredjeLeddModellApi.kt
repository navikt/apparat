package no.nav.aap.dto

import no.nav.aap.domene.Søker
import no.nav.aap.domene.vilkår.Vilkårsvurdering
import no.nav.aap.hendelse.Behov
import no.nav.aap.hendelse.KvalitetssikringParagraf_11_4AndreOgTredjeLedd
import no.nav.aap.hendelse.LøsningParagraf_11_4AndreOgTredjeLedd
import java.time.LocalDateTime
import java.util.*

data class LøsningParagraf_11_4AndreOgTredjeLeddModellApi(
    val løsningId: UUID,
    val vurdertAv: String,
    val tidspunktForVurdering: LocalDateTime,
    val erOppfylt: Boolean
) {

    constructor(
        vurdertAv: String,
        tidspunktForVurdering: LocalDateTime,
        erOppfylt: Boolean
    ) : this(
        løsningId = UUID.randomUUID(),
        vurdertAv = vurdertAv,
        tidspunktForVurdering = tidspunktForVurdering,
        erOppfylt = erOppfylt
    )

    fun håndter(søker: Søker): List<Behov> {
        val løsning = toLøsning()
        søker.håndterLøsning(løsning, Vilkårsvurdering<*>::håndterLøsning)
        return løsning.behov()
    }

    private fun toLøsning() =
        LøsningParagraf_11_4AndreOgTredjeLedd(løsningId, vurdertAv, tidspunktForVurdering, erOppfylt)
}

data class KvalitetssikringParagraf_11_4AndreOgTredjeLeddModellApi(
    val kvalitetssikringId: UUID,
    val løsningId: UUID,
    val kvalitetssikretAv: String,
    val tidspunktForKvalitetssikring: LocalDateTime,
    val erGodkjent: Boolean,
    val begrunnelse: String
) {

    constructor(
        løsningId: UUID,
        kvalitetssikretAv: String,
        tidspunktForKvalitetssikring: LocalDateTime,
        erGodkjent: Boolean,
        begrunnelse: String
    ) : this(
        kvalitetssikringId = UUID.randomUUID(),
        løsningId = løsningId,
        kvalitetssikretAv = kvalitetssikretAv,
        tidspunktForKvalitetssikring = tidspunktForKvalitetssikring,
        erGodkjent = erGodkjent,
        begrunnelse = begrunnelse
    )

    fun håndter(søker: Søker): List<Behov> {
        val kvalitetssikring = toKvalitetssikring()
        søker.håndterKvalitetssikring(kvalitetssikring, Vilkårsvurdering<*>::håndterKvalitetssikring)
        return kvalitetssikring.behov()
    }

    private fun toKvalitetssikring() = KvalitetssikringParagraf_11_4AndreOgTredjeLedd(
        kvalitetssikringId = kvalitetssikringId,
        løsningId = løsningId,
        kvalitetssikretAv = kvalitetssikretAv,
        tidspunktForKvalitetssikring = tidspunktForKvalitetssikring,
        erGodkjent = erGodkjent,
        begrunnelse = begrunnelse
    )
}