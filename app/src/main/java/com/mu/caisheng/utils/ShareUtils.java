package com.mu.caisheng.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;

import com.mu.caisheng.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

/**
 * @author Mu
 * @date 2015-6-11
 * @description 分享工具类
 */
public class ShareUtils {
    /**
     * @param context
     * @param content
     * @param imageUrl
     * @param url
     * @return
     */
    public static UMSocialService Share(final Context context, String content,
                                        String imageUrl, String url) {
        final UMSocialService mController = UMServiceFactory
                .getUMSocialService("com.umeng.share");
        // 设置分享内容
        mController.setShareContent(content);
        // 设置分享图片, 参数2为图片的url地址
        mController.setShareMedia(new UMImage(context, BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher)));
//
//        if (!ToosUtils.isStringEmpty(imageUrl)) {
//            if (imageUrl.contains("http://")) {
//                mController.setShareMedia(new UMImage(context, imageUrl));
//            } else {
//                mController.setShareMedia(new UMImage(context, BitmapFactory
//                        .decodeFile(imageUrl)));
//            }
//        }

        // 微信
        String appID = "wxb9e65e2f4a6e0ee6";
        String appSecret = "d4624c36b6795d1d99dcf0547af5443d";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(context, appID, appSecret);
        wxHandler.showCompressToast(false);
        if (!ToosUtils.isStringEmpty(url)) {
            wxHandler.setTargetUrl(url);
        }
        wxHandler.addToSocialSDK();
        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(context, appID, appSecret);
        wxCircleHandler.showCompressToast(false);
        wxCircleHandler.setToCircle(true);
        if (!ToosUtils.isStringEmpty(url)) {
            wxHandler.setTargetUrl(url);
        }
        wxCircleHandler.addToSocialSDK();

        // 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) context,
                "1104908839", "LM1MtvNLstcGppUa");
        qqSsoHandler.setTargetUrl(url);
        qqSsoHandler.addToSocialSDK();

        // 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
                (Activity) context, "1104908839", "LM1MtvNLstcGppUa");
        if (!ToosUtils.isStringEmpty(url)) {
            qZoneSsoHandler.setTargetUrl(url);
        }
        qZoneSsoHandler.addToSocialSDK();

        SinaSsoHandler sinaSsoHandler = new SinaSsoHandler();
        if (!ToosUtils.isStringEmpty(url)) {
            sinaSsoHandler.setTargetUrl(url);
        }

        // mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
        // 设置新浪SSO handler
        mController.getConfig().setSsoHandler(sinaSsoHandler);

        mController.getConfig().removePlatform(SHARE_MEDIA.TENCENT);
        mController.getConfig().registerListener(
                new SocializeListeners.SnsPostListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i,
                                           SocializeEntity socializeEntity) {

                    }
                });
        mController.getConfig().removePlatform(SHARE_MEDIA.QZONE);
        mController.openShare((Activity) context, false);

        return mController;

    }


}
