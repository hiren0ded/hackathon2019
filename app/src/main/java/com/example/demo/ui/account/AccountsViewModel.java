package com.example.demo.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AccountsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AccountsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("My Activity " +
                " \n"+"\n Earn Credit"+"\n "+"\n Setting"+"\n"+"\n Rate this App");
    }

    public LiveData<String> getText() {
        return mText;
    }
}