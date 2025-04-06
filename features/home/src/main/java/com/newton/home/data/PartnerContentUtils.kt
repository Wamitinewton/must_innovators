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
package com.newton.home.data

import android.content.Context
import com.newton.core.utils.PackageHandlers
import com.newton.network.domain.models.homeModels.*

/**
 * Extension class that adapts the generic PackageHandlers for Partner-specific functionality
 */
object PartnerContentUtils {
    /**
     * Share partner information via any sharing-capable app
     *
     * @param context The Android context
     * @param partner The partner data to share
     */
    fun sharePartner(context: Context, partner: PartnersData) {
        val shareText = createPartnerShareText(partner)
        PackageHandlers.shareContent(context, shareText, "Share Partner via...")
    }

    /**
     * Creates beautifully formatted text for sharing partners
     */
    private fun createPartnerShareText(partner: PartnersData): String {
        val divider = "\n" + "✨".repeat(15) + "\n"

        return buildString {
            append("🌟 Amazing Partnership Opportunity! 🌟\n\n")
            append("I'd like to share this wonderful partner with you:\n\n")
            append("🏢 ${partner.name}\n")

            if (partner.status.isNotEmpty()) {
                append("📊 Status: ${partner.status}\n")
            }

            if (partner.type.isNotEmpty()) {
                append("🔍 Type: ${partner.type}\n")
            }

            append(divider)
            append("📝 About:\n")
            append(partner.description.take(150))
            if (partner.description.length > 150) append("...")

            append("\n\n🌐 Website: ${partner.web_url}\n")

            if (partner.linked_in.isNotEmpty()) {
                append("📱 LinkedIn: ${partner.linked_in}\n")
            }

            if (partner.twitter.isNotEmpty()) {
                append("🐦 Twitter: ${partner.twitter}\n")
            }

            append("\n✉️ Contact: ${partner.contact_email}\n")
            append(divider)

            append("\nThis partnership has been ")
            if (partner.ongoing) {
                append("ongoing since ${partner.start_date}")
            } else {
                append("active from ${partner.start_date} to ${partner.end_date ?: "present"}")
            }

            append("\n\nLearn more about this partnership opportunity in our app!")
        }
    }
}
