package tpgus.example.com.rros.result_for_select_one_restaurant;

public class list {

    private String food_image; //프로젝트 안에 이미지가 있는경우 int를 쓰고 외부는 String으로 교체
    private String name;
    private String price;
    private String info;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddr() {
        return Addr;
    }

        public void setAddr(String addr) {
            Addr = addr;
    }

    private String type;
    private String Addr;


    public list(String food_image, String name, String price, String info, String type) {
        this.food_image = food_image;
        this.name = name;
        this.price = price;
        this.info = info;
        this.type = type;
    }

    public String getFood_image() {
        return food_image;
    }

    public void setFood_image(String food_image) {
        this.food_image = food_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}
