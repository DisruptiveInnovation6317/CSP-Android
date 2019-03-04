package com.frc63175985.csp;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * A class with a static method to generate a QR Code from
 * a {@link String}
 * @see com.google.zxing.qrcode.QRCodeWriter
 */
public class QrHelper {
    /**
     * Generate a QR code from a {@link String}
     * @param message the {@link String} to be encoded
     * @return A 800x800 image of the qr code
     */
    public static Bitmap qrFromString(@NonNull String message) {
        if (message.isEmpty()) {
            return null;
        }

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        try {
            int width = 800, height = 800;
            BitMatrix matrix = qrCodeWriter.encode(message, BarcodeFormat.QR_CODE, width, height);
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static AlertDialog qrDialogFromString(@NonNull Context context, @NonNull String title, @NonNull String message) {
        Bitmap qrCode = QrHelper.qrFromString(message);

        if (qrCode == null) {
            Toast.makeText(context, "Error generating QR code", Toast.LENGTH_LONG).show();
            return null;
        }

        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(qrCode);
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(imageView)
                .create();
    }
}
