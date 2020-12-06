package pooja.borkar.adminappfitness.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Expart_name {


    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("experties_type_name")
    @Expose
    private String experties_type_name;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getExperties_type_name() {
        return experties_type_name;
    }

    public void setExperties_type_name(String experties_type_name) {
        this.experties_type_name = experties_type_name;
    }
}