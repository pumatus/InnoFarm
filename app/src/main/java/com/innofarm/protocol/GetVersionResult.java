package com.innofarm.protocol;

/**
 * Created by dell on 2015/11/11.
 */
public class GetVersionResult {

    public String return_sts;
    public String version;

    public String getReturn_sts() {
        return return_sts;
    }

    public void setReturn_sts(String return_sts) {
        this.return_sts = return_sts;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "GetVersionResult{" +
                "return_sts='" + return_sts + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
