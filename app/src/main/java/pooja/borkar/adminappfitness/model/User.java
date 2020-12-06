package pooja.borkar.adminappfitness.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {


    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName(" profession")
    @Expose
    private String  profession;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("otp")
    @Expose
    private String otp;


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.phone = path;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.dob = active;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setProfession(String profession) {
        this.profession= profession;
    }
    public String getProfession() {
        return profession;
    }




    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String createdAt) {
        this.created_at = created_at;
    }

    public  User(String name, String email,String phone, String active,String image) {
        this.name = name;
        this.email = email;

        this.phone = phone;
        this.active = active;

        this.image = image;


    }}