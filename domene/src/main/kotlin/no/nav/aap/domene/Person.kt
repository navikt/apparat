package no.nav.aap.domene

import no.nav.aap.domene.Sak.Companion.toFrontendSak
import no.nav.aap.domene.Vilkårsvurdering.Companion.erNoenIkkeOppfylt
import no.nav.aap.domene.Vilkårsvurdering.Companion.toFrontendVilkårsvurdering
import no.nav.aap.domene.frontendView.FrontendSak
import no.nav.aap.domene.frontendView.FrontendVilkår
import no.nav.aap.domene.frontendView.FrontendVilkårsvurdering
import java.time.LocalDate

class Søker(
    private val personident: Personident,
    private val fødselsdato: Fødselsdato
) {
    private val saker: MutableList<Sak> = mutableListOf()

    fun håndterSøknad(søknad: Søknad) {
        val sak = Sak()
        saker.add(sak)
        sak.håndterSøknad(søknad, fødselsdato)
    }

    fun håndterOppgavesvar(oppgavesvar: OppgavesvarParagraf_11_5) {
        saker.forEach { it.håndterOppgavesvar(oppgavesvar) }
    }

    private fun toFrontendSaker() =
        saker.toFrontendSak(
            personident = personident,
            fødselsdato = fødselsdato
        )

    companion object {
        fun Iterable<Søker>.toFrontendSaker() = flatMap(Søker::toFrontendSaker)
    }
}

class Personident(
    private val ident: String
) {
    internal fun toFrontendPersonident() = ident
}

class Fødselsdato(private val dato: LocalDate) {
    private val `18ÅrsDagen`: LocalDate = this.dato.plusYears(18)
    private val `67ÅrsDagen`: LocalDate = this.dato.plusYears(67)

    internal fun erMellom18Og67År(vurderingsdato: LocalDate) = vurderingsdato in `18ÅrsDagen`..`67ÅrsDagen`

    internal fun toFrontendFødselsdato() = dato
}

internal class Sak {
    private val vilkårsvurderinger: MutableList<Vilkårsvurdering> = mutableListOf()
    private lateinit var vurderingsdato: LocalDate

    internal fun håndterSøknad(søknad: Søknad, fødselsdato: Fødselsdato) {
        this.vurderingsdato = LocalDate.now()
        tilstand.håndterSøknad(this, søknad, fødselsdato, vurderingsdato)
    }

    internal fun håndterOppgavesvar(oppgavesvar: OppgavesvarParagraf_11_5) {
        tilstand.håndterOppgavesvar(this, oppgavesvar)
    }

    private var tilstand: Tilstand = Start

    private fun tilstand(nyTilstand: Tilstand) {
        nyTilstand.onExit()
        tilstand = nyTilstand
        tilstand.onEntry()
    }

    private sealed interface Tilstand {
        val name: String
        fun onEntry() {}
        fun onExit() {}
        fun håndterSøknad(sak: Sak, søknad: Søknad, fødselsdato: Fødselsdato, vurderingsdato: LocalDate) {}
        fun håndterOppgavesvar(sak: Sak, oppgavesvar: OppgavesvarParagraf_11_5) {
            error("Forventet ikke oppgavesvar i tilstand $name")
        }

        fun toFrontendTilstand() = name
    }

    private object Start : Tilstand {
        override val name = "Start"
        override fun håndterSøknad(sak: Sak, søknad: Søknad, fødselsdato: Fødselsdato, vurderingsdato: LocalDate) {
            //opprett initielle vilkårsvurderinger
            sak.vilkårsvurderinger.add(Paragraf_11_4FørsteLedd())
            sak.vilkårsvurderinger.add(Paragraf_11_5())
            sak.vilkårsvurderinger.forEach { it.håndterSøknad(søknad, fødselsdato, vurderingsdato) }

            //Vurder om vilkår allerede er avslått
            if (sak.vilkårsvurderinger.erNoenIkkeOppfylt()) {
                sak.tilstand(IkkeOppfylt)
            } else {
                //bytt tilstand til SøknadMottatt
                sak.tilstand(SøknadMottatt)
            }
        }
    }

    private object SøknadMottatt : Tilstand {
        override val name = "SøknadMottatt"
        override fun håndterSøknad(sak: Sak, søknad: Søknad, fødselsdato: Fødselsdato, vurderingsdato: LocalDate) {
            error("Forventet ikke søknad i tilstand SøknadMottatt")
        }

        override fun håndterOppgavesvar(sak: Sak, oppgavesvar: OppgavesvarParagraf_11_5) {
            sak.vilkårsvurderinger.forEach { it.håndterOppgavesvar(oppgavesvar) }
        }
    }

    private object IkkeOppfylt : Tilstand {
        override val name = "IkkeOppfylt"
        override fun håndterSøknad(sak: Sak, søknad: Søknad, fødselsdato: Fødselsdato, vurderingsdato: LocalDate) {
            error("Forventet ikke søknad i tilstand IkkeOppfylt")
        }
    }


