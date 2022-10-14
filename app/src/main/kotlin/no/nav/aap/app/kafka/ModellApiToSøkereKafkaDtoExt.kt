package no.nav.aap.app.kafka

import no.nav.aap.dto.kafka.SøkereKafkaDto
import no.nav.aap.dto.kafka.SøkereKafkaDto.*
import no.nav.aap.modellapi.*

internal fun SøkerModellApi.toJson(gammelSekvensnummer: Long) = SøkereKafkaDto(
    personident = personident,
    fødselsdato = fødselsdato,
    sekvensnummer = gammelSekvensnummer + 1,
    saker = saker.map(SakModellApi::toJson),
)

private fun SakModellApi.toJson() = Sak(
    saksid = saksid,
    tilstand = tilstand,
    vurderingsdato = vurderingsdato,
    sakstyper = sakstyper.map(SakstypeModellApi::toJson),
    søknadstidspunkt = søknadstidspunkt,
    vedtak = vedtak?.toJson()
)

private fun SakstypeModellApi.toJson() = Sakstype(
    type = type,
    aktiv = aktiv,
    medlemskapYrkesskade =
    vilkårsvurderinger.filterIsInstance<MedlemskapYrkesskadeModellApi>().firstOrNull()?.toJson(),
    paragraf_8_48 =
    vilkårsvurderinger.filterIsInstance<Paragraf_8_48ModellApi>().firstOrNull()?.toJson(),
    paragraf_11_2 =
    vilkårsvurderinger.filterIsInstance<Paragraf_11_2ModellApi>().firstOrNull()?.toJson(),
    paragraf_11_3 =
    vilkårsvurderinger.filterIsInstance<Paragraf_11_3ModellApi>().firstOrNull()?.toJson(),
    paragraf_11_4FørsteLedd =
    vilkårsvurderinger.filterIsInstance<Paragraf_11_4FørsteLeddModellApi>().firstOrNull()?.toJson(),
    paragraf_11_4AndreOgTredjeLedd =
    vilkårsvurderinger.filterIsInstance<Paragraf_11_4AndreOgTredjeLeddModellApi>().firstOrNull()?.toJson(),
    paragraf_11_5 =
    vilkårsvurderinger.filterIsInstance<Paragraf_11_5ModellApi>().firstOrNull()?.toJson(),
    paragraf_11_5Yrkesskade =
    vilkårsvurderinger.filterIsInstance<Paragraf_11_5YrkesskadeModellApi>().firstOrNull()?.toJson(),
    paragraf_11_6 =
    vilkårsvurderinger.filterIsInstance<Paragraf_11_6ModellApi>().firstOrNull()?.toJson(),
    paragraf_11_14 =
    vilkårsvurderinger.filterIsInstance<Paragraf_11_14ModellApi>().firstOrNull()?.toJson(),
    paragraf_11_19 =
    vilkårsvurderinger.filterIsInstance<Paragraf_11_19ModellApi>().firstOrNull()?.toJson(),
    paragraf_11_22 =
    vilkårsvurderinger.filterIsInstance<Paragraf_11_22ModellApi>().firstOrNull()?.toJson(),
    paragraf_11_27FørsteLedd =
    vilkårsvurderinger.filterIsInstance<Paragraf_11_27FørsteLeddModellApi>().firstOrNull()?.toJson(),
    paragraf_11_29 =
    vilkårsvurderinger.filterIsInstance<Paragraf_11_29ModellApi>().firstOrNull()?.toJson(),
    paragraf_22_13 =
    vilkårsvurderinger.filterIsInstance<Paragraf_22_13ModellApi>().firstOrNull()?.toJson()
)

