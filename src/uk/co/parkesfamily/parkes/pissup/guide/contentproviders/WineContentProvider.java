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

public class WineContentProvider extends ContentProvider {

	private DBOpenHelper dbHelper;
	private static HashMap<String, String> WINE_PROJECTION_MAP;
	private static final String TABLE_NAME = "wine";
	private static final String AUTHORITY = "uk.co.parkesfamily.parkes.pissup.guide.contentproviders.winecontentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	public static final Uri _ID_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase());
	public static final Uri TYPE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/type");
	public static final Uri NAME_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/name");
	public static final Uri VARIETAL_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/varietal");
	public static final Uri COUNTRY_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/country");
	public static final Uri AREA_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/area");
	public static final Uri PRICE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/price");
	public static final Uri YEAR_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/year");
	public static final Uri PRODUCER_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/producer");
	public static final Uri SUPPLIER_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/supplier");
	public static final Uri ALCOHOL_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/alcohol");
	public static final Uri TRIED_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/tried");
	public static final Uri TASTINGNOTES_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/tastingnotes");
	public static final Uri IMAGE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/image");

	public static final String DEFAULT_SORT_ORDER = "_id ASC";

	private static final UriMatcher URL_MATCHER;

	private static final int WINE = 1;
	private static final int WINE__ID = 2;
	private static final int WINE_TYPE = 3;
	private static final int WINE_NAME = 4;
	private static final int WINE_VARIETAL = 5;
	private static final int WINE_COUNTRY = 6;
	private static final int WINE_AREA = 7;
	private static final int WINE_PRICE = 8;
	private static final int WINE_YEAR = 9;
	private static final int WINE_PRODUCER = 10;
	private static final int WINE_SUPPLIER = 11;
	private static final int WINE_ALCOHOL = 12;
	private static final int WINE_TRIED = 13;
	private static final int WINE_TASTINGNOTES = 14;
	private static final int WINE_IMAGE = 15;

	// Content values keys (using column names)
	public static final String _ID = "_id";
	public static final String TYPE = "Type";
	public static final String NAME = "Name";
	public static final String VARIETAL = "Varietal";
	public static final String COUNTRY = "Country";
	public static final String AREA = "Area";
	public static final String PRICE = "Price";
	public static final String YEAR = "Year";
	public static final String PRODUCER = "Producer";
	public static final String SUPPLIER = "Supplier";
	public static final String ALCOHOL = "Alcohol";
	public static final String TRIED = "Tried";
	public static final String TASTINGNOTES = "TastingNotes";
	public static final String IMAGE = "Image";