    private fun toFrontendSak(personident: Personident, fødselsdato: Fødselsdato) =
        FrontendSak(
            personident = personident.toFrontendPersonident(),
            fødselsdato = fødselsdato.toFrontendFødselsdato(),
            tilstand = tilstand.toFrontendTilstand(),
            vilkårsvurderinger = vilkårsvurderinger.toFrontendVilkårsvurdering()
        )

    internal companion object {
        internal fun Iterable<Sak>.toFrontendSak(personident: Personident, fødselsdato: Fødselsdato) = map {
            it.toFrontendSak(personident = personident, fødselsdato = fødselsdato)
        }
    }
}

class Søknad(
    private val personident: Personident,
    private val fødselsdato: Fødselsdato
) {
    fun opprettSøker() = Søker(personident, fødselsdato)
}

internal abstract class Vilkårsvurdering(
    private val paragraf: Paragraf,
    private val ledd: List<Ledd>
) {
    internal constructor(
        paragraf: Paragraf,
        ledd: Ledd
    ) : this(paragraf, listOf(ledd))

    internal enum class Paragraf {
        PARAGRAF_11_4, PARAGRAF_11_5
    }

    internal enum class Ledd {
        LEDD_1, LEDD_2;

        operator fun plus(other: Ledd) = listOf(this, other)
    }

    internal abstract fun erOppfylt(): Boolean
    internal abstract fun erIkkeOppfylt(): Boolean

    internal open fun håndterSøknad(søknad: Søknad, fødselsdato: Fødselsdato, vurderingsdato: LocalDate) {}
    internal open fun håndterOppgavesvar(oppgavesvar: OppgavesvarParagraf_11_5) {}

    private fun toFrontendVilkårsvurdering() =
        FrontendVilkårsvurdering(
            vilkår = FrontendVilkår(paragraf.name, ledd.map(Ledd::name)),
            tilstand = toFrontendTilstand()
        )

    protected abstract fun toFrontendTilstand(): String

    internal companion object {
        internal fun Iterable<Vilkårsvurdering>.erNoenIkkeOppfylt() = any(Vilkårsvurdering::erIkkeOppfylt)

        internal fun Iterable<Vilkårsvurdering>.toFrontendVilkårsvurdering() =
            map(Vilkårsvurdering::toFrontendVilkårsvurdering)
    }
}

internal class Paragraf_11_4FørsteLedd :
    Vilkårsvurdering(Paragraf.PARAGRAF_11_4, Ledd.LEDD_1) {
    private lateinit var fødselsdato: Fødselsdato
    private lateinit var vurderingsdato: LocalDate

    private var tilstand: Tilstand = Tilstand.IkkeVurdert

    private fun tilstand(nyTilstand: Tilstand) {
        this.tilstand = nyTilstand
    }

    private fun vurderAldersvilkår(fødselsdato: Fødselsdato, vurderingsdato: LocalDate) {
        this.fødselsdato = fødselsdato
        this.vurderingsdato = vurderingsdato
        if (fødselsdato.erMellom18Og67År(vurderingsdato)) tilstand(Tilstand.Oppfylt)
        else tilstand(Tilstand.IkkeOppfylt)
    }

    override fun håndterSøknad(søknad: Søknad, fødselsdato: Fødselsdato, vurderingsdato: LocalDate) {
        tilstand.håndterSøknad(this, søknad, fødselsdato, vurderingsdato)
    }

    override fun erOppfylt() = tilstand.erOppfylt()
    override fun erIkkeOppfylt() = tilstand.erIkkeOppfylt()

    internal sealed class Tilstand(
        private val name: String,
        private val erOppfylt: Boolean,
        private val erIkkeOppfylt: Boolean
    ) {
        internal fun erOppfylt() = erOppfylt
        internal fun erIkkeOppfylt() = erIkkeOppfylt

        internal abstract fun håndterSøknad(
            vilkårsvurdering: Paragraf_11_4FørsteLedd,
            søknad: Søknad,
            fødselsdato: Fødselsdato,
            vurderingsdato: LocalDate
        )

        object IkkeVurdert : Tilstand(
            name = "IKKE_VURDERT",
            erOppfylt = false,
            erIkkeOppfylt = false
        ) {
            override fun håndterSøknad(
                vilkårsvurdering: Paragraf_11_4FørsteLedd,
                søknad: Søknad,
                fødselsdato: Fødselsdato,
                vurderingsdato: LocalDate
            ) {
                vilkårsvurdering.vurderAldersvilkår(fødselsdato, vurderingsdato)
            }
        }

        object Oppfylt : Tilstand(
            name = "OPPFYLT",
            erOppfylt = true,
            erIkkeOppfylt = false
        ) {
            override fun håndterSøknad(
                vilkårsvurdering: Paragraf_11_4FørsteLedd,
                søknad: Søknad,
                fødselsdato: Fødselsdato,
                vurderingsdato: LocalDate
            ) {
                error("Vilkår allerede vurdert til oppfylt. Forventer ikke ny søknad")
            }
        }

        object IkkeOppfylt : Tilstand(
            name = "IKKE_OPPFYLT",
            erOppfylt = false,
            erIkkeOppfylt = true
        ) {
            override fun håndterSøknad(
                vilkårsvurdering: Paragraf_11_4FørsteLedd,
                søknad: Søknad,
                fødselsdato: Fødselsdato,
                vurderingsdato: LocalDate
            ) {
                error("Vilkår allerede vurdert til ikke oppfylt. Forventer ikke ny søknad")
            }
        }

        internal fun toFrontendTilstand(): String = name
    }

    override fun toFrontendTilstand(): String = tilstand.toFrontendTilstand()
}

