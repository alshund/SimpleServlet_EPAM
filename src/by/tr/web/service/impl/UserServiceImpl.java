package by.tr.web.service.impl;

import by.tr.web.dao.DAOFactory;
import by.tr.web.dao.UserDAO;
import by.tr.web.dao.exception.DAOSearchException;
import by.tr.web.domain.User;
import by.tr.web.service.UserService;
import by.tr.web.service.UtilityData;
import by.tr.web.service.exception.ServiceSearchException;
import by.tr.web.service.validator.NameSurnameValidatorImpl;
import by.tr.web.service.validator.Validator;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO;// поле не помешает сделать final

    public UserServiceImpl() {

        DAOFactory daoFactory = DAOFactory.getInstance();
        userDAO = daoFactory.getUserDAO();
    }

    @Override
    public List<User> nameSurnameSearch(String name, String surname) throws ServiceSearchException {

        Validator validator = new NameSurnameValidatorImpl();// ну не пложите сущности сверх необходимого
        // зачем каждый раз создавать новый валидатор?
        
        if (validator.execute(name, surname)) {// условие в if пишется на самый ожидаемый результат   if (!validator.execute(name, surname))
            try {
                List<User> users = userDAO.nameSurnameSearch(name, surname);
                return users;
            } catch (DAOSearchException | SQLException e) {
                throw new ServiceSearchException(UtilityData.SERVICE_SEARCH_EXCEPTION_MESSAGE, e);
            }
        } else {
            return null;// какой return null при невалидных параметрах?
            // мы уже давно исключения прошли
        }
    }
}
