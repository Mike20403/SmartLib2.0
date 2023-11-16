package com.app.smartlibhost.epubparser;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.app.smartlibhost.WebFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;


public class BookRenderTask extends AsyncTask<String, String,ViewPagerAdapter> {

    StringBuilder css;
    String webpage="";
    StringBuilder alldata;
    private String outPutFolder;
    private Book book;
    private String linezNew;
    List<WebFragment> webFragments;
    Activity activity;
    FragmentActivity context;

    private BookProgressListener bookProgressListener;
    private FragmentPageClick fragmentPageClick;
    private BookRenderListener myAsyncListener;
    ViewPagerAdapter adapter;


    public BookRenderTask(BookRenderListener myAsyncListener, BookProgressListener progressListener, Activity activity, FragmentActivity context, FragmentPageClick fragmentPageClick) {
        this.activity=activity;
        this.context=context;
        this.fragmentPageClick=fragmentPageClick;
        this.bookProgressListener=progressListener;
        this.myAsyncListener = myAsyncListener;
    }

    @Override
    protected ViewPagerAdapter doInBackground(String[] objects) {

        adapter = new ViewPagerAdapter(context.getSupportFragmentManager());
        alldata=new StringBuilder();
        outPutFolder=activity.getDir("Downloads", Context.MODE_PRIVATE) + "/epub/" ;


        AssetManager assetManager = activity.getAssets();

        try {
            // find InputStream for book

            File file=new File(objects[0]);
            InputStream epubInputStream = new FileInputStream(file);

            // Load Book from inputStream
            book = (new EpubReader()).readEpub(epubInputStream);

            // Log the book's authors
            Log.i("epublib", "author(s): " + book.getMetadata().getAuthors());

            // Log the book's title
            Log.i("epublib", "title: " + book.getTitle());

            // Log the book's coverimage property

                Bitmap coverImage = BitmapFactory.decodeStream(book.getCoverImage()
                        .getInputStream());

                Log.i("epublib", "Coverimage is " + coverImage.getWidth() + " by "
                        + coverImage.getHeight() + " pixels");

            // Log the tale of contents
            //logTableOfContents(book.getTableOfContents().getTocReferences(), 0);
            int i=1;
            for(Resource resource:book.getContents()){
                Log.d("Content",resource.getHref());
                Log.d("Content",resource.getId());
                Log.d("Content",resource.getInputEncoding());
                //     Log.d("Content",resource.getTitle());
                String str = new String(resource.getData(), StandardCharsets.UTF_8);
                Log.d("Content",str);
                InputStream in = resource.getInputStream();
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String read;

                while ((read=br.readLine()) != null) {
                    //System.out.println(read);
                    sb.append(read);
                }

                br.close();
                alldata.append(sb.toString());
                adapter.addFragment(new WebFragment().newInstance(sb.toString(),outPutFolder,(book.getTitle()!=null ? book.getTitle() : ""), i +"/"+book.getContents().size(),fragmentPageClick));
                int percentage=(i/book.getContents().size()*100);
                publishProgress(String.valueOf(i), String.valueOf(percentage),"page");
                i++;
                //webFragments.add();
                Log.d("Content",sb.toString());
                Log.d("Content",resource.getMediaType().getDefaultExtension());
                Log.d("Content",resource.getMediaType().getName());
                Log.d("Content", String.valueOf(resource.getMediaType().getExtensions()));
                Log.d("Content", resource.toString());
                Log.d("END","END");
                //break;

            }
        } catch (IOException e) {
            Log.e("epublib", e.getMessage());
        }


        //setOutPutFolder(outPutFolder);

        DownloadResource(outPutFolder);

//        webView.loadDataWithBaseURL("file://" + outPutFolder, finalDataToLoad, "text/html", "utf-8", null);

//        webpage="<!doctype html>\n" +
//                "<html lang=\"en\">\n" +
//                "  <head>\n" +
//                "    <!-- Required meta tags -->\n" +
//                "    <meta charset=\"utf-8\">\n" +
//                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
//                "\n" +
//                "    <!-- Bootstrap CSS -->\n" +
//                "    <link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n" +
//                "\n" +
//                "    <title>Hello, world!</title>\n" +
//                "  </head><style>/* * { background:#000; color:#fff; } */</style>\n" +
//                "  <body>\n" +
//                "<div>"+alldata.toString()+"</div>"+
//                "\n" +
//                "    <!-- Optional JavaScript -->\n" +
//                "    <!-- jQuery first, then Popper.js, then Bootstrap JS -->\n" +
//                "    <script src=\"http://code.jquery.com/jquery-3.2.1.slim.min.js\" ></script>\n" +
//                "    <script src=\"http://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" ></script>\n" +
//                "    <script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" ></script>\n" +
//                "  </body>\n" +
//                "</html>";
        //  webView.loadData(webpage,"text/html","UTF-8");
        // Create an object of page transformer

        return adapter;
    }

    private void DownloadResource(String directory) {
        try {

            Resources rst = book.getResources();
            Collection<Resource> clrst = rst.getAll();
            Iterator<Resource> itr = clrst.iterator();

            while (itr.hasNext()) {
                Resource rs = itr.next();

                if ((rs.getMediaType() == MediatypeService.JPG)
                        || (rs.getMediaType() == MediatypeService.PNG)
                        || (rs.getMediaType() == MediatypeService.GIF)) {

                    Log.d("Href", rs.getHref());

                    File oppath1 = new File(directory, rs.getHref().replace("OEBPS/", ""));

                    oppath1.getParentFile().mkdirs();
                    oppath1.createNewFile();
                    publishProgress("","100","image");

                    System.out.println("Path : "+oppath1.getParentFile().getAbsolutePath());


                    FileOutputStream fos1 = new FileOutputStream(oppath1);
                    fos1.write(rs.getData());
                    fos1.close();

                } else if (rs.getMediaType() == MediatypeService.CSS) {

                    File oppath = new File(directory, rs.getHref());

                    oppath.getParentFile().mkdirs();
                    oppath.createNewFile();
                    publishProgress("","100","style");

                    FileOutputStream fos = new FileOutputStream(oppath);
                    fos.write(rs.getData());
                    fos.close();

                }

            }

        } catch (Exception e) {

        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        bookProgressListener.onSuccessfulExecute(values[0],values[1],values[2]);
    }

    @Override
    protected void onPostExecute(ViewPagerAdapter viewPagerAdapter) {
        super.onPostExecute(viewPagerAdapter);
        myAsyncListener.onSuccessfulExecute(adapter);
    }
}