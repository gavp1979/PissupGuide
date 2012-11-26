/**
 * 
 */
package uk.co.parkesfamily.parkes.pissup.guide.winelist;

import uk.co.parkesfamily.parkes.pissup.guide.R;
import uk.co.parkesfamily.parkes.pissup.guide.contentproviders.WineContentProvider;
import uk.co.parkesfamily.parkes.pissup.guide.winedetails.WineDetails;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author gavin.parkes
 *
 */
public class WineListActivity extends FragmentActivity 
	implements LoaderCallbacks<Cursor>, OnItemClickListener
{
	private String[]	_lstWineCols;
	private ListView	_lstWines;
	private WineListCursorAdapter	_adapter;

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
		
		_lstWines = (ListView) findViewById(R.id.listWines);
		_adapter = new WineListCursorAdapter(this, null, 0);
		_lstWines.setAdapter(_adapter);
		_lstWines.setOnItemClickListener(this);
				
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
		_adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) 
	{
		_adapter.swapCursor(null);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Intent intWine = WineDetails.createIntent(this, id);
		startActivity(intWine);
	}
	
}
