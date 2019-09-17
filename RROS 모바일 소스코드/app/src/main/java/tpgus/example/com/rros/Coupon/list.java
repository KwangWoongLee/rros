package tpgus.example.com.rros.Coupon;

import android.graphics.Bitmap;

public class list {



    private String profile_image; //프로젝트 안에 이미지가 있는경우 int를 쓰고 외부는 String으로 교체
    private String Name;
    private String Type;
    private String Addr;
    private String Review;
    private Bitmap bitmap;
    private int license;
    private String url;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        Addr = addr;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }


    public list(String profile_image, String Name, String Type, String Addr, String Review, int license) {
        this.profile_image = profile_image;
        this.Name = Name;
        this.Type = Type;
        this.Addr = Addr;
        this.Review = Review;
        this.license = license;
    }

    public String getProfile_image() {
        return profile_image;
    }
    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getLicense() {
        return license;
    }

    public void setLicense(int license) {
        this.license = license;
    }
}
