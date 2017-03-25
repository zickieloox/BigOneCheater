package com.zic.bigonecheater.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zic.bigonecheater.R;
import com.zic.bigonecheater.data.Account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<Account> mAccounts = new ArrayList<>();
    private Account curAccount;

    public AccountAdapter(Context context, List<Account> accounts) {
        this.mContext = context;
        this.mAccounts = accounts;
        sortItems();
    }

    private void sortItems() {
        // Sort the data by $lineNumber
        Collections.sort(mAccounts, new Comparator<Account>() {

            @Override
            public int compare(Account o1, Account o2) {
                return o1.getLineNumber() - o2.getLineNumber();
            }
        });
    }

    public Account getCurAccount() {
        return curAccount;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_account, parent, false);

        return new AccountAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        curAccount = mAccounts.get(position);
        int lineNumber = curAccount.getLineNumber();
        String fid = curAccount.getFid();
        String password = curAccount.getPassword();

        holder.itemView.setTag(curAccount);
        holder.itemView.setOnClickListener(this);
        holder.btnLineNumber.setText(String.valueOf(lineNumber));
        holder.tvFid.setText(fid);
        holder.tvPassword.setText(password);
    }

    @Override
    public int getItemCount() {
        return mAccounts.size();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnLineNumber:
                break;
            default:
                curAccount = (Account) v.getTag();
                Toast.makeText(mContext, curAccount.getLineNumber() + " Clicked", Toast.LENGTH_SHORT).show();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        Button btnLineNumber;
        TextView tvFid;
        TextView tvPassword;

        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.btnLineNumber = (Button) itemView.findViewById(R.id.btnLineNumber);
            this.tvFid = (TextView) itemView.findViewById(R.id.tvFid);
            this.tvPassword = (TextView) itemView.findViewById(R.id.tvPassword);
        }
    }
}
