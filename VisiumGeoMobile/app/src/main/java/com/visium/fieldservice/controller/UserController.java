package com.visium.fieldservice.controller;

import com.visium.fieldservice.api.visium.bean.request.LoginRequest;
import com.visium.fieldservice.api.visium.bean.response.EnumClassResponse;
import com.visium.fieldservice.api.visium.bean.response.EquipmentConfigurationResponse;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.LoginResponse;
import com.visium.fieldservice.api.visium.user.UserApi;
import com.visium.fieldservice.entity.UserProfile;
import com.visium.fieldservice.entity.AuthToken;
import com.visium.fieldservice.entity.enums.EnumsConfiguration;
import com.visium.fieldservice.preference.PreferenceHelper;
import com.visium.fieldservice.util.LogUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @author Andrew Willard
 */
public class UserController {

    private static final UserController INSTANCE = new UserController();

    private UserController() {
    }

    public static UserController get() {
        return INSTANCE;
    }

    public void auth(String userName, String password,
                     final VisiumApiCallback<UserProfile> callback) {
        LoginRequest request = new LoginRequest(userName, password);
        UserApi.auth(request, new VisiumApiCallback<GenericResponse<LoginResponse>>() {

            @Override
            public void callback(GenericResponse<LoginResponse> response, ErrorResponse e) {
                UserProfile userProfile = null;
                if (e == null) {
                    List<EquipmentConfigurationResponse> responseList = response.getData().getListEquipmentConfiguration();
                    HashMap<String, EnumsConfiguration> equipmentMaps = null;

                    if (responseList != null) {
                        equipmentMaps = new HashMap<>(responseList.size());
                        for (EquipmentConfigurationResponse r : responseList) {
                            EnumsConfiguration eqp = new EnumsConfiguration(r);
                            LogUtils.log("Equipment name: " + eqp.getName());
                            equipmentMaps.put(eqp.getName(), eqp);
                        }
                    }
                    userProfile = new UserProfile(response.getData().getName(),
                            new AuthToken(response.getData().getToken(),
                                    response.getData().getTokenExpiry()), equipmentMaps);
                    PreferenceHelper.setUserProfile(userProfile);
                }
                 callback.callback(userProfile, e);
            }
        });
    }
    public UserProfile getUserProfile() {
        return PreferenceHelper.getUserProfile();
    }
}