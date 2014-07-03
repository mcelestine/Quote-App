package com.example.quoteapp;

import android.support.v4.app.Fragment;

public class QuoteDetailActivity extends SingleFragmentActivity {
	
	public static final String EXTRA_QUOTE_ID = "com.example.quoteapp.quote_id";

	@Override
	protected Fragment createFragment() {
		// return new QuoteDetailFragment();
		long quoteId = getIntent().getLongExtra(EXTRA_QUOTE_ID, -1);
		if (quoteId != -1) {
			return QuoteDetailFragment.newInstance(quoteId);
		} else {
			return new QuoteDetailFragment();
		}
	}
}
