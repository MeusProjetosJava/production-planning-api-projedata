package vitor.productionplanningapiprojedata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitor.productionplanningapiprojedata.entity.Product;
import vitor.productionplanningapiprojedata.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(Product product) {

        if (productRepository.existsByCode(product.getCode())) {
            throw new RuntimeException("Product code already exists.");
        }

        return productRepository.save(product);
    }

    public Product update(Long id, Product updatedProduct) {

        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found."));

        existing.setName(updatedProduct.getName());
        existing.setPrice(updatedProduct.getPrice());

        return productRepository.save(existing);
    }

    public void delete(Long id) {

        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found.");
        }

        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found."));
    }
}