package tpgus.example.com.rros.result_for_select_one_restaurant;

public class MenuList {

    public String getFood_image() {
        return food_image;
    }

    public void setFood_image(String food_image) {
        this.food_image = food_image;
    }

    private String food_image; //프로젝트 안에 이미지가 있는경우 int를 쓰고 외부는 String으로 교체
    private String name;

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

    private String price;
    private String info;


    public MenuList(String food_image, String menu_name, String price) {
        this.food_image = food_image;
        this.price = price;
        this.name = menu_name;

    }

}
