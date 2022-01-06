package dinve.mesa.converter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class File {
    private String name;
    private String url;

    public File(String name,String url){
        super();
        this.name = name;
        this.url = url;
    }


}
