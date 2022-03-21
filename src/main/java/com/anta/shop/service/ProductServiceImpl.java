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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    private final CurrencyRepository currencyRepository;

    private final DescriptionRepository descriptionRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CurrencyRepository currencyRepository, DescriptionRepository descriptionRepository) {
        this.productRepository = productRepository;
        this.currencyRepository = currencyRepository;
        this.descriptionRepository = descriptionRepository;
    }

    /**
     * Search all Products for AdminRestController
     * @return List
     */
    @Override
    public List<ProductDTO> getAllProductsForAdmin() {
        List<ProductDTO> productDTOS = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        products.forEach(pr -> {
            ProductDTO productDTO = fromProductToProductDTO(pr);
            productDTOS.add(productDTO);
        });
        return productDTOS;
    }

    /**
     * Search all Products for ClientRestController
     * @return List
     */
    @Override
    public List<ProductDTO> getAllProductsForClient() {
        List<ProductDTO> productDTOS = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        products.forEach(pr -> {
            if (!((pr.getCurrencies().isEmpty()) || (pr.getDescriptions().isEmpty()))) {
                ProductDTO productDTO = fromProductToProductDTO(pr);
                productDTOS.add(productDTO);
            }
        });
        return productDTOS;
    }

    /**
     * Search Product by id for AdminRestController
     * @param id Product id
     * @return Product
     */
    @Override
    public ProductDTO getProductForAdmin(int id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            return fromProductToProductDTO(productOptional.get());
        } else
            throw new NoSuchProductException();
    }

    /**
     * Search Product by id for ClentRestController
     * @param id Product id
     * @return Product
     */
    @Override
    public ProductDTO getProductForClient(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        ProductDTO productDTO = new ProductDTO();
        if ((productOptional.isPresent())) {
            productDTO = fromProductToProductDTO(productOptional.get());
        }
        if (!(productDTO == null || productDTO.getCurrencies().isEmpty() || productDTO.getDescriptions().isEmpty()))
            return productDTO;
        else {
            log.info("getProductForClient: logging exception");
            throw new NoSuchProductException();
        }
    }

    /**
     * Save new Product
     * @param productDTO new Product
     * @return new Product
     */
    @Override
    @Transactional
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = new Product();
        fromProductDTOToProduct(productDTO, product);
        Product newProduct = productRepository.save(product);
        return fromProductToProductDTO(newProduct);
    }

    /**
     * Save updated Product
     * @param id Product id
     * @param productDTO necessary Product
     * @return updated Product
     */
    @Override
    @Transactional
    public ProductDTO updateProduct(int id, ProductDTO productDTO) {
        Product product = productRepository.getById(id);
        fromProductDTOToProduct(productDTO, product);
        Product updatedProduct = productRepository.save(product);
        return fromProductToProductDTO(updatedProduct);
    }

    /**
     * Delete Product
     * @param id Product id
     * @return true or false
     */
    @Override
    public boolean deleteProduct(int id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else
            throw new NoSuchProductException();
    }

    /**
     * Search Products by name
     * @param name Product name
     * @return List
     */
    @Override
    public List<ProductDTO> getAllByName(String name) {
        List<ProductDTO> productDTOS = new ArrayList<>();
        List<Product> products = productRepository.findAllByName(name.toLowerCase());
        products.forEach(pr -> {
            if (!((pr.getCurrencies().isEmpty()) || (pr.getDescriptions().isEmpty()))) {
                ProductDTO productDTO = fromProductToProductDTO(pr);
                productDTOS.add(productDTO);
            }
        });
        return productDTOS;
    }

    /**
     * Search Products by description
     * @param description Product description
     * @return List
     */
    @Override
    public List<ProductDTO> getAllByDescription(String description) {
        List<ProductDTO> productDTOS = new ArrayList<>();
        List<Product> products = productRepository.findAllByDescription(description.toLowerCase());
        products.forEach(pr -> {
            if (!((pr.getCurrencies().isEmpty()) || (pr.getDescriptions().isEmpty()))) {
                ProductDTO productDTO = fromProductToProductDTO(pr);
                productDTOS.add(productDTO);
            }
        });
        return productDTOS;
    }

    /**
     * Transformation ProductDTO to Product
     * @param productDTO ProductDTO
     * @param product Product
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