private fun Paragraf_8_48ModellApi.toJson() = Paragraf_8_48(
    vilkårsvurderingsid = vilkårsvurderingsid,
    vurdertAv = vurdertAv,
    kvalitetssikretAv = kvalitetssikretAv,
    paragraf = paragraf,
    ledd = ledd,
    tilstand = tilstand,
    utfall = utfall.name,
    vurdertMaskinelt = vurdertMaskinelt,
    løsning_8_48_maskinell = løsning_8_48_maskinell.map(SykepengedagerModellApi::toJson),
    løsning_22_13_manuell = løsning_22_13_manuell.map(LøsningParagraf_22_13ModellApi::toJson),
    kvalitetssikringer_22_13 = kvalitetssikringer_22_13.map(KvalitetssikringParagraf_22_13ModellApi::toJson),
)

private fun MedlemskapYrkesskadeModellApi.toJson() = MedlemskapYrkesskade(
    vilkårsvurderingsid = vilkårsvurderingsid,
    vurdertAv = vurdertAv,
    kvalitetssikretAv = kvalitetssikretAv,
    paragraf = paragraf,
    ledd = ledd,
    tilstand = tilstand,
    utfall = utfall.name,
    vurdertMaskinelt = vurdertMaskinelt,
    løsning_medlemskap_yrkesskade_maskinell = løsning_medlemskap_yrkesskade_maskinell
        .map(LøsningMaskinellMedlemskapYrkesskadeModellApi::toJson),
    løsning_medlemskap_yrkesskade_manuell = løsning_medlemskap_yrkesskade_manuell
        .map(LøsningManuellMedlemskapYrkesskadeModellApi::toJson),
    kvalitetssikringer_medlemskap_yrkesskade = kvalitetssikringer_medlemskap_yrkesskade
        .map(KvalitetssikringMedlemskapYrkesskadeModellApi::toJson),
)

private fun Paragraf_11_2ModellApi.toJson() = Paragraf_11_2(
    vilkårsvurderingsid = vilkårsvurderingsid,
    vurdertAv = vurdertAv,
    kvalitetssikretAv = kvalitetssikretAv,
    paragraf = paragraf,
    ledd = ledd,
    tilstand = tilstand,
    utfall = utfall.name,
    vurdertMaskinelt = vurdertMaskinelt,
    løsning_11_2_maskinell = løsning_11_2_maskinell.map(LøsningMaskinellParagraf_11_2ModellApi::toJson),
    løsning_11_2_manuell = løsning_11_2_manuell.map(LøsningParagraf_11_2ModellApi::toJson),
    kvalitetssikringer_11_2 = kvalitetssikringer_11_2.map(KvalitetssikringParagraf_11_2ModellApi::toJson),
)

private fun Paragraf_11_3ModellApi.toJson() = Paragraf_11_3(
    vilkårsvurderingsid = vilkårsvurderingsid,
    vurdertAv = vurdertAv,
    kvalitetssikretAv = kvalitetssikretAv,
    paragraf = paragraf,
    ledd = ledd,
    tilstand = tilstand,
    utfall = utfall.name,
    vurdertMaskinelt = vurdertMaskinelt,
    løsning_11_3_manuell = løsning_11_3_manuell.map(LøsningParagraf_11_3ModellApi::toJson),
    kvalitetssikringer_11_3 = kvalitetssikringer_11_3.map(KvalitetssikringParagraf_11_3ModellApi::toJson),
)

private fun Paragraf_11_4FørsteLeddModellApi.toJson() = Paragraf_11_4FørsteLedd(
    vilkårsvurderingsid = vilkårsvurderingsid,
    vurdertAv = vurdertAv,
    kvalitetssikretAv = kvalitetssikretAv,
    paragraf = paragraf,
    ledd = ledd,
    tilstand = tilstand,
    utfall = utfall.name,
    vurdertMaskinelt = vurdertMaskinelt,
)

