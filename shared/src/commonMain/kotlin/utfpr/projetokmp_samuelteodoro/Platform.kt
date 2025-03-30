package utfpr.projetokmp_samuelteodoro

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform