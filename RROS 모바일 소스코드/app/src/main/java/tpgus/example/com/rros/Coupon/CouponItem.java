package tpgus.example.com.rros.Coupon;

public class CouponItem {
    public String getCall() {
        return Call;
    }

    public void setCall(String call) {
        Call = call;
    }

    String Call;
    public String getFk_LienseNo_coupon() {
        return fk_LicenseNo_coupon;
    }

    public void setFk_LicenseNo_coupon(String fk_LIcenseNo_coupon) {
        this.fk_LicenseNo_coupon = fk_LicenseNo_coupon;
    }

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    public String getCouponDate() {
        return couponDate;
    }

    public void setCouponDate(String couponDate) {
        this.couponDate = couponDate;
    }

    public String getCouponContent() {
        return couponContent;
    }

    public void setCouponContent(String couponContent) {
        this.couponContent = couponContent;
    }

    String fk_LicenseNo_coupon;
    String couponNo;
    String couponDate;
    String couponContent;
    String Image;
    public String id;
    String Life;


    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getLife() {
        return Life;
    }

    public void setLife(String life) {
        Life = life;
    }

    String Content;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    String Name;
    String Condition;
    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public CouponItem() {

    }
    public CouponItem(String Call,String fk_LicenseNo_coupon,String Image,String Content,String Life,String Name, String Condition) {
        this.fk_LicenseNo_coupon = fk_LicenseNo_coupon;
        this.Image = Image;
        this.Content = Content;
        this.Life = Life;
        this.Name = Name;
        this.Condition = Condition;
        this.Call = Call;
    }


}