private fun Paragraf_11_4AndreOgTredjeLeddModellApi.toJson() = Paragraf_11_4AndreOgTredjeLedd(
    vilkårsvurderingsid = vilkårsvurderingsid,
    vurdertAv = vurdertAv,
    kvalitetssikretAv = kvalitetssikretAv,
    paragraf = paragraf,
    ledd = ledd,
    tilstand = tilstand,
    utfall = utfall.name,
    vurdertMaskinelt = vurdertMaskinelt,
    løsning_11_4_ledd2_ledd3_manuell = løsning_11_4_ledd2_ledd3_manuell
        .map(LøsningParagraf_11_4AndreOgTredjeLeddModellApi::toJson),
    kvalitetssikringer_11_4_ledd2_ledd3 = kvalitetssikringer_11_4_ledd2_ledd3
        .map(KvalitetssikringParagraf_11_4AndreOgTredjeLeddModellApi::toJson),
)

private fun Paragraf_11_5ModellApi.toJson() = Paragraf_11_5(
    vilkårsvurderingsid = vilkårsvurderingsid,
    vurdertAv = vurdertAv,
    kvalitetssikretAv = kvalitetssikretAv,
    paragraf = paragraf,
    ledd = ledd,
    tilstand = tilstand,
    utfall = utfall.name,
    vurdertMaskinelt = vurdertMaskinelt,
    løsning_11_5_manuell = løsning_11_5_manuell.map(LøsningParagraf_11_5ModellApi::toJson),
    kvalitetssikringer_11_5 = kvalitetssikringer_11_5.map(KvalitetssikringParagraf_11_5ModellApi::toJson),
)

private fun Paragraf_11_5YrkesskadeModellApi.toJson() = Paragraf_11_5Yrkesskade(
    vilkårsvurderingsid = vilkårsvurderingsid,
    vurdertAv = vurdertAv,
    kvalitetssikretAv = kvalitetssikretAv,
    paragraf = paragraf,
    ledd = ledd,
    tilstand = tilstand,
    utfall = utfall.name,
    vurdertMaskinelt = vurdertMaskinelt,
    løsning_11_5_yrkesskade_manuell = løsning_11_5_yrkesskade_manuell
        .map(LøsningParagraf_11_5YrkesskadeModellApi::toJson),
    kvalitetssikringer_11_5_yrkesskade = kvalitetssikringer_11_5_yrkesskade
        .map(KvalitetssikringParagraf_11_5YrkesskadeModellApi::toJson),
)

private fun Paragraf_11_6ModellApi.toJson() = Paragraf_11_6(
    vilkårsvurderingsid = vilkårsvurderingsid,
    vurdertAv = vurdertAv,
    kvalitetssikretAv = kvalitetssikretAv,
    paragraf = paragraf,
    ledd = ledd,
    tilstand = tilstand,
    utfall = utfall.name,
    vurdertMaskinelt = vurdertMaskinelt,
    løsning_11_6_manuell = løsning_11_6_manuell.map(LøsningParagraf_11_6ModellApi::toJson),
    kvalitetssikringer_11_6 = kvalitetssikringer_11_6.map(KvalitetssikringParagraf_11_6ModellApi::toJson),
)

private fun Paragraf_11_14ModellApi.toJson() = Paragraf_11_14(
    vilkårsvurderingsid = vilkårsvurderingsid,
    vurdertAv = vurdertAv,
    kvalitetssikretAv = kvalitetssikretAv,
    paragraf = paragraf,
    ledd = ledd,
    tilstand = tilstand,
    utfall = utfall.name,
    vurdertMaskinelt = vurdertMaskinelt,
)

private fun Paragraf_11_19ModellApi.toJson() = Paragraf_11_19(
    vilkårsvurderingsid = vilkårsvurderingsid,
    vurdertAv = vurdertAv,
    kvalitetssikretAv = kvalitetssikretAv,
    paragraf = paragraf,
    ledd = ledd,
    tilstand = tilstand,
    utfall = utfall.name,
    vurdertMaskinelt = vurdertMaskinelt,
    løsning_11_19_manuell = løsning_11_19_manuell.map(LøsningParagraf_11_19ModellApi::toJson),
    kvalitetssikringer_11_19 = kvalitetssikringer_11_19.map(KvalitetssikringParagraf_11_19ModellApi::toJson),
)

