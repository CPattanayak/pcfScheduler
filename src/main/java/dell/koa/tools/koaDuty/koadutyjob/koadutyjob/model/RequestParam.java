package dell.koa.tools.koaDuty.koadutyjob.koadutyjob.model;

public class RequestParam {

    private String apiUrl;

    private String orgName;

    private String interval;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return "RequestParam{" +
                "apiUrl='" + apiUrl + '\'' +
                ", orgName='" + orgName + '\'' +
                ", interval='" + interval + '\'' +
                '}';
    }
}
