package com.zic.bigonecheater.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zic.bigonecheater.R;
import com.zic.bigonecheater.adapter.AccountAdapter;
import com.zic.bigonecheater.data.Account;
import com.zic.bigonecheater.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class AccountRecyclerFragment extends Fragment {

    private static final String TAG = "AccountRecyclerFragment";
    @SuppressLint("SdCardPath")
    private static final String accountListPath = "/sdcard/Zickie/accounts.txt";

    RecyclerView accountRecycler;
    AccountAdapter accountAdapter;

    List<String> lineList = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_list, container, false);
        accountRecycler = (RecyclerView) rootView.findViewById(R.id.accountRecycler);

        loadAccounts();
        setupAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        accountRecycler.setLayoutManager(linearLayoutManager);
        accountRecycler.addItemDecoration(new DividerItemDecoration(accountRecycler.getContext(), linearLayoutManager.getOrientation()));

        return rootView;
    }

    public void loadAccounts() {
        lineList = FileUtils.getLineList(accountListPath);
        for (int i = 0; i < lineList.size(); i++) {
            accounts.add(new Account(i, lineList.get(i)));
        }
    }

    public void setupAdapter() {
        accountAdapter = new AccountAdapter(getContext(), accounts);
        accountRecycler.setAdapter(accountAdapter);
    }

}
