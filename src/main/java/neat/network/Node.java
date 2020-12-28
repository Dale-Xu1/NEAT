package neat.network;

import java.util.ArrayList;
import java.util.List;

public class Node
{

    private final List<Connection> connections = new ArrayList<>();


    public void addConnection(Connection connection)
    {
        connections.add(connection);
    }

}
