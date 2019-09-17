package tpgus.example.com.rros;

/**
 * 사용자 정보를 저장하는 객체
 */
public class MemberInfoItem {

    public String id;
    public String nickname;
    public String name;
    public String email;
    public String gender;
    public String age;
    public String birthday;

    String CustomerNo;
    String Id;
    String CusName;

    public String getCustomerNo() {
        return CustomerNo;
    }

    public void setCustomerNo(String customerNo) {
        CustomerNo = customerNo;
    }

    public String getCusName() {
        return CusName;
    }

    public void setCusName(String cusName) {
        CusName = cusName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getFk_CouponNo_cus() {
        return fk_CouponNo_cus;
    }

    public void setFk_CouponNo_cus(String fk_CouponNo_cus) {
        this.fk_CouponNo_cus = fk_CouponNo_cus;
    }

    String Password;
    String PhoneNo;
    String Email;
    String Age;
    String Flag;
    String Image;
    String fk_CouponNo_cus;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setId(String id) {

        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public  MemberInfoItem(){

    }
    public MemberInfoItem(String id, String nickname, String name,String email, String gender, String age, String birthday, String profile_image) {
        this.id = id;
        this.nickname = nickname;
        this.name = name;
        this.email=email;
        this.gender = gender;
        this.age = age;
        this.birthday = birthday;
        this.profile_image = profile_image;
    }

    public MemberInfoItem(String customerNo, String id, String cusName, String password, String phoneNo, String email, String age, String flag, String image, String fk_CouponNo_cus) {
        CustomerNo = customerNo;
        Id = id;
        CusName = cusName;
        Password = password;
        PhoneNo = phoneNo;
        Email = email;
        Age = age;
        Flag = flag;
        Image = image;
    }

    public String profile_image;


}