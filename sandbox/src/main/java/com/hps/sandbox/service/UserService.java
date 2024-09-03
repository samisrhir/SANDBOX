package com.hps.sandbox.service;

import com.hps.sandbox.entity.Product;
import com.hps.sandbox.entity.User;
import com.hps.sandbox.repository.ProductRepository;
import com.hps.sandbox.repository.UserRepository;
import com.hps.sandbox.service.dtos.ProductDto;
import com.hps.sandbox.service.dtos.UserCsvRepresentation;
import com.hps.sandbox.service.dtos.UserDto;
import com.hps.sandbox.service.dtos.UserSearchDTO;
import com.hps.sandbox.service.exceptions.SandboxEntityNotFound;
import com.hps.sandbox.service.mappers.ProductMapper;
import com.hps.sandbox.service.mappers.UserMapper;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.criteria.Predicate;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper; // Import ProductMapper if it's not imported already
    private final UserMapper userMapper;

    // Constructor injection
    public UserService(UserRepository userRepository,ProductRepository productRepository, ProductMapper productMapper, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.productMapper = productMapper;
        this.userMapper = userMapper;
        this.productRepository = productRepository;
    }


    @Transactional
    public UserDto save(UserDto dto) {

        userRepository.findByUsername(dto.getUsername()).ifPresent(user -> {
            throw new RuntimeException("User already exists");
        });
        log.info("Saving user ...");
        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        final User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .enabled(dto.isEnabled())
                .products(dto.getProducts())
                .createdDate(new Date())
                .validityRangeDate(dto.getValidityRangeDate())
                .userId(dto.getUserId())
                .fullName(dto.getFullName())
                .phone(dto.getPhone())
                .build();
        userRepository.save(user);
        return userMapper.toModel(user);
    }


    public Page<UserDto> getAllUsers(PageRequest pageRequest) {

        Page<User> results = userRepository
                .findAll(pageRequest);
        return results
                .map(e->{
                    UserDto dto = userMapper.toModel(e);
                    dto.setIsActive(dto.isEnabled()?"Active account":"Inactive account");
                    dto.setPassword(null);
                    return dto;
                });
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    UserDto dto = userMapper.toModel(user);
                    dto.setIsActive(user.isEnabled() ? "Active" : "Inactive");
                    dto.setPassword(null);
                    return dto;
                })
                .collect(Collectors.toList());
    }
 public List<UserDto> getAllUsersByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
        return users.stream()
                .map(user -> {
                    UserDto dto = userMapper.toModel(user);
                    dto.setIsActive(user.isEnabled() ? "Active account" : "Inactive account");
                    dto.setPassword(null);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    public void deleteUserByIds(String id) {
        Integer userId = Integer.valueOf(id);
        userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));
        userRepository.deleteById(userId);
    }

    public Optional<UserDto> findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(userMapper::toModel);
    }


    public Optional<User>findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public UserDto findUserById(String id) {
        return userMapper
                .toModel(userRepository
                        .findById(Integer.valueOf(id))
                        .orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found")));
    }


    public UserDto updateUser(UserDto dto) throws SandboxEntityNotFound {
        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new SandboxEntityNotFound("User not found"));
        String password = user.getPassword();

        userMapper.updateUser(dto, user);
        user.setPassword(password);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
      //  user.setProducts(dto.getProducts());
        user.setCredentialsNonExpired(true);

        userRepository.save(user);
        return userMapper.toModel(user);
    }
    public Page<UserDto> searchByParams(UserSearchDTO searchDTO, Pageable pageable) {
        LocalDate startRange = null;
        LocalDate endRange = null;

        if (searchDTO.getStartDate() != null && !searchDTO.getStartDate().isEmpty()) {
            startRange = LocalDate.parse(searchDTO.getStartDate(), DateTimeFormatter.ISO_DATE);
        }
        if (searchDTO.getEndDate() != null && !searchDTO.getEndDate().isEmpty()) {
            endRange = LocalDate.parse(searchDTO.getEndDate(), DateTimeFormatter.ISO_DATE);
        }

        Specification<User> spec = getUserQuery(
                searchDTO.getFullName(),
                searchDTO.isEnabled(),
                startRange,
                endRange,
                searchDTO.getPhone(),
                searchDTO.getEmail());

        Page<User> userPage = userRepository.findAll(spec, pageable);

        List<UserDto> userDtoList = userPage.getContent().stream()
                .map(userMapper::toModel)
                .collect(Collectors.toList());

        return new PageImpl<>(userDtoList, pageable, userPage.getTotalElements());
    }


    public static Specification<User> getUserQuery(String keyword, boolean enabled, LocalDate startDate, LocalDate endDate,
                                                   String phone, String email) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isEmpty()) {
                String keywordLowerCase = keyword.toLowerCase(); // Convert keyword to lowercase
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + keywordLowerCase + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + keywordLowerCase + "%")
                ));
            }

            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), startDate));
            }

            if (endDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), endDate));
            }

            if (email != null && !email.isEmpty()) {
                String emailLowerCase = email.toLowerCase(); // Convert email to lowercase
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), emailLowerCase + "%"));
            }

            if (phone != null && !phone.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("phone"), "%" + phone + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }



    public Integer uploadUsers(MultipartFile file) throws IOException {
        Set<User> users = parseCsv(file);
        userRepository.saveAll(users);
        return users.size();
    }

    private Set<User> parseCsv(MultipartFile file) throws IOException {
        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<UserCsvRepresentation> strategy =
                    new HeaderColumnNameMappingStrategy<>();
            strategy.setType(UserCsvRepresentation.class);
            CsvToBean<UserCsvRepresentation> csvToBean =
                    new CsvToBeanBuilder<UserCsvRepresentation>(reader)
                            .withMappingStrategy(strategy)
                            .withIgnoreEmptyLine(true)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();
            return csvToBean.parse()
                    .stream()
                    .map(csvLine -> User.builder()
                            .fullName(csvLine.getFullName())
                            .username(csvLine.getUserName())
                            .email(csvLine.getEmail())
                            .phone(csvLine.getPhone())
                            .password(passwordEncoder.encode(csvLine.getPassword()))
                            .enabled(csvLine.isEnabled())
                            .build()
                    )
                    .collect(Collectors.toSet());
        }
    }

    public List<ProductDto> getUserProducts(String userId) {
        return userRepository.findById(Integer.parseInt(userId))
                .map(user -> user.getProducts()
                        .stream()
                        .map(productMapper::toModel) // Assuming you have a mapper from Product to ProductDto
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));
    }

    public void updateUserProducts(String userId, List<String> productIds) {
        // Find the user by userId
        User user = userRepository.findById(Integer.parseInt(userId))
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));

        // Convert productIds to a list of Product entities (assuming you have a repository for Product entity)
        List<Product> products = productIds.stream()
                .map(productId -> productRepository.findById(Integer.parseInt(productId))
                        .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productId + " not found")))
                .toList();
                                                                        
          user.setProducts(new HashSet<>(products));
        // Save the updated user entity
        userRepository.save(user);
    }
}

















