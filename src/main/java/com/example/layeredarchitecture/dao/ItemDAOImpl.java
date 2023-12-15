/*
 * Author  : Mr.electrix
 * Project : layered-architecture
 * Date    : 12/15/23

 */

package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;
import com.example.layeredarchitecture.model.ItemDTO;

import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO{
    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Item");
        ArrayList<ItemDTO> itemDTOArrayList = new ArrayList<>();

        while (resultSet.next()){
            ItemDTO itemDTO = new ItemDTO(resultSet.getString("code"), resultSet.getString("description"), resultSet.getBigDecimal("unitPrice"), resultSet.getInt("qtyOnHand"));
            itemDTOArrayList.add(itemDTO);
        }
        return itemDTOArrayList;
    }
    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Item WHERE code=?");
        preparedStatement.setString(1, code);
        return preparedStatement.executeUpdate()>0;
    }
    @Override
    public boolean update(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
        preparedStatement.setString(1, itemDTO.getDescription());
        preparedStatement.setBigDecimal(2, itemDTO.getUnitPrice());
        preparedStatement.setInt(3, itemDTO.getQtyOnHand());
        preparedStatement.setString(4, itemDTO.getCode());
        return preparedStatement.executeUpdate()>0;
    }
    @Override
    public boolean isExistItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT code FROM Item WHERE code=?");
        preparedStatement.setString(1, code);
        return preparedStatement.executeQuery().next();

    }
    @Override
    public boolean save(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)");
        preparedStatement.setString(1, itemDTO.getCode());
        preparedStatement.setString(2, itemDTO.getDescription());
        preparedStatement.setBigDecimal(3, itemDTO.getUnitPrice());
        preparedStatement.setInt(4, itemDTO.getQtyOnHand());
        return preparedStatement.executeUpdate()>0;
    }
    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1");
        if (resultSet.next()){
            String code = resultSet.getString("code");
            int newItemId = Integer.parseInt(code.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }
    @Override
    public ItemDTO findItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Item WHERE code=?");
        preparedStatement.setString(1, code);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return new ItemDTO(resultSet.getString("code"), resultSet.getString("description"), resultSet.getBigDecimal("unitPrice"), resultSet.getInt("qtyOnHand"));
        }
        return null;
    }
}
