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
package com.newton.core.utils

import android.content.*
import android.content.pm.*
import androidx.core.net.*

/**
 * Generic utility for handling external app navigation and sharing
 */
object PackageHandlers {
    /**
     * Opens the website URL in a browser
     */
    fun navigateToWebsite(
        context: Context,
        url: String
    ) {
        try {
            val websiteIntent = Intent(Intent.ACTION_VIEW, formatUrl(url).toUri())
            websiteIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(websiteIntent)
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Navigates to LinkedIn - tries app first, falls back to browser
     */
    fun navigateToLinkedIn(
        context: Context,
        linkedInUrl: String
    ) {
        try {
            val linkedInAppIntent = Intent(Intent.ACTION_VIEW)
            val formattedUrl = formatLinkedInUrl(linkedInUrl)

            if (isAppInstalled(context, "com.linkedin.android")) {
                linkedInAppIntent.setPackage("com.linkedin.android")
                linkedInAppIntent.data = formattedUrl.toUri()
            } else {
                linkedInAppIntent.data = formattedUrl.toUri()
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
    fun navigateToGithub(
        context: Context,
        githubUrl: String
    ) {
        try {
            val githubAppIntent = Intent(Intent.ACTION_VIEW)
            val formattedUrl = formatGithubUrl(githubUrl)

            if (isAppInstalled(context, "com.github.android")) {
                githubAppIntent.setPackage("com.github.android")
                githubAppIntent.data = formattedUrl.toUri()
            } else {
                githubAppIntent.data = formattedUrl.toUri()
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
    fun navigateToTwitter(
        context: Context,
        twitterUrl: String
    ) {
        try {
            val twitterAppIntent = Intent(Intent.ACTION_VIEW)
            val formattedUrl = formatTwitterUrl(twitterUrl)

            if (isAppInstalled(context, "com.twitter.android")) {
                twitterAppIntent.setPackage("com.twitter.android")
                twitterAppIntent.data = formattedUrl.toUri()
            } else {
                twitterAppIntent.data = formattedUrl.toUri()
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
    fun contactViaEmail(
        context: Context,
        email: String
    ) {
        try {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:$email".toUri()
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
     * Generic share function to share content via any sharing-capable app
     *
     * @param context The Android context
     * @param shareText The text content to share
     * @param shareTitle Optional title for the share dialog
     */
    fun shareContent(
        context: Context,
        shareText: String,
        shareTitle: String = "Share via..."
    ) {
        try {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }

            context.startActivity(Intent.createChooser(shareIntent, shareTitle))
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Helper function to check if an app is installed
     */
    private fun isAppInstalled(
        context: Context,
        packageName: String
    ): Boolean {
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
}
