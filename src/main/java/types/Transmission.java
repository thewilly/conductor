package types;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "transmission")
public class Transmission {

    @Id
    private ObjectId transmissionId;
    private Date date;

    public Transmission() {
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }
}
