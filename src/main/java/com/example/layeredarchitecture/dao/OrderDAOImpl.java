/*
 * Author  : Mr.electrix
 * Project : layered-architecture
 * Date    : 12/15/23

 */

package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
    ItemDAO itemDAO = new ItemDAOImpl();
    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT oid FROM Orders ORDER BY oid DESC LIMIT 1");

        return resultSet.next() ? String.format("OID-%03d", (Integer.parseInt(resultSet.getString("oid").replace("OID-", "")) + 1)) : "OID-001";
    }
    @Override
    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails){
        Connection connection = null;
        try {
            connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT oid FROM `Orders` WHERE oid=?");
            stm.setString(1, orderId);
            /*if order id already exist*/
            if (stm.executeQuery().next()) {

            }

            connection.setAutoCommit(false);
            stm = connection.prepareStatement("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)");
            stm.setString(1, orderId);
            stm.setDate(2, Date.valueOf(orderDate));
            stm.setString(3, customerId);

            if (stm.executeUpdate() != 1) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            boolean isOrderDetailSaved = orderDetailDAO.saveDetails(orderId,orderDetails);
            if (isOrderDetailSaved) {
                boolean isUpdated = itemDAO.updateItem(orderDetails);
                if (isUpdated) {
                    connection.commit();
                }
            }
            connection.rollback();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
