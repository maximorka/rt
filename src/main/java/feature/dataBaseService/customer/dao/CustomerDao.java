package feature.dataBaseService.customer.dao;

import feature.dataBaseService.customer.entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CustomerDao {
    private PreparedStatement createSt;
    private PreparedStatement getAllSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement getMaxIdSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement updateByIdSt;

    public CustomerDao(Connection connection) throws SQLException {

        createSt = connection.prepareStatement("INSERT INTO customer(name, address) VALUES(?,?)");
        getAllSt = connection.prepareStatement("Select name, address FROM customer");
        getByIdSt = connection.prepareStatement("Select name, address FROM customer WHERE id = ?");
        deleteByIdSt = connection.prepareStatement("DELETE FROM customer WHERE id = ? ");
        updateByIdSt = connection.prepareStatement("UPDATE customer SET name = ?, address = ? WHERE id = ?");

        getMaxIdSt = connection.prepareStatement("SELECT max(id) as maxId FROM customer");
    }

    public long createCustomer(Customer customer) throws SQLException {

        createSt.setString(1, customer.getName());
        createSt.setString(2, customer.getAddress());

        createSt.executeUpdate();

        long id;
        try (ResultSet resultSet = getMaxIdSt.executeQuery()) {
            resultSet.next();
            id = resultSet.getLong("maxId");
        }

        return id;

    }

    public Customer getCustomerById(long id) throws SQLException {

        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {

            if (!rs.next()) {
                return null;
            }
            Customer customer = new Customer();
            customer.setId(id);
            customer.setName(rs.getString("name"));
            customer.setAddress(rs.getString("address"));
            return customer;
        }
    }

    public List<Customer> getAllCustomers() throws SQLException {

        List<Customer> customers = new ArrayList<>();
        try (ResultSet rs = getAllSt.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customers.add(customer);
            }
            return customers;
        }
    }

    public boolean deleteById(long id) {
        try {
            deleteByIdSt.setLong(1, id);
            return deleteByIdSt.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    public boolean updateCustomer(Customer customer) {

        try {
            updateByIdSt.setLong(3, customer.getId());
            updateByIdSt.setString(1, customer.getName());
            updateByIdSt.setString(2, customer.getAddress());

            return updateByIdSt.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }
}
