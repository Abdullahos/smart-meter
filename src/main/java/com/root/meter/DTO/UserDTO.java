package com.root.meter.DTO;


import javax.validation.constraints.NotNull;

public class UserDTO {
    private Long meterId;
    @NotNull(message="username can't be null")
    private String name;
    @NotNull(message="user's email can't be null")
    private String email;
    @NotNull(message="password can't be null")
    private String password;
    @NotNull(message="confirm Password can't be null")
    private String confirmPassword;
    @NotNull(message="user state can't be null")
    private String state;
    @NotNull(message="user phone can't be null")
    private String phone;
    @NotNull(message="user address can't be null")
    private String address;
    @NotNull(message="user's credit card number can't be null")
    private String creditCardNo;
    @NotNull(message="user's cvv can't be null")
    private String  cvv; //card verification value for payment online or over the phone.

    public Long getMeterId() {
        return meterId;
    }

    public void setMeterId(Long meterId) {
        this.meterId = meterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
