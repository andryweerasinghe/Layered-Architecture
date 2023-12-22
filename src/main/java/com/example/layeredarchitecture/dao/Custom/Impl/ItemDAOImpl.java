/*
 * Author  : Mr.electrix
 * Project : layered-architecture
 * Date    : 12/15/23

 */

package com.example.layeredarchitecture.dao.Custom.Impl;

import com.example.layeredarchitecture.dao.Custom.ItemDAO;
import com.example.layeredarchitecture.dao.SQLUtil;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<ItemDTO> getAll() throws SQLException, ClassNotFoundException {
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
        return SQLUtil.execute("DELETE FROM Item WHERE code=?", code);
    }
    @Override
    public boolean update(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?", itemDTO.getDescription(), itemDTO.getUnitPrice(), itemDTO.getQtyOnHand(), itemDTO.getCode());
    }
    @Override
    public boolean isExist(String code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT code FROM Item WHERE code=?", code);
        return resultSet.next();
    }
    @Override
    public boolean save(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)", itemDTO.getCode(), itemDTO.getDescription(), itemDTO.getUnitPrice(), itemDTO.getQtyOnHand());
    }
    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT code FROM Item ORDER BY code DESC LIMIT 1");
        if (resultSet.next()){
            String code = resultSet.getString("code");
            int newItemId = Integer.parseInt(code.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }

    @Override
    public ItemDTO Search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ItemDTO findItem(String code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Item WHERE code=?", code);
        if (resultSet.next()){
            return new ItemDTO(resultSet.getString("code"), resultSet.getString("description"), resultSet.getBigDecimal("unitPrice"), resultSet.getInt("qtyOnHand"));
        }
        return null;
    }
    @Override
    public List<String> loadAllItemCodes() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Item");
        List<String> itemCodes = new ArrayList<>();

        while (resultSet.next()){
            String code = resultSet.getString("code");
            itemCodes.add(code);
        }
        return itemCodes;
    }
}
