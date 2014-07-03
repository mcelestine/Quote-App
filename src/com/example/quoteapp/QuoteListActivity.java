package com.example.quoteapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class QuoteListActivity extends SingleFragmentActivity implements
		QuoteListFragment.Callbacks {

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_master_list;
	}

	@Override
	protected Fragment createFragment() {
		return new QuoteListFragment();
	}

	@Override
	public void onQuoteSelected(long quoteId) {
		// If using a phone interface
		if (findViewById(R.id.detailFragmentContainer) == null) {
			Intent intent = new Intent(this, QuoteDetailActivity.class);
			intent.putExtra(QuoteDetailFragment.EXTRA_QUOTE_ID, quoteId);
			startActivity(intent);
		} else {

			// using tablet interface
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();

			Fragment oldDetail = fm
					.findFragmentById(R.id.detailFragmentContainer);
			//Fragment newDetail = QuoteDetailFragment.newInstance(quote.getId());
			Fragment newDetail = QuoteDetailFragment.newInstance(quoteId);
			if (oldDetail != null) {
				ft.remove(oldDetail);
			}

			ft.add(R.id.detailFragmentContainer, newDetail);
			ft.commit();
		}
	}

}
