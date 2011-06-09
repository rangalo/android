package com.hardik.android;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.security.PublicKey;

public class DebugTest extends Activity
{
    private EditText txtURL;
    private Button btnGo;
    private WebView wvBrowser;


    // set up an onClick routine to gather URLs entered by the user
    private final Button.OnClickListener btnGoOnClick = new Button.OnClickListener() {
        public void onClick(View v) {
            try{
                String sURL = txtURL.getText().toString();
                if(sURL.substring(0,7).equals("http://") ||sURL.substring(0,8).equals("https://") ) {
                    wvBrowser.loadUrl(sURL);
                } else {
                    sURL = "http://" + sURL;
                    wvBrowser.loadUrl(sURL);
                }
                wvBrowser.loadUrl(sURL);
            } catch (Exception e) {
            }
        }
    };



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // find the views in the layout file
        txtURL = (EditText) findViewById(R.id.URL);
        btnGo = (Button) findViewById(R.id.btnGo);
        wvBrowser = (WebView) findViewById(R.id.wvBrowser);

        btnGo.setOnClickListener(btnGoOnClick);
    }
}
