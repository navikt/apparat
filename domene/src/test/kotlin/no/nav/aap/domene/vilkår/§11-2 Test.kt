package no.nav.aap.domene.vilkår

import no.nav.aap.domene.entitet.Fødselsdato
import no.nav.aap.domene.entitet.Personident
import no.nav.aap.domene.vilkår.Vilkårsvurdering.Companion.toDto
import no.nav.aap.hendelse.Hendelse
import no.nav.aap.hendelse.LøsningParagraf_11_2
import no.nav.aap.hendelse.Søknad
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class `§11-2 Test` {
    @Test
    fun `Hvis søker er medlem, er vilkår for medlemskap oppfylt`() {
        val personident = Personident("12345678910")
        val fødselsdato = Fødselsdato(LocalDate.now().minusYears(67))

        val vilkår = Paragraf_11_2()

        val søknad = Søknad(personident, fødselsdato)
        vilkår.håndterSøknad(søknad, fødselsdato, LocalDate.now())
        assertHarBehov(søknad)
        assertSkalIkkeVurdersManuelt(vilkår)

        val løsning = LøsningParagraf_11_2(LøsningParagraf_11_2.ErMedlem.JA)
        vilkår.håndterLøsning(løsning)
        assertHarIkkeBehov(løsning)
        assertSkalIkkeVurdersManuelt(vilkår)

        assertTrue(vilkår.erOppfylt())
        assertFalse(vilkår.erIkkeOppfylt())
    }

    @Test
    fun `Hvis søker ikke er medlem, er vilkår for medlemskap ikke oppfylt`() {
        val personident = Personident("12345678910")
        val fødselsdato = Fødselsdato(LocalDate.now().minusYears(67))

        val vilkår = Paragraf_11_2()

        val søknad = Søknad(personident, fødselsdato)
        vilkår.håndterSøknad(søknad, fødselsdato, LocalDate.now())
        assertHarBehov(søknad)
        assertSkalIkkeVurdersManuelt(vilkår)

        val løsning = LøsningParagraf_11_2(LøsningParagraf_11_2.ErMedlem.NEI)
        vilkår.håndterLøsning(løsning)
        assertHarIkkeBehov(løsning)
        assertSkalIkkeVurdersManuelt(vilkår)

        assertFalse(vilkår.erOppfylt())
        assertTrue(vilkår.erIkkeOppfylt())
    }

    @Test
    fun `Hvis vi ikke vet om søker er medlem, er vilkår for medlemskap ikke vurdert ferdig`() {
        val personident = Personident("12345678910")
        val fødselsdato = Fødselsdato(LocalDate.now().minusYears(67))

        val vilkår = Paragraf_11_2()

        val søknad = Søknad(personident, fødselsdato)
        vilkår.håndterSøknad(søknad, fødselsdato, LocalDate.now())
        assertHarBehov(søknad)
        assertSkalIkkeVurdersManuelt(vilkår)

        val maskinellLøsning = LøsningParagraf_11_2(LøsningParagraf_11_2.ErMedlem.UAVKLART)
        vilkår.håndterLøsning(maskinellLøsning)
        assertHarIkkeBehov(maskinellLøsning)
        assertMåVurderesManuelt(vilkår)

        assertFalse(vilkår.erOppfylt())
        assertFalse(vilkår.erIkkeOppfylt())
    }

    @Test
    fun `Hvis søker er medlem etter manuell vurdering, er vilkår for medlemskap oppfylt`() {
        val personident = Personident("12345678910")
        val fødselsdato = Fødselsdato(LocalDate.now().minusYears(67))

        val vilkår = Paragraf_11_2()

        val søknad = Søknad(personident, fødselsdato)
        vilkår.håndterSøknad(søknad, fødselsdato, LocalDate.now())
        assertHarBehov(søknad)

        val maskinellLøsning = LøsningParagraf_11_2(LøsningParagraf_11_2.ErMedlem.UAVKLART)
        vilkår.håndterLøsning(maskinellLøsning)
        assertHarIkkeBehov(maskinellLøsning)
        assertMåVurderesManuelt(vilkår)

        val manuellLøsning = LøsningParagraf_11_2(LøsningParagraf_11_2.ErMedlem.JA)
        vilkår.håndterLøsning(manuellLøsning)
        assertHarIkkeBehov(manuellLøsning)
        assertSkalIkkeVurdersManuelt(vilkår)

        assertTrue(vilkår.erOppfylt())
        assertFalse(vilkår.erIkkeOppfylt())
    }

    @Test
    fun `Hvis søker ikke er medlem etter manuell vurdering, er vilkår for medlemskap ikke oppfylt`() {
        val personident = Personident("12345678910")
        val fødselsdato = Fødselsdato(LocalDate.now().minusYears(67))

        val vilkår = Paragraf_11_2()

        val søknad = Søknad(personident, fødselsdato)
        vilkår.håndterSøknad(søknad, fødselsdato, LocalDate.now())
        assertHarBehov(søknad)

        val maskinellLøsning = LøsningParagraf_11_2(LøsningParagraf_11_2.ErMedlem.UAVKLART)
        vilkår.håndterLøsning(maskinellLøsning)
        assertHarIkkeBehov(maskinellLøsning)
        assertMåVurderesManuelt(vilkår)

        val manuellLøsning = LøsningParagraf_11_2(LøsningParagraf_11_2.ErMedlem.NEI)
        vilkår.håndterLøsning(manuellLøsning)
        assertHarIkkeBehov(manuellLøsning)
        assertSkalIkkeVurdersManuelt(vilkår)

        assertFalse(vilkår.erOppfylt())
        assertTrue(vilkår.erIkkeOppfylt())
    }

    @Test
    fun `Hvis søker er uavklart etter manuell vurdering, er vilkår for medlemsskap ikke oppfylt`() {
        val personident = Personident("12345678910")
        val fødselsdato = Fødselsdato(LocalDate.now().minusYears(67))

        val vilkår = Paragraf_11_2()

        val søknad = Søknad(personident, fødselsdato)
        vilkår.håndterSøknad(søknad, fødselsdato, LocalDate.now())
        assertHarBehov(søknad)

        val maskinellLøsning = LøsningParagraf_11_2(LøsningParagraf_11_2.ErMedlem.UAVKLART)
        vilkår.håndterLøsning(maskinellLøsning)
        assertHarIkkeBehov(maskinellLøsning)
        assertMåVurderesManuelt(vilkår)

        val manuellLøsning = LøsningParagraf_11_2(LøsningParagraf_11_2.ErMedlem.UAVKLART)
        vilkår.håndterLøsning(manuellLøsning)
        assertHarIkkeBehov(manuellLøsning)
        assertSkalIkkeVurdersManuelt(vilkår)

        assertFalse(vilkår.erOppfylt())
        assertTrue(vilkår.erIkkeOppfylt())
    }

    @Test
    fun `Hvis søknad ikke er håndtert, er vilkåret hverken oppfylt eller ikke-oppfylt`() {
        val vilkår = Paragraf_11_2()

        assertFalse(vilkår.erOppfylt())
        assertFalse(vilkår.erIkkeOppfylt())
    }

    @Test
    fun `Hvis søknad er håndtert, men ikke løsning, er vilkåret hverken oppfylt eller ikke-oppfylt`() {
        val personident = Personident("12345678910")
        val fødselsdato = Fødselsdato(LocalDate.now().minusYears(67))

        val vilkår = Paragraf_11_2()

        val søknad = Søknad(personident, fødselsdato)
        vilkår.håndterSøknad(søknad, fødselsdato, LocalDate.now())
        assertHarBehov(søknad)
        assertSkalIkkeVurdersManuelt(vilkår)

        assertFalse(vilkår.erOppfylt())
        assertFalse(vilkår.erIkkeOppfylt())
    }

    private fun assertHarBehov(hendelse: Hendelse) {
        assertTrue(hendelse.behov().isNotEmpty())
    }

    private fun assertHarIkkeBehov(hendelse: Hendelse) {
        assertTrue(hendelse.behov().isEmpty())
    }

    private fun assertMåVurderesManuelt(vilkårsvurdering: Vilkårsvurdering) {
        assertTrue(listOf(vilkårsvurdering).toDto().first().måVurderesManuelt)
    }

    private fun assertSkalIkkeVurdersManuelt(vilkårsvurdering: Vilkårsvurdering) {
        assertFalse(listOf(vilkårsvurdering).toDto().first().måVurderesManuelt)
    }
}
