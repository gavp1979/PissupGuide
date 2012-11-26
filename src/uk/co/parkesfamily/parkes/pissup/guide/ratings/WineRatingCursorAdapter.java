package uk.co.parkesfamily.parkes.pissup.guide.ratings;

import uk.co.parkesfamily.GPCursorHelper;
import uk.co.parkesfamily.parkes.pissup.guide.R;
import uk.co.parkesfamily.parkes.pissup.guide.contentproviders.WineratingsContentProvider;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

public class WineRatingCursorAdapter extends CursorAdapter
{

	public WineRatingCursorAdapter(Context context, Cursor c, int flags)
	{
		super(context, c, flags);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{
		TextView tvDescription = (TextView) view.findViewById(R.id.lblDescription);
		RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
		
		int iRating = GPCursorHelper.getInt(cursor, WineratingsContentProvider.RATING);
		String strDescription = GPCursorHelper.getString(cursor, WineratingsContentProvider.DESCRIPTION);
		
		tvDescription.setText(strDescription);
		ratingBar.setRating(iRating);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)
	{
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.wine_rating_row, parent, false);
		bindView(v, context, cursor);
		
		return v;
	}

}