private fun Paragraf_11_22ModellApi.toJson() = Paragraf_11_22(
    vilkårsvurderingsid = vilkårsvurderingsid,
    vurdertAv = vurdertAv,
    kvalitetssikretAv = kvalitetssikretAv,
    paragraf = paragraf,
    ledd = ledd,
    tilstand = tilstand,
    utfall = utfall.name,
    vurdertMaskinelt = vurdertMaskinelt,
    løsning_11_22_manuell = løsning_11_22_manuell.map(LøsningParagraf_11_22ModellApi::toJson),
    kvalitetssikringer_11_22 = kvalitetssikringer_11_22.map(KvalitetssikringParagraf_11_22ModellApi::toJson),
)

private fun Paragraf_11_27FørsteLeddModellApi.toJson() = Paragraf_11_27FørsteLedd(
    vilkårsvurderingsid = vilkårsvurderingsid,
    vurdertAv = vurdertAv,
    kvalitetssikretAv = kvalitetssikretAv,
    paragraf = paragraf,
    ledd = ledd,
    tilstand = tilstand,
    utfall = utfall.name,
    vurdertMaskinelt = vurdertMaskinelt,
    løsning_11_27_maskinell = løsning_11_27_maskinell.map(LøsningParagraf_11_27_FørsteLedd_ModellApi::toJson),
    løsning_22_13_manuell = løsning_22_13_manuell.map(LøsningParagraf_22_13ModellApi::toJson),
    kvalitetssikringer_22_13 = kvalitetssikringer_22_13.map(KvalitetssikringParagraf_22_13ModellApi::toJson),
)

private fun Paragraf_11_29ModellApi.toJson() = Paragraf_11_29(
    vilkårsvurderingsid = vilkårsvurderingsid,
    vurdertAv = vurdertAv,
    kvalitetssikretAv = kvalitetssikretAv,
    paragraf = paragraf,
    ledd = ledd,
    tilstand = tilstand,
    utfall = utfall.name,
    vurdertMaskinelt = vurdertMaskinelt,
    løsning_11_29_manuell = løsning_11_29_manuell.map(LøsningParagraf_11_29ModellApi::toJson),
    kvalitetssikringer_11_29 = kvalitetssikringer_11_29.map(KvalitetssikringParagraf_11_29ModellApi::toJson),
)

private fun Paragraf_22_13ModellApi.toJson() = Paragraf_22_13(
    vilkårsvurderingsid = vilkårsvurderingsid,
    vurdertAv = vurdertAv,
    kvalitetssikretAv = kvalitetssikretAv,
    paragraf = paragraf,
    ledd = ledd,
    tilstand = tilstand,
    utfall = utfall.name,
    vurdertMaskinelt = vurdertMaskinelt,
    løsning_22_13_manuell = løsning_22_13_manuell.map(LøsningParagraf_22_13ModellApi::toJson),
    kvalitetssikringer_22_13 = kvalitetssikringer_22_13.map(KvalitetssikringParagraf_22_13ModellApi::toJson),
)

private fun LøsningMaskinellMedlemskapYrkesskadeModellApi.toJson() = LøsningMaskinellMedlemskapYrkesskade(
    løsningId = løsningId,
    erMedlem = erMedlem
)

private fun LøsningManuellMedlemskapYrkesskadeModellApi.toJson() = LøsningManuellMedlemskapYrkesskade(
    løsningId = løsningId,
    vurdertAv = vurdertAv,
    tidspunktForVurdering = tidspunktForVurdering,
    erMedlem = erMedlem
)

