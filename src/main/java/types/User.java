package types;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user")
public class User {

    @Id
    private ObjectId userId;

    private String userName;
    private String password;
    private boolean isAdmin;

    private List<Channel> channelsWithAccess;
    private List<Device> devicesWithAccess;

    public boolean canAccessToChannel(Channel channel) {
        return channelsWithAccess.contains(channel);
    }

    public boolean canAccessToDevice(Device device) {
        return channelsWithAccess.contains(device);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Channel> getChannelsWithAccess() {
        return channelsWithAccess;
    }

    public void setChannelsWithAccess(List<Channel> channelsWithAccess) {
        this.channelsWithAccess = channelsWithAccess;
    }

    public List<Device> getDevicesWithAccess() {
        return devicesWithAccess;
    }

    public void setDevicesWithAccess(List<Device> devicesWithAccess) {
        this.devicesWithAccess = devicesWithAccess;
    }
}
