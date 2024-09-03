package com.hps.sandbox.controller.console;

import com.hps.sandbox.service.UserService;
import com.hps.sandbox.service.dtos.ProductDto;
import com.hps.sandbox.service.dtos.UserDto;
import com.hps.sandbox.service.dtos.UserSearchDTO;
import com.hps.sandbox.service.exceptions.SandboxEntityNotFound;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController

@RequestMapping("/console/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*@GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseEntity<>(userService.getAllUsers(PageRequest.of(page, size)), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }*/
    @GetMapping("")
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseEntity<>(userService.getAllUsers(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") String userId) throws SandboxEntityNotFound {
        if (userService.findUserById(userId) == null) {
            throw new SandboxEntityNotFound("API with ID " + userId + " not found");
        }
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.save(userDto), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId, @RequestBody UserDto userDto) throws SandboxEntityNotFound {
        userDto.setUserId(Integer.parseInt(userId));
        UserDto updatedUser = userService.updateUser(userDto);
        if (updatedUser == null) {
            throw new SandboxEntityNotFound("API with ID " + userId + " not found");
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) throws SandboxEntityNotFound {
        if (userService.findUserById(userId) == null) {
            throw new SandboxEntityNotFound("API with ID " + userId + " not found");
        }
        userService.deleteUserByIds(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/search")
    public ResponseEntity<Page<UserDto>> searchUsers(@RequestBody UserSearchDTO searchDTO, Pageable pageable) {
        Page<UserDto> userPage = userService.searchByParams(searchDTO, pageable);
        return ResponseEntity.ok(userPage);
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<Integer> uploadStudents(
            @RequestPart("file") MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(userService.uploadUsers(file));


    }


    @GetMapping("/{userId}/products")
    public ResponseEntity<List<ProductDto>> getUserProducts(@PathVariable("userId") String userId) {
        try {
            List<ProductDto> userProducts = userService.getUserProducts(userId);
            return ResponseEntity.ok(userProducts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{userId}/products")
    public ResponseEntity<String> updateUserProducts(@PathVariable String userId,
                                                     @RequestBody List<String> productIds) {
        try {
            userService.updateUserProducts(userId, productIds);
            return ResponseEntity.ok("User products updated successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user products.");
        }
    }

}

