package model;

import lombok.Data;
//@Dataע����Ҫlombok����������Ļ����������
@Data
public class UserInfo {
    public String userId;
    public String password;
    public String owner;
    public String accountType;
}

