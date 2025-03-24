package project.demo.security.resultdata;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData {
    private String code;
    private String msg;
    private Object data;
    public static RsData of(String code, String msg){
        return new RsData(code, msg, null);
    }
    public static RsData of(String code, String msg, Object data){
        return new RsData(code, msg, data);
    }

    public boolean success() {
        return code.startsWith("S");
    }
}
