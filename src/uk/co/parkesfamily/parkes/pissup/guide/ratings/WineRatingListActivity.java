package uk.co.parkesfamily.parkes.pissup.guide.ratings;

import uk.co.parkesfamily.parkes.pissup.guide.R;
import uk.co.parkesfamily.parkes.pissup.guide.contentproviders.WineratingsContentProvider;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class WineRatingListActivity extends FragmentActivity
{
	private long		_lWineID;
	
	private String[]	_lstRatingCols;
	private String		_strRatingQuery;

	public WineRatingListActivity()
	{
		_lstRatingCols = new String[]{
				WineratingsContentProvider._ID,
				WineratingsContentProvider.DESCRIPTION,
				WineratingsContentProvider.RATING
		};
		_strRatingQuery = WineratingsContentProvider.WINE + " = ?";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wine_rating_list);
	}

	
}
