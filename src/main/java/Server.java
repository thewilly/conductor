import types.Channel;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private String serverName;
    private List<Channel> channels;

    public void Server(String serverName) {
        this.serverName = serverName;
        this.channels = new ArrayList<Channel>();
    }

    public boolean setServerName(String serverName) {
        if(serverName != null && serverName != "") {
            this.serverName = serverName;
            return true;
        } return false;
    }

    public String getServerName() { return this.serverName; }

    public boolean addChannel(Channel channel) { return this.channels.add(channel); }

    public boolean removeChannel(Channel channel) { return this.channels.remove(channel); }
}
