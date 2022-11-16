package ro.msg.learning.shop.repository.custom;

public interface ProductCustomRepository<T> {
    void deleteById(T id);
}
