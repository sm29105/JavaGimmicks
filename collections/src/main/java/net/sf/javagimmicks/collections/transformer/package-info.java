/**
 * This package spans up the JavaGimmicks
 * transformation API which allows to <b>decorate</b>
 * collections with transforming <i>views</i> that apply
 * a specified transformation logic <i>on access</i> to all
 * elements.
 * <p>
 * Let's use the terms
 * <i>internal format</i> and <i>external format</i> for the
 * two element formats (original and view).
 * <p>
 * For example, you can create a {@code List<String>} which is
 * actually internally backed by a {@code List<Integer>}. You 
 * wont't be aware of this fact and will think to work with
 * a {@code List<String>}. You can add, remove, get or set
 * elements and of course iterate over them. The instance
 * you are working on will automatically take care of transforming
 * between the respective element types {@link java.lang.String} and
 * {@link java.lang.Integer} and the results of all operations will be
 * immediately visible in the internal {@link java.util.List}.
 * <p>
 * Additionally, you could also think of a {@code List<String>}
 * which internally works with another {@code List<String>}
 * but keeps the elements in the reverse character order (for
 * what reasons ever you like).
 * <p>
 * Of course, in order to be able to work with transforming
 * instances, you need to provide to them the transformation rules.
 * You can do so by writing a class that implements the
 * {@link net.sf.javagimmicks.util.Function} or
 * {@link net.sf.javagimmicks.transform.BidiFunction} interface
 * which are both contained in this API.
 * <p>
 * When providing a {@link net.sf.javagimmicks.util.Function},
 * you will only be able to perform operations on the transforming instance that
 * do not need to convert from the external format to the internal
 * format. For example, you will be able to iterate over a
 * {@link java.util.List}, get elements, remove them or ask if they are
 * contained inside. But you will not be able to set or add elements
 * (neither on the {@link java.util.List} nor on any of its
 * {@link java.util.ListIterator}s. For more details, see below.
 * <p>
 * When providing a {@link net.sf.javagimmicks.transform.BidiFunction},
 * all operations will be available and some operations will even be faster.
 * 
 * <h2>Decorator methods in {@link net.sf.javagimmicks.collections.transformer.TransformerUtils}</h2>
 * This class provides a large number of methods {@code decorate()}
 * (and {@code decorateKeyBased()} and {@code decorateValueBased()}
 * for {@link java.util.Map}s) which can be used to create transformed instances
 * of a number of (generic) base classes by providing an instance of this
 * base class and one of {@link net.sf.javagimmicks.util.Function} or
 * {@link net.sf.javagimmicks.transform.BidiFunction}.
 * <p>
 * The resulting instance will implement the same interface (but
 * eventually with different type parameters) and additionally
 * {@link net.sf.javagimmicks.transform.Transforming} or
 * {@link net.sf.javagimmicks.transform.BidiTransforming}
 * (both depending on the provided transformation rule instance).
 * Furthermore, this instance
 * is completely backed on the the provided one and will contain
 * no data itself. Any changes on it will affect the internal one and
 * vice versa.
 * 
 * <a name="comparator"></a>
 * <h3>Operations on a transformed {@link java.util.Comparator}</h3>
 * <table border="2" summary="Operations on a transformed Comparator">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Function</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>compare()</td>
 *          <td align="center">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="iterator"></a>
 * <h3>Operations on a transformed {@link java.util.Iterator}</h3>
 * <table border="2" summary="Operations on a transformed Iterator">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Function</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>hasNext()</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>next()</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>remove()</td>
 *          <td align="center">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * 
 * <a name="collection"></a>
 * <h3>Operations on a transformed {@link java.util.Collection}</h3>
 * <table border="2" summary="Operations on a transformed Collection">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Function</th>
 *          <th>BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>add()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>addAll()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>clear()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>contains()</td>
 *          <td align="center" colspan="2">supported; needs {@code equals()} method</td>
 *       </tr>
 *       <tr>
 *          <td>containsAll()</td>
 *          <td align="center" colspan="2">supported; needs {@code equals()} method</td>
 *       </tr>
 *       <tr>
 *          <td>isEmpty()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>iterator()</td>
 *          <td align="center" colspan="2">supported; See <a href="#iterator">{@code Iterator} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>remove()</td>
 *          <td align="center" colspan="2">supported; needs {@code equals()} method</td>
 *       </tr>
 *       <tr>
 *          <td>removeAll()</td>
 *          <td align="center" colspan="2">supported; needs {@code equals()} method</td>
 *       </tr>
 *       <tr>
 *          <td>retainAll()</td>
 *          <td align="center" colspan="2">supported; needs {@code equals()} method</td>
 *       </tr>
 *       <tr>
 *          <td>size()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>toArray()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="set"></a>
 * <h3>Operations on a transformed {@link java.util.Set}</h3>
 * <table border="2" summary="Operations on a transformed Set">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Function</th>
 *          <th>BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>add()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>addAll()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>clear()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>contains()</td>
 *          <td align="center">supported; needs {@code equals()} method</td>
 *          <td align="center">supported; faster</td>
 *       </tr>
 *       <tr>
 *          <td>containsAll()</td>
 *          <td align="center">supported; needs {@code equals()} method</td>
 *          <td align="center">supported; faster</td>
 *       </tr>
 *       <tr>
 *          <td>isEmpty()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>iterator()</td>
 *          <td align="center" colspan="2">supported; See <a href="#iterator">{@code Iterator} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>remove()</td>
 *          <td align="center">supported; needs {@code equals()} method</td>
 *          <td align="center">supported; faster</td>
 *       </tr>
 *       <tr>
 *          <td>removeAll()</td>
 *          <td align="center">supported; needs {@code equals()} method</td>
 *          <td align="center">supported; faster</td>
 *       </tr>
 *       <tr>
 *          <td>retainAll()</td>
 *          <td align="center">supported; needs {@code equals()} method</td>
 *          <td align="center">supported; faster</td>
 *       </tr>
 *       <tr>
 *          <td>size()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>toArray()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="sortedset"></a>
 * <h3>Operations on a transformed {@link java.util.SortedSet}</h3>
 * <p><b>Note:</b> the sorting order of a transformed {@link java.util.SortedSet}
 * remains that from the wrapped one
 * <table border="2" summary="Operations on a transformed SortedSet">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Function</th>
 *          <th>BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>{@link java.util.Set} operations</td>
 *          <td align="center" colspan="2">See table for <a href="#set">{@code Set} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>comparator()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>first()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>headSet()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>last()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>subSet()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>tailSet()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="navigableset"></a>
 * <h3>Operations on a transformed {@link java.util.NavigableSet}</h3>
 * <p><b>Note:</b> the sorting order of a transformed {@link java.util.NavigableSet}
 * remains that from the wrapped one
 * <table border="2" summary="Operations on a transformed NavigableSet">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Function</th>
 *          <th>BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>{@link java.util.Set} operations</td>
 *          <td align="center" colspan="2">See table for <a href="#set">{@code Set} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>{@link java.util.SortedSet} operations</td>
 *          <td align="center" colspan="2">See table for <a href="#sortedset">{@code SortedSet} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>ceiling()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>descendingIterator()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>descendingSet()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>floor()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>headSet()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>higher()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>lower()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>pollFirst()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>pollLast()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>subSet()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>tailSet()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="listiterator"></a>
 * <h3>Operations on a transformed {@link java.util.ListIterator}</h3>
 * <table border="2" summary="Operations on a transformed ListIterator">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Function</th>
 *          <th>BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>{@link java.util.Iterator} operations</td>
 *          <td align="center" colspan="2">See table for <a href="#iterator">{@code Iterator} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>add()</td>
 *          <td>not supported</td>
 *          <td>supported</td>
 *       </tr>
 *       <tr>
 *          <td>hasPrevious()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>nextIndex()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>previous()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>previousIndex()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>set()</td>
 *          <td>not supported</td>
 *          <td>supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="list"></a>
 * <h3>Operations on a transformed {@link java.util.List}</h3>
 * <table border="2" summary="Operations on a transformed List">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Function</th>
 *          <th>BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>{@link java.util.Collection} operations</td>
 *          <td align="center" colspan="2">See table for <a href="#collection">{@code Collection} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>add()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>addAll()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>get()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>indexOf()</td>
 *          <td align="center" colspan="2">supported; needs {@code equals()} method</td>
 *       </tr>
 *       <tr>
 *          <td>lastIndexOf()</td>
 *          <td align="center" colspan="2">supported; needs {@code equals()} method</td>
 *       </tr>
 *       <tr>
 *          <td>listIterator()</td>
 *          <td align="center" colspan="2">supported; See <a href="#listiterator">{@code ListIterator} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>remove()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>set()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>subList()</td>
 *          <td align="center" colspan="2">supported (with same behaviour)</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="mapKey"></a>
 * <h3>Operations on a key-based transformed {@link java.util.Map}</h3>
 * <table border="2" summary="Operations on a key-based transformed Map">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Function</th>
 *          <th>BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>isEmpty()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>size()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>put()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>putAll()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>get()</td>
 *          <td align="center">supported; keys need {@code equals()} method</td>
 *          <td align="center">supported; faster</td>
 *       </tr>
 *       <tr>
 *          <td>containsKey()</td>
 *          <td align="center">supported; keys need {@code equals()} method</td>
 *          <td align="center">supported; faster</td>
 *       </tr>
 *       <tr>
 *          <td>containsValue()</td>
 *          <td align="center" colspan="2">supported; values need {@code equals()} method</td>
 *       </tr>
 *       <tr>
 *          <td>remove()</td>
 *          <td align="center">supported; keys need {@code equals()} method</td>
 *          <td align="center">supported; faster</td>
 *       </tr>
 *       <tr>
 *          <td>clear()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>keySet()</td>
 *          <td align="center" colspan="2">See table for <a href="#set">{@code Set} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>values()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>entrySet()</td>
 *          <td align="center" colspan="2">See table for <a href="#set">{@code Set} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>Entry.getKey()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>Entry.getValue()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>Entry.setValue()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="sortedMapKey"></a>
 * <h3>Operations on a key-based transformed {@link java.util.SortedMap}</h3>
 * <p><b>Note:</b> the sorting order of a transformed {@link java.util.SortedMap}
 * remains that from the wrapped one
 * <table border="2" summary="Operations on a key-based transformed SortedMap">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Function</th>
 *          <th>BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>{@link java.util.Map} operations</td>
 *          <td align="center" colspan="2">See table for <a href="#mapKey">{@code Map} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>comparator()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>firstKey()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>headMap()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>lastKey()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>subMap()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>tailMap()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="navigableMapKey"></a>
 * <h3>Operations on a key-based transformed {@link java.util.NavigableMap}</h3>
 * <p><b>Note:</b> the sorting order of a transformed {@link java.util.NavigableMap}
 * remains that from the wrapped one
 * <table border="2" summary="Operations on a key-based transformed NavigableMap">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Function</th>
 *          <th>BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>{@link java.util.Map} operations</td>
 *          <td align="center" colspan="2">See table for <a href="#mapKey">{@code Map} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>{@link java.util.SortedMap} operations</td>
 *          <td align="center" colspan="2">See table for <a href="#sortedMapKey">{@code SortedMap} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>ceilingEntry()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>ceilingKey()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>descendingKeySet()</td>
 *          <td align="center" colspan="2">supported; See table for <a href="#navigableset">{@code NavigableSet} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>descendingMap()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>firstEntry()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>floorEntry()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>floorKey()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>headMap()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>higherEntry()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>higherKey()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>lastEntry()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>lowerEntry()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>lowerKey()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>navigableKeySet()</td>
 *          <td align="center" colspan="2">supported; See table for <a href="#navigableset">{@code NavigableSet} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>pollFirstEntry()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>pollLastEntry()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>subMap()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>tailMap()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="mapValue"></a>
 * <h3>Operations on a value-based transformed {@link java.util.Map}</h3>
 * <table border="2" summary="Operations on a value-based transformed Map">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Function</th>
 *          <th>BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>isEmpty()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>size()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>put()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>putAll()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>get()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>containsKey()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>containsValue()</td>
 *          <td align="center">supported; values need {@code equals()} method</td>
 *          <td align="center">supported; faster</td>
 *       </tr>
 *       <tr>
 *          <td>remove()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>clear()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>keySet()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>values()</td>
 *          <td align="center" colspan="2">See table for <a href="#collection">{@code Collection} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>entrySet()</td>
 *          <td align="center" colspan="2">See table for <a href="#set">{@code Set} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>Entry.getKey()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>Entry.getValue()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>Entry.setValue()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="sortedMapValue"></a>
 * <h3>Operations on a value-based transformed {@link java.util.SortedMap}</h3>
 * <table border="2" summary="Operations on a value-based transformed SortedMap">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Function</th>
 *          <th>BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>{@link java.util.Map} operations</td>
 *          <td align="center" colspan="2">See table for <a href="#mapValue">{@code Map} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>comparator()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>firstKey()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>headMap()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>lastKey()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>subMap()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>tailMap()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="navigableMapValue"></a>
 * <h3>Operations on a value-based transformed {@link java.util.NavigableMap}</h3>
 * <table border="2" summary="Operations on a value-based transformed NavigableMap">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Function</th>
 *          <th>BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>{@link java.util.Map} operations</td>
 *          <td align="center" colspan="2">See table for <a href="#mapValue">{@code Map} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>{@link java.util.SortedMap} operations</td>
 *          <td align="center" colspan="2">See table for <a href="#sortedMapValue">{@code SortedMap} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>ceilingEntry()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>ceilingKey()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>descendingKeySet()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>descendingMap()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>firstEntry()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>floorEntry()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>floorKey()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>headMap()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>higherEntry()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>higherKey()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>lastEntry()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>lowerEntry()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>lowerKey()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>navigableKeySet()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>pollFirstEntry()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>pollLastEntry()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>subMap()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>tailMap()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="mapKeyValue"></a>
 * <h3>Operations on a key- and value-based transformed {@link java.util.Map}</h3>
 * <p>
 * <b>Note:</b> When using a combination of one Function and one BidiFunction
 * for keys and values allows you can fall back to the supported operation list
 * for the part that only has a Function.
 * <p>
 * For example a key-transformed and value-<b>bidi</b>-transformed {@link java.util.Map}
 * supports the same operations than a <a href="#mapKey">purely key-transformed</a>
 * {@link java.util.Map}.
 * <p>
 * Vice-versa example a key-<b>bidi</b>-transformed and value-transformed {@link java.util.Map}
 * supports the same operations than a <a href="#mapValue">purely value-transformed</a>
 * {@link java.util.Map}.
 * <table border="2" summary="Operations on a key- and value-based transformed Map">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Key- and Value-Function</th>
 *          <th>Key- and Value-BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>isEmpty()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>size()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>put()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>putAll()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>get()</td>
 *          <td align="center">supported; keys need {@code equals()} method</td>
 *          <td align="center">supported; faster</td>
 *       </tr>
 *       <tr>
 *          <td>containsKey()</td>
 *          <td align="center">supported; keys need {@code equals()} method</td>
 *          <td align="center">supported; faster</td>
 *       </tr>
 *       <tr>
 *          <td>containsValue()</td>
 *          <td align="center">supported; values need {@code equals()} method</td>
 *          <td align="center">supported; faster</td>
 *       </tr>
 *       <tr>
 *          <td>remove()</td>
 *          <td align="center">supported; keys need {@code equals()} method</td>
 *          <td align="center">supported; faster</td>
 *       </tr>
 *       <tr>
 *          <td>clear()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>keySet()</td>
 *          <td align="center" colspan="2">See table for <a href="#set">{@code Set} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>values()</td>
 *          <td align="center" colspan="2">See table for <a href="#collection">{@code Collection} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>entrySet()</td>
 *          <td align="center" colspan="2">See table for <a href="#set">{@code Set} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>Entry.getKey()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>Entry.getValue()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>Entry.setValue()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="sortedMapKeyValue"></a>
 * <h3>Operations on a key- and value-based transformed {@link java.util.SortedMap}</h3>
 * <p><b>Note:</b> the sorting order of a transformed {@link java.util.SortedMap}
 * remains that from the wrapped one
 * <p>
 * <b>Note:</b> When using a combination of one Function and one BidiFunction
 * for keys and values allows you can fall back to the supported operation list
 * for the part that only has a Function.
 * <p>
 * For example a key-transformed and value-<b>bidi</b>-transformed {@link java.util.SortedMap}
 * supports the same operations than a <a href="#sortedMapKey">purely key-transformed</a>
 * {@link java.util.SortedMap}.
 * <p>
 * Vice-versa example a key-<b>bidi</b>-transformed and value-transformed {@link java.util.SortedMap}
 * supports the same operations than a <a href="#sortedMapValue">purely value-transformed</a>
 * {@link java.util.SortedMap}.
 * <table border="2" summary="Operations on a key- and value-based transformed SortedMap">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Key- and Value-Function</th>
 *          <th>Key- and Value-BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>{@link java.util.Map} operations</td>
 *          <td align="center" colspan="2">See table for <a href="#mapKeyValue">{@code Map} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>comparator()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>firstKey()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>headMap()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>lastKey()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>subMap()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>tailMap()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="navigableMapKeyValue"></a>
 * <h3>Operations on a key- and value-based transformed {@link java.util.NavigableMap}</h3>
 * <p><b>Note:</b> the sorting order of a transformed {@link java.util.NavigableMap}
 * remains that from the wrapped one
 * <p>
 * <b>Note:</b> When using a combination of one Function and one BidiFunction
 * for keys and values allows you can fall back to the supported operation list
 * for the part that only has a Function.
 * <p>
 * For example a key-transformed and value-<b>bidi</b>-transformed {@link java.util.NavigableMap}
 * supports the same operations than a <a href="#navigableMapKey">purely key-transformed</a>
 * {@link java.util.NavigableMap}.
 * <p>
 * Vice-versa example a key-<b>bidi</b>-transformed and value-transformed {@link java.util.NavigableMap}
 * supports the same operations than a <a href="#navigableMapValue">purely value-transformed</a>
 * {@link java.util.NavigableMap}.
 * <table border="2" summary="Operations on a key- and value-based transformed NavigableMap">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Key- and Value-Function</th>
 *          <th>Key- and Value-BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>{@link java.util.Map} operations</td>
 *          <td align="center" colspan="2">See table for <a href="#mapKeyValue">{@code Map} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>{@link java.util.SortedMap} operations</td>
 *          <td align="center" colspan="2">See table for <a href="#sortedMapKeyValue">{@code SortedMap} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>ceilingEntry()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>ceilingKey()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>descendingKeySet()</td>
 *          <td align="center" colspan="2">supported; See table for <a href="#navigableset">{@code NavigableSet} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>descendingMap()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>firstEntry()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>floorEntry()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>floorKey()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>headMap()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>higherEntry()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>higherKey()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>lastEntry()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>lowerEntry()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>lowerKey()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>navigableKeySet()</td>
 *          <td align="center" colspan="2">supported; See table for <a href="#navigableset">{@code NavigableSet} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>pollFirstEntry()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>pollLastEntry()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>subMap()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>tailMap()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="ringCursor"></a>
 * <h3>Operations on a transformed {@link net.sf.javagimmicks.collections.RingCursor}</h3>
 * <table border="2" summary="Operations on a transformed RingCursor">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Function</th>
 *          <th>BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>cursor()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>get()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>insertAfter()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>insertBefore()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>isEmpty()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>iterator()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>next()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>previous()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>remove()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>set()</td>
 *          <td align="center">not supported</td>
 *          <td align="center">supported</td>
 *       </tr>
 *       <tr>
 *          <td>size()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *       <tr>
 *          <td>toList()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <a name="ring"></a>
 * <h3>Operations on a transformed {@link net.sf.javagimmicks.collections.Ring}</h3>
 * <table border="2" summary="Operations on a transformed Ring">
 *    <thead>
 *       <tr>
 *          <th>Operation</th>
 *          <th>Function</th>
 *          <th>BidiFunction</th>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>{@link java.util.Collection} operations</td>
 *          <td align="center" colspan="2">See table for <a href="#collection">{@code Collection} operations</a></td>
 *       </tr>
 *       <tr>
 *          <td>cursor()</td>
 *          <td align="center" colspan="2">supported</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * @author Michael Scholz
 */
package net.sf.javagimmicks.collections.transformer;