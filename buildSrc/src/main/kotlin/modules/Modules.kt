package modules

object Modules {
    // Core modules
    const val app = ":innovators"
    const val commonUi = ":commonUi"
    const val core = ":core"
    const val database = ":database"
    const val domain = ":domain"
    const val navigation = ":navigation"
    const val network = ":network"
    const val notifications = ":notifications"
    const val shared = ":shared"
    const val sharedPrefs = ":sharedPrefs"

    // Feature modules
    object Features {
        const val aboutUs = ":features:aboutUs"
        const val account = ":features:account"
        const val admin = ":features:admin"
        const val auth = ":features:auth"
        const val blogs = ":features:blogs"
        const val communities = ":features:communities"
        const val events = ":features:events"
        const val feedback = ":features:feedback"
        const val home = ":features:home"
        const val partners = ":features:partners"
        const val settings = ":features:settings"
        const val testimonials = ":features:testimonials"
        const val alumni = ":features:alumni"
    }
}