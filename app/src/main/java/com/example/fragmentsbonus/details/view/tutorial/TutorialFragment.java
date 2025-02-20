package com.example.fragmentsbonus.details.view.tutorial;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.details.presenter.Tutorial.TutorialPresenter;
import com.example.fragmentsbonus.details.presenter.Tutorial.TutorialPresenterImp;
import com.example.fragmentsbonus.models.meals.MealsItem;

public class TutorialFragment extends Fragment implements TutorialView {
    private MealsItem meal;
    private WebView webView;
    private TutorialPresenter presenter;
    public TutorialFragment() {
        // Required empty public constructor
    }

    public static TutorialFragment newInstance(MealsItem meal) {
        TutorialFragment fragment = new TutorialFragment();
        Bundle args = new Bundle();
        args.putParcelable("meal", meal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            meal = getArguments().getParcelable("meal");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);
        webView = view.findViewById(R.id.webView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new TutorialPresenterImp(this, meal);
        presenter.loadVideo();
    }

    @Override
    public void displayVideo(String videoUrl) {
        if (webView != null && videoUrl != null) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().setAllowFileAccess(true);
            webView.setWebChromeClient(new WebChromeClient());

            String embedUrl = videoUrl;
            if (videoUrl.contains("watch?v=")) {
                embedUrl = videoUrl.replace("watch?v=", "embed/");
            }

            String html = "<!DOCTYPE html><html>" +
                "<head>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<style>" +
                "body { margin: 0; padding: 0; }" +
                ".video-container { position: relative; width: 100%; height: 100vh;}" +
                ".video-container iframe { position: absolute; top: 0; left: 0; width: 100%; height: 100%; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='video-container'>" +
                "<iframe src='" + embedUrl + "' " +
                "frameborder='0' " +
                "allowfullscreen='true' " +
                "allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share'>" +
                "</iframe>" +
                "</div>" +
                "</body></html>";

            webView.loadDataWithBaseURL("https://www.youtube.com", html, "text/html", "UTF-8", null);
        }
    }


    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}