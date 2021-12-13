package com.course.coursework.entities;

import com.course.coursework.exception.ServiceException;
import com.course.coursework.service.UserService;
import com.course.coursework.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Facade {

    UserService userService = new UserServiceImpl();

    private List<Car> car;
    private List<Type> type;
    public Facade() {
        car = new ArrayList<>();
        type = new ArrayList<>();
    }

    public List<Car> getCarsList() throws ServiceException {
        car = userService.showAllCars();
        return car;
    }

    public List<Type> getTypesList() throws ServiceException{
        type = userService.showAllTypes();
        return type;
    }
}