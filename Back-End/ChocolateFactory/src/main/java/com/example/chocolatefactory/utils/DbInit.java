package com.example.chocolatefactory.utils;

import com.example.chocolatefactory.domain.entities.*;
import com.example.chocolatefactory.domain.enums.MessageStatus;
import com.example.chocolatefactory.domain.enums.OrderStatus;
import com.example.chocolatefactory.domain.enums.RoleEnum;
import com.example.chocolatefactory.domain.enums.UserStatus;
import com.example.chocolatefactory.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
public class DbInit implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private MessageRepository messageRepository;
    private UserEntity user;
    private UserEntity moderator;
    private UserEntity admin;
    private ProductEntity productEntity1;
    private ProductEntity productEntity2;
    private ProductEntity productEntity3;
    private ProductEntity productEntity4;

    @Autowired
    public DbInit(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                  ProductRepository productRepository, OrderRepository orderRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public void run(String... args) {
        initRoles();
        initInitialUsers();
        initProducts();
        initOrders();
        initMessages();
    }

    public void initRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.save(new RoleEntity().setRole(RoleEnum.ROLE_USER));
            roleRepository.save(new RoleEntity().setRole(RoleEnum.ROLE_MODERATOR));
            roleRepository.save(new RoleEntity().setRole(RoleEnum.ROLE_ADMIN));
        }
    }

    public void initInitialUsers() {
        if (userRepository.count() == 0) {
            RoleEntity userRole = roleRepository.findByRole(RoleEnum.ROLE_USER);
            RoleEntity moderatorRole = roleRepository.findByRole(RoleEnum.ROLE_MODERATOR);
            RoleEntity adminRole = roleRepository.findByRole(RoleEnum.ROLE_ADMIN);

            user = new UserEntity()
                    .setEmail("user@gmail.com")
                    .setPassword(passwordEncoder.encode("123456"))
                    .setFullName("User Userov")
                    .setCity("Sofia")
                    .setAddress("Geo Milev")
                    .setPhone("0888 111 111")
                    .setRoles(Set.of(userRole))
                    .setUserStatus(UserStatus.ACTIVE)
                    .setBonusPoints(0);
            moderator = new UserEntity()
                    .setEmail("moderator@gmail.com")
                    .setPassword(passwordEncoder.encode("123456"))
                    .setFullName("Moderator Moderatorov")
                    .setCity("Sofia")
                    .setAddress("Studentski")
                    .setPhone("0888 222 222")
                    .setRoles(Set.of(userRole, moderatorRole))
                    .setUserStatus(UserStatus.ACTIVE)
                    .setBonusPoints(0);
            admin = new UserEntity()
                    .setEmail("admin@gmail.com")
                    .setPassword(passwordEncoder.encode("123456"))
                    .setFullName("Admin Adminov")
                    .setCity("Sofia")
                    .setAddress("Ivan Vazov")
                    .setPhone("0888 333 333")
                    .setRoles(Set.of(userRole, moderatorRole, adminRole))
                    .setUserStatus(UserStatus.ACTIVE)
                    .setBonusPoints(0);

            userRepository.save(user);
            userRepository.save(moderator);
            userRepository.save(admin);
        }
    }

    public void initProducts() {
        if (productRepository.count() == 0) {
            productEntity1 = new ProductEntity()
                    .setName("2 bons").setDescription("2 bons")
                    .setImageUrl("https://res.cloudinary.com/lyb4ooo/image/upload/v1696582706/2pc_bons_zxy5nm.jpg")
                    .setPrice(BigDecimal.TEN).setQuantity(1000)
                    .setDepleted(false).setLowQuantity(false).setDeleted(false);
            productEntity2 = new ProductEntity()
                    .setName("8 bons").setDescription("8 bons")
                    .setImageUrl("https://res.cloudinary.com/lyb4ooo/image/upload/v1696582728/8PC_Interior_wshc3w.jpg")
                    .setPrice(BigDecimal.TEN).setQuantity(1000)
                    .setDepleted(false).setLowQuantity(false).setDeleted(false);
            productEntity3 = new ProductEntity()
                    .setName("16 bons").setDescription("16 bons")
                    .setImageUrl("https://res.cloudinary.com/lyb4ooo/image/upload/v1696582735/16pcbons_n5a4u7.jpg")
                    .setPrice(BigDecimal.TEN).setQuantity(1000)
                    .setDepleted(false).setLowQuantity(false).setDeleted(false);
            productEntity4 = new ProductEntity()
                    .setName("20 bons").setDescription("20 bons")
                    .setImageUrl("https://res.cloudinary.com/lyb4ooo/image/upload/v1696582740/20pc_Bonbon_Interior_gcpgou.jpg")
                    .setPrice(BigDecimal.TEN).setQuantity(1000)
                    .setDepleted(false).setLowQuantity(false).setDeleted(false);

            productRepository.save(productEntity1);
            productRepository.save(productEntity2);
            productRepository.save(productEntity3);
            productRepository.save(productEntity4);
        }
    }

    public void initOrders() {
        if (orderRepository.count() == 0) {
            productEntity1 = productRepository.findById(1L).get();
            productEntity2 = productRepository.findById(2L).get();
            productEntity3 = productRepository.findById(3L).get();
            productEntity4 = productRepository.findById(4L).get();

            user = userRepository.findById(1L).get();
            admin = userRepository.findById(3L).get();

            List<ProductEntity> products1 = List.of(productEntity1, productEntity2);
            BigDecimal total1 = products1.stream().map(ProductEntity::getPrice).reduce(BigDecimal::add).get();

            OrderEntity orderEntity1 = new OrderEntity()
                    .setOrderNumber(UUID.fromString("5ec0bc77-1a81-493b-8197-b0af73ab565c"))
                    .setStatus(OrderStatus.WAITING)
                    .setBuyer(user)
                    .setProducts(products1)
                    .setTotal(total1);

            List<ProductEntity> products2 = List.of(productEntity2, productEntity3, productEntity4);
            BigDecimal total2 = products2.stream().map(ProductEntity::getPrice).reduce(BigDecimal::add).get();

            OrderEntity orderEntity2 = new OrderEntity()
                    .setOrderNumber(UUID.randomUUID())
                    .setStatus(OrderStatus.WAITING)
                    .setBuyer(admin)
                    .setProducts(products2)
                    .setTotal(total2);

            orderRepository.save(orderEntity1);
            orderRepository.save(orderEntity2);
        }
    }

    private void initMessages() {
        if (messageRepository.count() == 0) {
            MessageEntity messageEntity1 = new MessageEntity()
                    .setTitle("Title one").setContact("user@gmail.com")
                    .setContent("Some content").setStatus(MessageStatus.UNANSWERED);
            MessageEntity messageEntity2 = new MessageEntity()
                    .setTitle("Title two").setContact("user@gmail.com")
                    .setContent("Some other content").setStatus(MessageStatus.UNANSWERED);

            messageRepository.save(messageEntity1);
            messageRepository.save(messageEntity2);
        }
    }
}
