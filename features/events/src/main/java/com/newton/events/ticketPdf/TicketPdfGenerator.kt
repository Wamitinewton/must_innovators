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

import android.content.*
import android.graphics.*
import android.graphics.pdf.*
import android.graphics.pdf.PdfDocument.PageInfo
import com.newton.core.utils.*
import com.newton.network.data.dto.admin.*
import java.io.*

/**
 * Utility for generating PDFs from event tickets with QR code support
 */

class TicketPdfGenerator(private val context: Context) {
    private val pageWidth = 595 // A4 width in points
    private val pageHeight = 842 // A4 height in points
    private val margin = 40f
    private val qrSize = 200

    private val titlePaint =
        Paint().apply {
            textSize = 24f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            color = android.graphics.Color.BLACK
        }

    private val subtitlePaint =
        Paint().apply {
            textSize = 18f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            color = android.graphics.Color.BLACK
        }

    private val bodyPaint =
        Paint().apply {
            textSize = 14f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            color = android.graphics.Color.BLACK
        }

    private val labelPaint =
        Paint().apply {
            textSize = 14f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            color = android.graphics.Color.BLACK
        }

    fun generatePdf(ticket: RegistrationResponse): File {
        val pdfDocument = PdfDocument()
        val pageInfo = PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        var yPosition = margin + 50f

        canvas.drawText(ticket.eventName, margin, yPosition, titlePaint)
        yPosition += 40f

        val qrBitmap = TicketQrGenerator().generateQrCode(ticket, qrSize)
        canvas.drawBitmap(
            qrBitmap,
            (pageWidth - qrSize) / 2f,
            yPosition,
            Paint()
        )
        yPosition += qrSize + 40f

        drawTicketDetails(canvas, ticket, yPosition)
        pdfDocument.finishPage(page)

        val ticketsDir =
            File(context.getExternalFilesDir(null), "tickets").apply {
                if (!exists()) mkdirs()
            }
        val pdfFile = File(ticketsDir, "ticket_${ticket.ticketNumber}.pdf")
        FileOutputStream(pdfFile).use { outputStream ->
            pdfDocument.writeTo(outputStream)
        }
        pdfDocument.close()
        qrBitmap.recycle()

        return pdfFile
    }

    private fun drawTicketDetails(
        canvas: Canvas,
        ticket: RegistrationResponse,
        startY: Float
    ) {
        var yPosition = startY

        // Draw ticket information in sections
        drawSection(canvas, "Event Details", yPosition) { y ->
            drawLabeledText("Date", formatDateTime(ticket.eventDate), y, canvas)
            drawLabeledText("Location", ticket.eventLocation, y + 20, canvas)
            y + 60f
        }.also { yPosition = it + 20f }

        drawSection(canvas, "Ticket Information", yPosition) { y ->
            drawLabeledText("Ticket Number", ticket.ticketNumber, y, canvas)
            drawLabeledText(
                "Registration Date",
                formatDateTime(ticket.registrationTimestamp),
                y + 20,
                canvas
            )
            drawLabeledText("Status", if (ticket.isUsed) "USED" else "VALID", y + 40, canvas)
            y + 60f
        }.also { yPosition = it + 20f }

        // Draw description
        drawSection(canvas, "Event Description", yPosition) { y ->
            val textLines = wrapText(ticket.eventDescription, bodyPaint, pageWidth - 2 * margin)
            textLines.forEachIndexed { index, line ->
                canvas.drawText(line, margin, y + (index * 20), bodyPaint)
            }
            y + (textLines.size * 20f)
        }
    }

    private fun drawSection(
        canvas: Canvas,
        title: String,
        startY: Float,
        content: (Float) -> Float
    ): Float {
        canvas.drawText(title, margin, startY, subtitlePaint)
        return content(startY + 30f)
    }

    private fun drawLabeledText(
        label: String,
        value: String,
        y: Float,
        canvas: Canvas
    ) {
        canvas.drawText("$label:", margin, y, labelPaint)
        canvas.drawText(value, margin + 120f, y, bodyPaint)
    }

    private fun wrapText(
        text: String,
        paint: Paint,
        maxWidth: Float
    ): List<String> {
        val words = text.split(" ")
        val lines = mutableListOf<String>()
        var currentLine = StringBuilder()

        for (word in words) {
            val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
            val measureWidth = paint.measureText(testLine)

            if (measureWidth <= maxWidth - 2 * margin) {
                currentLine.append(if (currentLine.isEmpty()) word else " $word")
            } else {
                lines.add(currentLine.toString())
                currentLine = StringBuilder(word)
            }
        }

        if (currentLine.isNotEmpty()) {
            lines.add(currentLine.toString())
        }
        return lines
    }
}
