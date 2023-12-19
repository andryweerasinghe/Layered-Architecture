package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> {
    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
    boolean save(T customerDTO) throws SQLException, ClassNotFoundException;
    boolean update(T customerDTO) throws SQLException, ClassNotFoundException;
    boolean isExist(String id) throws SQLException, ClassNotFoundException;
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    String generateNewId() throws SQLException, ClassNotFoundException;
    T Search(String id) throws SQLException, ClassNotFoundException;
}
