package com.wyd.aroundaddress;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author wyd
 * @description
 * @date 2018/8/21 下午1:59
 */
public interface AddressService {
    /**
     * 获取周边地址信息
     *
     * @param key
     * @param outputType
     * @param location
     * @param radius
     * @param sortrule
     * @param city
     * @param types
     * @param extensions
     * @param offset
     * @param page
     * @return
     */
    @GET("v3/place/around")
    Call<String> getAddressData(
            @Query("key") String key,
            @Query("output") String outputType,
            @Query("location") String location,
            @Query("radius") int radius,
            @Query("sortrule") String sortrule,
            @Query("city") String city,
            @Query("types") String types,
            @Query("extensions") String extensions,
            @Query("offset") int offset,
            @Query("page") int page);
}
