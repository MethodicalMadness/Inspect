package com.binarygiant.inspect;

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

/**
 * Used to preview and print to pdf.
 * Makes a Bitmap of each TemplatePage's corresponding view and renders it in a pdf.
 */
public class ViewPrintAdapter extends PrintDocumentAdapter {

    private PrintedPdfDocument printedPdfDocument;
    private Context context;
    private ArrayList<View> viewsList;

    /**
     * Constructor. Requires a list of the views. Each view is a page.
     * @param context
     * @param viewsList
     */
    public ViewPrintAdapter(Context context, ArrayList<View> viewsList) {
        this.context = context;
        this.viewsList = viewsList;
        LogManager.reportStatus(context, "VIEWPRINTADAPTER", "Constructor ViewPrintAdapter");
    }

    /**
     * Prepares the pdf documents layout and builds it so it can be written to.
     * @param oldAttributes
     * @param newAttributes
     * @param cancellationSignal
     * @param callback
     * @param extras
     */
    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes,
                         CancellationSignal cancellationSignal,
                         LayoutResultCallback callback, Bundle extras) {
        printedPdfDocument = new PrintedPdfDocument(context, newAttributes);
        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            Context context = App.getContext();
            LogManager.reportStatus(context, "VIEWPRINTADAPTER", "onLayout CANCELLED");
            return;
        }
        PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                .Builder("print_output.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(viewsList.size());
        PrintDocumentInfo info = builder.build();
        callback.onLayoutFinished(info, true);
        Context context = App.getContext();
        LogManager.reportStatus(context, "VIEWPRINTADAPTER", "onLayout");
    }

    /**
     * Renders a bitmap of each view and renders it on its corresponding page on the pdf.
     * @param pages
     * @param destination
     * @param cancellationSignal
     * @param callback
     */
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
        // write to the file
        try {
            printedPdfDocument.writeTo(new FileOutputStream(
                    destination.getFileDescriptor()));
        } catch (IOException e) {
            LogManager.wtf("VIEWPRINTADAPTER", "Constructor", e);
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            printedPdfDocument.close();
            printedPdfDocument = null;
        }
        // signal the print framework the document is complete
        PageRange[] writtenPages = {PageRange.ALL_PAGES};
        callback.onWriteFinished(writtenPages);
        Context context = App.getContext();
        LogManager.reportStatus(context, "VIEWPRINTADAPTER", "onWrite");
    }
}
