package com.example.quoteapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.quoteapp.model.Quote;

public class QuoteEditFragment extends Fragment {
	private static final String TAG = "QuoteEditFragment";
	private static final String ARG_QUOTE_ID = "QUOTE_ID";
	private QuoteKeeper mQuoteKeeper;

	private EditText mQuoteTextField, mQuoteAuthorField;
	private Button mDoneButton;

	private Quote mQuote;

	public static QuoteEditFragment newInstance(long quoteId) {
		Bundle args = new Bundle();
		args.putLong(ARG_QUOTE_ID, quoteId);
		QuoteEditFragment fragment = new QuoteEditFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

		mQuoteKeeper = QuoteKeeper.get(getActivity());
		Bundle args = getArguments();
		if (args != null) {
			long quoteId = args.getLong(ARG_QUOTE_ID, -1);
			if (quoteId != -1) {
				mQuote = mQuoteKeeper.getQuote(quoteId);
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_quote_edit, parent,
				false);

		mQuoteTextField = (EditText) view
				.findViewById(R.id.quote_text_editText);
		mQuoteAuthorField = (EditText) view
				.findViewById(R.id.quote_author_editText);
		mDoneButton = (Button) view.findViewById(R.id.button_done);

		mQuoteTextField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				//mQuote.setQuoteText(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		//mQuoteAuthorField.setText(mQuote.getQuoteAuthor());
		mQuoteAuthorField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				//mQuote.setQuoteAuthor(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		
		mDoneButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String quote = mQuoteTextField.getText().toString();
				String author = mQuoteAuthorField.getText().toString();
				Log.d(TAG, "onClicked with text: " + quote + " "+ author);
				
				mQuote = new Quote(quote, author);
				mQuoteKeeper.insertQuote(mQuote);
				
				getActivity().finish();
			}
		});
		return view;
	}
}
