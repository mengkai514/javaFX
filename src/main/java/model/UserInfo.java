package model;

import lombok.Data;
//@Data注脚需要lombok依赖，报错的话请更新依赖
@Data
public class UserInfo {
    public String userId;
    public String password;
    public String owner;
    public String accountType;
}

