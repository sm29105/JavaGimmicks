package net.sf.javagimmicks.graph;

/**
 * Specifies a factory that can create concrete {@link Edge} instances for a
 * given {@link Graph} and two vertices - used for example by {@link MapGraph}
 * instances.
 * 
 * @param <VertexType>
 *           the type of vertices of the {@link Edge}s to create
 * @param <EdgeType>
 *           the concrete type of {@link Edge}s to create
 * @see MapGraphBuilder#setEdgeFactory(EdgeFactory)
 */
public interface EdgeFactory<VertexType, EdgeType extends Edge<VertexType, EdgeType>>
{
   /**
    * Creates a new {@link Edge} of type {@code EdgeType} for the given
    * {@link Graph} and vertices.
    * 
    * @param graph
    *           the {@link Graph} to create an {@link Edge} for
    * @param source
    *           the first vertex to create the {@link Edge} for
    * @param target
    *           the second vertex to create the {@link Edge} for
    * @return the resulting {@link Edge}
    */
   public EdgeType createEdge(Graph<VertexType, EdgeType> graph, VertexType source, VertexType target);
}
