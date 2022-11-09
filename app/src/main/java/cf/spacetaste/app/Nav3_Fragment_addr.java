package cf.spacetaste.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Nav3_Fragment_addr extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_navi3_web_view);

        webView = (WebView) findViewById(R.id.webView);
        webView.addJavascriptInterface(new BridgeInterface(), "Android");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                //Android -> Javascript 함수 호출!
                webView.evaluateJavascript("start();",null);
            }
        });
        //최초 웹뷰 로드
        webView.loadUrl("https://api.space-taste.cf/address.html");
    }
    private class BridgeInterface
    {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void onResult(String data){
            //다음 주소 검색 API의 결과 값이 브릿지 통로를 통해 전달 받음(from Javascript)
            Intent intent = new Intent();
            intent.putExtra("data",data);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}