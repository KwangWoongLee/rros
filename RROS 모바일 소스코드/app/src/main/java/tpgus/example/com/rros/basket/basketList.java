package tpgus.example.com.rros.basket;

public class basketList {

    public String getFood_image() {
        return food_image;
    }

    public void setFood_image(String food_image) {
        this.food_image = food_image;
    }

    private String food_image; //프로젝트 안에 이미지가 있는경우 int를 쓰고 외부는 String으로 교체
    private String name;
    private String price;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String info;
    private String type;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    private String count;
    public basketList(String food_image, String Name,String Count,String Price) {
        this.food_image = food_image;
        this.name = Name;
        this.count = Count;
        this.price = Price;
    }
    public basketList(String food_image) {
        this.food_image = food_image;


    }
}
