package joebruckner.lastpick.data

data class Credits(
        val cast: List<Cast>,
        val crew: List<Crew>
) {
    fun getDirector(): Crew? {
        crew.forEach { member ->
            if (member.job == "Director") return member
        }
        return null
    }
}