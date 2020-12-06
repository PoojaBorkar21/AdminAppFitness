package pooja.borkar.adminappfitness.services;

import java.util.List;

import pooja.borkar.adminappfitness.model.Expart_name;
import pooja.borkar.adminappfitness.model.Experts;
import pooja.borkar.adminappfitness.model.MyUser;
import pooja.borkar.adminappfitness.model.User;
import pooja.borkar.adminappfitness.model.Usercount;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ServiceApi {

    @POST("regiform.php")
    @FormUrlEncoded
    Call<User> doRegisteration(
            @Field("name") String name,
            @Field("email") String email,
            @Field("gender") String gender,
            @Field("dob") String dob,
            @Field("password") String password,
            @Field("phone") String phone

    );

    @POST("login.php")
    @FormUrlEncoded

    Call<User> doLogin(
            @Field("email") String email,
            @Field("password") String password
    );
    @POST("emailv.php")
    @FormUrlEncoded
    Call<User> doverify(
            @Field("otp") String otp
    );
    @POST("profflist.php")
    Call<List<Experts>> getprofList();

    @POST("userlist.php")
    Call<List<User>> getuserList();

    @POST("statuschange.php")
    @FormUrlEncoded
    Call<User> doChange(
            @Field("email") String email,
            @Field("active") String active
    );
    @POST("statususer.php")
    @FormUrlEncoded
    Call<User> doChangeuser(
            @Field("email") String email,
            @Field("active") String active
    );

    @POST("forgotpass.php")
    @FormUrlEncoded
    Call<User> forgotPassword(
            @Field("email") String email

    );


    @POST("profile_update.php")
    @FormUrlEncoded
    Call<User> prof_update(
            @Field("name") String name,
            @Field("email") String email,
            @Field("gender") String gender,
            @Field("dob") String dob,
            @Field("phone") String phone,
            @Field("image") String image
    );
    @POST("admininfo.php")
    @FormUrlEncoded
    Call<User> getAdmin(@Field("email") String email);

    @POST("user_count.php")
    Call<Usercount> getUserCount();


    @POST("bannerUpload.php")
    @FormUrlEncoded
    Call<Usercount> bannerUpload(@Field("admin_baner_url") String admin_baner_url, @Field("admin_baner_type") String admin_baner_type);



    @POST("addExpertType.php")
    @FormUrlEncoded
    Call<Usercount> addExpertType(

            @Field("experties_type_name") String experties_type_name,
    @Field("experties_type_fees") String experties_type_fees

    );
    @POST("removeExpertType.php")
    @FormUrlEncoded
    Call<Usercount> removeExpertType(

            @Field("experties_type_name") String experties_type_name


    );
    @POST("expert_list.php")
    Call<List<Expart_name>> getExpertList();

    @POST("getBannerImages.php")
    Call<List<Usercount>> getBanner();
    @POST("deleteBanner.php")
    @FormUrlEncoded
    Call<Usercount> deleteBanner(

            @Field("admin_baner_url") String admin_baner_url


    );
    @POST("getNewSubs.php")
    Call<List<MyUser>> getNewSubs();

    @POST("admin_AddUser.php")
    @FormUrlEncoded
    Call<MyUser> adminAddUser(@Field("plan_sups_id")String plan_sups_id);
}
