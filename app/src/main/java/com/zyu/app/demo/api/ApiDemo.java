package com.zyu.app.demo.api;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zyu.app.demo.model.AdvertisementResponse;
import com.zyu.app.demo.model.FileResponse;
import com.zyu.app.domain.LoginInfoResponse;
import com.zyu.app.domain.RequestResult;
import com.zyu.app.http.rx.RxBaseModel;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;

/**
 * 用户请求的示例
 * Created by xtagwgj on 16/9/25.
 */

public class ApiDemo extends RxBaseModel {



    public ApiDemo() {

    }

    /**
     * 登录
     * 切换页面依旧执行登陆，不根据生命周期改变而更改登录的请求
     *
     * @param username   用户名
     * @param password   密码
     * @param subscriber
     */
    public <T> void login(String username, String password,
                      Subscriber<T> subscriber) {



    }

    /**
     * 获取广告
     *
     * @param context    切换页面的时候停止获取数据，要是用content进行生命周期的请求管理
     * @param code       小区编号，未登陆时传0
     * @param type       0为广告位，1为小区图片轮播
     * @param subscriber
     */
    public void getAd(RxAppCompatActivity context, String code, int type, Subscriber<List<AdvertisementResponse>> subscriber) {

    }

    /**
     * 上传单张图片
     *
     * @param context
     * @param filePath
     * @param subscriber
     */
    public void uploadPic(RxAppCompatActivity context, String filePath, Subscriber<List<FileResponse>> subscriber) {
//        File file = new File(filePath);
//        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("img", file.getName(), imageBody);
//        requestBindCycle(context, apiStore.uploadImage(imageBodyPart), subscriber);
    }

    /**
     * 上传多张图片
     */
//    private void upLoad() {
//        List<String> pathList = getPathList();//此处是伪代码，获取多张待上传图片的地址列表
//        String token = "ASDDSKKK19990SDDDSS";//用户token
//        MultipartBody.Builder builder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)//表单类型
//                .addFormDataPart(ParamKey.TOKEN, token);//ParamKey.TOKEN 自定义参数key常量类，即参数名
//        //多张图片
//        for (int i = 0; i < pathList.size(); i++) {
//            File file = new File(pathList.get(i));//filePath 图片地址
//            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            builder.addFormDataPart("imgfile" + i, file.getName(), imageBody);//"imgfile"+i 后台接收图片流的参数名
//        }
//
//        List<MultipartBody.Part> parts = builder.build().parts();
//    }




    private interface ApiStore {
        @POST("authLogin.do")
        Observable<RequestResult<List<LoginInfoResponse>>> login(@Query("username") String username,
                                                                 @Query("password") String password,
                                                                 @Query("deviceType") int deviceType,
                                                                 @Query("deviceToken") String deviceToken);

        @GET("anyQueryAdvertisementList.do")
        Observable<RequestResult<List<AdvertisementResponse>>> getAd(@Query("code") String code,
                                                                     @Query("type") int type);

        /**
         * 上传单张图片
         */

        @Multipart
        @POST("fileUpload!upload")
        Observable<RequestResult<List<FileResponse>>> uploadImage(@Part MultipartBody.Part img);

        /**
         * 上传多张图片
         */
        @Multipart
        @POST("/member/uploadMemberIcon.do")
        Observable<RequestResult<List<FileResponse>>> uploadMemberIcon(@Part List<MultipartBody.Part> partList);

    }

}
