package com.example.quoteapp;

import android.support.v4.app.Fragment;

public class QuoteEditActivity extends SingleFragmentActivity {
	public static final String EXTRA_QUOTE_ID = "QUOTE_ID";

	@Override
	protected Fragment createFragment() {
		long quoteId = getIntent().getLongExtra(EXTRA_QUOTE_ID, -1);
		if (quoteId != -1) {
			return QuoteEditFragment.newInstance(quoteId);
		} else {
			return new QuoteEditFragment();
		} 
	}
}
