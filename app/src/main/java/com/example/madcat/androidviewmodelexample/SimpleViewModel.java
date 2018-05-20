package com.example.madcat.androidviewmodelexample;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

public class SimpleViewModel extends ViewModel {

    MutableLiveData<String> data;
    MutableLiveData<String> statusData;

    TestAsync t;

    public LiveData<String> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        return data;
    }

    public LiveData<String> getStatusData() {
        if (statusData == null) {
            statusData = new MutableLiveData<>();
        }
        return statusData;
    }

    public void loadData(final Integer count){
        t = new TestAsync();
        t.execute(count);
    }

    // async data added task
    private class TestAsync extends AsyncTask<Integer, Integer, Void>{

        @Override
        protected Void doInBackground(Integer... integers) {
            for(int i = 1; i <= integers[0]; i++){
                if(isCancelled()){
                    break;
                }

                data.postValue("test value: " + i);

                publishProgress(i, integers[0]);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            statusData.setValue(values[0] + " from " + values[1] + " loaded");
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        // clear resources
        t.cancel(true);
    }
}
