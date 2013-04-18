package com.stevenbyle.androiddensityindependence;

import java.text.DecimalFormat;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Simple demo of Android's dimension units and how they work depending on
 * screen density.
 * 
 * @author Steven Byle
 */
public class MainActivity extends Activity {

	// Constants
	private static final double MM_PER_INCH = 25.4;
	private static final double PT_PER_INCH = 72;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get data about the device's display
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		float densityPxPerDp = displayMetrics.density;
		float densityPxPerSp = displayMetrics.scaledDensity;
		float densityBucketDpi = displayMetrics.densityDpi;
		float xdpi = displayMetrics.xdpi;
		float ydpi = displayMetrics.ydpi;

		// Get data about the drawable displayed
		Drawable drawable = getResources().getDrawable(R.drawable.android_logo);
		int imageWidthInPixels = drawable.getIntrinsicWidth();
		int imageHeightInPixels = drawable.getIntrinsicHeight();

		// Get dimensions used on each image
		double numDp = extractDouble(getResources().getString(R.dimen.dimen_in_dp));
		double numIn = extractDouble(getResources().getString(R.dimen.dimen_in_inches));
		double numMm = extractDouble(getResources().getString(R.dimen.dimen_in_mm));
		double numPt = extractDouble(getResources().getString(R.dimen.dimen_in_pt));

		// Setup formatted text for display
		DecimalFormat df = new DecimalFormat("#.#");
		String xdpiFormatted = df.format(xdpi);
		String ydpiFormatted = df.format(ydpi);
		String numDpFormatted = df.format(numDp);
		String numInFormatted = df.format(numIn);
		String numMmFormatted = df.format(numMm);
		String numPtFormatted = df.format(numPt);
		String mmPerInchFormatted = df.format(MM_PER_INCH);
		String ptPerInchFormatted = df.format(PT_PER_INCH);

		// Get references to all the views we need
		TextView densityInfoText = (TextView) findViewById(R.id.text_density_info);
		TextView image1Text = (TextView) findViewById(R.id.text_image1);
		TextView image2Text = (TextView) findViewById(R.id.text_image2);
		TextView image3Text = (TextView) findViewById(R.id.text_image3);
		TextView image4Text = (TextView) findViewById(R.id.text_image4);
		TextView image5Text = (TextView) findViewById(R.id.text_image5);

		// Display info about the device's screen density
		densityInfoText.setText("xdpi: " + xdpi + " | ydpi: " + ydpi
				+ "\nDensity Bucket: " + densityBucketDpi + "dpi"
				+ "\nDensity Scaler: " + densityPxPerDp + "px/dp"
				+ "\nDensity Font Scaler: " + densityPxPerSp + "px/sp");

		// Wrap content
		image1Text.setText("Resource size: " + imageWidthInPixels + "px x "  + imageHeightInPixels + "px"
				+ "\n\nWidth: wrap_content = " + imageWidthInPixels + "px"
				+ "\nHeight: wrap_content = " + imageHeightInPixels + "px");

		// Density independent pixels
		image2Text.setText(imageWidthInPixels + "px / " + densityPxPerDp + "px/dp = " + df.format(imageWidthInPixels / densityPxPerDp) + "dp"
				+ "\n\nWidth: " + numDpFormatted + "dp x " + densityPxPerDp + "px/dp = " + numDp * densityPxPerDp  + "px"
				+ "\nHeight: " + numDpFormatted + "dp x " + densityPxPerDp + "px/dp = " + numDp * densityPxPerDp  + "px");

		// Inches
		image3Text.setText(imageWidthInPixels + "px / " + densityBucketDpi + "dpi = " + imageWidthInPixels / densityBucketDpi + "in"
				+ "\n\nWidth: " + numInFormatted + "in x " + xdpiFormatted + "xdpi = " + df.format(xdpi * numIn)  + "px"
				+ "\nHeight: " + numInFormatted + "in x " + ydpiFormatted + "ydpi = " + df.format(ydpi * numIn)  + "px");

		// Millimeters
		image4Text.setText(imageWidthInPixels + "px / " + densityBucketDpi + "dpi x " + mmPerInchFormatted + "mm/in = " + imageWidthInPixels / densityBucketDpi * MM_PER_INCH + "mm"
				+ "\n\nWidth: " + numMmFormatted + "mm / " + mmPerInchFormatted + "mm/in x " + xdpiFormatted + "xdpi = " + df.format(xdpi * numMm / MM_PER_INCH)  + "px"
				+ "\nHeight: " + numMmFormatted + "mm / " + mmPerInchFormatted + "mm/in x " + ydpiFormatted + "ydpi = " + df.format(ydpi * numMm / MM_PER_INCH)  + "px");

		// Points
		image5Text.setText(imageWidthInPixels + "px / " + densityBucketDpi + "dpi x " + ptPerInchFormatted + "pt/in = " + imageWidthInPixels / densityBucketDpi * PT_PER_INCH + "pt"
				+ "\n\nWidth: " + numPtFormatted + "pt / " + ptPerInchFormatted + "pt/in x " + xdpiFormatted + "xdpi = " + df.format(xdpi * numPt / PT_PER_INCH)  + "px"
				+ "\nHeight: " + numPtFormatted + "pt / " + ptPerInchFormatted + "pt/in x " + ydpiFormatted + "ydpi = " + df.format(ydpi * numPt / PT_PER_INCH)  + "px");
	}

	/**
	 * Pulls a double out of a string with other characters in it. Reads all
	 * digits, decimals, and negative signs as a single digit, not a robust
	 * method, cannot handle more than a single double.
	 * 
	 * @param doubleString
	 *            string containing the double to extract
	 * @return the double found in the string provided
	 */
	public static double extractDouble(String doubleString) {
		String validDoubleString = "";
		for (int i = 0; i < doubleString.length(); i++) {
			char c = doubleString.charAt(i);
			if (Character.isDigit(c) || c == '-' || c == '.') {
				validDoubleString += c;
			}
		}

		return Double.parseDouble(validDoubleString);
	}

}
