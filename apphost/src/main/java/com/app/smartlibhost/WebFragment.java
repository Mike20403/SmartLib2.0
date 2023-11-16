package com.app.smartlibhost;

import android.content.Context;
import android.os.Bundle;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.smartlibhost.epubparser.FragmentPageClick;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class WebFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private static FragmentPageClick fragmentPageClick;

    public WebFragment() {
        // Required empty public constructor
    }


    public static WebFragment newInstance(String param1, String param2, String param3, String param4, FragmentPageClick fragmentPageClick1) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragmentPageClick=fragmentPageClick1;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.from(container.getContext()).inflate(R.layout.fragment_web, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView webView=view.findViewById(R.id.web);
        TextView title=view.findViewById(R.id.title);
        TextView pageno=view.findViewById(R.id.page_no);

        if(mParam3.isEmpty()){
            title.setVisibility(View.GONE);
        }
        title.setText(mParam3);
        pageno.setText("Page No: "+mParam4);
        String linezNew = mParam1.replace("../", "");
        final String regex = "href=\"(.*?)\"";
        linezNew = linezNew.replaceAll(regex, "#");
        String finalDataToLoad = "";
        pageno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPageDialog();
            }
        });
        finalDataToLoad+="<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <!-- Required meta tags -->\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
                "\n" +
                "    <!-- Bootstrap CSS -->\n" +
                "    <link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n" +
                "\n" +
                "    <title>Hello, world!</title>\n" +
                "  </head><style>* { background:#fff; color:#000; } body { width:100%!important;padding:5px;} img { width:100%!important;}</style>\n" +
                "  <body>\n" +
                "<div>"+linezNew+"</div>"+
                "\n" +
                "    <!-- Optional JavaScript -->\n" +
                "    <!-- jQuery first, then Popper.js, then Bootstrap JS -->\n" +
                "    <script src=\"http://code.jquery.com/jquery-3.2.1.slim.min.js\" ></script>\n" +
                "    <script src=\"http://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" ></script>\n" +
                "    <script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" ></script>\n" +
                "  </body>\n" +
                "</html>";
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
       // webView.getSettings().setAllowFileAccess(false);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
       // webView.getSettings().setBuiltInZoomControls(true);
        webView.setPadding(0, 0, 0, 0);
        webView.setInitialScale(getScale());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL("file://" + mParam2, finalDataToLoad, "text/html", "utf-8", null);
    }

    private void showPageDialog() {
        final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(getContext());
        View view=getLayoutInflater().inflate(R.layout.page_goto,null);
        final EditText editText=view.findViewById(R.id.pageno);
        Button gotobtn=view.findViewById(R.id.gotopage);
        gotobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().isEmpty()){
                    editText.setError("Please Enter page No.");
                }
                else {
                    fragmentPageClick.pageNoClick(editText.getText().toString());
                    bottomSheetDialog.dismiss();
                }
            }
        });
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

    private int getScale(){
            Display display = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            int width = display.getWidth();
            Double val = new Double(width)/new Double(1000);
            val = val * 100d;
            return val.intValue();
        }

}
