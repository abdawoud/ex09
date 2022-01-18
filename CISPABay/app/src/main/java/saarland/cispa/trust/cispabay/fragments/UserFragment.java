package saarland.cispa.trust.cispabay.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import saarland.cispa.trust.cispabay.R;

public class UserFragment extends Fragment {

    private static final String LOGIN_PAGE = "https://cispbay.de/";

    public UserFragment() {}

    public static UserFragment newInstance() {
        return new UserFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        WebView webView = (WebView) view.findViewById(R.id.webview);
        webView.loadUrl(LOGIN_PAGE);

        return view;
    }
}