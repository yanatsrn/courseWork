package com.course.coursework.dao;

import com.course.coursework.entities.*;
import com.course.coursework.exception.DaoException;
import com.course.coursework.exception.ServiceException;

import java.sql.ResultSet;
import java.util.List;

public interface UserDao {

    List<User> findAllUsers() throws DaoException;
    List<User> showAllUsers() throws DaoException;
    boolean createUser(User user) throws DaoException;
    boolean isExistMail(String mail) throws DaoException;
    List<Model> showAllModels() throws DaoException;
    List<Type> showAllTypes() throws DaoException;
    List<Object> showAll(int modelId, int carId, int typeId) throws DaoException;
    List<Model> showOneModel(int modelId) throws DaoException;
    List<Car> showOneCar(int carId) throws DaoException;
    List<Type> showOneType(int typeId) throws DaoException;
    boolean deleteModel(int id) throws DaoException;
    boolean updateModel(Model model) throws DaoException;
    List<Model> sortByOneParameterModel(String parametr) throws DaoException;
    List<Car> showAllCars() throws DaoException;
    List<Model> countCar(int carId) throws DaoException;
    boolean addPurchase(int modelId, int userId, int price) throws DaoException;
    boolean updateAfterPurchase(int userId, int newDiscountSum, int modelId, boolean isBought) throws DaoException;
    List<Model> findGolf() throws DaoException;
    boolean createModel(Model model) throws DaoException;
    User findUser (String login) throws DaoException;
    boolean updateUser(User user) throws DaoException;
    ResultSet showUserHistory(int userId) throws DaoException;
}
