package am.artipd.home.mywebbrowser;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;


public class MainActivity extends ActionBarActivity {

    private WebView myWeb;
    private EditText edUrl;
    private ProgressBar pb;
    final Activity activ = this;
    private boolean ifLoading, isFocused;

    private ImageButton goStopButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = (ProgressBar)findViewById(R.id.pBar);

        goStopButton = (ImageButton)findViewById(R.id.goStop);
        myWeb = (WebView)findViewById(R.id.webView);
        edUrl = (EditText)findViewById(R.id.editUrl);
        myWeb.setWebViewClient(new MyWC());
        myWeb.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView ww, int progress){
               // activ.setTitle("Loading...");

               // activ.setProgress(progress*100);
                if(progress<100&&pb.getVisibility()==ProgressBar.GONE)
                {
                    pb.setVisibility(ProgressBar.VISIBLE);
                    goStopButton.setBackground(getDrawable(R.drawable.stopload));
                    ifLoading=true;

                }
                pb.setProgress(progress);
                if(progress == 100){
                    isFocused=false;
                    ifLoading=false;
                    goStopButton.setBackground(getDrawable(R.drawable.icgo));
                    pb.setVisibility(ProgressBar.GONE);
                 //   activ.setTitle(ww.getTitle());
                    edUrl.setText(ww.getTitle());
                    edUrl.clearFocus();
                    edUrl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            hasFocus = isFocused;
                            if(edUrl.hasFocus())
                            {
                                edUrl.setText(myWeb.getUrl());

                            }
                            else{
                                edUrl.setText(myWeb.getTitle());
                            }
                        }
                    });
                }
            }
        });
        myWeb.getSettings().setJavaScriptEnabled(true);
        myWeb.getSettings().setSupportZoom(true);

        myWeb.loadUrl("http:\\viktoria.do.am");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
            break;
            case R.id.action_go:
                String URL ="http:\\"+ edUrl.getText().toString();
                myWeb.loadUrl(URL);
                break;
            case R.id.action_stopLoad:
                action_stopLoad();
            default:
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void action_stopLoad (){
        myWeb.stopLoading();


    }
    public void onBackPressed(){
        if(myWeb.canGoBack())
            myWeb.goBack();
        else
            super.onBackPressed();
    }

    public void onGoStopButton(View view) {
        if(!ifLoading) {
            String URL = "http:\\" + edUrl.getText().toString();
            myWeb.loadUrl(URL);
        }
        else
        {
            action_stopLoad();
        }
    }
}
