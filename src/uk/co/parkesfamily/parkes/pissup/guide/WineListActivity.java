/**
 * 
 */
package uk.co.parkesfamily.parkes.pissup.guide;

import uk.co.parkesfamily.parkes.pissup.guide.contentproviders.WineContentProvider;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.Toast;

/**
 * @author gavin.parkes
 *
 */
public class WineListActivity extends FragmentActivity 
	implements LoaderCallbacks<Cursor>
{
	private String[]	_lstWineCols;

	public WineListActivity() 
	{
		super();
		
		_lstWineCols = new String[]{
				WineContentProvider._ID,
				WineContentProvider.NAME,
				WineContentProvider.YEAR
		};
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wine_list);
		
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int identifier, Bundle args) 
	{
		CursorLoader loader = new CursorLoader(this, WineContentProvider.CONTENT_URI, _lstWineCols,
				null, null, null);
		
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) 
	{
		Toast.makeText(this, cursor.getCount() + " rows retrieved.", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) 
	{
		Toast.makeText(this, "Cursor reset.", Toast.LENGTH_LONG).show();
	}
	
}
