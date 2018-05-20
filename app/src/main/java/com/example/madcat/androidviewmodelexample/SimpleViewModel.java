package com.example.madcat.androidviewmodelexample;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

public class SimpleViewModel extends ViewModel {

    MutableLiveData<String> data;

    public LiveData<String> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        return data;
    }

    public void loadData(final Integer count){
        TestAsync t = new TestAsync();
        t.execute(count);
    }

    // async data added task
    private class TestAsync extends AsyncTask<Integer, Void, Void>{

        @Override
        protected Void doInBackground(Integer... integers) {
            for(int i = 1; i <= integers[0]; i++){
                data.postValue("test value: " + i);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
