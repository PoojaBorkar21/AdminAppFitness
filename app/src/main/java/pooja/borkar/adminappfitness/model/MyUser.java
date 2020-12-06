package pooja.borkar.adminappfitness.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyUser {
    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("plan_sups_id")
    @Expose
    private String plan_sups_id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("user_name")
    @Expose
    private String user_name;
    @SerializedName("expert_name")
    @Expose
    private String expert_name;

    @SerializedName("expert_plans_name")
    @Expose
    private String expert_plans_name;

    @SerializedName("image")
    @Expose
    private String image;

    public MyUser(String plan_sups_id, String user_name, String image, String expert_plans_name,String expert_name ) {
        this.user_name=user_name;
        this.image =image  ;
        this. expert_plans_name =expert_plans_name  ;

        this.plan_sups_id=plan_sups_id;
        this.expert_name=expert_name;
    }

    public String getPlan_sups_id() {
        return plan_sups_id;
    }
    public String getResponse() {
        return response;
    }

    public String getEmail() {
        return email;
    }

    public String getUser_name() {
        return user_name;
    }
    public String getExpert_name() {
        return expert_name;
    }


    public String getExpert_plans_name (){
        return expert_plans_name;
    }
    public String getImage() {
        return image;
    }



}
