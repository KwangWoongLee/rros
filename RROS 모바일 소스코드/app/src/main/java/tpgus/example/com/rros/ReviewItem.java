package tpgus.example.com.rros;

public class ReviewItem {
    String Resname;
    String addr;
    int imgId;
    String type;
    String review;
    int LicenseNo;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    String Content;

    public ReviewItem(String Resname,String type,String review,  String addr, int imgId) {
        this.Resname = Resname;
        this.addr = addr;
        this.imgId = imgId;
        this.review = review;
        this.type = type;
    }

    @Override
    public String toString() {
        return "MyItem{" +
                "name='" + Resname + '\'' +
                ", addr='" + addr + '\'' +
                ", type='" + type + '\'' +
                ", review='" + review + '\'' +
                ", img=" + imgId +
                '}';
    }

    public String getResname() {
        return Resname;
    }

    public void setName(String Resname) {
        this.Resname = Resname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getImg() {
        return imgId;
    }

    public void setImg(int imgId) {
        this.imgId = imgId;
    }

    public int getLicense() {
        return LicenseNo;
    }

    public void setAddr(int LicenseNo) {
        this.LicenseNo = LicenseNo;
    }


}

