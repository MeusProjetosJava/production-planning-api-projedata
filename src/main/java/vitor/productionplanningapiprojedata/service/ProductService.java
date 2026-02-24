package vitor.productionplanningapiprojedata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitor.productionplanningapiprojedata.dto.RequestProductDTO;
import vitor.productionplanningapiprojedata.dto.ResponseProductDTO;
import vitor.productionplanningapiprojedata.entity.Product;
import vitor.productionplanningapiprojedata.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ResponseProductDTO create(RequestProductDTO dto) {

        if (productRepository.existsByCode(dto.code())) {
            throw new RuntimeException("Product code already exists.");
        }

        Product product = Product.builder()
                .code(dto.code())
                .name(dto.name())
                .price(dto.price())
                .build();

        Product saved = productRepository.save(product);

        return mapToResponse(saved);
    }

    public ResponseProductDTO update(Long id, RequestProductDTO dto) {

        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found."));

        if (!existing.getCode().equals(dto.code())
        && productRepository.existsByCode(dto.code())) {
            throw new RuntimeException("Product code already exists.");
        }

        existing.setCode(dto.code());
        existing.setName(dto.name());
        existing.setPrice(dto.price());

        Product saved = productRepository.save(existing);

        return mapToResponse(saved);
    }

    public void delete(Long id) {

        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found.");
        }

        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ResponseProductDTO> findAll()
    {
        return productRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Transactional(readOnly = true)
    public ResponseProductDTO findById(Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found."));

        return mapToResponse(product);
    }


    private ResponseProductDTO mapToResponse(Product product) {
        return new ResponseProductDTO(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getPrice()

        );
    }
}