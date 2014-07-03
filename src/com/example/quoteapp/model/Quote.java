package com.example.quoteapp.model;

import java.util.Date;

public class Quote {
	
	private long mId;
	private String mQuoteText;
	private String mQuoteAuthor;
	private Date mDateAdded;
	
	public Quote() {
		mId = -1;
		mDateAdded = new Date();
	}
	
	public Quote(String quoteText, String quoteAuthor) {
		mQuoteText = quoteText;
		mQuoteAuthor = quoteAuthor;
		mDateAdded = new Date();
	}
	
	public void setId(long id) {
		mId = id;
	}

	public long getId() {
		return mId;
	}

	public String getQuoteText() {
		return mQuoteText;
	}

	public void setQuoteText(String quoteText) {
		this.mQuoteText = quoteText;
	}

	public String getQuoteAuthor() {
		return mQuoteAuthor;
	}

	public void setQuoteAuthor(String quoteAuthor) {
		this.mQuoteAuthor = quoteAuthor;
	}
	
	public void setDateAdded(Date dateAdded) {
		mDateAdded = dateAdded;
	}
		
	public Date getDateAdded() {
		return mDateAdded;
	}

	@Override
	public String toString() {
		return mQuoteText;
	}

}
