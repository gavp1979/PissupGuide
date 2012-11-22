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

public class WinetypesContentProvider extends ContentProvider {

	private DBOpenHelper dbHelper;
	private static HashMap<String, String> WINETYPES_PROJECTION_MAP;
	private static final String TABLE_NAME = "winetypes";
	private static final String AUTHORITY = "uk.co.parkesfamily.parkes.pissup.guide.contentproviders.winetypescontentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	public static final Uri _ID_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase());
	public static final Uri CATEGORY_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/category");
	public static final Uri CODE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/code");
	public static final Uri DESCRIPTION_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/description");

	public static final String DEFAULT_SORT_ORDER = "_id ASC";

	private static final UriMatcher URL_MATCHER;

	private static final int WINETYPES = 1;
	private static final int WINETYPES__ID = 2;
	private static final int WINETYPES_CATEGORY = 3;
	private static final int WINETYPES_CODE = 4;
	private static final int WINETYPES_DESCRIPTION = 5;

	// Content values keys (using column names)
	public static final String _ID = "_id";
	public static final String CATEGORY = "Category";
	public static final String CODE = "Code";
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
		case WINETYPES:
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(WINETYPES_PROJECTION_MAP);
			break;
		case WINETYPES__ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("_id=" + url.getPathSegments().get(1));
			break;
		case WINETYPES_CATEGORY:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("category='" + url.getPathSegments().get(2) + "'");
			break;
		case WINETYPES_CODE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("code='" + url.getPathSegments().get(2) + "'");
			break;
		case WINETYPES_DESCRIPTION:
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
		case WINETYPES:
			return "vnd.android.cursor.dir/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.winetypes";
		case WINETYPES__ID:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.winetypes";
		case WINETYPES_CATEGORY:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.winetypes";
		case WINETYPES_CODE:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.winetypes";
		case WINETYPES_DESCRIPTION:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.winetypes";

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
		if (URL_MATCHER.match(url) != WINETYPES) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		rowID = mDB.insert("winetypes", "winetypes", values);
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
		case WINETYPES:
			count = mDB.delete(TABLE_NAME, where, whereArgs);
			break;
		case WINETYPES__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.delete(TABLE_NAME,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINETYPES_CATEGORY:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"category="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINETYPES_CODE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"code="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINETYPES_DESCRIPTION:
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
		case WINETYPES:
			count = mDB.update(TABLE_NAME, values, where, whereArgs);
			break;
		case WINETYPES__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.update(TABLE_NAME, values,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINETYPES_CATEGORY:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"category="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINETYPES_CODE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"code="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINETYPES_DESCRIPTION:
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
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase(), WINETYPES);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/#",
				WINETYPES__ID);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/category"
				+ "/*", WINETYPES_CATEGORY);
		URL_MATCHER.addURI(AUTHORITY,
				TABLE_NAME.toLowerCase() + "/code" + "/*", WINETYPES_CODE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/description"
				+ "/*", WINETYPES_DESCRIPTION);

		WINETYPES_PROJECTION_MAP = new HashMap<String, String>();
		WINETYPES_PROJECTION_MAP.put(_ID, "_id");
		WINETYPES_PROJECTION_MAP.put(CATEGORY, "category");
		WINETYPES_PROJECTION_MAP.put(CODE, "code");
		WINETYPES_PROJECTION_MAP.put(DESCRIPTION, "description");

	}
}
