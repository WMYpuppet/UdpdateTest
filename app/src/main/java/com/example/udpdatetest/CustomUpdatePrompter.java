package com.example.udpdatetest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;

import com.xuexiang.xupdate.entity.PromptEntity;
import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.proxy.IUpdatePrompter;
import com.xuexiang.xupdate.proxy.IUpdateProxy;
import com.xuexiang.xupdate.service.OnFileDownloadListener;
import com.xuexiang.xupdate.utils.UpdateUtils;

import java.io.File;

/**
 * 作者：Created by Administrator on 2021/5/21.
 * 邮箱：
 */
public class CustomUpdatePrompter implements IUpdatePrompter {
    private Context mContext;

    public CustomUpdatePrompter(Context context) {
        mContext = context;
    }
    /**
     * 显示自定义提示
     *
     * @param updateEntity
     * @param updateProxy
     */
    private void showUpdatePrompt(final @NonNull UpdateEntity updateEntity, final @NonNull IUpdateProxy updateProxy) {
//        String updateInfo = UpdateUtils.getDisplayUpdateInfo(updateProxy.getContext(), updateEntity);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(updateProxy.getContext())
//                .setTitle(String.format("是否升级到%s版本？", updateEntity.getVersionName()))
//                .setMessage(updateInfo)
//                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        updateProxy.startDownload(updateEntity, new OnFileDownloadListener() {
//                            @Override
//                            public void onStart() {
//                                HProgressDialogUtils.showHorizontalProgressDialog(updateProxy.getContext(), "下载进度", false);
//                            }
//
//                            @Override
//                            public void onProgress(float progress, long total) {
//                                HProgressDialogUtils.setProgress(Math.round(progress * 100));
//                            }
//
//                            @Override
//                            public boolean onCompleted(File file) {
//                                HProgressDialogUtils.cancel();
//                                return true;
//                            }
//
//                            @Override
//                            public void onError(Throwable throwable) {
//                                HProgressDialogUtils.cancel();
//                            }
//                        });
//                    }
//                });
//        if (updateEntity.isIgnorable()) {
//            builder.setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    UpdateUtils.saveIgnoreVersion(updateProxy.getContext(), updateEntity.getVersionName());
//                }
//            }).setCancelable(true);
//        } else  {
//            builder.setCancelable(false);
//        }
//        builder.create().show();
        String updateInfo = UpdateUtils.getDisplayUpdateInfo(mContext, updateEntity);

        new AlertDialog.Builder(mContext)
                .setTitle(String.format("是否升级到%s版本？", updateEntity.getVersionName()))
                .setMessage(updateInfo)
                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateProxy.startDownload(updateEntity, new OnFileDownloadListener() {
                            @Override
                            public void onStart() {
                                HProgressDialogUtils.showHorizontalProgressDialog(mContext, "下载进度", false);
                            }

                            @Override
                            public void onProgress(float progress, long total) {
                                HProgressDialogUtils.setProgress(Math.round(progress * 100));
                            }

                            @Override
                            public boolean onCompleted(File file) {
                                HProgressDialogUtils.cancel();
                                return true;
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                HProgressDialogUtils.cancel();
                            }
                        });
                    }
                })
                .setNegativeButton("暂不升级", null)
                .setCancelable(false)
                .create()
                .show();
    }

    /**
     * 显示版本更新提示
     *
     * @param updateEntity 更新信息
     * @param updateProxy  更新代理
     * @param promptEntity 提示界面参数
     */
    @Override
    public void showPrompt(@NonNull UpdateEntity updateEntity, @NonNull IUpdateProxy updateProxy, @NonNull PromptEntity promptEntity) {
        showUpdatePrompt(updateEntity, updateProxy);
    }
}
