import java.net.InetAddress;

/**
 * @author knappa
 * @version 1.0
 */
public interface ClientListener {

    /**
     * Called when a server is found
     *
     * @param address address of server
     */
    void onClientFound(InetAddress address);

}
