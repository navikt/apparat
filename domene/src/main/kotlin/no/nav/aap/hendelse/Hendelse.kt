package no.nav.aap.hendelse

open class Hendelse {
    private val behov = mutableListOf<Behov>()

    internal fun opprettBehov(behov: Behov) {
        this.behov.add(behov)
    }

    fun behov(): List<Behov> = behov.toList()
}

interface Behov {
    fun toDto(ident: String): DtoBehov
}

sealed class DtoBehov {
    open fun erMedlem() = false
    open fun erDtoBehov_11_3() = false
    open fun erDtoBehov_11_4FørsteLedd() = false
    open fun erDtoBehov_11_4AndreOgTredjeLedd() = false
    open fun erDtoBehov_11_5() = false
    open fun erDtoBehov_11_6() = false
    open fun erDtoBehov_11_12FørsteLedd() = false
    open fun erDtoBehov_11_29() = false
    open fun erDtoBehovVurderingAvBeregningsdato() = false

    open fun accept(lytter: Lytter) {}

    class Medlem(private val ident: String) : DtoBehov() {
        override fun erMedlem() = true
        override fun accept(lytter: Lytter) {
            lytter.medlem(ident)
        }
    }

    class DtoBehov_11_3(private val ident: String) : DtoBehov() {
        override fun erDtoBehov_11_3() = true
        override fun accept(lytter: Lytter) {
            lytter.behov_11_3(ident)
        }
    }

    class DtoBehov_11_4AndreOgTredjeLedd(private val ident: String) : DtoBehov() {
        override fun erDtoBehov_11_4AndreOgTredjeLedd() = true
        override fun accept(lytter: Lytter) {
            lytter.behov_11_4AndreOgTredjeLedd(ident)
        }
    }

    class DtoBehov_11_5(private val ident: String) : DtoBehov() {
        override fun erDtoBehov_11_5() = true
        override fun accept(lytter: Lytter) {
            lytter.behov_11_5(ident)
        }
    }

    class DtoBehov_11_6(private val ident: String) : DtoBehov() {
        override fun erDtoBehov_11_6() = true
        override fun accept(lytter: Lytter) {
            lytter.behov_11_6(ident)
        }
    }

    class DtoBehov_11_12FørsteLedd(private val ident: String) : DtoBehov() {
        override fun erDtoBehov_11_12FørsteLedd() = true
        override fun accept(lytter: Lytter) {
            lytter.behov_11_12FørsteLedd(ident)
        }
    }

    class DtoBehov_11_29(private val ident: String) : DtoBehov() {
        override fun erDtoBehov_11_29() = true
        override fun accept(lytter: Lytter) {
            lytter.behov_11_29(ident)
        }
    }

    class DtoBehovVurderingAvBeregningsdato(private val ident: String) : DtoBehov() {
        override fun erDtoBehovVurderingAvBeregningsdato() = true
        override fun accept(lytter: Lytter) {
            lytter.behovVurderingAvBeregningsdato(ident)
        }
    }
}

interface Lytter {
    fun medlem(ident: String) {}
    fun behovVurderingAvBeregningsdato(ident: String) {}
    fun behov_11_3(ident: String) {}
    fun behov_11_4FørsteLedd(ident: String) {}
    fun behov_11_4AndreOgTredjeLedd(ident: String) {}
    fun behov_11_5(ident: String) {}
    fun behov_11_6(ident: String) {}
    fun behov_11_12FørsteLedd(ident: String) {}
    fun behov_11_29(ident: String) {}
}
