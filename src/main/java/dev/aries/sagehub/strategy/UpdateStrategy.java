package dev.aries.sagehub.strategy;

public interface UpdateStrategy<T, R> {
	T update(T entity, R request);
}
