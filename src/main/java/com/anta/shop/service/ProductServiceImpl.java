package com.anta.shop.service;

import com.anta.shop.dao.CurrencyRepository;
import com.anta.shop.dao.DescriptionRepository;
import com.anta.shop.dao.ProductRepository;
import com.anta.shop.dto.ProductDTO;
import com.anta.shop.entity.Currency;
import com.anta.shop.entity.Description;
import com.anta.shop.entity.Product;
import com.anta.shop.exception_handling.NoSuchProductException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Resource
    private ProductRepository productRepository;

    @Resource
    private CurrencyRepository currencyRepository;

    @Resource
    private DescriptionRepository descriptionRepository;

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

    @Override
    public ProductDTO getProductForAdmin(int id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            return fromProductToProductDTO(productOptional.get());
        } else
            throw new NoSuchProductException();
    }

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

    @Override
    @Transactional
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = new Product();
        fromProductDTOToProduct(productDTO, product);
        Product newProduct = productRepository.save(product);
        return fromProductToProductDTO(newProduct);
    }

    @Override
    @Transactional
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
