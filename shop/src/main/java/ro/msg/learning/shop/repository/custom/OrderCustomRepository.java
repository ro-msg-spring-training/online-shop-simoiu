package ro.msg.learning.shop.repository.custom;

public interface OrderCustomRepository<T> {
    T save(T entity);
}
