package pooja.borkar.adminappfitness.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usercount {


    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("total_user")
    @Expose
    private String total_user;
    @SerializedName("active_user")
    @Expose
    private String active_user;
    @SerializedName("total_expert")
    @Expose
    private String total_expert;
    @SerializedName("active_expert")
    @Expose
    private String active_expert;
    @SerializedName("admin_baner_url")
    @Expose
    private String admin_baner_url;
    @SerializedName("admin_baner_type")
    @Expose
    private String admin_baner_type;

    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("experties_type_name")
    @Expose
    private String experties_type_name;



    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getTotal_user() {
        return total_user;
    }

    public void setTotal_user(String total_user) {
        this.total_user =total_user;
    }

    public String getActive_user() {
        return active_user;
    }

    public void setActive_user(String active_user) {
        this.active_user= active_user;
    }



    public String getTotal_expert() {
        return total_expert;
    }

    public void setTotal_expert(String gender) {
        this.total_expert = total_expert;
    }

    public String getActive_expert() {
        return active_expert;
    }

    public void setActive_expert(String active_expert) {
        this.active_expert = active_expert;
    }


    public String getAdmin_baner_type() {
        return admin_baner_type;
    }

    public void setAdmin_baner_type(String admin_baner_type) {
        this.admin_baner_type = admin_baner_type;
    }

    public String getAdmin_baner_url() {
        return admin_baner_url;
    }

    public void setAdmin_baner_url(String admin_baner_url) {
        this.admin_baner_url = admin_baner_url;
    }

         public  Usercount (String admin_baner_url)
        {
            this.admin_baner_url = admin_baner_url;


        }

}