package com.example.guzman.app1;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.example.guzman.app1.LogUtils.LOGE;
import static com.example.guzman.app1.PermissionsActivity.PERMISSION_REQUEST_CODE;
import static com.example.guzman.app1.PermissionsChecker.REQUIRED_PERMISSION;

public class Pdf extends AppCompatActivity {
    String mail, roomtype, roomprice, arrive, depart;

    Context mContext;
    PermissionsChecker checker;

    Button btnPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        mContext = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("email")) {
            mail = getIntent().getStringExtra("email");
        }if (getIntent().hasExtra("price")) {
            roomprice = getIntent().getStringExtra("price");
        }if (getIntent().hasExtra("arrival")) {
            arrive = getIntent().getStringExtra("arrival");
        }if (getIntent().hasExtra("departure")) {
            depart = getIntent().getStringExtra("departure");
        }if (getIntent().hasExtra("room")) {
            roomtype = getIntent().getStringExtra("room");
        }else {
            TastyToast.makeText(Pdf.this, "error no extras", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
        }

        checker = new PermissionsChecker(this);

        createPdf(FileUtils.getAppPath(mContext) + "Receipt.pdf");

        btnPrint = (Button) findViewById(R.id.pdf_print);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checker.lacksPermissions(REQUIRED_PERMISSION)) {

                    PermissionsActivity.startActivityForResult(Pdf.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);
                } else {
                    createPdf(FileUtils.getAppPath(mContext) + "Receipt.pdf");
                }
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (checker.lacksPermissions(REQUIRED_PERMISSION)) {
//                    PermissionsActivity.startActivityForResult(Pdf.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);
//                } else {
//                    createPdf(FileUtils.getAppPath(mContext) + "123.pdf");
//                }
//            }
//        });
    }

    public void createPdf(String dest) {

        if (new File(dest).exists()) {
            new File(dest).delete();
        }

        try {
            /**
             * Creating Document
             */
            Document document = new Document();

            // Location to save
            PdfWriter.getInstance(document, new FileOutputStream(dest));

            // Open to write
            document.open();

            // Document Settings
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("SirRumpe");
            document.addCreator("SirRumpe");


            /***
             * Variables for further use....
             */
            BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
            float mHeadingFontSize = 26.0f;
            float mValueFontSize = 20.0f;

            /**
             * How to USE FONT....
             */
            BaseFont urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);

            // LINE SEPARATOR
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

            //the company logo is stored in the assets which is read only
            //get the logo and print on the document
            InputStream inputStream = getAssets().open("logo.png");
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image companyLogo = Image.getInstance(stream.toByteArray());
            companyLogo.setAlignment(Element.ALIGN_CENTER);
//            companyLogo.setAbsolutePosition(25,700);
            companyLogo.scalePercent(75);
            document.add(companyLogo);

            // Title Receipt Details...
            // Adding Title....
            Font mOrderDetailsTitleFont = new Font(urName, 36.0f, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderDetailsTitleChunk = new Chunk("Kisii Hotels Application", mOrderDetailsTitleFont);
            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
            mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(mOrderDetailsTitleParagraph);

            // Adding Line Breakable Space....
            document.add(new Paragraph(""));
            // Adding Horizontal Line...
            document.add(new Chunk(lineSeparator));
            // Adding Line Breakable Space....
            document.add(new Paragraph(""));

            // Fields of Order Details...

            // Adding Chunks for Title and value
            Font mOrderIdFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
            Chunk mOrderIdChunk = new Chunk("Receipt No:", mOrderIdFont);
            Paragraph mOrderIdParagraph = new Paragraph(mOrderIdChunk);
            document.add(mOrderIdParagraph);

            Font mOrderIdValueFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderIdValueChunk = new Chunk("HFDJ678", mOrderIdValueFont);
            Paragraph mOrderIdValueParagraph = new Paragraph(mOrderIdValueChunk);
            document.add(mOrderIdValueParagraph);

            // Fields of Order Details...
            Font mOrderDateFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
            Chunk mOrderDateChunk = new Chunk("Email:", mOrderDateFont);
            Paragraph mOrderDateParagraph = new Paragraph(mOrderDateChunk);
            document.add(mOrderDateParagraph);

            Font mOrderDateValueFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderDateValueChunk = new Chunk(mail, mOrderDateValueFont);
            Paragraph mOrderDateValueParagraph = new Paragraph(mOrderDateValueChunk);
            document.add(mOrderDateValueParagraph);

//            document.add(new Paragraph(""));
//            document.add(new Chunk(lineSeparator));
//            document.add(new Paragraph(""));

            // Fields of Order Details...
            Font mOrderAcNameFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
            Chunk mOrderAcNameChunk = new Chunk("Room:", mOrderAcNameFont);
            Paragraph mOrderAcNameParagraph = new Paragraph(mOrderAcNameChunk);
            document.add(mOrderAcNameParagraph);

            Font mOrderAcNameValueFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderAcNameValueChunk = new Chunk(roomtype, mOrderAcNameValueFont);
            Paragraph mOrderAcNameValueParagraph = new Paragraph(mOrderAcNameValueChunk);
            document.add(mOrderAcNameValueParagraph);

//            document.add(new Paragraph(""));
//            document.add(new Chunk(lineSeparator));
//            document.add(new Paragraph(""));

            // Fields of Amount Details...
            Font mOrderAmountFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
            Chunk mOrderAmountChunk = new Chunk("Amount:", mOrderAmountFont);
            Paragraph mOrderAmountParagraph = new Paragraph(mOrderAmountChunk);
            document.add(mOrderAmountParagraph);

            Font mOrderAmountValueFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderAmountValueChunk = new Chunk("$" +roomprice, mOrderAmountValueFont);
            Paragraph mOrderAmountValueParagraph = new Paragraph(mOrderAmountValueChunk);
            document.add(mOrderAmountValueParagraph);

//            document.add(new Paragraph(""));
//            document.add(new Chunk(lineSeparator));
//            document.add(new Paragraph(""));

            // Fields of Arrival Details...
            Font mOrderArrivalFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
            Chunk mOrderArrivalChunk = new Chunk("Arrival:", mOrderArrivalFont);
            Paragraph mOrderArrivalParagraph = new Paragraph(mOrderArrivalChunk);
            document.add(mOrderArrivalParagraph);

            Font mOrderArrivalValueFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderArrivalValueChunk = new Chunk(arrive, mOrderArrivalValueFont);
            Paragraph mOrderArrivalValueParagraph = new Paragraph(mOrderArrivalValueChunk);
            document.add(mOrderArrivalValueParagraph);

//            document.add(new Paragraph(""));
//            document.add(new Chunk(lineSeparator));
//            document.add(new Paragraph(""));

            // Fields of Departure Details...
            Font mOrderDepartureFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
            Chunk mOrderDepartureChunk = new Chunk("Departure:", mOrderDepartureFont);
            Paragraph mOrderDepartureParagraph = new Paragraph(mOrderDepartureChunk);
            document.add(mOrderDepartureParagraph);

            Font mOrderDepartureValueFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderDepartureValueChunk = new Chunk(depart, mOrderDepartureValueFont);
            Paragraph mOrderDepartureValueParagraph = new Paragraph(mOrderDepartureValueChunk);
            document.add(mOrderDepartureValueParagraph);

//            document.add(new Paragraph(""));
//            document.add(new Chunk(lineSeparator));
//            document.add(new Paragraph(""));

            // Fields of Payment Details...
            Font mOrderPaymentFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
            Chunk mOrderPaymentChunk = new Chunk("Payment State:", mOrderPaymentFont);
            Paragraph mOrderPaymentParagraph = new Paragraph(mOrderPaymentChunk);
            document.add(mOrderPaymentParagraph);

            Font mOrderPaymentValueFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderPaymentValueChunk = new Chunk("Approved", mOrderPaymentValueFont);
            Paragraph mOrderPaymentValueParagraph = new Paragraph(mOrderPaymentValueChunk);
            document.add(mOrderPaymentValueParagraph);

//            document.add(new Paragraph(""));
//            document.add(new Chunk(lineSeparator));
//            document.add(new Paragraph(""));

            Font mOrderThanksFont = new Font(urName, mHeadingFontSize, Font.NORMAL, BaseColor.DARK_GRAY);
            Chunk mOrderThanksChunk = new Chunk("Thank You", mOrderThanksFont);
            Paragraph mOrderThanksParagraph = new Paragraph(mOrderThanksChunk);
            mOrderThanksParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(mOrderThanksParagraph);


            document.close();

            Toast.makeText(mContext, "Created... : !!!)", Toast.LENGTH_SHORT).show();

            FileUtils.openFile(mContext, new File(dest));

        } catch (IOException | DocumentException ie) {
            LOGE("createPdf: Error " + ie.getLocalizedMessage());
        } catch (ActivityNotFoundException ae) {
            Toast.makeText(mContext, "No application found to open this file.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PermissionsActivity.PERMISSIONS_GRANTED) {
            Toast.makeText(mContext, "Permission Granted to Save", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Permission not granted, Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
