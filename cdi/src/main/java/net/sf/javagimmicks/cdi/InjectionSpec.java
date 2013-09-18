package net.sf.javagimmicks.cdi;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.UnsatisfiedResolutionException;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

public class InjectionSpec<E>
{
   private final Type _type;
   private final Set<Annotation> _annotations;

   private final String _name;

   public static <E> Builder<E> build(final BeanManager beanManager)
   {
      return new Builder<E>(beanManager);
   }

   public static <E> Builder<E> build()
   {
      return build(null);
   }

   public InjectionSpec(final String name)
   {
      if (name == null || name.length() == 0)
      {
         throw new IllegalArgumentException("Name must be specified!");
      }

      _name = name;
      _type = null;
      _annotations = null;
   }

   public InjectionSpec(final Type type, final Collection<Annotation> annotations)
   {
      if (type == null)
      {
         throw new IllegalArgumentException("Type must be specified!");
      }

      _type = type;
      _annotations = annotations != null ? new HashSet<Annotation>(annotations) : Collections.<Annotation> emptySet();

      _name = null;
   }

   public InjectionSpec(final Type type, final Annotation... annotations)
   {
      this(type, Arrays.asList(annotations));
   }

   public InjectionSpec(final Class<? extends E> clazz, final List<Type> typeParameters,
         final Collection<Annotation> annotations)
   {
      this(buildType(clazz, typeParameters), annotations);
   }

   public InjectionSpec(final Class<E> clazz, final Annotation... annotations)
   {
      this(clazz, null, Arrays.asList(annotations));
   }

   public boolean isTypeBased()
   {
      return _type != null;
   }

   public boolean isNameBased()
   {
      return _name != null;
   }

   public Type getType()
   {
      return _type;
   }

   public Set<Annotation> getAnnotations()
   {
      return Collections.unmodifiableSet(_annotations);
   }

   public String getName()
   {
      return _name;
   }

   @SuppressWarnings("unchecked")
   public E getInstance(final BeanManager beanManager)
   {
      // Type-based case
      if (isTypeBased())
      {
         final Bean<?> bean = beanManager
               .resolve(beanManager.getBeans(_type, _annotations.toArray(new Annotation[0])));

         if (bean == null)
         {
            throw new UnsatisfiedResolutionException("Unable to resolve a bean for " + _type + " with bindings "
                  + _annotations);
         }

         final CreationalContext<?> cc = beanManager.createCreationalContext(bean);
         return (E) beanManager.getReference(bean, _type, cc);
      }

      // Name-based case
      else if (isNameBased())
      {
         final Bean<?> bean = beanManager.resolve(beanManager.getBeans(_name));
         if (bean == null)
         {
            throw new UnsatisfiedResolutionException("Unable to resolve a bean name " + _name);
         }
         final CreationalContext<?> cc = beanManager.createCreationalContext(bean);
         return (E) beanManager.getReference(bean, bean.getBeanClass(), cc);
      }
      else
      {
         return null;
      }
   }

   private static Type buildType(final Class<?> clazz, final Collection<Type> typeParameters)
   {
      if (clazz == null)
      {
         throw new IllegalArgumentException("Class must be specified!");
      }

      if (typeParameters != null && !typeParameters.isEmpty())
      {
         return new ParameterizedType() {

            @Override
            public Type getRawType()
            {
               return clazz;
            }

            @Override
            public Type getOwnerType()
            {
               return null;
            }

            @Override
            public Type[] getActualTypeArguments()
            {
               return typeParameters.toArray(new Type[0]);
            }
         };
      }
      else
      {
         return clazz;
      }
   }

   public static class Builder<E>
   {
      private final BeanManager _beanManager;

      private Type _type;
      private Class<? extends E> _class;
      private final List<Type> _typeParameters = new ArrayList<Type>();
      private final Set<Annotation> _annotations = new HashSet<Annotation>();

      private String _name;

      private Builder(final BeanManager beanManager)
      {
         _beanManager = beanManager;
      }

      public Builder<E> setClass(final Class<? extends E> clazz)
      {
         checkHasName();
         checkHasType();

         _class = clazz;

         return this;
      }

      public Builder<E> addTypeParameters(final Collection<Type> typeParameters)
      {
         checkHasName();
         checkHasType();

         _typeParameters.addAll(typeParameters);

         return this;
      }

      public Builder<E> addTypeParameters(final Type... typeParameters)
      {
         checkHasName();
         checkHasType();

         return addTypeParameters(Arrays.asList(typeParameters));
      }

      public Builder<E> addAnnotations(final Collection<Annotation> annotations)
      {
         checkHasName();

         _annotations.addAll(annotations);

         return this;
      }

      public Builder<E> addAnnotations(final Annotation... annotation)
      {
         return addAnnotations(Arrays.asList(annotation));
      }

      public <A extends Annotation> AnnotationBuilder<A> annotation(final Class<A> annotationType)
      {
         checkHasName();

         return new AnnotationBuilder<A>(AnnotationLiteralHelper.annotationWithMembers(annotationType));
      }

      public Builder<E> setName(final String name)
      {
         checkHasClassOrTypeParameters();
         checkHasType();

         _name = name;

         return this;
      }

      public Builder<E> setType(final Type type)
      {
         checkHasName();
         checkHasClassOrTypeParameters();

         _type = type;

         return this;
      }

      public InjectionSpec<E> getInjection()
      {
         if (_name != null)
         {
            return new InjectionSpec<E>(_name);
         }
         else if (_class != null)
         {
            return new InjectionSpec<E>(_class, _typeParameters, _annotations);
         }
         else if (_type != null)
         {
            return new InjectionSpec<E>(_type, _annotations);
         }
         else
         {
            throw new IllegalStateException("Neither a name nor a type is set in this builder!");
         }
      }

      public E getInstance()
      {
         final BeanManager beanManager = _beanManager == null ? CDIContext.getBeanManager() : _beanManager;
         return getInjection().getInstance(beanManager);
      }

      private void checkHasName()
      {
         if (_name != null)
         {
            throw new IllegalStateException("There is already a name set on this builder!");
         }
      }

      private void checkHasClassOrTypeParameters()
      {
         if (_class != null || !_typeParameters.isEmpty())
         {
            throw new IllegalStateException(
                  "There is already a class set or type parameters specified on this builder!");
         }
      }

      private void checkHasType()
      {
         if (_type != null)
         {
            throw new IllegalStateException("There is already a type set on this builder!");
         }
      }

      public class AnnotationBuilder<A extends Annotation>
      {
         private final AnnotationLiteralHelper.Builder<A> _delegate;

         private AnnotationBuilder(final AnnotationLiteralHelper.Builder<A> delegate)
         {
            this._delegate = delegate;
         }

         public AnnotationBuilder<A> member(final String memberName, final Object value)
         {
            _delegate.member(memberName, value);

            return this;
         }

         public Builder<E> add()
         {
            addAnnotations(_delegate.get());

            return Builder.this;
         }
      }
   }
}
