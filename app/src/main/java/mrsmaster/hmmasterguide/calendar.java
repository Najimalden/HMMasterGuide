package mrsmaster.hmmasterguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class calendar extends AppCompatActivity {


    private WebView Webgeo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Webgeo = (WebView) findViewById(R.id.webviewmaster);

        Webgeo.getSettings().setJavaScriptEnabled(true);




        Webgeo.loadUrl("https://calendar.google.com/calendar?cid=c2NodWxlaG9jaHNjaHVsZWhvY2hAZ21haWwuY29t");
    }
}
