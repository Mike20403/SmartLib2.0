package com.app.smartlibhost.epubparser;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.app.smartlibhost.R;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

import java.util.List;

import nl.siegmann.epublib.domain.TOCReference;

public class EpubParseActivity extends AppCompatActivity implements BookRenderListener,BookProgressListener,FragmentPageClick {

   ViewPager viewPager;
   TextView progress3;
   ProgressBar progressBar2;
    private ViewPagerAdapter adapter;
    LazyLoader progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epubparse);
        progressBar=findViewById(R.id.progress);
        progressBar2=findViewById(R.id.progress2);
        progress3=findViewById(R.id.progress3);
        viewPager=findViewById(R.id.viewpager);
        if(getIntent().getStringExtra("paths")==null){
            Toast.makeText(EpubParseActivity.this, "Please Open Valid File!", Toast.LENGTH_SHORT).show();
            onBackPressed();
            finish();
        }
        BookRenderTask bookRenderTask=new BookRenderTask(this,this, EpubParseActivity.this, EpubParseActivity.this, EpubParseActivity.this);
        bookRenderTask.execute(getIntent().getStringExtra("paths"));

        Toast.makeText(EpubParseActivity.this, "Click on Bottom Page No. To Visit Page!", Toast.LENGTH_SHORT).show();

    }

    private void logTableOfContents(List<TOCReference> tocReferences, int depth) {
        if (tocReferences == null) {
            return;
        }
        for (TOCReference tocReference : tocReferences) {
            StringBuilder tocString = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                tocString.append("\t");
            }
            tocString.append(tocReference.getTitle());
            Log.i("epublib", tocString.toString());

            logTableOfContents(tocReference.getChildren(), depth + 1);
        }
    }



    @Override
    public void onSuccessfulExecute(ViewPagerAdapter viewPagerAdapter) {
        adapter=viewPagerAdapter;
        viewPager.setAdapter(adapter);
        BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
        //  bookFlipPageTransformer.

// Enable / Disable scaling while flipping. If true, then next page will scale in (zoom in). By default, its true.
        bookFlipPageTransformer.setEnableScale(true);

// The amount of scale the page will zoom. By default, its 5 percent.
        bookFlipPageTransformer.setScaleAmountPercent(10f);

// Assign the page transformer to the ViewPager.
        viewPager.setPageTransformer(true, bookFlipPageTransformer);

        viewPager.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        progressBar2.setVisibility(View.GONE);
        progress3.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessfulExecute(String pageno, String progress, String type) {

        if(type.equalsIgnoreCase("image")){
            progress3.setText("Loading Images...");
        }
        else if(type.equalsIgnoreCase("style")){
            progress3.setText("Loading UI...");
        }
        else if(type.equalsIgnoreCase("page")){
            progress3.setText("Loading Page : "+pageno);
            progressBar2.setProgress(Integer.parseInt(progress));
        }
    }

    @Override
    public void pageNoClick(String pageno) {
        int page= Integer.parseInt(pageno);
        page=page-1;
        if(adapter.getCount() > page){
            viewPager.setCurrentItem(page);
        }
        else{
            Toast.makeText(EpubParseActivity.this, "Page Not Found!", Toast.LENGTH_SHORT).show();
        }
    }
}
