package tpgus.example.com.rros;

public class menuListItem {
    private String mName;
    private String mContent;
    private String mNum;

    public menuListItem() {}

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public void setmImg(String mImg) {
        this.mNum = mImg;
    }

    public menuListItem(String name, String Content, String num) {
        mName = name;
        mContent = Content;
        mNum = num;

    }

    public String getmName() {
        return mName;
    }

    public String getmContent() {
        return mContent;
    }

    public String getmNum() {
        return mNum;
    }
}

