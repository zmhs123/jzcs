/**
 * @(#)MergeDumpAction.java, 2020/7/15.
 * <p/>
 * Copyright 2020 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import cn.tech.wings.constant.ConstantVar;
import cn.tech.wings.utils.FileUtils;
import org.jacoco.core.tools.ExecFileLoader;

import java.io.File;
import java.io.IOException;

/**
 * 覆盖率合并执行器
 *
 * @author on 2020/7/15.
 */
public class CovMergeExecutor {

    private final String path; //exec所在路径
    private final File destFile; //目标合并的exec文件

    public CovMergeExecutor(String path, String destFileName) {
        this.path = path;
        this.destFile = new File(path + ConstantVar.FILE_SEPARATOR + destFileName);
    }

    /**
     * 执行merge
     *
     * @throws
     */
    public boolean executeMerge(String ip) {
        final ExecFileLoader loader = new ExecFileLoader();
        try {
            load(loader, ip);
            save(loader);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 执行merge
     *
     * @throws
     */
    public boolean executeMerge(String ip, int mergeMode) {
        final ExecFileLoader loader = new ExecFileLoader(mergeMode);
        try {
            load(loader, ip);
            save(loader);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 加载dump文件
     *
     * @param loader
     * @throws
     */
    private void load(final ExecFileLoader loader, String ip) throws IOException {
        for (final File fileSet : FileUtils.fileSets(this.path)) {
            final File inputFile = new File(this.path, fileSet.getName());
            if (inputFile.isDirectory()) {
                continue;
            }
            try {
                System.out.println("Loading execution data file :" + inputFile.getAbsolutePath() + ", file size :" + FileUtils.getFileSize(inputFile));
                loader.load(inputFile);
            } catch (IOException e) {
                throw new IOException("Unable to read " + inputFile.getAbsolutePath(), e);
            }
        }
    }

    /**
     * 执行合并文件和保存
     *
     * @param loader
     * @throws IOException
     */
    private void save(final ExecFileLoader loader) throws IOException {
 /*       if (loader.getExecutionDataStore().getContents().isEmpty()) {
            log.debug("Skipping JaCoCo merge execution due to missing execution data files");
            return;
        }*/
        try {
            System.out.println("start save ...");
            loader.save(this.destFile, true);
            System.out.println("save end ... Writing merged execution data to " + this.destFile.getAbsolutePath() + ", file size:" + FileUtils.getFileSize(this.destFile));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Unable to write merged file " + this.destFile.getAbsolutePath(), e);
        }
    }

}