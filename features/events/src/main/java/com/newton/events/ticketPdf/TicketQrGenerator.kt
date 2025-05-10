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
package com.newton.events.ticketPdf

import android.graphics.*
import com.google.zxing.*
import com.google.zxing.qrcode.*
import com.google.zxing.qrcode.decoder.*
import com.newton.core.utils.*
import com.newton.network.data.dto.admin.*

class TicketQrGenerator {
    fun generateQrCode(
        ticket: RegistrationResponse,
        size: Int
    ): Bitmap {
        val qrContent =
            buildString {
                append("Event: ${ticket.eventName}\n")
                append("Ticket: ${ticket.ticketNumber}\n")
                append("Date: ${formatDateTime(ticket.eventDate)}")
            }

        val hints =
            mapOf(
                EncodeHintType.MARGIN to 1,
                EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.H
            )

        val bitMatrix =
            QRCodeWriter().encode(
                qrContent,
                BarcodeFormat.QR_CODE,
                size,
                size,
                hints
            )

        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }

        return bitmap
    }
}
