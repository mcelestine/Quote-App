package com.example.quoteapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quoteapp.model.Quote;
import com.example.quoteapp.model.QuoteDBHelper.QuoteCursor;

public class QuoteListFragment extends ListFragment implements 
	LoaderCallbacks<Cursor> {
	
	//private static final String TAG = QuoteListFragment.class.getSimpleName();
	private static final int REQUEST_NEW_QUOTE = 0;
	private static final String ARG_QUOTE_ID = "QUOTE_ID";
	private Quote mQuote;
	
	private Callbacks mCallbacks;
	
	/**
	 * Interface that hosting activities will use
	 *
	 */
	public interface Callbacks {
		public void onQuoteSelected(long quoteId);
		
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mCallbacks = (Callbacks)activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
		
		// Initialize the loader to load the list of quotes
		getLoaderManager().initLoader(0, null, this);		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		
		ListView listView = (ListView)view.findViewById(android.R.id.list);
		
		registerForContextMenu(listView);
		
		return view;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.quote_list_options, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_new_quote:
			Intent intent = new Intent(getActivity(), QuoteEditActivity.class);
			startActivityForResult(intent, REQUEST_NEW_QUOTE);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getActivity().getMenuInflater().inflate(R.menu.quote_list_item_context, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		int position = info.position;
		switch (item.getItemId()) {
		case R.id.menu_item_delete_quote:
			//QuoteKeeper.get(getActivity()).deleteQuote(mQuote);
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (REQUEST_NEW_QUOTE == requestCode) {

			// Restart the loader to get any new quote available
			getLoaderManager().restartLoader(0, null,  this);
		}		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mCallbacks.onQuoteSelected(id);	
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private static class QuoteListCursorLoader extends SQLiteCursorLoader {

		public QuoteListCursorLoader(Context context) {
			super(context);
		}

		@Override
		protected Cursor loadCursor() {
			// Query the list of quotes
			return QuoteKeeper.get(getContext()).queryQuotes();
		}
	}
	
	private static class QuoteCursorAdapter extends CursorAdapter {
		private QuoteCursor mQuoteCursor;
		
		public QuoteCursorAdapter(Context context, QuoteCursor cursor) {
			super(context, cursor, 0);
			mQuoteCursor = cursor;
		}
		
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			return inflater.inflate(R.layout.row_quote, parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// get the quote for the current row from cursor
			Quote quote = mQuoteCursor.getQuote();
			
			// set up the widgets
			TextView quoteTextTV = (TextView)view.findViewById(R.id.tv_quote_text);
			quoteTextTV.setText(quote.getQuoteText());
		}
	}
	
	// ------------- Loader Callbacks ------------///

	// Where the activity requests a Loader
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new QuoteListCursorLoader(getActivity());
	}

	// Called by the system once the data has been loaded in the background, 
	// passing in the data
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		QuoteCursorAdapter adapter = new QuoteCursorAdapter(getActivity(), (QuoteCursor)cursor);
		setListAdapter(adapter);
		
	}

	// Called in case the data is stale or unavailable
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		setListAdapter(null);
	}
	
	// -----------------------------------------------------//
	private class QuoteLoaderCallbacks implements LoaderCallbacks<Quote> {

		@Override
		public Loader<Quote> onCreateLoader(int id, Bundle args) {
			return new QuoteLoader(getActivity(), args.getLong(ARG_QUOTE_ID));
		}

		@Override
		public void onLoadFinished(Loader<Quote> loader, Quote quote) {
			mQuote = quote;
		}

		@Override
		public void onLoaderReset(Loader<Quote> loader) {
			// Do nothing			
		}
		
	}
}
