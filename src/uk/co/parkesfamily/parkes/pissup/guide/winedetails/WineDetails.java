package uk.co.parkesfamily.parkes.pissup.guide.winedetails;

import uk.co.parkesfamily.GPCursorHelper;
import uk.co.parkesfamily.parkes.pissup.guide.R;
import uk.co.parkesfamily.parkes.pissup.guide.contentproviders.WineContentProvider;
import uk.co.parkesfamily.parkes.pissup.guide.contentproviders.WineratingsContentProvider;
import uk.co.parkesfamily.parkes.pissup.guide.ratings.WineRatingActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class WineDetails extends FragmentActivity
	implements LoaderCallbacks<Cursor>
{
	private static final int	LOADER_WINE	= 0;
	private static final int	LOADER_RATINGS = 1;
	
	private final String[]	_lstWineCols, _lstRatingCols;
	private final String	_strWineQuery, _strRatingQuery;
	
	private long			_lWineID, _lRatingID; 
	
	private TextView		_edtName, _edtBasedOn, _edtCountry, _edtArea, 
							_edtAlcohol, _edtProducer, _edtPrice, _edtTastingNotes;
	private	ImageView		_imgWine;
	private RatingBar		_ratingBar;
	
	public static class ExtraFields
	{
		public static final String WINE_ID = "WineID";
	}
	
	public static Intent createIntent(Context context, final long lWineID)
	{
		Intent intResult = new Intent(context, WineDetails.class);
		intResult.putExtra(ExtraFields.WINE_ID, lWineID);
		
		return intResult;
	}
	
	

	public WineDetails()
	{
		super();
		
		_lstWineCols = new String[]{
				WineContentProvider._ID,
				WineContentProvider.IMAGE,
				WineContentProvider.NAME,
				WineContentProvider.YEAR,
				WineContentProvider.ALCOHOL,
				WineContentProvider.COUNTRY,
				WineContentProvider.AREA,
				WineContentProvider.PRODUCER,
				WineContentProvider.PRICE,
				WineContentProvider.TASTINGNOTES
		};
		_strWineQuery = WineContentProvider._ID + " = ?";
		
		_lstRatingCols = new String[]{
				WineratingsContentProvider._ID,
				WineratingsContentProvider.RATING
		};
		_strRatingQuery = WineratingsContentProvider.WINE + " = ?";
	}



	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wine_details);
		
		_imgWine = (ImageView) findViewById(R.id.imageWine);
		_edtName = (TextView) findViewById(R.id.edtName);
		_edtBasedOn = (TextView) findViewById(R.id.edtBasedOn);
		_ratingBar = (RatingBar) findViewById(R.id.RatingBar);
		_edtCountry = (TextView) findViewById(R.id.edtCountry);
		_edtArea = (TextView) findViewById(R.id.edtArea); 
		_edtAlcohol = (TextView) findViewById(R.id.edtAlcohol);
		_edtProducer = (TextView) findViewById(R.id.edtProducer);
		_edtPrice = (TextView) findViewById(R.id.edtPrice);
		_edtTastingNotes = (TextView) findViewById(R.id.edtTastingNotes);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			_lWineID = extras.getLong(ExtraFields.WINE_ID);
			
			getSupportLoaderManager().initLoader(LOADER_WINE, null, this);
			getSupportLoaderManager().initLoader(LOADER_RATINGS, null, this);
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int identifier, Bundle args)
	{
		CursorLoader loader = null;
		
		if (identifier == LOADER_WINE)
		{
			String[] lstArgs = new String[]{Long.toString(_lWineID)};
			loader = new CursorLoader(this, WineContentProvider.CONTENT_URI, 
					_lstWineCols, _strWineQuery, lstArgs, null);
		}
		else if (identifier == LOADER_RATINGS)
		{
			String[] lstArgs = new String[]{Long.toString(_lWineID)};
			loader = new CursorLoader(this, WineratingsContentProvider.CONTENT_URI, 
					_lstRatingCols, _strRatingQuery, lstArgs, null);
		}
		
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
	{
		if (loader.getId() == LOADER_WINE)
			populateData(cursor);
		else if (loader.getId() == LOADER_RATINGS)
			populateRatings(cursor);
	}

	private void populateRatings(Cursor cursor)
	{
		String strComment;
		float iRating = 0;
		
		if (GPCursorHelper.hasRecords(cursor))
		{
			final int lCount = cursor.getCount();
			strComment = "Based on " + Integer.toString(lCount) + " reviews.";
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast())
			{
				_lRatingID = GPCursorHelper.getLong(cursor, WineratingsContentProvider._ID);
				iRating = iRating + GPCursorHelper.getInt(cursor, WineratingsContentProvider.RATING);
				
				cursor.moveToNext();
			}
			iRating = iRating / lCount;
		} 
		else
		{
			strComment = "There are no reviews yet.";
		}
		
		_edtBasedOn.setText(strComment);
		_ratingBar.setRating(iRating);
	}

	public void ratingClick(View view)
	{
		Intent intRating = WineRatingActivity.createIntent(this, _lRatingID);
		startActivity(intRating);
	}


	@Override
	public void onLoaderReset(Loader<Cursor> loader)
	{
		populateData(null);
	}

	private void populateData(final Cursor cursor)
	{
		String strName = "", strCountry = "", strArea = "", strAlcohol = "", 
				strProducer = "", strPrice = "", strTastingNotes = "";
		Bitmap bmpImage = null;
		
		if (GPCursorHelper.hasRecords(cursor))
		{
			cursor.moveToFirst();
			
			strName = GPCursorHelper.getString(cursor, WineContentProvider.NAME);
			bmpImage = GPCursorHelper.GetBitmap(cursor, WineContentProvider.IMAGE);
			
			strCountry = GPCursorHelper.getString(cursor, WineContentProvider.COUNTRY);
			strArea = GPCursorHelper.getString(cursor, WineContentProvider.AREA);
			strAlcohol = GPCursorHelper.getString(cursor, WineContentProvider.ALCOHOL) + "%"; 
			strProducer = GPCursorHelper.getString(cursor, WineContentProvider.PRODUCER);
			strPrice = "£" + GPCursorHelper.getString(cursor, WineContentProvider.PRICE);
			strTastingNotes = GPCursorHelper.getString(cursor, WineContentProvider.TASTINGNOTES);
		}
		
		_edtName.setText(strName);
		_imgWine.setImageBitmap(bmpImage);
		
		_edtCountry.setText(strCountry);
		_edtArea.setText(strArea); 
		_edtAlcohol.setText(strAlcohol);
		_edtProducer.setText(strProducer);
		_edtPrice.setText(strPrice);
		_edtTastingNotes.setText(strTastingNotes); 
	}
}
