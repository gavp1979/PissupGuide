package uk.co.parkesfamily.parkes.pissup.guide.ratings;

import uk.co.parkesfamily.GPCursorHelper;
import uk.co.parkesfamily.parkes.pissup.guide.R;
import uk.co.parkesfamily.parkes.pissup.guide.contentproviders.WineratingsContentProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.EditText;
import android.widget.RatingBar;

public class WineRatingActivity extends FragmentActivity
	implements LoaderCallbacks<Cursor>
{
	private static final long NO_ID = -1;
	private static final int LOADER_RATINGS = 0;
	
	private final String[]	_lstRatingCols;
	
	private long _lRatingID = NO_ID;
	
	private RatingBar	_ratingBar;
	private EditText	_edtName, _edtDate, _edtDescription;
	
	public static class ExtraFields
	{
		public static String RATING_ID	= "RatingID";
	}

	public static Intent createIntent(Context context, final long lRatingID)
	{
		Intent intResult = new Intent(context, WineRatingActivity.class);
		intResult.putExtra(ExtraFields.RATING_ID, lRatingID);
		
		return intResult;
	}
	
	public WineRatingActivity()
	{
		super();

		_lstRatingCols = new String[]{
				WineratingsContentProvider.RATER,
				WineratingsContentProvider.RATING,
				WineratingsContentProvider.DESCRIPTION,
				WineratingsContentProvider.WINE
		};
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wine_rating_activity);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			_lRatingID = extras.getLong(ExtraFields.RATING_ID);
		}
		
		_ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		_edtName = (EditText) findViewById(R.id.edtRatedBy);
		_edtDate = (EditText) findViewById(R.id.edtDate);
		_edtDescription = (EditText) findViewById(R.id.edtDescription);
		
		getSupportLoaderManager().initLoader(LOADER_RATINGS, null, this);
	}
	
	private void populateRatingData(Cursor cursor)
	{
		long lRating = 0;
		String strName, strDescription, strDate;
		strName = strDescription = strDate = "";
		
		if (GPCursorHelper.hasRecords(cursor))
		{
			cursor.moveToFirst();
			
			lRating = GPCursorHelper.getLong(cursor, WineratingsContentProvider.RATING);
			strName = GPCursorHelper.getString(cursor, WineratingsContentProvider.RATER);
			strDate = "Needs Implementing";
			strDescription = GPCursorHelper.getString(cursor, WineratingsContentProvider.DESCRIPTION);
		}
		
		_ratingBar.setRating(lRating);
		_edtName.setText(strName);
		_edtDate.setText(strDate);
		_edtDescription.setText(strDescription);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int identifier, Bundle args)
	{
		CursorLoader loader = null;
		
		if (identifier == LOADER_RATINGS)
		{
			String strQuery = null;
			String[] lstArgs = null;
			
			if (_lRatingID != NO_ID)
			{
				strQuery = WineratingsContentProvider._ID + " = ?";
				lstArgs = new String[] {Long.toString(_lRatingID)};
				
				loader = new CursorLoader(this, WineratingsContentProvider.CONTENT_URI,
						_lstRatingCols, strQuery, lstArgs, null);
			}
		}
		
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
	{
		if (loader.getId() == LOADER_RATINGS)
		{
			populateRatingData(cursor);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader)
	{
		if (loader.getId() == LOADER_RATINGS)
		{
			populateRatingData(null);
		}
	}

}
