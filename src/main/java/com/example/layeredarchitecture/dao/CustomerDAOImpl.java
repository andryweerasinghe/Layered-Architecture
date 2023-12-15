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

public class CustomerDAOImpl {
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
    public void save(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Customer (id, name, address) VALUES (?,?,?)");
        preparedStatement.setString(1, customerDTO.getId());
        preparedStatement.setString(2, customerDTO.getName());
        preparedStatement.setString(3, customerDTO.getAddress());

        preparedStatement.executeUpdate();
    }
}
