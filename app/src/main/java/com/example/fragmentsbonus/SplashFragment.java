package com.example.fragmentsbonus;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.fragmentsbonus.splash.presenter.SplashPresenter;
import com.example.fragmentsbonus.splash.presenter.SplashPresenterImp;
import com.example.fragmentsbonus.splash.view.SplashView;

public class SplashFragment extends Fragment implements SplashView {

    private SplashPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SplashPresenterImp(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.checkAuthStatus();
    }

    @Override
    public void navigateToHome(boolean isGuest) {
        if (getView() != null) {
            SplashFragmentDirections.ActionSplashFragmentToHomeFragment action =
                    SplashFragmentDirections.actionSplashFragmentToHomeFragment(isGuest);
            Navigation.findNavController(getView()).navigate(action);
        }
    }

    @Override
    public void navigateToOnBoarding() {
        if (getView() != null) {
            Navigation.findNavController(getView())
                    .navigate(R.id.action_splashFragment_to_onBoardingFragment);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }
}