package net.sf.javagimmicks.swing.model;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import net.sf.javagimmicks.collections.transformer.TransformerUtils;
import net.sf.javagimmicks.transform.Transformer;

/**
 * An abstract base implementation of {@link TypedParentTreeNode} that provides
 * some basics for child handling.
 */
public abstract class AbstractTypedParentTreeNode<Value, ChildValue, ChildNode extends TypedChildTreeNode<? extends ChildValue, Value, ? extends TypedParentTreeNode<?, ?, ?>>>
      extends AbstractTypedTreeNode<Value> implements TypedParentTreeNode<Value, ChildValue, ChildNode>
{
   protected AbstractTypedParentTreeNode(final Value value, final boolean noChildrenMeansLeaf)
   {
      super(value, true, noChildrenMeansLeaf);
   }

   protected AbstractTypedParentTreeNode(final Value value)
   {
      super(value, true);
   }

   @Override
   public Enumeration<? extends ChildNode> children()
   {
      final List<? extends ChildNode> childNodesList = getChildNodes();
      return Collections.enumeration(childNodesList);
   }

   @Override
   public ChildNode getChildAt(final int childIndex)
   {
      return buildChildNode(getChildValues().get(childIndex));
   }

   @Override
   public int getChildCount()
   {
      return getChildValues().size();
   }

   @Override
   public int getIndex(final TreeNode node)
   {
      if (!(node instanceof TypedTreeNode<?>))
      {
         return -1;
      }

      final Object childValue = ((TypedTreeNode<?>) node).getValue();

      return getChildValues().indexOf(childValue);
   }

   protected List<ChildNode> getChildNodes()
   {
      final List<ChildValue> childValues = getChildValues();

      if (childValues == null || childValues.isEmpty())
      {
         return Collections.emptyList();
      }

      return TransformerUtils.decorate(childValues, _valueToNodeTransformer);
   }

   abstract protected ChildNode buildChildNode(ChildValue childValue);

   private final Transformer<ChildValue, ChildNode> _valueToNodeTransformer = new Transformer<ChildValue, ChildNode>()
   {
      @Override
      public ChildNode transform(final ChildValue childValue)
      {
         return buildChildNode(childValue);
      }
   };
}
