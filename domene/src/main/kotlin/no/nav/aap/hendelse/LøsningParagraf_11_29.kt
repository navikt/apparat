package no.nav.aap.hendelse

import no.nav.aap.dto.DtoKvalitetssikringParagraf_11_2
import no.nav.aap.dto.DtoKvalitetssikringParagraf_11_29
import no.nav.aap.dto.DtoLøsningParagraf_11_29
import java.time.LocalDateTime

internal class LøsningParagraf_11_29(
    private val vurdertAv: String,
    private val tidspunktForVurdering: LocalDateTime,
    private val erOppfylt: Boolean
) : Hendelse() {
    internal companion object {
        internal fun Iterable<LøsningParagraf_11_29>.toDto() = map(LøsningParagraf_11_29::toDto)
    }

    internal fun vurdertAv() = vurdertAv
    internal fun erManueltOppfylt() = erOppfylt

    private fun toDto() = DtoLøsningParagraf_11_29(vurdertAv, tidspunktForVurdering, erOppfylt)
}

class KvalitetssikringParagraf_11_29(
    private val kvalitetssikretAv: String,
    private val erGodkjent: Boolean,
    private val begrunnelse: String
) : Hendelse() {

    internal companion object {
        internal fun Iterable<KvalitetssikringParagraf_11_29>.toDto() = map(KvalitetssikringParagraf_11_29::toDto)
    }

    internal fun erGodkjent() = erGodkjent
    internal fun kvalitetssikretAv() = kvalitetssikretAv
    internal fun toDto() = DtoKvalitetssikringParagraf_11_29(kvalitetssikretAv, erGodkjent, begrunnelse)
}

