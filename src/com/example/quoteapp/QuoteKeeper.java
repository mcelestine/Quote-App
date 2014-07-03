package com.example.quoteapp;

import android.content.Context;

import com.example.quoteapp.model.Quote;
import com.example.quoteapp.model.QuoteDBHelper;
import com.example.quoteapp.model.QuoteDBHelper.QuoteCursor;


public class QuoteKeeper {
	
	//private static final String TAG = QuoteKeeper.class.getSimpleName();
	
	//private static final String PREFS_FILE = "quotes";
//	private static final String PREF_CURRENT_ID = "QuoteKeeper.currentQuoteId";
	
	private static QuoteKeeper sQuoteKeeper;
	private Context mAppContext;
	private QuoteDBHelper mQuoteDBHelper;
//	private SharedPreferences mPrefs;
	//private long mCurrentQuoteId;
		
	private QuoteKeeper(Context appContext) {
		mAppContext = appContext;
		mQuoteDBHelper = new QuoteDBHelper(mAppContext);
	//	mPrefs = mAppContext.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
	//	mCurrentQuoteId = mPrefs.getLong(PREF_CURRENT_ID, -1);
	}
	
	public static QuoteKeeper get(Context context) {
		if (sQuoteKeeper == null) {
			sQuoteKeeper = new QuoteKeeper(context.getApplicationContext());
		}
		return sQuoteKeeper;
	}
	
	// insert a quote into the db
	public void insertQuote(Quote quote) {
		quote.setId(mQuoteDBHelper.insertQuote(quote));
	}
	
	// Delete a quote
	public void deleteQuote(Quote quote) {
		mQuoteDBHelper.deleteQuote(quote);
	}
	
	// Get quotes
	public QuoteCursor queryQuotes() {
		return mQuoteDBHelper.getQuotes();
	}
	
	// Update a quote
	public void updateQuote(Quote quote) {
		mQuoteDBHelper.updateQuote(quote);
	}
	
	// Get a quote
	public Quote getQuote(long id) {
		Quote quote = null;
		QuoteCursor cursor = mQuoteDBHelper.getQuote(id);
		cursor.moveToFirst();
		// If you got a row, get a quote
		if (!cursor.isAfterLast())
			quote = cursor.getQuote();
		cursor.close();
		return quote;
	}
	
	public void close() {
		mQuoteDBHelper.close();
	}
	
}
