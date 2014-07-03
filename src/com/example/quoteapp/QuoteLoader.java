package com.example.quoteapp;

import android.content.Context;

import com.example.quoteapp.model.Quote;

public class QuoteLoader extends DataLoader<Quote> {
	private long mQuoteId;

	public QuoteLoader(Context context, long quoteId) {
		super(context);
		mQuoteId = quoteId;
	}

	@Override
	public Quote loadInBackground() {
		return QuoteKeeper.get(getContext()).getQuote(mQuoteId);
	}

}
