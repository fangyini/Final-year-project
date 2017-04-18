package sockets;

public interface ReceiveListener {
    public void dataReceive(Client client, String data);
}

