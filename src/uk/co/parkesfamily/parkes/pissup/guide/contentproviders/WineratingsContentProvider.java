/*
 ******************************************************************************
 * Parts of this code sample are licensed under Apache License, Version 2.0   *
 * Copyright (c) 2009, Android Open Handset Alliance. All rights reserved.    *
 *																			  *																			*
 * Except as noted, this code sample is offered under a modified BSD license. *
 * Copyright (C) 2010, Motorola Mobility, Inc. All rights reserved.           *
 * 																			  *
 * For more details, see MOTODEV_Studio_for_Android_LicenseNotices.pdf        * 
 * in your installation folder.                                               *
 ******************************************************************************
 */
package uk.co.parkesfamily.parkes.pissup.guide.contentproviders;

import java.util.*;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.net.*;
import android.text.*;

import uk.co.parkesfamily.parkes.pissup.guide.helperclasses.*;

public class WineratingsContentProvider extends ContentProvider {

	private DBOpenHelper dbHelper;
	private static HashMap<String, String> WINERATINGS_PROJECTION_MAP;
	private static final String TABLE_NAME = "wineratings";
	private static final String AUTHORITY = "uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wineratingscontentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	public static final Uri _ID_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase());
	public static final Uri WINE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/wine");
	public static final Uri RATER_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/rater");
	public static final Uri RATING_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/rating");
	public static final Uri DESCRIPTION_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/description");

	public static final String DEFAULT_SORT_ORDER = "_id ASC";

	private static final UriMatcher URL_MATCHER;

	private static final int WINERATINGS = 1;
	private static final int WINERATINGS__ID = 2;
	private static final int WINERATINGS_WINE = 3;
	private static final int WINERATINGS_RATER = 4;
	private static final int WINERATINGS_RATING = 5;
	private static final int WINERATINGS_DESCRIPTION = 6;

	// Content values keys (using column names)
	public static final String _ID = "_id";
	public static final String WINE = "Wine";
	public static final String RATER = "Rater";
	public static final String RATING = "Rating";
	public static final String DESCRIPTION = "Description";

	public boolean onCreate() {
		dbHelper = new DBOpenHelper(getContext(), true);
		return (dbHelper == null) ? false : true;
	}

	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		SQLiteDatabase mDB = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (URL_MATCHER.match(url)) {
		case WINERATINGS:
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(WINERATINGS_PROJECTION_MAP);
			break;
		case WINERATINGS__ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("_id=" + url.getPathSegments().get(1));
			break;
		case WINERATINGS_WINE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("wine='" + url.getPathSegments().get(2) + "'");
			break;
		case WINERATINGS_RATER:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("rater='" + url.getPathSegments().get(2) + "'");
			break;
		case WINERATINGS_RATING:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("rating='" + url.getPathSegments().get(2) + "'");
			break;
		case WINERATINGS_DESCRIPTION:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("description='" + url.getPathSegments().get(2) + "'");
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		String orderBy = "";
		if (TextUtils.isEmpty(sort)) {
			orderBy = DEFAULT_SORT_ORDER;
		} else {
			orderBy = sort;
		}
		Cursor c = qb.query(mDB, projection, selection, selectionArgs, null,
				null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), url);
		return c;
	}

	public String getType(Uri url) {
		switch (URL_MATCHER.match(url)) {
		case WINERATINGS:
			return "vnd.android.cursor.dir/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wineratings";
		case WINERATINGS__ID:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wineratings";
		case WINERATINGS_WINE:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wineratings";
		case WINERATINGS_RATER:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wineratings";
		case WINERATINGS_RATING:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wineratings";
		case WINERATINGS_DESCRIPTION:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wineratings";

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
	}

	public Uri insert(Uri url, ContentValues initialValues) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		long rowID;
		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}
		if (URL_MATCHER.match(url) != WINERATINGS) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		rowID = mDB.insert("wineratings", "wineratings", values);
		if (rowID > 0) {
			Uri uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(uri, null);
			return uri;
		}
		throw new SQLException("Failed to insert row into " + url);
	}

	public int delete(Uri url, String where, String[] whereArgs) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		int count;
		String segment = "";
		switch (URL_MATCHER.match(url)) {
		case WINERATINGS:
			count = mDB.delete(TABLE_NAME, where, whereArgs);
			break;
		case WINERATINGS__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.delete(TABLE_NAME,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINERATINGS_WINE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"wine="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINERATINGS_RATER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"rater="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINERATINGS_RATING:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"rating="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINERATINGS_DESCRIPTION:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"description="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	public int update(Uri url, ContentValues values, String where,
			String[] whereArgs) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		int count;
		String segment = "";
		switch (URL_MATCHER.match(url)) {
		case WINERATINGS:
			count = mDB.update(TABLE_NAME, values, where, whereArgs);
			break;
		case WINERATINGS__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.update(TABLE_NAME, values,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINERATINGS_WINE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"wine="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINERATINGS_RATER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"rater="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINERATINGS_RATING:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"rating="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINERATINGS_DESCRIPTION:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"description="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	static {
		URL_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase(), WINERATINGS);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/#",
				WINERATINGS__ID);
		URL_MATCHER.addURI(AUTHORITY,
				TABLE_NAME.toLowerCase() + "/wine" + "/*", WINERATINGS_WINE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/rater"
				+ "/*", WINERATINGS_RATER);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/rating"
				+ "/*", WINERATINGS_RATING);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/description"
				+ "/*", WINERATINGS_DESCRIPTION);

		WINERATINGS_PROJECTION_MAP = new HashMap<String, String>();
		WINERATINGS_PROJECTION_MAP.put(_ID, "_id");
		WINERATINGS_PROJECTION_MAP.put(WINE, "wine");
		WINERATINGS_PROJECTION_MAP.put(RATER, "rater");
		WINERATINGS_PROJECTION_MAP.put(RATING, "rating");
		WINERATINGS_PROJECTION_MAP.put(DESCRIPTION, "description");

	}
}