private fun SykepengedagerModellApi.toJson() = LøsningMaskinellParagraf_8_48(
    løsningId = løsningId,
    tidspunktForVurdering = tidspunktForVurdering,
    sykepengedager = sykepengedager?.let { sykepengedager ->
        LøsningMaskinellParagraf_8_48.Sykepengedager(
            gjenståendeSykedager = sykepengedager.gjenståendeSykedager,
            foreløpigBeregnetSluttPåSykepenger = sykepengedager.foreløpigBeregnetSluttPåSykepenger,
            kilde = sykepengedager.kilde,
        )
    }
)

private fun LøsningMaskinellParagraf_11_2ModellApi.toJson() = LøsningMaskinellParagraf_11_2(
    løsningId = løsningId,
    tidspunktForVurdering = tidspunktForVurdering,
    erMedlem = erMedlem
)

private fun LøsningParagraf_11_2ModellApi.toJson() = LøsningManuellParagraf_11_2(
    løsningId = løsningId,
    vurdertAv = vurdertAv,
    tidspunktForVurdering = tidspunktForVurdering,
    erMedlem = erMedlem
)

private fun LøsningParagraf_11_3ModellApi.toJson() = LøsningParagraf_11_3(
    løsningId = løsningId,
    vurdertAv = vurdertAv,
    tidspunktForVurdering = tidspunktForVurdering,
    erOppfylt = erOppfylt
)

private fun LøsningParagraf_11_4AndreOgTredjeLeddModellApi.toJson() = LøsningParagraf_11_4AndreOgTredjeLedd(
    løsningId = løsningId,
    vurdertAv = vurdertAv,
    tidspunktForVurdering = tidspunktForVurdering,
    erOppfylt = erOppfylt
)

private fun LøsningParagraf_11_5ModellApi.toJson() = LøsningParagraf_11_5(
    løsningId = løsningId,
    vurdertAv = vurdertAv,
    tidspunktForVurdering = tidspunktForVurdering,
    kravOmNedsattArbeidsevneErOppfylt = kravOmNedsattArbeidsevneErOppfylt,
    nedsettelseSkyldesSykdomEllerSkade = nedsettelseSkyldesSykdomEllerSkade,
)

private fun LøsningParagraf_11_5YrkesskadeModellApi.toJson() = LøsningParagraf_11_5_yrkesskade(
    løsningId = løsningId,
    vurdertAv = vurdertAv,
    tidspunktForVurdering = tidspunktForVurdering,
    arbeidsevneErNedsattMedMinst50Prosent = arbeidsevneErNedsattMedMinst50Prosent,
    arbeidsevneErNedsattMedMinst30Prosent = arbeidsevneErNedsattMedMinst30Prosent,
)

private fun LøsningParagraf_11_6ModellApi.toJson() = LøsningParagraf_11_6(
    løsningId = løsningId,
    vurdertAv = vurdertAv,
    tidspunktForVurdering = tidspunktForVurdering,
    harBehovForBehandling = harBehovForBehandling,
    harBehovForTiltak = harBehovForTiltak,
    harMulighetForÅKommeIArbeid = harMulighetForÅKommeIArbeid
)

private fun LøsningParagraf_11_19ModellApi.toJson() = LøsningParagraf_11_19(
    løsningId = løsningId,
    vurdertAv = vurdertAv,
    tidspunktForVurdering = tidspunktForVurdering,
    beregningsdato = beregningsdato
)

private fun LøsningParagraf_11_22ModellApi.toJson() = LøsningParagraf_11_22(
    løsningId = løsningId,
    vurdertAv = vurdertAv,
    tidspunktForVurdering = tidspunktForVurdering,
    erOppfylt = erOppfylt,
    andelNedsattArbeidsevne = andelNedsattArbeidsevne,
    år = år,
    antattÅrligArbeidsinntekt = antattÅrligArbeidsinntekt,
)

