package com.example.udpdatetest;

import java.io.Serializable;

/**
 * 作者：Created by Administrator on 2021/5/21.
 * 邮箱：
 */
public class CustomResult  implements Serializable {
    public boolean hasUpdate;

    public boolean isIgnorable;

    public int versionCode;

    public String versionName;

    public String updateLog;

    public String apkUrl;

    public long apkSize;
}
