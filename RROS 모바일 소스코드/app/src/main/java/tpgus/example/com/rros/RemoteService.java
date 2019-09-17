package tpgus.example.com.rros;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import tpgus.example.com.rros.Coupon.CouponItem;
import tpgus.example.com.rros.ListView_Search.getLicenseInfo;

/**
 * 서버에 호출할 메소드를 선언하는 인터페이스
 */
public interface RemoteService {
    String BASE_URL = "http://192.168.43.161:3000";


    @POST("/adduser") //@POST("/process/adduser")
     Call<String>insertMemberInfo(@Body MemberInfoItem memberInfoItem);

    @POST("/androidLogin") //@POST("/process/login")
    Call<String>selectMemberInfo(@Body SelectMemberInfo selectMemberInfo);

    @POST("/reservation")
    Call<ReservationInfo>reservationInfo(@Body ReservationInfo reservationInfo);

    @POST("/authReservation")
    Call<String> postId(@Body request_Beacon_Flag rbf);

    @POST("/authWifi")
    Call<net> postLicenseNo(@Body request_Beacon_Flag rbf);

    @GET("/reqCoupon")
    Call<ArrayList<CouponItem>> reqCoupon(@Query("Id") String Id);

    @POST("/setUserItem")
    Call<MemberInfoItem> setUesrItem(@Body String id);

//    @GET("/food/list")
//   Call<ArrayList<FoodInfoItem>> listFoodInfo1(@("licenseNo") int licenseNo);

    @GET("/getMenu")
    Call<ArrayList<FoodInfoItem>> listFoodInfo(@Query("licenseNo") int licenseNo);

    @GET("/getLicense")
    Call<ArrayList<Result_Search_Restaurant>>result_search(@Query("Name") String Name);

    @POST("/licenseInfo2")
    Call<ArrayList<getLicenseInfo>> get_licenseInfo(@Body getLicenseInfo getLicenseInfo);

    @GET("/reqMyInfo")
    Call<ArrayList<getLicenseInfo>> getRestaurant();

    @GET("/member/sortRestaurantInfo")
    Call<ArrayList<getLicenseInfo>> sortRestaurant(@Query("orderType") int orderType);


    @GET("/compareRestaurant")
    Call<ArrayList<getLicenseInfo>> compareRestaurant(@Query("Name") String Name);

    @GET("/getReview")
    Call<ArrayList<getLicenseInfo>> getReviewItem(@Query("orderType") int orderType);

    @GET("/reqCouponPlace")
    Call<ArrayList<positionInfo>> getPosition(@Query("LicenseNo") int LicenseNo);

    @GET("/member/getRestaurantInfo")
    Call<ArrayList<getLicenseInfo>> getRestaurant2();

    @GET("/reqFiltering")
    Call<ArrayList<getLicenseInfo>>reqFiltering(@Query("selectedItem")int selectedItem);

    @GET("/giveCoupon")
    Call<String>getCoupon(@Query("mac") String mac,@Query("id") String id);

    @GET("/alertCoupon")
    Call<ArrayList<CouponItem>>alertCoupon(@Query("mac") String mac,@Query("id") String id);

    @GET("/getMember")
    Call<ArrayList<MemberInfoItem>>getMember(@Query("Email") String Email);

    @GET("/shake")
    Call<String>shake(@Query("cusNum") String cusNum);
}