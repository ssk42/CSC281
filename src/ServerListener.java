import java.net.InetAddress;

/**
 * @author knappa
 * @version 1.0
 */
public interface ServerListener {

    /**
     * Called when a server is found
     *
     * @param address address of server
     */
    void onServerFound(InetAddress address);

}
