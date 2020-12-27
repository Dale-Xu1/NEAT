package neat.network;

import neat.population.genome.Gene;
import neat.population.genome.Genome;

public class Network
{

    private final Node[] nodes;

    private final Node[] inputs;
    private final Node bias;

    private final Node[] outputs;


    public Network(Genome genome)
    {
        // Construct network from genome
        nodes = new Node[genome.getNodes()];

        inputs = new Node[genome.getInputs()];
        outputs = new Node[genome.getOutputs()];

        // Create nodes
        for (int i = 0; i < nodes.length; i++)
        {
            nodes[i] = new Node();
        }

        // Organize node references
        int n = 0;
        for (int i = 0; i < inputs.length; i++)
        {
            inputs[i] = nodes[n++];
        }
        bias = nodes[n++];

        for (int i = 0; i < outputs.length; i++)
        {
            outputs[i] = nodes[n++];
        }

        // Initialize connections
        for (Gene gene : genome.getGenes())
        {
            Node in = nodes[gene.getIn()];
            Node out = nodes[gene.getOut()];

            // Create connection
            Connection connection = new Connection(in, gene.getWeight());
            out.addConnection(connection);
        }
    }

}
