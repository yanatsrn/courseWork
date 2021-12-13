package com.course.coursework.service.impl;

import com.course.coursework.dao.UserDao;
import com.course.coursework.dao.impl.UserDaoImpl;
import com.course.coursework.entities.*;
import com.course.coursework.exception.DaoException;
import com.course.coursework.exception.ServiceException;
import com.course.coursework.service.UserService;

import java.sql.ResultSet;
import java.util.List;

public class UserServiceImpl implements UserService {

    public UserDao userDao = new UserDaoImpl();

    @Override
    public List<User> findAllUsers() throws ServiceException {
        List<User> users;
        try {
            users = userDao.findAllUsers();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public List<User> showAllUsers() throws ServiceException {
        List<User> users;
        try {
            users = userDao.showAllUsers();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public boolean createUser(User user) throws ServiceException {
        boolean isAdd;
        try {
            isAdd = userDao.createUser(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isAdd;
    }

    @Override
    public boolean isExistMail(String mail) throws ServiceException {
        boolean isExist;
        try {
            isExist = userDao.isExistMail(mail);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isExist;
    }

    @Override
    public List<Model> showAllModels() throws ServiceException {
        List<Model> models;
        try {
            models = userDao.showAllModels();
        }
        catch (DaoException e) {
            throw new ServiceException();
        }
        return models;
    }


    @Override
    public List<Object> showAll(int modelId, int carId, int typeId) throws ServiceException {
        List<Object> list;
        try {
            list = userDao.showAll(modelId, carId, typeId);
        }
        catch (DaoException e) {
            throw new ServiceException();
        }
        return list;
    }

    @Override
    public List<Model> showOneModel(int modelId) throws ServiceException {
        List<Model> list;
        try {
            list = userDao.showOneModel(modelId);
        }
        catch (DaoException e) {
            throw new ServiceException();
        }
        return list;
    }

    @Override
    public List<Car> showOneCar(int carId) throws ServiceException {
        List<Car> list;
        try {
            list = userDao.showOneCar(carId);
        }
        catch (DaoException e) {
            throw new ServiceException();
        }
        return list;
    }

    @Override
    public List<Type> showOneType(int typeId) throws ServiceException {
        List<Type> list;
        try {
            list = userDao.showOneType(typeId);
        }
        catch (DaoException e) {
            throw new ServiceException();
        }
        return list;
    }

    @Override
    public boolean deleteModel(int id) throws ServiceException {
        boolean isDeleted;
        try {
             isDeleted = userDao.deleteModel(id);
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return isDeleted;
    }

    @Override
    public boolean updateModel(Model model) throws ServiceException {
        boolean isUpdated;
        try {
            isUpdated = userDao.updateModel(model);
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return isUpdated;
    }

    @Override
    public List<Model> sortByOneParameter(String parameter) throws ServiceException {
        List<Model> models;
        try {
            models = userDao.sortByOneParameterModel(parameter);
        }
        catch (DaoException e) {
            throw new ServiceException();
        }
        return models;
    }

    @Override
    public List<Car> showAllCars() throws ServiceException {
        List<Car> cars;
        try {
            cars = userDao.showAllCars();
        }
        catch (DaoException e) {
            throw new ServiceException();
        }
        return cars;
    }

    @Override
    public List<Type> showAllTypes() throws ServiceException {
        List<Type> types;
        try {
            types = userDao.showAllTypes();
        }
        catch (DaoException e) {
            throw new ServiceException();
        }
        return types;
    }

    @Override
    public List<Model> countCar(int carId) throws ServiceException {
        List<Model> cars;
        try {
            cars = userDao.countCar(carId);
        }
        catch (DaoException e) {
            throw new ServiceException();
        }
        return cars;
    }

    @Override
    public boolean addPurchase(int modelId, int userId, int price) throws ServiceException {
        boolean isAdded;
        try {
            isAdded = userDao.addPurchase(modelId, userId, price);
        }
        catch (DaoException e) {
            throw new ServiceException();
        }
        return isAdded;
    }

    @Override
    public boolean updateAfterPurchase(int userId, int newDiscountSum, int modelId, boolean isBought) throws ServiceException {
        boolean isUpdated;
        try {
            isUpdated = userDao.updateAfterPurchase(userId, newDiscountSum, modelId, isBought);
        }
        catch (DaoException e) {
            throw new ServiceException();
        }
        return isUpdated;
    }

    @Override
    public List<Model> findGolf() throws ServiceException {
        List<Model> models;
        try {
            models = userDao.findGolf();
        }
        catch (DaoException e) {
            throw new ServiceException();
        }
        return models;
    }

    @Override
    public boolean createModel(Model model) throws ServiceException {
        boolean isAdded;
        try {
            isAdded = userDao.createModel(model);
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return isAdded;
    }

    @Override
    public User findUser(String login) throws ServiceException {
        User user = new User();
        try {
            user = userDao.findUser(login);
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return user;
    }

    @Override
    public boolean updateUser(User user) throws ServiceException {
        boolean isAdded;
        try {
            isAdded = userDao.updateUser(user);
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return isAdded;
    }

    @Override
    public ResultSet showUserHistory(int userId) throws ServiceException {
        ResultSet resultSet;
        try {
            resultSet = userDao.showUserHistory(userId);
        }
        catch (DaoException e) {
            throw new ServiceException();
        }
        return resultSet;
    }
}
