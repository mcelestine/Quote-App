package com.example.quoteapp.model;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuoteDBHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "quoteDB.db";
	private static final String DATABASE_TABLE_NAME = "quotes";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_QUOTE_TEXT = "quoteText";
	public static final String COLUMN_QUOTE_AUTHOR = "quoteAuthor";
	public static final String COLUMN_DATE_ADDED = "dateAdded";

	private static final String CREATE_QUOTES_TABLE = "create table "
			+ DATABASE_TABLE_NAME + " (" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_QUOTE_TEXT
			+ " text not null, " + COLUMN_QUOTE_AUTHOR + " text not null, "
			+ COLUMN_DATE_ADDED + " integer)";

	private static final String[] COLUMNS = { COLUMN_ID, COLUMN_QUOTE_TEXT,
			COLUMN_QUOTE_AUTHOR, COLUMN_DATE_ADDED };

	public QuoteDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.beginTransaction();
			db.execSQL(CREATE_QUOTES_TABLE);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
		onCreate(db);
	}

	public long insertQuote(Quote quote) {
		Log.d("addQuote", quote.toString());

		// SQLiteDatabase db = this.getWritableDatabase();

		ContentValues newValues = new ContentValues();

		newValues.put(COLUMN_QUOTE_TEXT, quote.getQuoteText());
		newValues.put(COLUMN_QUOTE_AUTHOR, quote.getQuoteAuthor());
		newValues.put(COLUMN_DATE_ADDED, quote.getDateAdded().getTime()); // store
																// value

		// return db.insert(DATABASE_TABLE_NAME, null, newValues);
		return this.getWritableDatabase().insert(DATABASE_TABLE_NAME, null,
				newValues);
		// db.close();
	}
	
	public QuoteCursor getQuote(long id) {
		Cursor cursor = getReadableDatabase().query(DATABASE_TABLE_NAME, 
				null, // All columns 
				COLUMN_ID + " = ?", // Look for quote ID
				new String[] { String.valueOf(id) }, // with this value
				null, // group by
				null, // order by 
				null, //having
				"1"); // limit 1 row
		return new QuoteCursor(cursor);	
	}

	// returns a QuoteCursor listing all the runs in order by date
	public QuoteCursor getQuotes() {
		// Same as "select * from quotes order by dateAdded asc
		Cursor wrappedCursor = getReadableDatabase().query(DATABASE_TABLE_NAME,
				null, // return all columns
				null, // return all rows
				null, null, // group by
				null, // having
				COLUMN_DATE_ADDED + " asc"); // order by

		return new QuoteCursor(wrappedCursor);
	}

	public int updateQuote(Quote quote) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues updatedValues = new ContentValues();
		updatedValues.put(COLUMN_QUOTE_TEXT, quote.getQuoteText());
		updatedValues.put(COLUMN_QUOTE_AUTHOR, quote.getQuoteAuthor());

		int i = db.update(DATABASE_TABLE_NAME, updatedValues, COLUMN_ID + " = ?",
				new String[] { String.valueOf(quote.getId()) });

		db.close();

		return i;
	}

	public void deleteQuote(Quote quote) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(DATABASE_TABLE_NAME, COLUMN_ID + " = ?",
				new String[] { String.valueOf(quote.getId()) });

		db.close();

		Log.d("deleteQuote", quote.toString());
	}

	/**
	 * A convenience class to wrap a cursor that returns rows from the quotes
	 * table. getQuote() will return a Quote instance representing the current
	 * row.
	 */
	public static class QuoteCursor extends CursorWrapper {
		public QuoteCursor(Cursor cursor) {
			super(cursor);
		}

		public Quote getQuote() {
			if (isBeforeFirst() || isAfterLast())
				return null;

			Quote quote = new Quote();
			long quoteId = getLong(getColumnIndex(COLUMN_ID));
			quote.setId(quoteId);

			String quoteText = getString(getColumnIndex(COLUMN_QUOTE_TEXT));
			quote.setQuoteText(quoteText);

			String quoteAuthor = getString(getColumnIndex(COLUMN_QUOTE_AUTHOR));
			quote.setQuoteAuthor(quoteAuthor);

			long dateAdded = getLong(getColumnIndex(COLUMN_DATE_ADDED));
			quote.setDateAdded(new Date(dateAdded));

			return quote;
		}

	}

}
