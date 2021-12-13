package com.course.coursework.dao.impl;

import com.course.coursework.dao.UserDao;
import com.course.coursework.entities.*;
import com.course.coursework.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final String URL = "jdbc:mysql://localhost:3306/autosalon";
    private static final String NAME = "root";
    private static final String PASSWORD = "root";
    public static Connection connection;
    public static PreparedStatement preparedStatement;
    public static Statement statement;

    private static final String SHOW_ALL_PEOPLE = "SELECT `surname`, `name`, `age`, `phone`, `mail`, `user_id`, `login`, `discount_sum` FROM `people`, `users` WHERE users.person_id = people.person_id;";
    private static final String FIND_ALL_USERS = "SELECT * FROM `users`";
    private static final String FIND_USER = "SELECT surname, name, age, phone, mail, user_id, login, password, discount_sum, users.person_id FROM `people`, `users` WHERE `login` = ? AND users.person_id = people.person_id";
    private static final String CREATE_NEW_PERSON = "INSERT INTO `people` (`surname`, `name`, `age`, `phone`, `mail`) VALUES (?, ?, ?, ?, ?);";
    private static final String CREATE_NEW_USER = "INSERT INTO `users` (`login`, `password`, `role`, `person_id`) VALUES (?, ?, ?, ?);";
    private static final String FIND_MAIL = "SELECT * FROM `people` WHERE mail = ?";
    private static final String SHOW_ALL_MODELS = "SELECT * FROM `models`";
    private static final String SHOW_ALL_TYPES = "SELECT * FROM `types`";
    private static final String DELETE_MODEL = "DELETE FROM `models` WHERE `model_id` = ?";
    private static final String UPDATE_MODEL = "UPDATE `models` SET `model_name` = ?, `year` = ?, `distance` = ?, `fuel` =  ?, `fuel_consumption` = ?, `transmission` = ?, `price` = ?  WHERE `model_id` = ?";
    private static final String SORT_BY_NAME = "SELECT * FROM models ORDER BY `model_name`";
    private static final String SORT_BY_YEAR = "SELECT * FROM models ORDER BY `year`";
    private static final String SORT_BY_ID = "SELECT * FROM models ORDER BY `model_id`";
    private static final String SORT_BY_TRANSMISSION = "SELECT * FROM models ORDER BY `transmission`";
    private static final String SORT_BY_FUEL = "SELECT * FROM models ORDER BY `fuel`";
    private static final String SORT_BY_FUEL_CONSUMPTION = "SELECT * FROM models ORDER BY `fuel_consumption`";
    private static final String SORT_BY_PRICE = "SELECT * FROM models ORDER BY `price`";
    private static final String SORT_BY_DISTANCE = "SELECT * FROM models ORDER BY `distance`";
    private static final String SHOW_ALL_CARS = "SELECT * FROM `cars`";
    private static final String SHOW_COUNTED_MODELS = "SELECT * FROM `models` WHERE `car_id` = ?";
    private static final String SHOW_ONE_MODEL = "SELECT * FROM `models` WHERE `model_id` = ?";
    private static final String SHOW_ONE_CAR = "SELECT * FROM `cars` WHERE `car_id` = ?";
    private static final String SHOW_ONE_TYPE = "SELECT * FROM `types` WHERE `type_id` = ?";
    private static final String ADD_PURCHASE = "INSERT INTO `purchases` (`price`, `model_id`, `user_id`) VALUES (?, ?, ?);";
    private static final String UPDATE_DISCOUNTSUM = "UPDATE users SET `discount_sum` = ? WHERE user_id = ?";
    private static final String UPDATE_ISBOUGHT = "UPDATE models SET `is_bought` = ? WHERE model_id = ?";
    private static final String FIND_GOLF = "SELECT * FROM models WHERE model_name = 'Golf'";
    private static final String CREATE_NEW_MODEL = "INSERT INTO `models` (`model_name`, `year`, `distance`, `fuel`, `fuel_consumption`, `transmission`, `price`,`car_id`, `type_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE users, people SET surname = ?, name = ?, age = ?, phone = ?, password = ?  WHERE users.person_id = ? AND people.person_id = ?";
    private static final String SHOW_USER_HISTORY = "SELECT model_name, purchases.price FROM purchases, models WHERE purchases.user_id = ? AND models.model_id = purchases.model_id";


    @Override
    public List<User> findAllUsers() throws DaoException {
        List<User> users = new ArrayList<>();
        connection = null;
        preparedStatement = null;
        ResultSet resultSet = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(FIND_ALL_USERS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setRole(RoleType.valueOf(resultSet.getString(4).toUpperCase()));
                user.setDiscountSum(resultSet.getInt(5));
                user.setPersonId(resultSet.getInt(6));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try { //только connection или все ?
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public List<User> showAllUsers() throws DaoException {
        List<User> users = new ArrayList<>();
        connection = null;
        preparedStatement = null;
        ResultSet resultSet = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(SHOW_ALL_PEOPLE);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setSurname(resultSet.getString(1));
                user.setName(resultSet.getString(2));
                user.setAge(resultSet.getInt(3));
                user.setPhone(resultSet.getString(4));
                user.setMail(resultSet.getString(5));
                user.setUserId(resultSet.getInt(6));
                user.setLogin(resultSet.getString(7));
                user.setDiscountSum(resultSet.getInt(8));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try { //только connection или все ?
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public boolean createUser(User user) throws DaoException {
        boolean isAdd = true;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            if (createPerson(user)) {
                preparedStatement = connection.prepareStatement(CREATE_NEW_USER);
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getRole().name());
                preparedStatement.setInt(4, user.getPersonId());

                isAdd = preparedStatement.executeUpdate() > 0;

            }

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try { //только connection или все ?
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isAdd;
    }

    public boolean createPerson(User user) throws DaoException {
        boolean isAdd;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(CREATE_NEW_PERSON);
            preparedStatement.setString(1, user.getSurname());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getMail());

            isAdd = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try { //только connection или все ?
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isAdd;
    }

    @Override
    public boolean updateAfterPurchase(int userId, int newDiscountSum, int modelId, boolean isBought) throws DaoException {
        boolean isUpdated = false;
        try {
            if(updateModelAfterPurchase(modelId, isBought)) {
                if(updateUserAfterPurchase(userId, newDiscountSum))
                    isUpdated = true;
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    public boolean updateUserAfterPurchase(int userId, int newDiscountSum) throws DaoException {
        boolean isUpdated;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(UPDATE_DISCOUNTSUM);
            preparedStatement.setInt(1, newDiscountSum);
            preparedStatement.setInt(2, userId);

            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isUpdated;
    }

    public boolean updateModelAfterPurchase(int modelId, boolean isBought) throws DaoException {
        boolean isUpdated;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(UPDATE_ISBOUGHT);
            preparedStatement.setBoolean(1, isBought);
            preparedStatement.setInt(2, modelId);

            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isUpdated;
    }

    @Override
    public boolean isExistMail(String mail) throws DaoException {
        boolean isExist = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(FIND_MAIL);
            preparedStatement.setString(1, mail);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                isExist = true;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isExist;
    }

    @Override
    public List<Model> showAllModels() throws DaoException {
        List<Model> models = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(SHOW_ALL_MODELS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Model modelEntity = new Model();
                modelEntity.setModelId(resultSet.getInt(1));
                modelEntity.setName(resultSet.getString(2));
                modelEntity.setYear(resultSet.getString(3));
                modelEntity.setDistance(resultSet.getInt(4));
                modelEntity.setFuel(resultSet.getString(5));
                modelEntity.setFuelConsumption(resultSet.getString(6));
                modelEntity.setTransmission(resultSet.getString(7));
                modelEntity.setPrice(resultSet.getInt(8));
                modelEntity.setBought(resultSet.getBoolean(9));
                modelEntity.setCarId(resultSet.getInt(10));
                modelEntity.setTypeId(resultSet.getInt(11));

                models.add(modelEntity);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return models;
    }

    @Override
    public List<Object> showAll(int modelId, int carId, int typeId) throws DaoException {
        List<Model> modelsList = showOneModel(modelId);
        List<Car> carsList = showOneCar(carId);
        List<Type> typeList = showOneType(typeId);
        List<Object> list = new ArrayList<>();
        list.add(carsList);
        list.add(modelsList);
        list.add(typeList);

        return list;
    }

    @Override
    public List<Model> showOneModel(int id) throws DaoException {
        List<Model> models = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(SHOW_ONE_MODEL);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Model modelEntity = new Model();
                modelEntity.setModelId(resultSet.getInt(1));
                modelEntity.setName(resultSet.getString(2));
                modelEntity.setYear(resultSet.getString(3));
                modelEntity.setDistance(resultSet.getInt(4));
                modelEntity.setFuel(resultSet.getString(5));
                modelEntity.setFuelConsumption(resultSet.getString(6));
                modelEntity.setTransmission(resultSet.getString(7));
                modelEntity.setPrice(resultSet.getInt(8));
                modelEntity.setBought(resultSet.getBoolean(9));
                modelEntity.setCarId(resultSet.getInt(10));
                modelEntity.setTypeId(resultSet.getInt(11));

                models.add(modelEntity);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return models;
    }

    @Override
    public List<Car> showOneCar(int id) throws DaoException {
        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(SHOW_ONE_CAR);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Car entity = new Car();
                entity.setCarId(resultSet.getInt(1));
                entity.setName(resultSet.getString(2));
                entity.setCountry(resultSet.getString(3));
                cars.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cars;
    }

    @Override
    public List<Type> showOneType(int id) throws DaoException {

        List<Type> types = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(SHOW_ONE_TYPE);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Type typeEntity = new Type();
                typeEntity.setTypeId(resultSet.getInt(1));
                typeEntity.setBody(resultSet.getString(2));
                typeEntity.setSalon(resultSet.getString(3));
                typeEntity.setColor(resultSet.getString(4));
                typeEntity.setParkingHelper(resultSet.getBoolean(5));
                types.add(typeEntity);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return types;

    }

    @Override
    public List<Model> findGolf() throws DaoException {
        List<Model> models = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(FIND_GOLF);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Model modelEntity = new Model();
                modelEntity.setModelId(resultSet.getInt(1));
                modelEntity.setName(resultSet.getString(2));
                modelEntity.setYear(resultSet.getString(3));
                modelEntity.setDistance(resultSet.getInt(4));
                modelEntity.setFuel(resultSet.getString(5));
                modelEntity.setFuelConsumption(resultSet.getString(6));
                modelEntity.setTransmission(resultSet.getString(7));
                modelEntity.setPrice(resultSet.getInt(8));
                modelEntity.setBought(resultSet.getBoolean(9));
                modelEntity.setCarId(resultSet.getInt(10));
                modelEntity.setTypeId(resultSet.getInt(11));

                models.add(modelEntity);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return models;
    }

    @Override
    public List<Type> showAllTypes() throws DaoException {
        List<Type> types = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(SHOW_ALL_TYPES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Type typeEntity = new Type();
                typeEntity.setTypeId(resultSet.getInt(1));
                typeEntity.setBody(resultSet.getString(2));
                typeEntity.setSalon(resultSet.getString(3));
                typeEntity.setColor(resultSet.getString(4));
                typeEntity.setParkingHelper(resultSet.getBoolean(5));
                types.add(typeEntity);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return types;
    }

    @Override
    public boolean deleteModel(int id) throws DaoException {
        boolean isDeleted;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(DELETE_MODEL);
            preparedStatement.setInt(1, id);
            isDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try { //только connection или все ?
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isDeleted;
    }

    @Override
    public boolean updateModel(Model model) throws DaoException {
        boolean isUpdate;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(UPDATE_MODEL);
            preparedStatement.setString(1, model.getName());
            preparedStatement.setString(2, model.getYear());
            preparedStatement.setInt(3, model.getDistance());
            preparedStatement.setString(4, model.getFuel());
            preparedStatement.setString(5, model.getFuelConsumption());
            preparedStatement.setString(6, model.getTransmission());
            preparedStatement.setInt(7, model.getPrice());
            preparedStatement.setInt(8, model.getModelId());
            isUpdate = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isUpdate;
    }

    @Override
    public List<Model> sortByOneParameterModel(String parameter) throws DaoException {
        List<Model> models = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            switch(parameter) {
                case "ID":
                    preparedStatement = connection.prepareStatement(SORT_BY_ID);
                    break;
                case "Название":
                    preparedStatement = connection.prepareStatement(SORT_BY_NAME);
                    break;
                case "Год":
                    preparedStatement = connection.prepareStatement(SORT_BY_YEAR);
                    break;
                case "Пробег":
                    preparedStatement = connection.prepareStatement(SORT_BY_DISTANCE);
                    break;
                case "Топливо":
                    preparedStatement = connection.prepareStatement(SORT_BY_FUEL);
                    break;
                case "Расход":
                    preparedStatement = connection.prepareStatement(SORT_BY_FUEL_CONSUMPTION);
                    break;
                case "Коробка передач":
                    preparedStatement = connection.prepareStatement(SORT_BY_TRANSMISSION);
                    break;
                case "Цена":
                    preparedStatement = connection.prepareStatement(SORT_BY_PRICE);
                    break;
            }
            //preparedStatement = connection.prepareStatement(SORT_BY_ONE_PARAMETER);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Model modelEntity = new Model();
                modelEntity.setModelId(resultSet.getInt(1));
                modelEntity.setName(resultSet.getString(2));
                modelEntity.setYear(resultSet.getString(3));
                modelEntity.setDistance(resultSet.getInt(4));
                modelEntity.setFuel(resultSet.getString(5));
                modelEntity.setFuelConsumption(resultSet.getString(6));
                modelEntity.setTransmission(resultSet.getString(7));
                modelEntity.setPrice(resultSet.getInt(8));
                modelEntity.setBought(resultSet.getBoolean(9));
                modelEntity.setCarId(resultSet.getInt(10));
                modelEntity.setTypeId(resultSet.getInt(11));

                models.add(modelEntity);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return models;
    }

    @Override
    public List<Car> showAllCars() throws DaoException {
        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(SHOW_ALL_CARS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Car entity = new Car();
                entity.setCarId(resultSet.getInt(1));
                entity.setName(resultSet.getString(2));
                entity.setCountry(resultSet.getString(3));
                cars.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cars;
    }

    @Override
    public List<Model> countCar(int carId) throws DaoException {
        List<Model> models = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(SHOW_COUNTED_MODELS);
            preparedStatement.setInt(1, carId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Model modelEntity = new Model();
                modelEntity.setModelId(resultSet.getInt(1));
                modelEntity.setName(resultSet.getString(2));
                modelEntity.setYear(resultSet.getString(3));
                modelEntity.setDistance(resultSet.getInt(4));
                modelEntity.setFuel(resultSet.getString(5));
                modelEntity.setFuelConsumption(resultSet.getString(6));
                modelEntity.setTransmission(resultSet.getString(7));
                modelEntity.setPrice(resultSet.getInt(8));
                modelEntity.setBought(resultSet.getBoolean(9));
                modelEntity.setCarId(resultSet.getInt(10));
                modelEntity.setTypeId(resultSet.getInt(11));

                models.add(modelEntity);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return models;
    }

    @Override
    public boolean addPurchase(int modelId, int userId, int price) throws DaoException {
        boolean isAdded;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(ADD_PURCHASE);
            preparedStatement.setInt(1, price);
            preparedStatement.setInt(2, modelId);
            preparedStatement.setInt(3, userId);

            isAdded = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isAdded;
    }

    @Override
    public boolean createModel(Model model) throws DaoException {
        boolean isAdded = true;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(CREATE_NEW_MODEL);
            preparedStatement.setString(1, model.getName());
            preparedStatement.setString(2, model.getYear());
            preparedStatement.setInt(3, model.getDistance());
            preparedStatement.setString(4, model.getFuel());
            preparedStatement.setString(5, model.getFuelConsumption());
            preparedStatement.setString(6, model.getTransmission());
            preparedStatement.setInt(7, model.getPrice());
            preparedStatement.setInt(8, model.getCarId());
            preparedStatement.setInt(9, model.getTypeId());

            isAdded = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try { //только connection или все ?
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isAdded;
    }

    @Override
    public User findUser(String login) throws DaoException {
        User entity = new User();
        connection = null;
        preparedStatement = null;
        ResultSet resultSet = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(FIND_USER);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entity.setSurname(resultSet.getString(1));
                entity.setName(resultSet.getString(2));
                entity.setAge(resultSet.getInt(3));
                entity.setPhone(resultSet.getString(4));
                entity.setMail(resultSet.getString(5));
                entity.setUserId(resultSet.getInt(6));
                entity.setLogin(resultSet.getString(7));
                entity.setPassword(resultSet.getString(8));
                entity.setDiscountSum(resultSet.getInt(9));
                entity.setPersonId(resultSet.getInt(10));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try { //только connection или все ?
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return entity;
    }

    @Override
    public boolean updateUser(User user) throws DaoException {
        boolean isUpdated;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, user.getSurname());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setInt(6, user.getPersonId());
            preparedStatement.setInt(7, user.getPersonId());

            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isUpdated;
    }

    @Override
    public ResultSet showUserHistory(int userId) throws DaoException {
        connection = null;
        preparedStatement = null;
        ResultSet resultSet = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(SHOW_USER_HISTORY);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return resultSet;
    }
}
