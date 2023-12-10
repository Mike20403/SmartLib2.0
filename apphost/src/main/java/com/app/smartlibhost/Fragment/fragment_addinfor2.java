package com.app.smartlibhost.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.smartlibhost.R;
import com.microblink.blinkid.MicroblinkSDK;
import com.microblink.blinkid.entities.recognizers.Recognizer;
import com.microblink.blinkid.entities.recognizers.RecognizerBundle;
import com.microblink.blinkid.entities.recognizers.blinkid.documentface.DocumentFaceRecognizer;
import com.microblink.blinkid.intent.IntentDataTransferMode;
import com.microblink.blinkid.uisettings.ActivityRunner;
import com.microblink.blinkid.uisettings.DocumentUISettings;
import com.microblink.blinkid.uisettings.UISettings;
import com.microblink.blinkid.uisettings.options.BeepSoundUIOptions;
import com.microblink.blinkid.uisettings.options.OcrResultDisplayMode;
import com.microblink.blinkid.uisettings.options.OcrResultDisplayUIOptions;

public class fragment_addinfor2 extends Fragment {
    View view;
    ImageView CMND, facereg;
    TextView faceregtv,cmndtv;
    DocumentFaceRecognizer documentFaceRecognizer;
    RecognizerBundle mRecognizerBundle;
    int MY_REQUEST_CODE = 123;
    static String value;
    static Bitmap bitmap1,bitmap2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_addinfor2,container,false);
        CMND = (ImageView) view.findViewById(R.id.CMND);
        facereg = (ImageView) view.findViewById(R.id.faceregconize);
        faceregtv = (TextView) view.findViewById(R.id.faceregtv);
        cmndtv = (TextView) view.findViewById(R.id.cmndtv);
       MicroblinkSDK.setLicenseFile("license.mblic", getContext());
       MicroblinkSDK.setIntentDataTransferMode(IntentDataTransferMode.PERSISTED_OPTIMISED);
       CMND.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {

                startScanning();


            }
        });
        return view;
    }
    private void scanAction(@NonNull UISettings activitySettings, @Nullable Intent helpIntent) {
        setupActivitySettings(activitySettings, helpIntent);
        ActivityRunner.startActivityForResult(this, MY_REQUEST_CODE, activitySettings);
    }


    /**
     * Starts scan activity. Activity that will be used is determined by the passed activity settings.
     * UI options are configured inside this method.
     * @param activitySettings activity settings that will be used for scanning, only recognizers are
     *                         important, UI options will be configured inside this method.
     */
    private void scanAction(@NonNull UISettings activitySettings) {
        scanAction(activitySettings, null);
    }
    private void setupActivitySettings(@NonNull UISettings settings, @Nullable Intent helpIntent) {
        if (settings instanceof BeepSoundUIOptions) {
            // optionally, if you want the beep sound to be played after a scan
            // add a sound resource id
            ((BeepSoundUIOptions) settings).setBeepSoundResourceID(R.raw.beep);
        }
//        if (helpIntent != null && settings instanceof HelpIn) {
//            // if we have help intent, we can pass it to scan activity so it can invoke
//            // it if user taps the help button. If we do not set the help intent,
//            // scan activity will hide the help button.
//            ((HelpIntentUIOptions) settings).setHelpIntent(helpIntent);
//        }
        if (settings instanceof OcrResultDisplayUIOptions) {
            // If you want, you can disable drawing of OCR results on scan activity. Drawing OCR results can be visually
            // appealing and might entertain the user while waiting for scan to complete, but might introduce a small
            // performance penalty.
            // ((ShowOcrResultUIOptions) settings).setShowOcrResult(false);

            // Enable showing of OCR results as animated dots. This does not have effect if non-OCR recognizer like
            // barcode recognizer is active.
            ((OcrResultDisplayUIOptions) settings).setOcrResultDisplayMode(OcrResultDisplayMode.ANIMATED_DOTS);
        }
    }

    public void startScanning() {
        // Settings for BlinkIdActivity
        documentFaceRecognizer = new DocumentFaceRecognizer();

        documentFaceRecognizer.setReturnFullDocumentImage(true);
        documentFaceRecognizer.setReturnFaceImage(true);
        documentFaceRecognizer.setFullDocumentImageDpi(300);
        scanAction(new DocumentUISettings(prepareRecognizerBundle(documentFaceRecognizer)));

        // tweak settings as you wish

        // Start activity

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // onActivityResult is called whenever we are returned from activity started
        // with startActivityForResult. We need to check request code to determine
        // that we have really returned from expected activity.
        if (resultCode == Activity.RESULT_OK && data != null ) {
            // set intent's component to ResultActivity and pass its contents
            // to ResultActivity. ResultActivity will show how to extract
            // data from result.
            DocumentFaceRecognizer.Result result = documentFaceRecognizer.getResult();
            if (result.getResultState() == Recognizer.Result.State.Valid) {
                // result is valid, you can use it however you wish
                Log.d("Checkk", String.valueOf(result.getResultState()));
                value = String.valueOf(result.getResultState());
                facereg.setVisibility(View.VISIBLE);
                cmndtv.setVisibility(View.VISIBLE);
                faceregtv.setVisibility(View.VISIBLE);
                bitmap1 = result.getFaceImage().convertToBitmap();
                bitmap2 = result.getFullDocumentImage().convertToBitmap();
                facereg.setImageBitmap(bitmap1);

                CMND.setImageBitmap(bitmap2);


            }

        } else {
            // if activity did not return result, user has probably
            // pressed Back button and cancelled scanning
            Toast.makeText(getContext(), "Scan cancelled!", Toast.LENGTH_SHORT).show();
        }
    }

    public static String GetResultState(){
        return  value;
    }
    public static Bitmap GetFaceReg(){
        return bitmap1;
    }
    public static Bitmap GetCMND(){
        return bitmap2;
    }




    private RecognizerBundle prepareRecognizerBundle(@NonNull Recognizer<?>... recognizers ) {
        return new RecognizerBundle(recognizers);
    }
}
