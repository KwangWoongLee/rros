package tpgus.example.com.rros;

public class ResListitem {
    private String mName;
    private String mNumber;
    private String mAddedOn;

    public ResListitem() {}
    public ResListitem(String name, String number, String addedOn) {
        mName = name;
        mNumber = number;
        mAddedOn = addedOn;
    }

    public String getmAddedOn() {
        return mAddedOn;
    }

    public String getmName() {
        return mName;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmAddedOn(String mAddedOn) {
        this.mAddedOn = mAddedOn;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }
}

