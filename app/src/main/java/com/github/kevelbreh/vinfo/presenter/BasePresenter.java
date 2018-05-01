package com.github.kevelbreh.vinfo.presenter;

import com.github.kevelbreh.vinfo.ui.BaseView;

public interface BasePresenter<T extends BaseView> {

    void onAttach(T view);

    void onDetach(T view);
}
