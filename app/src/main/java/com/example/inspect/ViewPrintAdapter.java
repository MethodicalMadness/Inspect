package com.example.inspect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;
import android.view.View;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ViewPrintAdapter extends PrintDocumentAdapter {

    private PrintedPdfDocument printedPdfDocument;
    private Context context;
    private ArrayList<View> viewsList;

    public ViewPrintAdapter(Context context, ArrayList<View> viewsList) {
        this.context = context;
        this.viewsList = viewsList;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes,
                         CancellationSignal cancellationSignal,
                         LayoutResultCallback callback, Bundle extras) {
        printedPdfDocument = new PrintedPdfDocument(context, newAttributes);
        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }
        PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                .Builder("print_output.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(viewsList.size());
        PrintDocumentInfo info = builder.build();
        callback.onLayoutFinished(info, true);
    }

    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination,
                        CancellationSignal cancellationSignal,
                        WriteResultCallback callback) {
        for(int i = 0; i < viewsList.size();i++){
            System.out.println(i + " of " + viewsList.size());
            // Get current View
            View view = viewsList.get(i);
            // Start the page
            PdfDocument.Page page = printedPdfDocument.startPage(i);
            // Create a bitmap and put it a canvas for the view to draw to. Make it the size of the view
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            // create a Rect with the view's dimensions.
            Rect src = new Rect(0, 0, view.getWidth(), view.getHeight());
            // get the page canvas and measure it.
            Canvas pageCanvas = page.getCanvas();
            float pageWidth = pageCanvas.getWidth();
            float pageHeight = pageCanvas.getHeight();
            // how can we fit the Rect src onto this page while maintaining aspect ratio?
            float scale = Math.min(pageWidth / src.width(), pageHeight / src.height());
            float left = pageWidth / 2 - src.width() * scale / 2;
            float top = pageHeight / 2 - src.height() * scale / 2;
            float right = pageWidth / 2 + src.width() * scale / 2;
            float bottom = pageHeight / 2 + src.height() * scale / 2;
            RectF dst = new RectF(left, top, right, bottom);
            //draw page
            pageCanvas.drawBitmap(bitmap, src, dst, null);
            printedPdfDocument.finishPage(page);
        }

        try {
            printedPdfDocument.writeTo(new FileOutputStream(
                    destination.getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            printedPdfDocument.close();
            printedPdfDocument = null;
        }
        // signal the print framework the document is complete
        PageRange[] writtenPages = {PageRange.ALL_PAGES};
        callback.onWriteFinished(writtenPages);
    }
}
