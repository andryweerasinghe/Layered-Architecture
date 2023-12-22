/*
 * Author  : Mr.electrix
 * Project : layered-architecture
 * Date    : 12/15/23

 */

package com.example.layeredarchitecture.dao.Custom.Impl;

import com.example.layeredarchitecture.dao.Custom.CustomerDAO;
import com.example.layeredarchitecture.dao.SQLUtil;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<CustomerDTO> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Customer");
        ArrayList<CustomerDTO> customerDTOArrayList = new ArrayList<>();

        while (resultSet.next()){
            CustomerDTO customerDTO = new CustomerDTO(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("address"));
            customerDTOArrayList.add(customerDTO);
        }
        return customerDTOArrayList;
    }
    @Override
    public boolean save(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Customer (id, name, address) VALUES (?,?,?)", customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress());
    }
    @Override
    public boolean update(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Customer SET name=?, address=? WHERE id=?", customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress());
    }
    @Override
    public boolean isExist(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT id FROM Customer WHERE id=?", id);
        return resultSet.next();
    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Customer WHERE id=?", id);
    }
    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT id FROM Customer ORDER BY id DESC LIMIT 1");
        if (resultSet.next()){
            String id = resultSet.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }
    public CustomerDTO Search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Customer WHERE id=?", id);
        if (resultSet.next()){
            return new CustomerDTO(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("address"));
        }
        return null;
    }
    public List<String> loadAllCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Customer");
        List<String> customerIds = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString("id");
            customerIds.add(id);
        }
        return customerIds;
    }
}
