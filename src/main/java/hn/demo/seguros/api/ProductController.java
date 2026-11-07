package hn.demo.seguros.api;

import hn.demo.seguros.infrastructure.ProductEntity;
import hn.demo.seguros.infrastructure.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProductController {


 private final ProductRepository products;


 public ProductController(
         ProductRepository products
 ) {
  this.products = products;
 }


 @GetMapping("/products")
 public Page<ProductEntity> list(
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "10") int size
 ) {

  int currentPage = Math.max(0, page);

  int pageSize = Math.max(
          1,
          Math.min(size, 50)
  );


  return products.findByActiveTrue(
          PageRequest.of(
                  currentPage,
                  pageSize
          )
  );
 }
}
