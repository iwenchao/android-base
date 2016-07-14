package com.wenchaos.mxnews.base;

import com.wenchaos.mxnews.api.ApiConnector;
import com.wenchaos.mxnews.api.repository.UserDataRepository;
import com.wenchaos.mxnews.api.repository.source.UserDataCloudSource;
import com.wenchaos.mxnews.api.repository.source.UserDataPrefsSource;

/**
 * Created by Administrator
 * on 2016/7/14 0014.
 */
public class Injection {

    public static UserDataRepository provideUserDataRepository() {
        return UserDataRepository.getInstance(UserDataPrefsSource.getInstance(),
                UserDataCloudSource.getInstance(ApiConnector.getInstance()));
    }

}
