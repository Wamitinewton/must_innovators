/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
import extensions.addRetrofitDependencies
import extensions.implementCoreModules
import dependencies.Dependencies

plugins {
    id("innovators.module.plugin")
}

android {
    namespace = "com.newton.notifications"
}

dependencies {
    addRetrofitDependencies()
    implementation(Dependencies.timber)

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-analytics")

    // Initializer
    implementation(Dependencies.initializer)
    implementCoreModules()
}
