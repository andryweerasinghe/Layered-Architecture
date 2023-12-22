package com.example.layeredarchitecture.dao.Custom;


import com.example.layeredarchitecture.dao.CrudDAO;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.OrderDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface OrderDAO extends CrudDAO<OrderDTO> {
//    String generateNewOrderId() throws SQLException, ClassNotFoundException;
//    boolean isExistOrder(String orderId) throws SQLException, ClassNotFoundException;
//    boolean saveOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;
}
