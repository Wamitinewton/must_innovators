package com.newton.core.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.newton.core.domain.models.home_models.PartnersData

object PackageHandlers {

    /**
     * Opens the website URL in a browser
     */
    fun navigateToWebsite(context: Context, url: String) {
        try {
            val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(formatUrl(url)))
            websiteIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(websiteIntent)
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Navigates to LinkedIn - tries app first, falls back to browser
     */
    fun navigateToLinkedIn(context: Context, linkedInUrl: String) {
        try {
            val linkedInAppIntent = Intent(Intent.ACTION_VIEW)
            val formattedUrl = formatLinkedInUrl(linkedInUrl)

            if (isAppInstalled(context, "com.linkedin.android")) {
                linkedInAppIntent.setPackage("com.linkedin.android")
                linkedInAppIntent.data = Uri.parse(formattedUrl)
            } else {
                linkedInAppIntent.data = Uri.parse(formattedUrl)
            }

            linkedInAppIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(linkedInAppIntent)
        } catch (e: ActivityNotFoundException) {
            navigateToWebsite(context, linkedInUrl)
        }
    }

    /**
     * Navigates to Github - tries app first, falls back to browser
     */
    fun navigateToGithub(context: Context, githubUrl: String) {
        try {
            val githubAppIntent = Intent(Intent.ACTION_VIEW)
            val formattedUrl = formatGithubUrl(githubUrl)

            if (isAppInstalled(context, "com.github.android")) {
                githubAppIntent.setPackage("com.github.android")
                githubAppIntent.data = Uri.parse(formattedUrl)
            } else {
                githubAppIntent.data = Uri.parse(formattedUrl)
            }

            githubAppIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(githubAppIntent)
        } catch (e: ActivityNotFoundException) {
            navigateToWebsite(context, githubUrl)
        }
    }

    /**
     * Navigates to Twitter/X - tries app first, falls back to browser
     */
    fun navigateToTwitter(context: Context, twitterUrl: String) {
        try {
            val twitterAppIntent = Intent(Intent.ACTION_VIEW)
            val formattedUrl = formatTwitterUrl(twitterUrl)

            if (isAppInstalled(context, "com.twitter.android")) {
                twitterAppIntent.setPackage("com.twitter.android")
                twitterAppIntent.data = Uri.parse(formattedUrl)
            } else {
                twitterAppIntent.data = Uri.parse(formattedUrl)
            }

            twitterAppIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(twitterAppIntent)
        } catch (e: ActivityNotFoundException) {
            navigateToWebsite(context, twitterUrl)
        }
    }

    /**
     * Opens email app with pre-filled email
     */
    fun contactViaEmail(context: Context, email: String) {
        try {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
            }

            if (isAppInstalled(context, "com.google.android.gm")) {
                emailIntent.setPackage("com.google.android.gm")
            }

            context.startActivity(Intent.createChooser(emailIntent, "Send email using..."))
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Share partner information via any sharing-capable app
     */
    fun sharePartner(context: Context, partner: PartnersData) {
        try {
            val shareText = createShareText(partner)

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }

            context.startActivity(Intent.createChooser(shareIntent, "Share Partner via..."))
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Helper function to check if an app is installed
     */
    private fun isAppInstalled(context: Context, packageName: String): Boolean {
        return try {
            context.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    /**
     * Ensures URL has http/https prefix
     */
    private fun formatUrl(url: String): String {
        return if (!url.startsWith("http://") && !url.startsWith("https://")) {
            "https://$url"
        } else {
            url
        }
    }

    /**
     * Format LinkedIn URL for app or web
     */
    private fun formatLinkedInUrl(url: String): String {
        val formattedUrl = formatUrl(url)
        if (formattedUrl.contains("linkedin.com/in/")) {
            val username = formattedUrl.substringAfter("linkedin.com/in/").substringBefore("/")
            return "linkedin://profile/$username"
        }
        return formattedUrl
    }

    /**
     * Format Twitter URL for app or web
     */
    private fun formatTwitterUrl(url: String): String {
        val formattedUrl = formatUrl(url)
        if (formattedUrl.contains("twitter.com/") || formattedUrl.contains("x.com/")) {
            val username = formattedUrl
                .replace("https://twitter.com/", "")
                .replace("http://twitter.com/", "")
                .replace("https://x.com/", "")
                .replace("http://x.com/", "")
                .substringBefore("/")

            return "twitter://user?screen_name=$username"
        }
        return formattedUrl
    }

    private fun formatGithubUrl(githubUrl: String): String {
        return if (githubUrl.startsWith("http://") || githubUrl.startsWith("https://")) {
            githubUrl
        } else {
            "https://github.com/$githubUrl"
        }
    }


    /**
     * Creates beautifully formatted text for sharing
     */
    private fun createShareText(partner: PartnersData): String {
        val divider = "\n" + "✨".repeat(15) + "\n"

        return """
            |🌟 Amazing Partnership Opportunity! 🌟
            |
            |I'd like to share this wonderful partner with you:
            |
            |🏢 ${partner.name} 
            |${if (partner.status.isNotEmpty()) "📊 Status: ${partner.status}\n" else ""}${if (partner.type.isNotEmpty()) "🔍 Type: ${partner.type}\n" else ""}
            |$divider
            |📝 About:
            |${partner.description.take(150)}${if (partner.description.length > 150) "..." else ""}
            |
            |🌐 Website: ${partner.web_url}
            |${if (partner.linked_in.isNotEmpty()) "📱 LinkedIn: ${partner.linked_in}\n" else ""}${if (partner.twitter.isNotEmpty()) "🐦 Twitter: ${partner.twitter}\n" else ""}
            |
            |✉️ Contact: ${partner.contact_email}
            |$divider
            |
            |This partnership has been ${if (partner.ongoing) "ongoing since ${partner.start_date}" else "active from ${partner.start_date} to ${partner.end_date ?: "present"}"}
            |
            |Learn more about this partnership opportunity in our app!
        """.trimMargin()
    }
}