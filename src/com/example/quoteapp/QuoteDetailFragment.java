package com.example.quoteapp;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quoteapp.model.Quote;

public class QuoteDetailFragment extends Fragment implements 
	LoaderCallbacks<Quote>{
	
	//private static final String TAG = QuoteDetailFragment.class.getSimpleName();
	private static final String ARG_QUOTE_ID = "QUOTE_ID";
	public static final String EXTRA_QUOTE_ID = "com.example.quoteapp.quote_id";
		
	private TextView mQuoteTextView, mQuoteAuthorTextView;
	
	private Quote mQuote;
	
	public static QuoteDetailFragment newInstance(long quoteId) {
		Bundle args = new Bundle();
		args.putLong(ARG_QUOTE_ID, quoteId);
		QuoteDetailFragment fragment = new QuoteDetailFragment();
		fragment.setArguments(args);
		return fragment;
	}
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		
		Bundle args = getArguments();
		if (args != null) {
			long quoteId = args.getLong(ARG_QUOTE_ID, -1);
			if (quoteId != -1) {

				LoaderManager lm = getLoaderManager();
				lm.initLoader(0, args, this);
			}
		}
		setHasOptionsMenu(true);
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_quote_detail, container, false);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		
		mQuoteTextView = (TextView) view.findViewById(R.id.quote_text_textView);
		mQuoteAuthorTextView = (TextView) view.findViewById(R.id.quote_author_textView);
		updateUI();
		return view;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// Check if there's a parent activity named in metadata of manifest file
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	// ----------- DataLoader Callbacks ---------------------------- //
	@Override
	public Loader<Quote> onCreateLoader(int id, Bundle args) {
		return new QuoteLoader(getActivity(), args.getLong(ARG_QUOTE_ID));
	}

	@Override
	public void onLoadFinished(Loader<Quote> laoder, Quote quote) {
		mQuote = quote;
		updateUI();
		
	}

	@Override
	public void onLoaderReset(Loader<Quote> loader) {
		// Do nothing
	}
	
	private void updateUI() {
		// update widgets with text from quote
		if (mQuote != null) {
			mQuoteTextView.setText(mQuote.getQuoteText());
			mQuoteAuthorTextView.setText(mQuote.getQuoteAuthor());
		}
	}

}