	public boolean onCreate() {
		dbHelper = new DBOpenHelper(getContext(), true);
		return (dbHelper == null) ? false : true;
	}

	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		SQLiteDatabase mDB = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (URL_MATCHER.match(url)) {
		case WINE:
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(WINE_PROJECTION_MAP);
			break;
		case WINE__ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("_id=" + url.getPathSegments().get(1));
			break;
		case WINE_TYPE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("type='" + url.getPathSegments().get(2) + "'");
			break;
		case WINE_NAME:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("name='" + url.getPathSegments().get(2) + "'");
			break;
		case WINE_VARIETAL:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("varietal='" + url.getPathSegments().get(2) + "'");
			break;
		case WINE_COUNTRY:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("country='" + url.getPathSegments().get(2) + "'");
			break;
		case WINE_AREA:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("area='" + url.getPathSegments().get(2) + "'");
			break;
		case WINE_PRICE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("price='" + url.getPathSegments().get(2) + "'");
			break;
		case WINE_YEAR:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("year='" + url.getPathSegments().get(2) + "'");
			break;
		case WINE_PRODUCER:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("producer='" + url.getPathSegments().get(2) + "'");
			break;
		case WINE_SUPPLIER:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("supplier='" + url.getPathSegments().get(2) + "'");
			break;
		case WINE_ALCOHOL:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("alcohol='" + url.getPathSegments().get(2) + "'");
			break;
		case WINE_TRIED:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("tried='" + url.getPathSegments().get(2) + "'");
			break;
		case WINE_TASTINGNOTES:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("tastingnotes='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case WINE_IMAGE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("image='" + url.getPathSegments().get(2) + "'");
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
		case WINE:
			return "vnd.android.cursor.dir/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wine";
		case WINE__ID:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wine";
		case WINE_TYPE:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wine";
		case WINE_NAME:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wine";
		case WINE_VARIETAL:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wine";
		case WINE_COUNTRY:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wine";
		case WINE_AREA:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wine";
		case WINE_PRICE:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wine";
		case WINE_YEAR:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wine";
		case WINE_PRODUCER:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wine";
		case WINE_SUPPLIER:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wine";
		case WINE_ALCOHOL:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wine";
		case WINE_TRIED:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wine";
		case WINE_TASTINGNOTES:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wine";
		case WINE_IMAGE:
			return "vnd.android.cursor.item/vnd.uk.co.parkesfamily.parkes.pissup.guide.contentproviders.wine";

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
		if (URL_MATCHER.match(url) != WINE) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		rowID = mDB.insert("wine", "wine", values);
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
		case WINE:
			count = mDB.delete(TABLE_NAME, where, whereArgs);
			break;
		case WINE__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.delete(TABLE_NAME,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_TYPE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"type="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_NAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"name="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_VARIETAL:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"varietal="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_COUNTRY:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"country="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_AREA:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"area="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_PRICE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"price="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_YEAR:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"year="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_PRODUCER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"producer="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_SUPPLIER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"supplier="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_ALCOHOL:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"alcohol="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_TRIED:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"tried="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_TASTINGNOTES:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"tastingnotes="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_IMAGE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"image="
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
		case WINE:
			count = mDB.update(TABLE_NAME, values, where, whereArgs);
			break;
		case WINE__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.update(TABLE_NAME, values,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_TYPE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"type="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_NAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"name="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_VARIETAL:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"varietal="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_COUNTRY:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"country="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_AREA:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"area="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_PRICE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"price="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_YEAR:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"year="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_PRODUCER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"producer="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_SUPPLIER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"supplier="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_ALCOHOL:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"alcohol="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_TRIED:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"tried="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_TASTINGNOTES:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"tastingnotes="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case WINE_IMAGE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"image="
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
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase(), WINE);
		URL_MATCHER
				.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/#", WINE__ID);
		URL_MATCHER.addURI(AUTHORITY,
				TABLE_NAME.toLowerCase() + "/type" + "/*", WINE_TYPE);
		URL_MATCHER.addURI(AUTHORITY,
				TABLE_NAME.toLowerCase() + "/name" + "/*", WINE_NAME);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/varietal"
				+ "/*", WINE_VARIETAL);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/country"
				+ "/*", WINE_COUNTRY);
		URL_MATCHER.addURI(AUTHORITY,
				TABLE_NAME.toLowerCase() + "/area" + "/*", WINE_AREA);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/price"
				+ "/*", WINE_PRICE);
		URL_MATCHER.addURI(AUTHORITY,
				TABLE_NAME.toLowerCase() + "/year" + "/*", WINE_YEAR);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/producer"
				+ "/*", WINE_PRODUCER);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/supplier"
				+ "/*", WINE_SUPPLIER);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/alcohol"
				+ "/*", WINE_ALCOHOL);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/tried"
				+ "/*", WINE_TRIED);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/tastingnotes" + "/*", WINE_TASTINGNOTES);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/image"
				+ "/*", WINE_IMAGE);

		WINE_PROJECTION_MAP = new HashMap<String, String>();
		WINE_PROJECTION_MAP.put(_ID, "_id");
		WINE_PROJECTION_MAP.put(TYPE, "type");
		WINE_PROJECTION_MAP.put(NAME, "name");
		WINE_PROJECTION_MAP.put(VARIETAL, "varietal");
		WINE_PROJECTION_MAP.put(COUNTRY, "country");
		WINE_PROJECTION_MAP.put(AREA, "area");
		WINE_PROJECTION_MAP.put(PRICE, "price");
		WINE_PROJECTION_MAP.put(YEAR, "year");
		WINE_PROJECTION_MAP.put(PRODUCER, "producer");
		WINE_PROJECTION_MAP.put(SUPPLIER, "supplier");
		WINE_PROJECTION_MAP.put(ALCOHOL, "alcohol");
		WINE_PROJECTION_MAP.put(TRIED, "tried");
		WINE_PROJECTION_MAP.put(TASTINGNOTES, "tastingnotes");
		WINE_PROJECTION_MAP.put(IMAGE, "image");

	}
}
