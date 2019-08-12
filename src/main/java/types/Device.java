package types;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "device")
public class Device {

    @Id
    private ObjectId deviceId;
    private String deviceName;
    private String deviceToken;

    private Channel listenningChannel;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Channel getListenningChannel() {
        return listenningChannel;
    }

    public void setListenningChannel(Channel listenningChannel) {
        this.listenningChannel = listenningChannel;
    }
}
