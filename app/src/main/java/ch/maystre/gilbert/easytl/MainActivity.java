package ch.maystre.gilbert.easytl;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

//todo: put on github
public class MainActivity extends Activity {

    // region widgets

    private CustomSwipeRefreshLayout refreshLayout;

    private FrameLayout frameLayout;

    private TextView creditText;

    private WebView webView;

    private TextView textView;

    private Button reloadButton;

    // endregion

    private boolean loadingFailed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findItems();

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                if(!loadingFailed){
                    applyNormalState();
                }
            }

            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                loadingFailed = true;
                applyLoadFailedState();
            }
        });
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.t-l.ch/tl-live-mobile/index.php");

        setHelloText();

        refreshLayout.setOnRefreshListener(
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    webView.reload();
                }
            }
        );

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyLoadingState();
                webView.reload();
                loadingFailed = false;
                webView.clearView(); //don't do this at home kids
            }
        });
    }


    @Override
    public void onBackPressed() {
        refreshLayout.setRefreshing(false);
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void applyLoadingState(){
        refreshLayout.setRefreshing(false);
        refreshLayout.setEnabled(false);
        textView.setText(R.string.loading);
        frameLayout.setVisibility(View.GONE);
        reloadButton.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
    }

    private void applyLoadFailedState(){
        refreshLayout.setRefreshing(false);
        refreshLayout.setEnabled(false);
        textView.setText(R.string.load_faillure);
        frameLayout.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        reloadButton.setVisibility(View.VISIBLE);
    }

    private void applyNormalState(){
        refreshLayout.setRefreshing(false);
        refreshLayout.setEnabled(true);
        textView.setVisibility(View.GONE);
        reloadButton.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
    }

    private void setHelloText(){
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString str1 = new SpannableString("Hacked for you by ");
        str1.setSpan(new ForegroundColorSpan(Color.rgb(186, 186, 186)), 0, "Hacked for you by ".length(), 0);
        builder.append(str1);

        SpannableString str2 = new SpannableString("Gilou");
        str2.setSpan(new ForegroundColorSpan(Color.rgb(229, 158, 158)), 0, "Gilou".length(), 0);
        builder.append(str2);

        SpannableString str3 = new SpannableString(" with ");
        str3.setSpan(new ForegroundColorSpan(Color.rgb(186, 186, 186)), 0, " with ".length(), 0);
        builder.append(str3);

        SpannableString str4 = new SpannableString("<3");
        str4.setSpan(new ForegroundColorSpan(Color.rgb(229, 158, 158)), 0, "<3".length(), 0);
        builder.append(str4);

        creditText.setText(builder, TextView.BufferType.SPANNABLE);
    }

    private void findItems(){
        webView = findViewById(R.id.webView);
        textView = findViewById(R.id.loadingText);
        refreshLayout = findViewById(R.id.swipe);
        reloadButton = findViewById(R.id.reloadButton);
        frameLayout = findViewById(R.id.frameLayout);
        creditText = findViewById(R.id.hello_sign);
    }

}
