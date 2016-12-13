# TemplateApp
RxJava+RxAndroid+Retrofit模版

##自带的库
###common
#####第三方库
* [AutoLayout](https://github.com/hongyangAndroid/AndroidAutoLayout) 直接填写设计图上的像素尺寸即可完成适配
* [Easypermissions](https://github.com/googlesamples/easypermissions) Easypermissions简化了Android M的运行时权限的申请、结果处理、判断等。
* [RxJava](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [RxLifeCycle](https://github.com/trello/RxLifecycle)
* [RxBinding](https://github.com/JakeWharton/RxBinding) 使用RxBinding方式绑定控件, 异步监听事件
* [ButterKnief](https://github.com/JakeWharton/butterknife)
* [Glide](https://github.com/bumptech/glide)
* [PhotoView]()
* [BGAPhotoPicker-Android](https://github.com/bingoogolapple/BGAPhotoPicker-Android)

#####包base
类名 |功能
------------- | -------------
AppManager |activity管理
BaseActivity BaesFragment|普通activity和fragment的基类，继承自RxAppCompatActivity、RxFragment
BaseMvpActivity BaseMvpFragment|使用Mvp方式继承的父类，继承自RxAppCompatActivity、RxFragment
BaseFragmentAdapter|
BaseFragmentStateAdapter|
BaseModel|
RxBus|用RxJava实现的EventBus
RxManager|用于管理单个presenter的RxBus的事件和Rxjava相关代码的生命周期处理

#####包commonutils
类名 |功能
------------- | -------------
CloseUtils|关闭的数据源或目标
ConstUtils|存储相关常量、时间相关常量、正则相关常量
ConvertUtils|转换类
DisplayUtil|屏幕相关的辅助类
EncodeUtils|编码的工具
FormatUtil|格式化验证（判断身份证格式、判断是否是银行卡号、身份证的有效验证、验证日期字符串）
GlideCircleTransfromUtil|glide转圆形图片
GlideRoundTransformUtil|glide转换圆角图片
ImageLoaderUtils|图片加载工具类 使用glide框架封装
KeyBordUtil|输入框弹出管理
LocationUtils|定位相关工具类（记得添加定位的权限）
MoneyUtil|金钱的格式化工具
NetWorkUtils|网络相关的工具类
PhoneUtils|手机信息的工具类
RegexUtils|正则表达式验证
SdCardUtils|sdcard工具类
SnackbarUtils|snackbar
SPUtils|SharePreferences
StringUtils|
TimeUtil|
ToastUitl|

#####包commonwidget
类名 |功能
------------- | -------------
BannerLayout|轮播，使用glide加载图片
LoadingDialog|弹窗浮动加载进度条
LoadingTip|加载页面内嵌提示
MyRadioGroup|listview和recyclerview中可全局使用
NormalTitleBar|
NoScrollGridView、NoScrollListView|不会滚动的
onDoubleClickListener|双击事件
onNoDoubleClickListener|防止重复点击,可使用RxBinding代替
RecyclerViewDivider|RecyclerView的分割线
StatusBarCompat|透明状态栏
ViewPagerFixed|

#####包compressorutils
类名 |功能
------------- | -------------
Compressor|压缩图片
FileUtil|文件处理
ImageUtil|图像工具

#####包daynightmodeutils
```
	/**
     * 
     * @param ctx
     * @param mode MODE_DAY
     *             MODE_NIGHT
     */
	ChangeModeHelper.setChangeMode(Context ctx, int mode);
```

#####包imagePager
```
	/**
     * 
     * @param activity
     * @param imgUrls 图片地址的集合
     * @param position 当前查看图片的位置
     */
     BigImagePagerActivity.startImagePagerActivity(Activity activity, List<String> imgUrls, int position)；
```

#####包loadinglayout ---> 详细用法查看[LoadingLayout](https://github.com/weavey/LoadingLayoutDemo)
  
#####包security
类名 |功能
------------- | -------------
AESUtil|AES加解密算法
CodeSecurity|加解密方法的封装类
DESBase64Util|客户端与服务器通讯时对消息体加密和解密的工具类
Md5Security|MD5加密类

###irecyclerview

###retrofitutils
