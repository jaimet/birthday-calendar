/*
 * Copyright (C) 2012-2013 Dominik Schürmann <dominik@dominikschuermann.de>
 *
 * This file is part of Birthday Adapter.
 * 
 * Birthday Adapter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Birthday Adapter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Birthday Adapter.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.birthdayadapter.ui;

import java.util.List;

import org.birthdayadapter.R;
import org.birthdayadapter.util.AccountListEntry;
import org.birthdayadapter.util.AccountListAdapter;
import org.birthdayadapter.util.AccountListLoader;
import org.birthdayadapter.util.Constants;
import org.birthdayadapter.util.Log;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

@SuppressLint("NewApi")
public class AccountListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<List<AccountListEntry>> {

    AccountListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_list_fragment, null);
        return view;
    }

    /**
     * Define Adapter and Loader on create of Activity
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Can't be used with a custom content view
        // setEmptyText("No accounts");

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new AccountListAdapter(getActivity());
        setListAdapter(mAdapter);

        // Start out with a progress indicator.
        // Can't be used with a custom content view
        // setListShown(false);

        // Prepare the loader. Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);

        Button saveButton = (Button) getActivity().findViewById(R.id.account_list_save);
        saveButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO

            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Bug on Android >= 4.1, http://code.google.com/p/android/issues/detail?id=35885
        // l.setItemChecked(position, l.isItemChecked(position));

        CheckBox cBox = (CheckBox) v.findViewWithTag("checkbox_" + position);
        Log.d(Constants.TAG, "Search cbox: " + "checkbox_" + position);

        if (cBox != null) {
            cBox.setChecked(!cBox.isChecked());

            AccountListEntry entry = mAdapter.getItem(position);
            entry.setSelected(!cBox.isChecked());
        } else {
            Log.e(Constants.TAG, "Checkbox could not be found!");
        }
        Log.e("LoaderCustom", "Item clicked: " + id);
    }

    @Override
    public Loader<List<AccountListEntry>> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created. This
        // sample only has one Loader with no arguments, so it is simple.
        return new AccountListLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<AccountListEntry>> loader, List<AccountListEntry> data) {
        // Set the new data in the adapter.
        mAdapter.setData(data);

        // The list should now be shown.
        if (isResumed()) {
            // Can't be used with a custom content view
            // setListShown(true);
        } else {
            // Can't be used with a custom content view
            // setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<AccountListEntry>> loader) {
        // Clear the data in the adapter.
        mAdapter.setData(null);
    }

}