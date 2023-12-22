/*
 * Author  : Mr.electrix
 * Project : layered-architecture
 * Date    : 12/22/23

 */

package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.dao.Custom.Impl.CustomerDAOImpl;
import com.example.layeredarchitecture.dao.Custom.Impl.ItemDAOImpl;
import com.example.layeredarchitecture.dao.Custom.Impl.OrderDAOImpl;
import com.example.layeredarchitecture.dao.Custom.Impl.OrderDetailDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){

    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory == null)?daoFactory=new DAOFactory():daoFactory;
    }
    public enum DAOTypes{
        CUSTOMER, ITEM, ORDER, ORDER_DETAIL
    }
    public CrudDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();
            default:
                return null;
        }
    }
}
