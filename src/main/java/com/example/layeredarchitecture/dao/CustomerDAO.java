package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface CustomerDAO {
    ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;
    boolean save(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;
    void update(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;
    boolean isExistCustomer(String id) throws SQLException, ClassNotFoundException;
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    String generateNewId() throws SQLException, ClassNotFoundException;
    CustomerDTO Search(String id) throws SQLException, ClassNotFoundException;
    List<String> loadAllCustomerIds() throws SQLException, ClassNotFoundException;
}


