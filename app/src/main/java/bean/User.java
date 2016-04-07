package bean;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Natalya on 04/03/16.
 */
public class User {
    @SerializedName("id")
    public String idUrl;

    @SerializedName("name")
    public String nameUrl;

    @SerializedName("picture")
    public String pictureUrl;

    @SerializedName("title")
    public String titleUrl;

    @SerializedName("text")
    public String textUrl;

    public User(String id, String name, String picture, String title, String text){
        this.idUrl = id;
        this.nameUrl = name;
        this.pictureUrl = picture;
        this.titleUrl = title;
        this.textUrl = text;
    }

    public User(){}
}
