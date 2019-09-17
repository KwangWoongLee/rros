package tpgus.example.com.rros;

import android.media.Image;

public class RecyclerViewItem {
    private String Name;
    private String Type;
    private String Addr;
    private Double x;
    private Double y;

    public RecyclerViewItem(){}

    public RecyclerViewItem (String Image, String Name, String Type, String Addr, String Info, String Review,Double x, Double y){
     this.Image = Image;
     this.Name=  Name;
     this.Type = Type;
     this.Addr= Addr;
     this.Info = Info;
     this.Review = Review;
     this.x= x;
     this.y = y;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        this.Addr = addr;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        this.Review = review;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        this.Info = info;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public void setX(Double x) {this.x = x;}
    public void setY(Double y) {this.y = y;}

    public Double getX(){return x;}
    public Double getY(){return y;}

    private String Review;
    private String Info;
    private String Image;
}
