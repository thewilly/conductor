package types;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "channels")
public class Channel {

    @Id
    private ObjectId channelId;
    private String channelName;

    public String getChannelName() { return this.channelName; }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
