package com.course.coursework.service;

import com.course.coursework.entities.*;
import com.course.coursework.exception.DaoException;
import com.course.coursework.exception.ServiceException;

import java.sql.ResultSet;
import java.util.List;

public interface UserService {

    List<User> findAllUsers() throws ServiceException;
    List<User> showAllUsers() throws ServiceException;
    boolean createUser(User user) throws ServiceException;
    boolean isExistMail(String mai) throws ServiceException;
    List<Model> showAllModels() throws ServiceException;
    List<Object> showAll(int modelId, int carId, int typeId) throws ServiceException;
    boolean deleteModel(int id) throws ServiceException;
    boolean updateModel(Model model) throws ServiceException;
    List<Model> sortByOneParameter(String parameter) throws ServiceException;
    List<Car> showAllCars() throws ServiceException;
    List<Model> countCar(int carId) throws ServiceException;
    List<Model> showOneModel(int modelId) throws ServiceException;
    List<Car> showOneCar(int carId) throws ServiceException;
    List<Type> showOneType(int typeId) throws ServiceException;
    boolean addPurchase(int modelId, int userId, int price) throws ServiceException;
    boolean updateAfterPurchase(int userId, int newDiscountSum, int modelId, boolean isBought) throws ServiceException;
    List<Model> findGolf() throws ServiceException;
    List<Type> showAllTypes() throws ServiceException;
    boolean createModel(Model model) throws ServiceException;
    User findUser(String login) throws ServiceException;
    boolean updateUser(User user) throws ServiceException;
    ResultSet showUserHistory(int userId) throws ServiceException;
}
