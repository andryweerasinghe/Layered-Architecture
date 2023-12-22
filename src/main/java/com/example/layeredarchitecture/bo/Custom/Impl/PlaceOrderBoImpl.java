/*
 * Author  : Mr.electrix
 * Project : layered-architecture
 * Date    : 12/22/23

 */

package com.example.layeredarchitecture.bo.Custom.Impl;

import com.example.layeredarchitecture.bo.Custom.PlaceOrderBo;
import com.example.layeredarchitecture.dao.Custom.CustomerDAO;
import com.example.layeredarchitecture.dao.Custom.ItemDAO;
import com.example.layeredarchitecture.dao.Custom.OrderDAO;
import com.example.layeredarchitecture.dao.Custom.OrderDetailDAO;
import com.example.layeredarchitecture.dao.Custom.Impl.CustomerDAOImpl;
import com.example.layeredarchitecture.dao.Custom.Impl.ItemDAOImpl;
import com.example.layeredarchitecture.dao.Custom.Impl.OrderDAOImpl;
import com.example.layeredarchitecture.dao.Custom.Impl.OrderDetailDAOImpl;
import com.example.layeredarchitecture.dao.DAOFactory;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlaceOrderBoImpl implements PlaceOrderBo {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        /*Transaction*/
        Connection connection = null;
        connection = DBConnection.getDbConnection().getConnection();
        //Check order id already exist or not
        boolean b1 = orderDAO.isExist(orderId);
        /*if order id already exist*/
        if (b1) {
            return false;
        }
        connection.setAutoCommit(false);
        //Save the Order to the order table
        System.out.println(orderId);
        boolean b2 = orderDAO.save(new OrderDTO(orderId, orderDate, customerId));
        if (!b2) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        // add data to the Order Details table
        for (OrderDetailDTO detail : orderDetails) {
            System.out.println(detail);
            boolean b3 = orderDetailDAO.saveOrderDetails(orderId, detail);
            if (!b3) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            //Search & Update Item
            ItemDTO item = itemDAO.findItem(detail.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());
            //update item
            boolean b = itemDAO.update(new ItemDTO(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
            if (!b) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewId();
    }
    public boolean isExistCustomer(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.isExist(customerId);
    }
    public CustomerDTO searchCustomer(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.Search(customerId);
    }
    public boolean isExistItem(String itemCode) throws SQLException, ClassNotFoundException {
        return itemDAO.isExist(itemCode);
    }
    public ItemDTO searchItem(String itemCode) throws SQLException, ClassNotFoundException {
        return itemDAO.findItem(itemCode);
    }
    public List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        return customerDAO.loadAllCustomerIds();
    }
    public List<String> getAllItemCodes() throws SQLException, ClassNotFoundException {
        return itemDAO.loadAllItemCodes();
    }
}
