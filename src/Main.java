/**
 * TODO
 *
 * @Description
 * @Author zm_hs
 * @Date 2023/7/19 0019 下午 04:19
 **/
public class Main {
    public static void main(String[] args) {
        String workspace = "/tmp/hsperfdata_es/execfile";
        String mergedExecFileName = "jacoco-211.exec";
        boolean result = CoverageOperate.merge(workspace, mergedExecFileName, null);
        System.out.println(result);
        System.out.println(result+"提交测试dev分支");
    }
}