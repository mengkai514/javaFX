package model;

import lombok.Data;

@Data
public class UserInfo {
    public String userId;
    public String password;
    public String owner;
    public String accountType;
}

