object Build {
    const val DEBUG = "debug"
    const val RELEASE = "release"
}

interface BuildType {
    val isMinifyEnabled: Boolean
}

object BuildTypeDebug : BuildType {
    override val isMinifyEnabled = false
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = true
}