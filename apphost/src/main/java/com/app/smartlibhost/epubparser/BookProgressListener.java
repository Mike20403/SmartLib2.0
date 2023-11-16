package com.app.smartlibhost.epubparser;

public interface BookProgressListener {
   void onSuccessfulExecute(String pageno, String progress, String types);
}
