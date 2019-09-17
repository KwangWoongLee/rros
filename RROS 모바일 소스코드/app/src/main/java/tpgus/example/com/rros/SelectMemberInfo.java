package tpgus.example.com.rros;

import java.io.Serializable;

public class SelectMemberInfo implements Serializable {

public String id;
public String password;
public int customerNo;


    @Override
    public String toString()
    {
        return id;
    }

}
