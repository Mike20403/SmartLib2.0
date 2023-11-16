package com.app.smartlibhost.Retrofit2;

import static com.app.smartlibhost.ultil.server.localhost;

public class APIutils {
    public static final String baseurl ="https://" +localhost+ "/Smartlib/";
    public static DataClient getData(){
        return RetrofitClient.getClient(baseurl).create(DataClient.class);
    }
}
