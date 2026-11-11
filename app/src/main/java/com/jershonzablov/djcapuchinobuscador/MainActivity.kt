package com.jershonzablov.djcapuchinobuscador

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var urlBar: EditText

    private val homePage = "https://muse.ai/s/dj-capuchino-2-cs68xatxglxshw/OHs44WUcXTxDF8re4-vQiQOYIhx0af20v0LnDFykNHs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        urlBar = findViewById(R.id.urlBar)
        val btnGo = findViewById<Button>(R.id.btnGo)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnHome = findViewById<Button>(R.id.btnHome)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                urlBar.setText(url)
            }
        }

        btnGo.setOnClickListener { go() }
        urlBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                go()
                true
            } else {
                false
            }
        }
        btnBack.setOnClickListener {
            if (webView.canGoBack()) webView.goBack()
        }
        btnHome.setOnClickListener { webView.loadUrl(homePage) }

        webView.loadUrl(homePage)
    }

    private fun go() {
        val text = urlBar.text.toString().trim()
        if (text.isEmpty()) return
        if (text.contains(".") && !text.contains(" ")) {
            val url = if (text.startsWith("http://") || text.startsWith("https://")) text else "https://$text"
            webView.loadUrl(url)
        } else {
            val query = URLEncoder.encode(text, "UTF-8")
            webView.loadUrl("https://www.google.com/search?q=$query")
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
