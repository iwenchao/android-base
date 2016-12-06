package com.wenchaos.base.base;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.squareup.otto.Subscribe;
import com.wenchaos.base.api.ErrorBody;
import com.wenchaos.base.data.BusProvider;
import com.wenchaos.base.event.ErrorEvent;
import com.wenchaos.base.util.Toaster;
import com.wenchaos.base.util.UiUtils;
import com.wenchaos.base.view.dialog.ProgressAnimateDialog;

import org.androidannotations.annotations.EFragment;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment
public class BaseFragment extends Fragment implements BaseView {

    protected final static String ANIMATED_LOADING_DIALOG = "loadingDialog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void showBaseLoadingProgressDialog() {
        showLoadingProgressDialog(null);
    }

    @Override
    public void showLoadingProgressDialog(@Nullable String message) {
        showLoadingProgressDialog(message, ProgressAnimateDialog.ANIMATE_TYPE_COMMON, true);
    }

    @Override
    public void showLoadingProgressDialog(@Nullable String message, int type, boolean cancelable) {
        removePrevDialog(ANIMATED_LOADING_DIALOG);
//        ProgressAnimateDialog progressAnimateDialog = ProgressAnimateDialog_.builder()
//                .type(type)
//                .message(message)
//                .cancelable(cancelable)
//                .build();
//        progressAnimateDialog.show(getFragmentManager(), ANIMATED_LOADING_DIALOG);
    }

    @Override
    public void removePrevDialog() {
        removePrevDialog(null);
    }

    @Override
    public void removePrevDialog(String tag) {
        if (isRemoving()) {
            return;
        }

        if (getFragmentManager() == null) {
            return;
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(TextUtils.isEmpty(tag) ? ANIMATED_LOADING_DIALOG : tag);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void showErrorToast(ErrorBody body) {
        UiUtils.showErrorToaster(body.getMessage(), getActivity());
    }

    @Override
    public void showToast(String message) {
        Toaster.showShort(getActivity(), message);
    }

    @Override
    public Resources getRes() {
        return getResources();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getBus().register(this);
    }

    @Subscribe
    public void onErrorEvent(ErrorEvent event) {
        UiUtils.showErrorToaster(event.getmErrorMsg(), getActivity());
    }

    @Override
    public void onPause() {
        BusProvider.getBus().unregister(this);
        super.onPause();
    }
}
