package com.nganhthien.mikemovie.utils;

import android.os.AsyncTask;

public abstract class BaseGetDataFromLocal<T> extends AsyncTask<String, Void, T> {
    protected BaseGetDataFromLocal.WrapperException mWrapperException;

    public static class WrapperException {
        private Exception mException;

        public Exception getException() {
            return mException;
        }

        public void setException(Exception exception) {
            this.mException = exception;
        }
    }
}
