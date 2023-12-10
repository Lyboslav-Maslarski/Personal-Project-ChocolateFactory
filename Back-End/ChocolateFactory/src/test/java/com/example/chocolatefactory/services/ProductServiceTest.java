package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.entities.CommentEntity;
import com.example.chocolatefactory.domain.entities.ProductEntity;
import com.example.chocolatefactory.domain.entities.UserEntity;
import com.example.chocolatefactory.domain.requestDTOs.product.ProductAddDTO;
import com.example.chocolatefactory.domain.requestDTOs.product.ProductUpdateDTO;
import com.example.chocolatefactory.domain.responseDTOs.comment.CommentDTO;
import com.example.chocolatefactory.domain.responseDTOs.product.ProductDTO;
import com.example.chocolatefactory.domain.responseDTOs.product.ProductDetailsDTO;
import com.example.chocolatefactory.domain.responseDTOs.product.ProductUpdateDetailsDTO;
import com.example.chocolatefactory.exceptions.AppException;
import com.example.chocolatefactory.mappers.CommentMapper;
import com.example.chocolatefactory.mappers.ProductMapper;
import com.example.chocolatefactory.repositories.CommentRepository;
import com.example.chocolatefactory.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductServiceTest {
    public static final String PRODUCT_1 = "product1";
    public static final String PRODUCT_2 = "product2";
    public static final BigDecimal PRICE_1 = BigDecimal.valueOf(10);
    public static final BigDecimal PRICE_2 = BigDecimal.valueOf(20);
    public static final String DESCRIPTION_1 = "description1";
    public static final String IMAGE_URL = "imageUrl";
    public static final int QUANTITY = 10;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private CommentMapper commentMapper;
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, commentRepository, productMapper, commentMapper);
    }

    @Test
    void testGetAllProducts_ShouldReturnListOfProducts() {
        // Arrange
        ProductEntity product1 = new ProductEntity()
                .setName(PRODUCT_1).setPrice(PRICE_1);
        ProductEntity product2 = new ProductEntity()
                .setName(PRODUCT_2).setPrice(PRICE_2);
        List<ProductEntity> mockProducts = Arrays.asList(product1, product2);

        ProductDTO productDTO1 = new ProductDTO()
                .setName(PRODUCT_1).setPrice(PRICE_1);
        ProductDTO productDTO2 = new ProductDTO()
                .setName(PRODUCT_2).setPrice(PRICE_2);

        when(productRepository.findAll()).thenReturn(mockProducts);

        when(productMapper.entityToProductDTO(product1)).thenReturn(productDTO1);
        when(productMapper.entityToProductDTO(product2)).thenReturn(productDTO2);

        // Act
        List<ProductDTO> result = productService.getAllProducts();

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(productDTO1, result.get(0));
        assertEquals(productDTO2, result.get(1));

        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).entityToProductDTO(product1);
        verify(productMapper, times(1)).entityToProductDTO(product2);
    }

    @Test
    void testGetProductDetails_WhenProductExists_ShouldReturnProductDetailsWithComments() {
        Long productId = 1L;
        Long appUserDetailsId = 1L;
        String content = "content";

        ProductEntity productEntity = new ProductEntity()
                .setName(PRODUCT_1)
                .setPrice(PRICE_1);
        productEntity.setId(productId);
        List<CommentEntity> commentEntities = new ArrayList<>();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(appUserDetailsId);
        commentEntities.add(new CommentEntity().setProduct(productEntity).setText(content).setUser(userEntity));

        CommentDTO commentDTO = new CommentDTO().setText(content);
        ProductDetailsDTO expected = new ProductDetailsDTO()
                .setName(PRODUCT_1)
                .setPrice(PRICE_1)
                .setComments(List.of(commentDTO));

        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(commentRepository.findByProductId(productId)).thenReturn(commentEntities);

        when(productMapper.entityToProductDetailsDTO(productEntity)).thenReturn(expected);
        when(commentMapper.commentEntityToCommentDTO(any())).thenReturn(commentDTO);

        ProductDetailsDTO result = productService.getProductDetails(productId, appUserDetailsId);

        assertNotNull(result);
        assertEquals(1, result.getComments().size());
        assertEquals(expected.getName(), result.getName());

        verify(productRepository, times(1)).findById(productId);
        verify(commentRepository, times(1)).findByProductId(productId);
        verify(productMapper, times(1)).entityToProductDetailsDTO(productEntity);
        verify(commentMapper, times(commentEntities.size())).commentEntityToCommentDTO(any());
    }

    @Test
    void testGetProductDetails_WhenProductDoesNotExist_ShouldThrowException() {
        Long productId = 1L;
        Long appUserDetailsId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> productService.getProductDetails(productId, appUserDetailsId));

        verify(productRepository, times(1)).findById(productId);
        verify(commentRepository, never()).findByProductId(any());
    }

    @Test
    void testAddProduct_ShouldReturnAddedProduct() {
        ProductAddDTO productAddDTO = new ProductAddDTO(PRODUCT_1, DESCRIPTION_1, IMAGE_URL, QUANTITY, PRICE_1);
        ProductEntity productEntity = new ProductEntity()
                .setName(PRODUCT_1)
                .setDescription(DESCRIPTION_1)
                .setImageUrl(IMAGE_URL)
                .setQuantity(QUANTITY)
                .setPrice(PRICE_1);

        ProductDTO expected = new ProductDTO()
                .setName(PRODUCT_1)
                .setImageUrl(IMAGE_URL)
                .setPrice(PRICE_1);
        ;

        when(productMapper.productDtoToEntity(productAddDTO)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productMapper.entityToProductDTO(productEntity)).thenReturn(expected);

        ProductDTO result = productService.addProduct(productAddDTO);

        assertNotNull(result);
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getPrice(), result.getPrice());

        verify(productMapper, times(1)).productDtoToEntity(productAddDTO);
        verify(productRepository, times(1)).save(productEntity);
        verify(productMapper, times(1)).entityToProductDTO(productEntity);
    }

    @Test
    void testDeleteProduct_ShouldDeleteProduct() {
        Long productId = 1L;
        ProductEntity productEntity = new ProductEntity().setName(PRODUCT_1);
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).findById(productId);
        assertTrue(productEntity.getDeleted());
    }

    @Test
    void testGetProductForUpdate_WhenProductExists_ShouldReturnProductUpdateDetails() {
        Long productId = 1L;
        ProductEntity productEntity = new ProductEntity().setName(PRODUCT_1);
        ProductUpdateDetailsDTO expected = new ProductUpdateDetailsDTO().setName(PRODUCT_1);

        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        when(productMapper.entityToProductUpdateDTO(productEntity)).thenReturn(expected);

        ProductUpdateDetailsDTO result = productService.getProductForUpdate(productId);

        assertNotNull(result);
        assertEquals(expected.getName(), result.getName());

        verify(productRepository, times(1)).findById(productId);

        verify(productMapper, times(1)).entityToProductUpdateDTO(productEntity);
    }

    @Test
    void testGetProductForUpdate_WhenProductDoesNotExist_ShouldThrowException() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> productService.getProductForUpdate(productId));

        verify(productRepository, times(1)).findById(productId);

        verify(productMapper, never()).entityToProductUpdateDTO(any());
    }

    @Test
    void testUpdateProduct_ShouldUpdateProduct() {
        Long productId = 1L;
        ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO(PRODUCT_2, DESCRIPTION_1, QUANTITY, PRICE_1);

        ProductEntity existingProductEntity = new ProductEntity()
                .setName(PRODUCT_1)
                .setDescription(DESCRIPTION_1)
                .setImageUrl(IMAGE_URL)
                .setQuantity(QUANTITY)
                .setPrice(PRICE_1);
        existingProductEntity.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProductEntity));
        when(productRepository.save(existingProductEntity)).thenReturn(existingProductEntity);

        productService.updateProduct(productId, productUpdateDTO);

        assertEquals(PRODUCT_2, existingProductEntity.getName());

        verify(productRepository, times(1)).findById(productId);

        verify(productRepository, times(1)).save(existingProductEntity);
    }
}