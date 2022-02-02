package no.nav.aap

import java.time.LocalDate

internal fun Int.januar(år: Int) = LocalDate.of(år, 1, this)
internal val Int.januar get() = this.januar(2022)
internal fun Int.februar(år: Int) = LocalDate.of(år, 2, this)
internal val Int.februar get() = this.februar(2022)
internal fun Int.mars(år: Int) = LocalDate.of(år, 3, this)
internal val Int.mars get() = this.mars(2022)
internal fun Int.april(år: Int) = LocalDate.of(år, 4, this)
internal val Int.april get() = this.april(2022)
internal fun Int.mai(år: Int) = LocalDate.of(år, 5, this)
internal val Int.mai get() = this.mai(2022)
internal fun Int.juni(år: Int) = LocalDate.of(år, 6, this)
internal val Int.juni get() = this.juni(2022)
internal fun Int.juli(år: Int) = LocalDate.of(år, 7, this)
internal val Int.juli get() = this.juli(2022)
internal fun Int.august(år: Int) = LocalDate.of(år, 8, this)
internal val Int.august get() = this.august(2022)
internal fun Int.september(år: Int) = LocalDate.of(år, 9, this)
internal val Int.september get() = this.september(2022)
internal fun Int.oktober(år: Int) = LocalDate.of(år, 10, this)
internal val Int.oktober get() = this.oktober(2022)
internal fun Int.november(år: Int) = LocalDate.of(år, 11, this)
internal val Int.november get() = this.november(2022)
internal fun Int.desember(år: Int) = LocalDate.of(år, 12, this)
internal val Int.desember get() = this.desember(2022)
