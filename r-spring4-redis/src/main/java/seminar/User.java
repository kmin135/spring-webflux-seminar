package seminar;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {

    private int id;
    private String fullName;
    private String data1;
    private String data2;
    private String data3;
    private String data4;
    private String data5;
}
