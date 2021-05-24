package com.example.udpdatetest;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.xuexiang.xaop.annotation.Permission;
import com.xuexiang.xaop.consts.PermissionConsts;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.proxy.impl.DefaultUpdateChecker;
import com.xuexiang.xupdate.service.OnFileDownloadListener;
import com.xuexiang.xutil.app.IntentUtils;
import com.xuexiang.xutil.app.PathUtils;
import com.xuexiang.xutil.tip.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_one)
    Button btnOne;
    @BindView(R.id.btn_two)
    Button btnTwo;
    @BindView(R.id.btn_three)
    Button btnThree;
    @BindView(R.id.btn_four)
    Button btnFour;
    @BindView(R.id.btn_five)
    Button btnFive;
    private final static int REQUEST_CODE_SELECT_APK_FILE = 1000;

    protected Unbinder unbinder;

    private String mUpdateUrl = "https://gitee.com/xuexiangjys/XUpdate/raw/master/jsonapi/update_test.json";

    private String mUpdateUrl2 = "https://gitee.com/xuexiangjys/XUpdate/raw/master/jsonapi/update_forced.json";

    private String mUpdateUrl3 = "https://gitee.com/xuexiangjys/XUpdate/raw/master/jsonapi/update_custom.json";

    private String mDownloadUrl = "https://xuexiangjys.oss-cn-shanghai.aliyuncs.com/apk/xupdate_demo_1.0.2.apk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_one, R.id.btn_two, R.id.btn_three, R.id.btn_four, R.id.btn_five})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                XUpdate.newBuild(this)
                        .updateUrl(mUpdateUrl)
                        .update();
                break;
            case R.id.btn_two:
                XUpdate.newBuild(this)
                        .updateUrl(mUpdateUrl)
                        .promptThemeColor(getResources().getColor(R.color.colorAccent))
                        .promptButtonTextColor(Color.WHITE)
                        .promptTopResId(R.mipmap.bg_update_top)
                        .promptWidthRatio(0.7F)
                        .update();
                break;
            case R.id.btn_three:
                XUpdate.newBuild(this)
                        .updateUrl(mUpdateUrl)
                        .updateChecker(new DefaultUpdateChecker() {
                            @Override
                            public void onBeforeCheck() {
                                super.onBeforeCheck();
                                CProgressDialogUtils.showProgressDialog(MainActivity.this, "查询中...");
                            }
                            @Override
                            public void onAfterCheck() {
                                super.onAfterCheck();
                                CProgressDialogUtils.cancelProgressDialog(MainActivity.this);
                            }
                        })
                        .updatePrompter(new CustomUpdatePrompter(this))
                        .update();
                break;
            case R.id.btn_four:
                XUpdate.newBuild(this)
                        .updateUrl(mUpdateUrl3)
                        .updateParser(new CustomUpdateParser())
                        .update();
                break;
            case R.id.btn_five:
                useApkDownLoadFunction();
                break;
        }
    }


    @Permission(PermissionConsts.STORAGE)
    private void useApkDownLoadFunction() {
        XUpdate.newBuild(this)
                .apkCacheDir(PathUtils.getExtDownloadsPath())
                .build()
                .download(mDownloadUrl, new OnFileDownloadListener() {
                    @Override
                    public void onStart() {
                        HProgressDialogUtils.showHorizontalProgressDialog(MainActivity.this, "下载进度", false);
                    }

                    @Override
                    public void onProgress(float progress, long total) {
                        HProgressDialogUtils.setProgress(Math.round(progress * 100));
                    }

                    @Override
                    public boolean onCompleted(File file) {
                        HProgressDialogUtils.cancel();
                        ToastUtils.toast("apk下载完毕，文件路径：" + file.getPath());
                        return false;
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        HProgressDialogUtils.cancel();
                    }
                });
    }

    @Permission(PermissionConsts.STORAGE)
    private void selectAPKFile() {
        startActivityForResult(IntentUtils.getDocumentPickerIntent(IntentUtils.DocumentType.ANY), REQUEST_CODE_SELECT_APK_FILE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  unbinder.unbind();
    }
}
