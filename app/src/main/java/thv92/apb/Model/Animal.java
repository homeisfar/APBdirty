package thv92.apb.Model;

//Socrata imports
import com.socrata.android.client.SodaEntity;
import com.socrata.android.client.SodaField;
import com.socrata.android.soql.datatypes.Location;
import com.socrata.android.soql.datatypes.Url;

//Java imports
import java.util.Date;

/**
 * Model entity representing a remote record on the Soda sample datasets
 * http://dev.socrata.com/consumers/getting-started
 *
 */

@SodaEntity
public class Animal {

    @SodaField("animal_id")
    private String animaId;

    @SodaField("location")
    private Location location;

    @SodaField("at_aac")
    private String atAac;

    @SodaField("intake_date")
    private Date intakeDate;

    @SodaField("type")
    private String type;

    @SodaField("looks_like")
    private String looksLike;

    @SodaField("color")
    private String color;

    @SodaField("sex")
    private String sex;

    @SodaField("age")
    private String age;

    @SodaField("image")
    private Url image;

    public String getAnimaId() {
        return animaId;
    }

    public void setAnimaId(String animaId) {
        this.animaId = animaId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAtAac() {
        return atAac;
    }

    public void setAtAac(String atAac) {
        this.atAac = atAac;
    }

    public Date getIntakeDate() {
        return intakeDate;
    }

    public void setIntakeDate(Date intakeDate) {
        this.intakeDate = intakeDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLooksLike() {
        return looksLike;
    }

    public void setLooksLike(String looksLike) {
        this.looksLike = looksLike;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Url getImage() {
        return image;
    }

    public void setImage(Url image) {
        this.image = image;
    }
}
