package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface ItemDAO {
    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;
    boolean delete(String code) throws SQLException, ClassNotFoundException;
    boolean update(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    boolean isExistItem(String code) throws SQLException, ClassNotFoundException;
    boolean save(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    String generateNewId() throws SQLException, ClassNotFoundException;
    ItemDTO findItem(String code) throws SQLException, ClassNotFoundException;
    List<String> loadAllItemCodes() throws SQLException, ClassNotFoundException;
    boolean updateItem(List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException;
    boolean updateItems(OrderDetailDTO dto) throws SQLException, ClassNotFoundException;
}
