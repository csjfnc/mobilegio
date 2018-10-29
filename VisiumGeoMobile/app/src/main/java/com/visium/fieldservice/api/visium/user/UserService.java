package com.visium.fieldservice.api.visium.user;

import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.LoginResponse;
import retrofit.Callback;
import retrofit.http.POST;

/**
 * @author Andrew Willard
 */
interface UserService {

    @POST( "/auth/login")
    public void auth(Callback<GenericResponse<LoginResponse>> callback);
}