private fun LøsningParagraf_11_27_FørsteLedd_ModellApi.toJson() = LøsningMaskinellParagraf_11_27FørsteLedd(
    løsningId = løsningId,
    tidspunktForVurdering = tidspunktForVurdering,
    svangerskapspenger = LøsningMaskinellParagraf_11_27FørsteLedd.Svangerskapspenger(
        fom = svangerskapspenger.fom,
        tom = svangerskapspenger.tom,
        grad = svangerskapspenger.grad,
        vedtaksdato = svangerskapspenger.vedtaksdato,
    )
)

private fun LøsningParagraf_11_29ModellApi.toJson() = LøsningParagraf_11_29(
    løsningId = løsningId,
    vurdertAv = vurdertAv,
    tidspunktForVurdering = tidspunktForVurdering,
    erOppfylt = erOppfylt
)

private fun LøsningParagraf_22_13ModellApi.toJson() = LøsningParagraf_22_13(
    løsningId = løsningId,
    vurdertAv = vurdertAv,
    tidspunktForVurdering = tidspunktForVurdering,
    bestemmesAv = bestemmesAv,
    unntak = unntak,
    unntaksbegrunnelse = unntaksbegrunnelse,
    manueltSattVirkningsdato = manueltSattVirkningsdato
)

private fun KvalitetssikringMedlemskapYrkesskadeModellApi.toJson() = KvalitetssikringMedlemskapYrkesskade(
    kvalitetssikringId = kvalitetssikringId,
    løsningId = løsningId,
    kvalitetssikretAv = kvalitetssikretAv,
    tidspunktForKvalitetssikring = tidspunktForKvalitetssikring,
    erGodkjent = erGodkjent,
    begrunnelse = begrunnelse
)

private fun KvalitetssikringParagraf_11_2ModellApi.toJson() = KvalitetssikringParagraf_11_2(
    kvalitetssikringId = kvalitetssikringId,
    løsningId = løsningId,
    kvalitetssikretAv = kvalitetssikretAv,
    tidspunktForKvalitetssikring = tidspunktForKvalitetssikring,
    erGodkjent = erGodkjent,
    begrunnelse = begrunnelse
)

private fun KvalitetssikringParagraf_11_3ModellApi.toJson() = KvalitetssikringParagraf_11_3(
    kvalitetssikringId = kvalitetssikringId,
    løsningId = løsningId,
    kvalitetssikretAv = kvalitetssikretAv,
    tidspunktForKvalitetssikring = tidspunktForKvalitetssikring,
    erGodkjent = erGodkjent,
    begrunnelse = begrunnelse
)

private fun KvalitetssikringParagraf_11_4AndreOgTredjeLeddModellApi.toJson() =
    KvalitetssikringParagraf_11_4AndreOgTredjeLedd(
        kvalitetssikringId = kvalitetssikringId,
        løsningId = løsningId,
        kvalitetssikretAv = kvalitetssikretAv,
        tidspunktForKvalitetssikring = tidspunktForKvalitetssikring,
        erGodkjent = erGodkjent,
        begrunnelse = begrunnelse
    )

private fun KvalitetssikringParagraf_11_5ModellApi.toJson() = KvalitetssikringParagraf_11_5(
    kvalitetssikringId = kvalitetssikringId,
    løsningId = løsningId,
    kvalitetssikretAv = kvalitetssikretAv,
    tidspunktForKvalitetssikring = tidspunktForKvalitetssikring,
    erGodkjent = erGodkjent,
    begrunnelse = begrunnelse
)

private fun KvalitetssikringParagraf_11_5YrkesskadeModellApi.toJson() = KvalitetssikringParagraf_11_5Yrkesskade(
    kvalitetssikringId = kvalitetssikringId,
    løsningId = løsningId,
    kvalitetssikretAv = kvalitetssikretAv,
    tidspunktForKvalitetssikring = tidspunktForKvalitetssikring,
    erGodkjent = erGodkjent,
    begrunnelse = begrunnelse
)

