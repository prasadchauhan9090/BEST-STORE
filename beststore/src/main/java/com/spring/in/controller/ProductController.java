package com.spring.in.controller;

import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.spring.in.models.Product;
import com.spring.in.models.ProductDto;
import com.spring.in.services.ProductsRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductsRepository repo;

    // 🔹 LIST PRODUCTS
    @GetMapping({"", "/"})
    public String showProductList(Model model) {
        List<Product> products = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("products", products);
        return "products/index";
    }

    // 🔹 CREATE PAGE
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "products/CreateProduct";
    }

    // 🔹 CREATE PRODUCT
    @PostMapping("/create")
    public String createProduct(
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result
    ) {

        if (productDto.getImageFile() == null || productDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("productDto", "imageFile", "Image is required"));
        }

        if (result.hasErrors()) {
            return "products/CreateProduct";
        }

        String uploadDir = "public/images/";
        String fileName = null;
        Date createdAt = new Date();

        try {
            MultipartFile image = productDto.getImageFile();

            if (image != null && !image.isEmpty()) {

                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                fileName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream,
                            uploadPath.resolve(fileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCreatedAt(createdAt);
        product.setImageFileName(fileName);

        repo.save(product);

        return "redirect:/products";
    }

    // 🔹 EDIT PAGE
    @GetMapping("/edit/{id}")
    public String showEditPage(Model model, @PathVariable int id) {

        Optional<Product> optional = repo.findById(id);

        if (optional.isEmpty()) {
            return "redirect:/products";
        }

        Product product = optional.get();
        model.addAttribute("product", product);

        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setBrand(product.getBrand());
        productDto.setCategory(product.getCategory());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());

        model.addAttribute("productDto", productDto);

        return "products/EditProduct";
    }

    // 🔹 UPDATE PRODUCT
    @PostMapping("/edit/{id}")
    public String updateProduct(
            @PathVariable int id,
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result,
            Model model
    ) {

        Optional<Product> optional = repo.findById(id);

        if (optional.isEmpty()) {
            return "redirect:/products";
        }

        Product product = optional.get();
        model.addAttribute("product", product);

        if (result.hasErrors()) {
            return "products/EditProduct";
        }

        try {
            String uploadDir = "public/images/";

            // 🔸 Update Image
            if (productDto.getImageFile() != null && !productDto.getImageFile().isEmpty()) {

                // delete old image
                if (product.getImageFileName() != null) {
                    Path oldPath = Paths.get(uploadDir + product.getImageFileName());
                    if (Files.exists(oldPath)) {
                        Files.delete(oldPath);
                    }
                }

                MultipartFile image = productDto.getImageFile();
                String newFileName = new Date().getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream,
                            Paths.get(uploadDir + newFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }

                product.setImageFileName(newFileName);
            }

            // 🔸 Update Data
            product.setName(productDto.getName());
            product.setBrand(productDto.getBrand());
            product.setCategory(productDto.getCategory());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());

            repo.save(product);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "redirect:/products";
    }

    // 🔹 DELETE PRODUCT
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {

        Optional<Product> optional = repo.findById(id);

        if (optional.isPresent()) {
            Product product = optional.get();

            try {
                String uploadDir = "public/images/";

                if (product.getImageFileName() != null) {
                    Path path = Paths.get(uploadDir + product.getImageFileName());
                    if (Files.exists(path)) {
                        Files.delete(path);
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            repo.delete(product);
        }

        return "redirect:/products";
    }
}