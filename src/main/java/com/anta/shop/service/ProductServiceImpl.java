package com.anta.shop.service;

import com.anta.shop.repository.CurrencyRepository;
import com.anta.shop.repository.DescriptionRepository;
import com.anta.shop.repository.ProductRepository;
import com.anta.shop.dto.ProductDTO;
import com.anta.shop.entity.Currency;
import com.anta.shop.entity.Description;
import com.anta.shop.entity.Product;
import com.anta.shop.exception_handling.NoSuchProductException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    private final CurrencyRepository currencyRepository;

    private final DescriptionRepository descriptionRepository;

    public ProductServiceImpl(ProductRepository productRepository, CurrencyRepository currencyRepository, DescriptionRepository descriptionRepository) {
        this.productRepository = productRepository;
        this.currencyRepository = currencyRepository;
        this.descriptionRepository = descriptionRepository;
    }

    @Override
    public List<ProductDTO> getAllProductsForAdmin() {
        return productRepository.findAll().stream()
                .map(this::fromProductToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProductsForClient() {
        return productRepository.findAll().stream()
                .filter(pr -> !(pr.getCurrencies().isEmpty() || pr.getDescriptions().isEmpty()))
                .map(this::fromProductToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductForAdmin(int id) {
        return productRepository.findById(id)
                .map(this::fromProductToProductDTO)
                .orElseThrow(NoSuchProductException::new);
    }

    @Override
    public ProductDTO getProductForClient(int id) {
        return productRepository.findById(id)
                .map(this::fromProductToProductDTO)
                .filter(pr -> !(pr.getCurrencies().isEmpty() || pr.getDescriptions().isEmpty()))
                .orElseThrow(NoSuchProductException::new);
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = new Product();
        fromProductDTOToProduct(productDTO, product);
        Product newProduct = productRepository.save(product);
        return fromProductToProductDTO(newProduct);
    }

    @Override
    public ProductDTO updateProduct(int id, ProductDTO productDTO) {
        Product product = productRepository.getById(id);
        fromProductDTOToProduct(productDTO, product);
        Product updatedProduct = productRepository.save(product);
        return fromProductToProductDTO(updatedProduct);
    }

    @Override
    public boolean deleteProduct(int id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else
            throw new NoSuchProductException();
    }

    @Override
    public List<ProductDTO> getAllByName(String name) {
        return productRepository.findAllByName(name.toLowerCase()).stream()
                .filter(pr -> !(pr.getCurrencies().isEmpty() || pr.getDescriptions().isEmpty()))
                .map(this::fromProductToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllByDescription(String description) {
        return productRepository.findAllByDescription(description.toLowerCase()).stream()
                .filter(pr -> !(pr.getCurrencies().isEmpty() || pr.getDescriptions().isEmpty()))
                .map(this::fromProductToProductDTO)
                .collect(Collectors.toList());
    }

    /**
     * Transformation ProductDTO to Product
     *
     * @param productDTO ProductDTO
     * @param product    Product
     */
    public void fromProductDTOToProduct(ProductDTO productDTO, Product product) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        if (product.getCurrencies() == null) {
            product.setCurrencies(new HashSet<>());
        }
        for (String currencyValue : productDTO.getCurrencies()) {
            Currency currency = currencyRepository.findByValue(currencyValue);
            if (currency == null) {
                currency = new Currency();
                currency.setProducts(new HashSet<>());
            }
            currency.setValue(currencyValue);
            product.addCurrencyToProduct(currency);
        }

        if (product.getDescriptions() == null) {
            product.setDescriptions(new HashSet<>());
        }
        for (String descriptionLanguage : productDTO.getDescriptions()) {
            Description description = descriptionRepository.findByLanguage(descriptionLanguage);
            if (description == null) {
                description = new Description();
                description.setProducts(new HashSet<>());
            }
            description.setLanguage(descriptionLanguage);
            product.addDescriptionToProduct(description);
        }
    }

    /**
     * Transformation Product to ProductDTO
     *
     * @param product Product
     * @return ProductDTO
     */
    public ProductDTO fromProductToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setDateOfCreation(product.getDateOfCreation());
        productDTO.setDateOfModification(product.getDateOfModification());
        productDTO.setCurrencies(product.getCurrencies().stream().map(Currency::getValue).collect(Collectors.toSet()));
        productDTO.setDescriptions(product.getDescriptions().stream().map(Description::getLanguage).collect(Collectors.toSet()));
        return productDTO;
    }
}
