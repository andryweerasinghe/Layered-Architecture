package com.example.layeredarchitecture.bo.Custom;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface PlaceOrderBo {
    boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException;
    String generateNewOrderId() throws SQLException, ClassNotFoundException;
    boolean isExistCustomer(String customerId) throws SQLException, ClassNotFoundException;
    CustomerDTO searchCustomer(String customerId) throws SQLException, ClassNotFoundException;
    boolean isExistItem(String itemCode) throws SQLException, ClassNotFoundException;
    ItemDTO searchItem(String itemCode) throws SQLException, ClassNotFoundException;
    List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException;
    List<String> getAllItemCodes() throws SQLException, ClassNotFoundException;
}
