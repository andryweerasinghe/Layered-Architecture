/*
 * Author  : Mr.electrix
 * Project : layered-architecture
 * Date    : 12/15/23

 */

package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO{
    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT * FROM Customer");
        ArrayList<CustomerDTO> customerDTOArrayList = new ArrayList<>();

        while (resultSet.next()){
            CustomerDTO customerDTO = new CustomerDTO(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("address"));
            customerDTOArrayList.add(customerDTO);
        }
        return customerDTOArrayList;
    }
    @Override
    public boolean save(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Customer (id, name, address) VALUES (?,?,?)");
        preparedStatement.setString(1, customerDTO.getId());
        preparedStatement.setString(2, customerDTO.getName());
        preparedStatement.setString(3, customerDTO.getAddress());

        return preparedStatement.executeUpdate()> 0;

    }
    @Override
    public void update(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Customer SET name=?, address=? WHERE id=?");
        preparedStatement.setString(1, customerDTO.getName());
        preparedStatement.setString(2, customerDTO.getAddress());
        preparedStatement.setString(3, customerDTO.getId());

        preparedStatement.executeUpdate();
    }
    @Override
    public boolean isExistCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM Customer WHERE id=?");
        preparedStatement.setString(1, id);
        return preparedStatement.executeQuery().next();

    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Customer WHERE id=?");
        preparedStatement.setString(1, id);
        return preparedStatement.executeUpdate()>0;
    }
    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1");
        if (resultSet.next()) {
            String id = resultSet.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }
    public CustomerDTO Search(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Customer WHERE id=?");
        preparedStatement.setString(1, (id));
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return new CustomerDTO(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("address"));
        }
        return null;
    }
    public List<String> loadAllCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds = new ArrayList<>();
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Customer");

        while (resultSet.next()){
            String id = resultSet.getString("id");
            customerIds.add(id);
        }
        return customerIds;
    }
}
