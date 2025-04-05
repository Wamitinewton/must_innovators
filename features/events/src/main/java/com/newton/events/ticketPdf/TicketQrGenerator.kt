package com.newton.events.ticketPdf

import android.graphics.*
import com.google.zxing.*
import com.google.zxing.qrcode.*
import com.google.zxing.qrcode.decoder.*
import com.newton.core.data.response.admin.*
import com.newton.core.utils.*

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
