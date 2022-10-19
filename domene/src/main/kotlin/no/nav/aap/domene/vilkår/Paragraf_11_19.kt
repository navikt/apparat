package no.nav.aap.domene.vilkår

import no.nav.aap.domene.UlovligTilstandException
import no.nav.aap.domene.entitet.Fødselsdato
import no.nav.aap.domene.vilkår.Paragraf_11_19.AvventerManuellVurdering
import no.nav.aap.domene.visitor.VilkårsvurderingVisitor
import no.nav.aap.hendelse.KvalitetssikringParagraf_11_19
import no.nav.aap.hendelse.KvalitetssikringParagraf_11_19.Companion.toDto
import no.nav.aap.hendelse.LøsningParagraf_11_19
import no.nav.aap.hendelse.LøsningParagraf_11_19.Companion.toDto
import no.nav.aap.hendelse.Søknad
import no.nav.aap.hendelse.behov.Behov_11_19
import no.nav.aap.modellapi.Paragraf_11_19ModellApi
import no.nav.aap.modellapi.Utfall
import no.nav.aap.modellapi.VilkårsvurderingModellApi
import java.time.LocalDate
import java.util.*

internal class Paragraf_11_19 private constructor(
    vilkårsvurderingsid: UUID,
    tilstand: Tilstand<Paragraf_11_19>
) :
    Vilkårsvurdering<Paragraf_11_19>(
        vilkårsvurderingsid,
        Paragraf.PARAGRAF_11_19,
        Ledd.LEDD_1,
        tilstand
    ) {
    private val løsninger = mutableListOf<LøsningParagraf_11_19>()
    private val kvalitetssikringer = mutableListOf<KvalitetssikringParagraf_11_19>()

    internal constructor() : this(UUID.randomUUID(), IkkeVurdert)

    override fun <T> callWithReceiver(block: Paragraf_11_19.() -> T) = this.block()

    private object IkkeVurdert : Tilstand.IkkeVurdert<Paragraf_11_19>() {
        override fun håndterSøknad(
            vilkårsvurdering: Paragraf_11_19,
            søknad: Søknad,
            fødselsdato: Fødselsdato,
            vurderingsdato: LocalDate,
        ) {
            søknad.opprettBehov(Behov_11_19())
            vilkårsvurdering.tilstand(AvventerManuellVurdering, søknad)
        }

        override fun toDto(vilkårsvurdering: Paragraf_11_19): VilkårsvurderingModellApi {
            UlovligTilstandException.ulovligTilstand("IkkeVurdert skal håndtere søknad før serialisering")
        }
    }

    internal object AvventerManuellVurdering : Tilstand.AvventerManuellVurdering<Paragraf_11_19>() {
        override fun håndterLøsning(
            vilkårsvurdering: Paragraf_11_19,
            løsning: LøsningParagraf_11_19
        ) {
            vilkårsvurdering.løsninger.add(løsning)
            vilkårsvurdering.tilstand(OppfyltAvventerKvalitetssikring, løsning)
        }

        override fun toDto(vilkårsvurdering: Paragraf_11_19) =
            Paragraf_11_19ModellApi(
                vilkårsvurderingsid = vilkårsvurdering.vilkårsvurderingsid,
                vurdertAv = null,
                kvalitetssikretAv = null,
                paragraf = vilkårsvurdering.paragraf.name,
                ledd = vilkårsvurdering.ledd.map(Ledd::name),
                tilstand = tilstandsnavn.name,
                utfall = Utfall.IKKE_VURDERT,
                vurdertMaskinelt = vurdertMaskinelt,
                løsning_11_19_manuell = vilkårsvurdering.løsninger.toDto(),
                kvalitetssikringer_11_19 = vilkårsvurdering.kvalitetssikringer.toDto(),
            )
    }

    internal object OppfyltAvventerKvalitetssikring : Tilstand.OppfyltManueltAvventerKvalitetssikring<Paragraf_11_19>() {
        override fun håndterKvalitetssikring(
            vilkårsvurdering: Paragraf_11_19,
            kvalitetssikring: KvalitetssikringParagraf_11_19
        ) {
            vilkårsvurdering.kvalitetssikringer.add(kvalitetssikring)
            when {
                kvalitetssikring.erGodkjent() -> vilkårsvurdering.tilstand(OppfyltKvalitetssikret, kvalitetssikring)
                else -> vilkårsvurdering.tilstand(AvventerManuellVurdering, kvalitetssikring)
            }
        }

        override fun accept(vilkårsvurdering: Paragraf_11_19, visitor: VilkårsvurderingVisitor) {
            visitor.preVisitParagraf_11_19(vilkårsvurdering)
            visitor.preVisitGjeldendeLøsning(vilkårsvurdering.løsninger.last())
            vilkårsvurdering.løsninger.last().accept(visitor)
            visitor.postVisitGjeldendeLøsning(vilkårsvurdering.løsninger.last())
            visitor.postVisitParagraf_11_19(vilkårsvurdering)
        }

        override fun toDto(vilkårsvurdering: Paragraf_11_19) =
            Paragraf_11_19ModellApi(
                vilkårsvurderingsid = vilkårsvurdering.vilkårsvurderingsid,
                vurdertAv = vilkårsvurdering.løsninger.last().vurdertAv(),
                kvalitetssikretAv = null,
                paragraf = vilkårsvurdering.paragraf.name,
                ledd = vilkårsvurdering.ledd.map(Ledd::name),
                tilstand = tilstandsnavn.name,
                utfall = Utfall.OPPFYLT,
                vurdertMaskinelt = vurdertMaskinelt,
                løsning_11_19_manuell = vilkårsvurdering.løsninger.toDto(),
                kvalitetssikringer_11_19 = vilkårsvurdering.kvalitetssikringer.toDto(),
            )

        override fun gjenopprettTilstand(
            vilkårsvurdering: Paragraf_11_19,
            modellApi: Paragraf_11_19ModellApi
        ) {
            vilkårsvurdering.settManuellLøsning(modellApi)
            vilkårsvurdering.settKvalitetssikring(modellApi)
        }
    }

    internal object OppfyltKvalitetssikret : Tilstand.OppfyltManueltKvalitetssikret<Paragraf_11_19>() {
        override fun accept(vilkårsvurdering: Paragraf_11_19, visitor: VilkårsvurderingVisitor) {
            visitor.preVisitParagraf_11_19(vilkårsvurdering)
            visitor.preVisitGjeldendeLøsning(vilkårsvurdering.løsninger.last())
            vilkårsvurdering.løsninger.last().accept(visitor)
            visitor.postVisitGjeldendeLøsning(vilkårsvurdering.løsninger.last())
            visitor.postVisitParagraf_11_19(vilkårsvurdering)
        }

        override fun toDto(vilkårsvurdering: Paragraf_11_19) =
            Paragraf_11_19ModellApi(
                vilkårsvurderingsid = vilkårsvurdering.vilkårsvurderingsid,
                vurdertAv = vilkårsvurdering.løsninger.last().vurdertAv(),
                kvalitetssikretAv = vilkårsvurdering.kvalitetssikringer.last().kvalitetssikretAv(),
                paragraf = vilkårsvurdering.paragraf.name,
                ledd = vilkårsvurdering.ledd.map(Ledd::name),
                tilstand = tilstandsnavn.name,
                utfall = Utfall.OPPFYLT,
                vurdertMaskinelt = vurdertMaskinelt,
                løsning_11_19_manuell = vilkårsvurdering.løsninger.toDto(),
                kvalitetssikringer_11_19 = vilkårsvurdering.kvalitetssikringer.toDto(),
            )

        override fun gjenopprettTilstand(
            vilkårsvurdering: Paragraf_11_19,
            modellApi: Paragraf_11_19ModellApi
        ) {
            vilkårsvurdering.settManuellLøsning(modellApi)
            vilkårsvurdering.settKvalitetssikring(modellApi)
        }
    }

    private fun settManuellLøsning(vilkårsvurdering: Paragraf_11_19ModellApi) {
        løsninger.addAll(vilkårsvurdering.løsning_11_19_manuell.map {
            LøsningParagraf_11_19(
                løsningId = it.løsningId,
                vurdertAv = it.vurdertAv,
                tidspunktForVurdering = it.tidspunktForVurdering,
                beregningsdato = it.beregningsdato,
            )
        })
    }

    private fun settKvalitetssikring(vilkårsvurdering: Paragraf_11_19ModellApi) {
        kvalitetssikringer.addAll(vilkårsvurdering.kvalitetssikringer_11_19.map {
            KvalitetssikringParagraf_11_19(
                kvalitetssikringId = it.kvalitetssikringId,
                kvalitetssikretAv = it.kvalitetssikretAv,
                tidspunktForKvalitetssikring = it.tidspunktForKvalitetssikring,
                erGodkjent = it.erGodkjent,
                begrunnelse = it.begrunnelse,
                løsningId = it.løsningId
            )
        })
    }

    internal companion object {
        internal fun gjenopprett(vilkårsvurderingsid: UUID, tilstandsnavn: Tilstand.Tilstandsnavn): Paragraf_11_19 =
            Paragraf_11_19(vilkårsvurderingsid, tilknyttetTilstand(tilstandsnavn))

        private fun tilknyttetTilstand(tilstandsnavn: Tilstand.Tilstandsnavn) = when (tilstandsnavn) {
            Tilstand.Tilstandsnavn.IKKE_VURDERT -> IkkeVurdert
            Tilstand.Tilstandsnavn.AVVENTER_MANUELL_VURDERING -> AvventerManuellVurdering
            Tilstand.Tilstandsnavn.OPPFYLT_MANUELT_AVVENTER_KVALITETSSIKRING -> OppfyltAvventerKvalitetssikring
            Tilstand.Tilstandsnavn.OPPFYLT_MANUELT_KVALITETSSIKRET -> OppfyltKvalitetssikret
            else -> error("Tilstand ${tilstandsnavn.name} ikke i bruk i Paragraf_11_19")
        }
    }
}
