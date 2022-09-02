package no.nav.aap.dto

import java.util.*

data class VilkårsvurderingModellApi(
    val vilkårsvurderingsid: UUID,
    val vurdertAv: String?,
    val kvalitetssikretAv: String?,
    val paragraf: String,
    val ledd: List<String>,
    val tilstand: String,
    val utfall: Utfall,
    val vurdertMaskinelt: Boolean,
    val løsning_medlemskap_yrkesskade_maskinell: List<LøsningMaskinellMedlemskapYrkesskadeModellApi>? = null,
    val løsning_medlemskap_yrkesskade_manuell: List<LøsningManuellMedlemskapYrkesskadeModellApi>? = null,
    val løsning_11_2_maskinell: List<LøsningMaskinellParagraf_11_2ModellApi>? = null,
    val løsning_11_2_manuell: List<LøsningParagraf_11_2ModellApi>? = null,
    val løsning_11_3_manuell: List<LøsningParagraf_11_3ModellApi>? = null,
    val løsning_11_4_ledd2_ledd3_manuell: List<LøsningParagraf_11_4AndreOgTredjeLeddModellApi>? = null,
    val løsning_11_5_manuell: List<LøsningParagraf_11_5ModellApi>? = null,
    val løsning_11_5_yrkesskade_manuell: List<LøsningParagraf_11_5YrkesskadeModellApi>? = null,
    val løsning_11_6_manuell: List<LøsningParagraf_11_6ModellApi>? = null,
    val løsning_11_12_ledd1_manuell: List<LøsningParagraf_11_12FørsteLeddModellApi>? = null,
    val løsning_11_19_manuell: List<LøsningParagraf_11_19ModellApi>? = null,
    val løsning_11_22_manuell: List<LøsningParagraf_11_22ModellApi>? = null,
    val løsning_11_29_manuell: List<LøsningParagraf_11_29ModellApi>? = null,
    val kvalitetssikringer_medlemskap_yrkesskade: List<KvalitetssikringMedlemskapYrkesskadeModellApi>? = null,
    val kvalitetssikringer_11_2: List<KvalitetssikringParagraf_11_2ModellApi>? = null,
    val kvalitetssikringer_11_3: List<KvalitetssikringParagraf_11_3ModellApi>? = null,
    val kvalitetssikringer_11_4_ledd2_ledd3: List<KvalitetssikringParagraf_11_4AndreOgTredjeLeddModellApi>? = null,
    val kvalitetssikringer_11_5: List<KvalitetssikringParagraf_11_5ModellApi>? = null,
    val kvalitetssikringer_11_5_yrkesskade: List<KvalitetssikringParagraf_11_5YrkesskadeModellApi>? = null,
    val kvalitetssikringer_11_6: List<KvalitetssikringParagraf_11_6ModellApi>? = null,
    val kvalitetssikringer_11_12_ledd1: List<KvalitetssikringParagraf_11_12FørsteLeddModellApi>? = null,
    val kvalitetssikringer_11_19: List<KvalitetssikringParagraf_11_19ModellApi>? = null,
    val kvalitetssikringer_11_22: List<KvalitetssikringParagraf_11_22ModellApi>? = null,
    val kvalitetssikringer_11_29: List<KvalitetssikringParagraf_11_29ModellApi>? = null,
)