package no.nav.aap.domene

import no.nav.aap.domene.beregning.Arbeidsgiver
import no.nav.aap.domene.beregning.Beløp.Companion.beløp
import no.nav.aap.domene.beregning.Inntekt
import no.nav.aap.domene.beregning.Inntekt.Companion.inntektSiste3Kalenderår
import no.nav.aap.domene.beregning.Inntektsgrunnlag
import no.nav.aap.januar
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Year

class TidslinjeTest {

    private fun Iterable<Inntekt>.inntektsgrunnlag(år: Year) = Inntektsgrunnlag(år, this.inntektSiste3Kalenderår(år))


    @Test
    fun `Når man oppretter en ny tidsperiode med dato og grunnlag, får man en liste med dager med beløp`() {
        val inntekter = listOf(
            Inntekt(Arbeidsgiver(), januar(2014), 540527.beløp),
            Inntekt(Arbeidsgiver(), januar(2015), 459248.beløp),
            Inntekt(Arbeidsgiver(), januar(2016), 645246.beløp)
        )
        val grunnlag = inntekter.inntektsgrunnlag(Year.of(2016))

        val tidsperiode = Tidsperiode.fyllPeriodeMedDager(1 januar 2017, grunnlag)

        assertEquals(0.beløp, tidsperiode.getDagsatsFor(1 januar 2017))
        assertEquals(1410.beløp, tidsperiode.getDagsatsFor(2 januar 2017))
        assertEquals(1410.beløp, tidsperiode.getDagsatsFor(13 januar 2017))
        assertEquals(0.beløp, tidsperiode.getDagsatsFor(14 januar 2017))
        assertEquals(14100.beløp, tidsperiode.summerDagsatserForPeriode())
        assertThrows<NoSuchElementException> { tidsperiode.getDagsatsFor(15 januar 2017) }
    }

    @Test
    fun `Når man oppretter en ny tidslinje med startdato og grunnlag, får man en periode med dager med beløp`() {
        val inntekter = listOf(
            Inntekt(Arbeidsgiver(), januar(2014), 540527.beløp),
            Inntekt(Arbeidsgiver(), januar(2015), 459248.beløp),
            Inntekt(Arbeidsgiver(), januar(2016), 645246.beløp)
        )
        val grunnlag = inntekter.inntektsgrunnlag(Year.of(2016))

        val tidslinje = Tidslinje.opprettTidslinje(1 januar 2017, grunnlag)

        assertEquals(14100.beløp, tidslinje.summerTidslinje())
    }

}