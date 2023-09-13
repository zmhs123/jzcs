/**
 * @(#)CoverageOperate.java, 2020/11/12.
 * <p/>
 * Copyright 2020 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import cn.tech.wings.executor.CovOperateExecutor;
import org.jacoco.core.tools.ExecDumpClient;
import org.jacoco.core.tools.ExecFileLoader;

import java.io.File;

/**
 * 覆盖率操作
 *
 * @author  on 2020/11/12.
 */
public class CoverageOperate {


    /**
     * 远程清空覆盖率操作
     *
     * @param ip   目标应用所在ip
     * @param port 目标应用的port
     * @return
     */
    public static boolean reset(String ip, Integer port) {
        return CovOperateExecutor.reset(ip, port);
    }


    /**
     * 远程dump exec文件
     *
     * @param ip           目标应用所在ip
     * @param port         目标应用的port
     * @param execFilePath 目标exec文件存放路径
     * @return
     */
    public static boolean dump(String ip, Integer port, String execFilePath,String appCode,String tracePushUrl) {

        try {
            ExecDumpClient client = new ExecDumpClient();
            client.setDump(true);
            client.setAppCode(appCode);
            client.setTracePushUrl(tracePushUrl);
            ExecFileLoader execFileLoader = client.dump(ip, port);
            File file = new File(execFilePath);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }

            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();
            execFileLoader.save(file, true,appCode,tracePushUrl);
            if (file.length() == 0L) {
                return false;
            } else {
                return true;
            }
        } catch (Exception var7) {
            var7.printStackTrace();
            return false;
        }
    }


    /**
     * exec文件合并
     * 1、用于多台应用实例覆盖率需要合并的场景
     * 2、用于历史和新的覆盖率数据合并场景
     *
     * @param execPath 需要合并exec所在路径
     * @param destFile 合并后目标的exec文件名
     * @param ip       应用ip（exec文件名中带有相同ip标识的会做合并操作，为空则不指定）
     * @return
     */
    public static boolean merge(String execPath, String destFile, String ip) {
        CovMergeExecutor covMergeExecutor = new CovMergeExecutor(execPath, destFile);
        return covMergeExecutor.executeMerge(ip);
    }

    public static boolean merge(String execPath, String destFile, String ip, int mergeMode) {
        CovMergeExecutor covMergeExecutor = new CovMergeExecutor(execPath, destFile);
        return covMergeExecutor.executeMerge(ip, mergeMode);
    }
}