internal class Paragraf_11_5 :
    Vilkårsvurdering(Paragraf.PARAGRAF_11_5, Ledd.LEDD_1 + Ledd.LEDD_2) {
    private lateinit var oppgavesvar: OppgavesvarParagraf_11_5
    private var gradNedsattArbeidsevne: Int = -1

    private var tilstand: Tilstand = Tilstand.IkkeVurdert

    private fun tilstand(nyTilstand: Tilstand) {
        this.tilstand.onExit(this)
        this.tilstand = nyTilstand
        nyTilstand.onEntry(this)
    }

    override fun håndterSøknad(søknad: Søknad, fødselsdato: Fødselsdato, vurderingsdato: LocalDate) {
        tilstand.håndterSøknad(this, søknad, fødselsdato, vurderingsdato)
    }

    override fun håndterOppgavesvar(oppgavesvar: OppgavesvarParagraf_11_5) {
        oppgavesvar.vurderNedsattArbeidsevne(this)
    }

    internal fun vurderNedsattArbeidsevne(oppgavesvar: OppgavesvarParagraf_11_5, gradNedsattArbeidsevne: Int) {
        tilstand.vurderNedsattArbeidsevne(this, oppgavesvar, gradNedsattArbeidsevne)
    }

    override fun erOppfylt() = tilstand.erOppfylt()
    override fun erIkkeOppfylt() = tilstand.erIkkeOppfylt()

    internal sealed class Tilstand(
        private val name: String,
        private val erOppfylt: Boolean,
        private val erIkkeOppfylt: Boolean
    ) {
        internal open fun onEntry(vilkårsvurdering: Paragraf_11_5) {}
        internal open fun onExit(vilkårsvurdering: Paragraf_11_5) {}
        internal fun erOppfylt() = erOppfylt
        internal fun erIkkeOppfylt() = erIkkeOppfylt

        internal open fun håndterSøknad(
            vilkårsvurdering: Paragraf_11_5,
            søknad: Søknad,
            fødselsdato: Fødselsdato,
            vurderingsdato: LocalDate
        ) {
            error("Søknad skal ikke håndteres i tilstand $name")
        }

        internal open fun vurderNedsattArbeidsevne(
            vilkårsvurdering: Paragraf_11_5,
            oppgavesvar: OppgavesvarParagraf_11_5,
            gradNedsattArbeidsevne: Int
        ) {
            error("Oppgave skal ikke håndteres i tilstand $name")
        }

        object IkkeVurdert : Tilstand(
            name = "IKKE_VURDERT",
            erOppfylt = false,
            erIkkeOppfylt = false
        ) {
            override fun håndterSøknad(
                vilkårsvurdering: Paragraf_11_5,
                søknad: Søknad,
                fødselsdato: Fødselsdato,
                vurderingsdato: LocalDate
            ) {
                vilkårsvurdering.tilstand(SøknadMottatt)
            }
        }

        object SøknadMottatt : Tilstand(name = "SØKNAD_MOTTATT", erOppfylt = false, erIkkeOppfylt = false) {
            override fun onEntry(vilkårsvurdering: Paragraf_11_5) {
                //send ut oppgaver for manuell vurdering av vilkår
            }

            override fun vurderNedsattArbeidsevne(
                vilkårsvurdering: Paragraf_11_5,
                oppgavesvar: OppgavesvarParagraf_11_5,
                gradNedsattArbeidsevne: Int
            ) {
                vilkårsvurdering.oppgavesvar = oppgavesvar
                vilkårsvurdering.gradNedsattArbeidsevne = gradNedsattArbeidsevne
                fun Int.erNedsattMedMinstHalvparten() = this >= 50
                if (gradNedsattArbeidsevne.erNedsattMedMinstHalvparten()) {
                    vilkårsvurdering.tilstand(Oppfylt)
                } else {
                    vilkårsvurdering.tilstand(IkkeOppfylt)
                }
            }
        }

        object Oppfylt : Tilstand(
            name = "OPPFYLT",
            erOppfylt = true,
            erIkkeOppfylt = false
        )

        object IkkeOppfylt : Tilstand(
            name = "IKKE_OPPFYLT",
            erOppfylt = false,
            erIkkeOppfylt = true
        )

        internal fun toFrontendTilstand(): String = name
    }

    override fun toFrontendTilstand(): String = tilstand.toFrontendTilstand()
}

class OppgavesvarParagraf_11_5(private val gradNedsattArbeidsevne: Int) {
    internal fun vurderNedsattArbeidsevne(vilkår: Paragraf_11_5) {
        vilkår.vurderNedsattArbeidsevne(this, gradNedsattArbeidsevne)
    }
}