private fun KvalitetssikringParagraf_11_6ModellApi.toJson() = KvalitetssikringParagraf_11_6(
    kvalitetssikringId = kvalitetssikringId,
    løsningId = løsningId,
    kvalitetssikretAv = kvalitetssikretAv,
    tidspunktForKvalitetssikring = tidspunktForKvalitetssikring,
    erGodkjent = erGodkjent,
    begrunnelse = begrunnelse
)

private fun KvalitetssikringParagraf_22_13ModellApi.toJson() = KvalitetssikringParagraf_22_13(
    kvalitetssikringId = kvalitetssikringId,
    løsningId = løsningId,
    kvalitetssikretAv = kvalitetssikretAv,
    tidspunktForKvalitetssikring = tidspunktForKvalitetssikring,
    erGodkjent = erGodkjent,
    begrunnelse = begrunnelse
)

private fun KvalitetssikringParagraf_11_19ModellApi.toJson() = KvalitetssikringParagraf_11_19(
    kvalitetssikringId = kvalitetssikringId,
    løsningId = løsningId,
    kvalitetssikretAv = kvalitetssikretAv,
    tidspunktForKvalitetssikring = tidspunktForKvalitetssikring,
    erGodkjent = erGodkjent,
    begrunnelse = begrunnelse
)

private fun KvalitetssikringParagraf_11_22ModellApi.toJson() = KvalitetssikringParagraf_11_22(
    kvalitetssikringId = kvalitetssikringId,
    løsningId = løsningId,
    kvalitetssikretAv = kvalitetssikretAv,
    tidspunktForKvalitetssikring = tidspunktForKvalitetssikring,
    erGodkjent = erGodkjent,
    begrunnelse = begrunnelse
)

private fun KvalitetssikringParagraf_11_29ModellApi.toJson() = KvalitetssikringParagraf_11_29(
    kvalitetssikringId = kvalitetssikringId,
    løsningId = løsningId,
    kvalitetssikretAv = kvalitetssikretAv,
    tidspunktForKvalitetssikring = tidspunktForKvalitetssikring,
    erGodkjent = erGodkjent,
    begrunnelse = begrunnelse
)


private fun VedtakModellApi.toJson() = Vedtak(
    vedtaksid = vedtaksid,
    innvilget = innvilget,
    inntektsgrunnlag = inntektsgrunnlag.toJson(),
    vedtaksdato = vedtaksdato,
    virkningsdato = virkningsdato,
)

private fun InntektsgrunnlagModellApi.toJson() = Inntektsgrunnlag(
    beregningsdato = beregningsdato,
    inntekterSiste3Kalenderår = inntekterSiste3Kalenderår.map(InntekterForBeregningModellApi::toJson),
    yrkesskade = yrkesskade?.toJson(),
    fødselsdato = fødselsdato,
    sisteKalenderår = sisteKalenderår,
    grunnlagsfaktor = grunnlagsfaktor,
)

private fun InntekterForBeregningModellApi.toJson() = InntekterForBeregning(
    inntekter = inntekter.map(InntektModellApi::toJson),
    inntektsgrunnlagForÅr = inntektsgrunnlagForÅr.toJson(),
)

private fun InntektModellApi.toJson() = Inntekt(
    arbeidsgiver = arbeidsgiver,
    inntekstmåned = inntekstmåned,
    beløp = beløp,
)

private fun InntektsgrunnlagForÅrModellApi.toJson() = InntektsgrunnlagForÅr(
    år = år,
    beløpFørJustering = beløpFørJustering,
    beløpJustertFor6G = beløpJustertFor6G,
    erBeløpJustertFor6G = erBeløpJustertFor6G,
    grunnlagsfaktor = grunnlagsfaktor,
)

private fun YrkesskadeModellApi.toJson() = Yrkesskade(
    gradAvNedsattArbeidsevneKnyttetTilYrkesskade = gradAvNedsattArbeidsevneKnyttetTilYrkesskade,
    inntektsgrunnlag = inntektsgrunnlag.toJson(),
)
