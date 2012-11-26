/**
 * 
 */
package uk.co.parkesfamily.parkes.pissup.guide.winelist;

import uk.co.parkesfamily.GPCursorHelper;
import uk.co.parkesfamily.parkes.pissup.guide.R;
import uk.co.parkesfamily.parkes.pissup.guide.contentproviders.WineContentProvider;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Administrator
 *
 */
public class WineListCursorAdapter extends CursorAdapter
{

	public WineListCursorAdapter(Context context, Cursor c, int flags)
	{
		super(context, c, flags);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.support.v4.widget.CursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{
		TextView txtName = (TextView) view.findViewById(R.id.lblName);
		TextView txtYear = (TextView) view.findViewById(R.id.lblYear);
		
		txtName.setText(GPCursorHelper.getString(cursor, WineContentProvider.NAME));
		txtYear.setText(GPCursorHelper.getString(cursor, WineContentProvider.YEAR));
	}

	/* (non-Javadoc)
	 * @see android.support.v4.widget.CursorAdapter#newView(android.content.Context, android.database.Cursor, android.view.ViewGroup)
	 */
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)
	{
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.wine_list_row, parent, false);
		bindView(v, context, cursor);
		
		return v;
	}

}
