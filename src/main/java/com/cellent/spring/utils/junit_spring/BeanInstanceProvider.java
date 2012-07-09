package com.cellent.spring.utils.junit_spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Interface for a class providing instantiation methods for Spring beans in
 * test scope. The problem solved by this is that you need not create setters on
 * autowired beans in your class under test but rather have those instances be
 * injected.
 * 
 * For Object Autowiring via {@link Autowired} you may do the following:
 * {@link #registerInstance(Object)} to register an object which you want to
 * autowire (so you are in full control over the instantiation of ths object),
 * {@link #createBean(Class)} to actually create the class under test. To obtain
 * your registered object, you may use {@link #getInstanceOf(Class)} in your
 * test class.
 * 
 * If you want a mocked intance to be autowired, simply use
 * {@link #createBean(Class)} to create the class under test. All Beans which
 * are autowired into the class will be created with your favorite mocking
 * framework.
 * 
 * You may also register {@link Value}s here to be injected into the
 * corresponding fields in the class under test by using
 * {@link #setValue(String, Object)}. You may retrieve these values by using
 * {@link #getValue(String)}.
 * 
 * @author bjoern
 * 
 */
public interface BeanInstanceProvider {

	/**
	 * Find an instance of the desired class in the context. If none is known so
	 * far, a Mock of this class is created, put into the context and returned.
	 * 
	 * @param clazz
	 *            The desired class.
	 * @return An instance of the desired class, either a newly created mock or
	 *         the instance already known to the context.
	 */
	<T> T getInstanceOf(Class<T> clazz);

	/**
	 * Crea an instance (no mock!) if the desired class. All fields of this
	 * instance will be subject to Autowire-Processing and filled with the
	 * instances known in this context (by using {@link #getInstanceOf(Class)}).
	 * 
	 * @param desiredClass
	 *            The desired class. May not be an interface.
	 * 
	 * @return An instance of the desired class, in whcih all fields which are
	 *         autowired (either via {@link Autowired} or via {@link Value} will
	 *         be filled.
	 */
	<T> T createBean(Class<T> desiredClass);

	/**
	 * Register the object given n the context. Whenever after that
	 * {@link #getInstanceOf(Class)} is called which matches the class of the
	 * object given, that object will be returned.
	 * 
	 * @param beanInstance
	 *            An instance of a specific class.
	 */
	void registerInstance(Object beanInstance);

	/**
	 * Register a certain {@link Value}.
	 * 
	 * @param key
	 *            Key of the configuration parameter.
	 * @param value
	 *            Value of the configuration parameter.
	 */
	void setValue(String key, Object value);

	/**
	 * Provide the object which was registered under the given {@link Value}
	 * -Key. If none os known, null will be returned.
	 * 
	 * @param value
	 *            The key of the {@link Value}-Annotation.
	 * @return The value of this annotation or null if no such value is known.
	 */
	Object getValue(String value);

}
