package in.hoptec.anyshare.model;


import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import in.hoptec.anyshare.Constants;

/**
 * Created by shivesh on 29/6/17.
 */

public class GenricUser {

    private String name;
    private String alias;
    private String password ;
    private String id;
    private String email;
    private String image;
    private String avatar;
    private String phone;
    private String gender ="male";
    private String type;
    private String dateofbirth;
    private String about;

    @SerializedName("diseasesIds")
    @Expose
    private ArrayList<String> diseaseIds  ;
    private String fcmToken;
    private String status;
    private String verifdoc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVerifdoc() {
        return verifdoc;
    }

    public void setVerifdoc(String verifdoc) {
        this.verifdoc = verifdoc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAlias() {
        return alias;
    }

    public String getAliasAndVerif() {
        if(status.startsWith("VERIFIED"))
            return alias+ Constants.V2V_VERIFIED;
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return id;
    }

    public void setUid(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender.toLowerCase();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Nullable
    public String getAge() {

        if(dateofbirth==null )
            return null;

        Date d=new Date(Long.parseLong(dateofbirth));
        int age= Calendar.getInstance().getTime().getYear()-
                d.getYear();

        return ""+age;

    }


    @Nullable
    public int getAgeInt() {

        if(dateofbirth==null )
            return 0;

        Date d=new Date(Long.parseLong(dateofbirth));
        int age= Calendar.getInstance().getTime().getYear()-
                d.getYear();

        return  age;

    }

    public String  getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public ArrayList<String> getDiseaseIds() {
        return diseaseIds;
    }

    public void setDiseaseIds(ArrayList<String> diseaseIds) {
        this.diseaseIds = diseaseIds;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public GenricUser()
    {

    }


    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
