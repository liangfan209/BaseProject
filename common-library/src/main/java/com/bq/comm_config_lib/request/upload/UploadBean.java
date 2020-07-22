package com.bq.comm_config_lib.request.upload;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/22
 * 版权：
 */
public class UploadBean {

    /**
     * status : ok
     * result : {"file_paths":["/resource/1/image_1595384418.image"]}
     */

    private String status;
    private ResultBean result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<String> file_paths;

        public List<String> getFile_paths() {
            return file_paths;
        }

        public void setFile_paths(List<String> file_paths) {
            this.file_paths = file_paths;
        }
    }
}
