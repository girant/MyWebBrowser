package am.artipd.home.mywebbrowser;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Home on 02.02.2015.
 */
public class MyWC extends WebViewClient {
    public boolean shouldOverrideUrlLoading(WebView view, String url){
        view.loadUrl(url);
        return true;
    }
}
