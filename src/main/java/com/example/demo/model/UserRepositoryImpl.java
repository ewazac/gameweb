//package com.example.demo.model;
//
//import org.bson.types.Binary;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.List;
//import java.util.Optional;
//
//public class UserRepositoryImpl implements UserRepository {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public AppUser findUserByEmail(String email) {
//        return userRepository.findUserByEmail(email);
//    }
//
//    @Override
//    public AppUser findUserById(String id) {
//        return null;
//    }
//
//    @Override
//    public AppUser findByAvatar(Binary avatar) {
//        return null;
//    }
//
//
//    public boolean checkIfValidPassword(AppUser appUser, String oldPassword) {
//        return passwordEncoder.matches(oldPassword, appUser.getPassword());
//    }
//
//
//    public void changePassword(AppUser appUser, String password) {
//        appUser.setPassword(passwordEncoder.encode(password));
//        userRepository.save(appUser);
//    }
//
//    @Override
//    public <S extends AppUser> S save(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends AppUser> List<S> saveAll(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public Optional<AppUser> findById(String s) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsById(String s) {
//        return false;
//    }
//
//    @Override
//    public List<AppUser> findAll() {
//        return null;
//    }
//
//    @Override
//    public Iterable<AppUser> findAllById(Iterable<String> strings) {
//        return null;
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void deleteById(String s) {
//
//    }
//
//    @Override
//    public void delete(AppUser entity) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends AppUser> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public List<AppUser> findAll(Sort sort) {
//        return null;
//    }
//
//    @Override
//    public Page<AppUser> findAll(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public <S extends AppUser> S insert(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends AppUser> List<S> insert(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public <S extends AppUser> Optional<S> findOne(Example<S> example) {
//        return Optional.empty();
//    }
//
//    @Override
//    public <S extends AppUser> List<S> findAll(Example<S> example) {
//        return null;
//    }
//
//    @Override
//    public <S extends AppUser> List<S> findAll(Example<S> example, Sort sort) {
//        return null;
//    }
//
//    @Override
//    public <S extends AppUser> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public <S extends AppUser> long count(Example<S> example) {
//        return 0;
//    }
//
//    @Override
//    public <S extends AppUser> boolean exists(Example<S> example) {
//        return false;
//    }
//}
